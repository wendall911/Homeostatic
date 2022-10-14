package homeostatic.common.item;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.util.WaterHelper;

public class WaterContainerItem extends ItemFluidContainer {

    public WaterContainerItem(Properties properties, int capacity) {
        super(properties.durability(capacity), capacity);
    }

    @Override
    public @Nonnull InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        Fluid targetFluid = blockState.getFluidState().getType();
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);
        FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
        boolean isEmpty = fluidStack.isEmpty();

        if ((isEmpty || fluidStack.getFluid().isSame(targetFluid)) && fluidStack.getAmount() != capacity) {
            if (targetFluid == Fluids.WATER) {
                return InteractionResultHolder.success(getFilledItem(stack, player, targetFluid, capacity));
            }
            else if (WaterHelper.getFluidHydration(targetFluid) != null) {
                if (blockState.getBlock() instanceof BucketPickup bucketPickup) {
                    if (!bucketPickup.pickupBlock(level, pos, blockState).isEmpty()) {
                        return InteractionResultHolder.success(getFilledItem(stack, player, targetFluid, capacity));
                    }
                }
            }
        }

        if (!isEmpty && canDrink(player, fluidStack)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public @Nonnull ItemStack finishUsingItem(ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

        fluidHandlerItem.drain(250, IFluidHandler.FluidAction.EXECUTE);
        if (entity instanceof Player player) {
            if (fluidHandlerItem.getFluidInTank(0).isEmpty()) {
                return getCraftingRemainingItem(stack);
            }
            else {
                WaterHelper.updateDamage(stack);
            }
        }

        return stack;
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag tag) {
        return new FluidHandlerItem(stack, capacity);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> components, @Nonnull TooltipFlag tooltipFlag) {
        Component textComponent = Component.translatable("tooltip.water_container.empty");
        int amount = 0;

        super.appendHoverText(stack, level, components, tooltipFlag);

        if (stack.getTagElement(FLUID_NBT_KEY) != null) {
            IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(stack).orElse(null);

            textComponent = Component.translatable(fluidHandlerItem.getFluidInTank(0).getDisplayName().getString());
            amount = fluidHandlerItem.getFluidInTank(0).getAmount();
        }

        components.add(Component.translatable(textComponent.getString() + String.format(": %d uses.", amount / 250))
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 32;
    }

    @Override
    public @Nonnull UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.DRINK;
    }

    public ItemStack getFilledItem(ItemStack stack, Player player, Fluid fluid, int amount) {
        ItemStack copy = WaterHelper.getFilledItem(stack, fluid, amount);

        player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));

        return copy;
    }

    public boolean canDrink(Player player, FluidStack fluidStack) {
        AtomicBoolean canDrink = new AtomicBoolean(WaterHelper.getFluidHydration(fluidStack.getFluid()) != null && fluidStack.getAmount() >= 250);

        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            canDrink.set(canDrink.get() && data.getWaterLevel() < 20);
        });

        return canDrink.get();
    }

}