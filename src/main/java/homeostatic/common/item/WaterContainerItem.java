package homeostatic.common.item;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import homeostatic.common.fluid.HomeostaticFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.util.WaterHelper;

public class WaterContainerItem extends ItemFluidContainer {

    boolean canDrink = false;

    public WaterContainerItem(Properties properties, int capacity) {
        super(properties.durability(capacity), capacity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos pos = hitResult.getBlockPos();
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

        if (fluidHandlerItem.getFluidInTank(0).isEmpty() || fluidHandlerItem.getFluidInTank(0).getFluid().isSame(Fluids.WATER)) {
            if (level.getFluidState(pos).getType() == Fluids.WATER) {
                return InteractionResultHolder.success(getFilledItem(stack, player));
            }
        }

        if (canDrink(stack, player)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

        fluidHandlerItem.drain(250, IFluidHandler.FluidAction.EXECUTE);
        if (entity instanceof Player player) {
            if (fluidHandlerItem.getFluidInTank(0).isEmpty()) {
                return getCraftingRemainingItem(stack);
            }
            else {
                updateDamage(stack);

                if (!player.level.isClientSide) {
                    if (fluidHandlerItem.getFluidInTank(0).getFluid() == Fluids.WATER) {
                        WaterHelper.drinkWater((ServerPlayer) player, true, true);
                    }
                    else if (fluidHandlerItem.getFluidInTank(0).getFluid() == HomeostaticFluids.PURIFIED_WATER) {
                        WaterHelper.drinkWater((ServerPlayer) player, false, true);
                    }
                }
            }
        }

        return stack;
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag tag) {
        return new FluidHandlerItemStack.SwapEmpty(stack, stack.getCraftingRemainingItem(), this.capacity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        Component textComponent = Component.translatable("tooltip.water_container.empty");
        int amount = 0;

        super.appendHoverText(stack, level, components, tooltipFlag);

        if (stack.getTagElement(FLUID_NBT_KEY) != null) {
            IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(stack).orElse(null);

            textComponent = Component.translatable(fluidHandlerItem.getFluidInTank(0).getDisplayName().getString());
            amount = fluidHandlerItem.getFluidInTank(0).getAmount();
        }

        components.add(Component.translatable(textComponent.getString() + String.format(": %d uses.", amount / 250)).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public int getUseDuration(ItemStack stack) {
        return canDrink ? 32 : 0;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public ItemStack getFilledItem(ItemStack stack, Player player) {
        ItemStack copy = stack.copy();
        IFluidHandlerItem fluidHandlerItem = copy.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

        player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));

        fluidHandlerItem.fill(new FluidStack(Fluids.WATER, capacity), IFluidHandler.FluidAction.EXECUTE);
        updateDamage(copy);

        return copy;
    }

    public void updateDamage(ItemStack stack) {
        if (stack.isDamageableItem()) {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

            stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getMaxDamage() - fluidHandlerItem.getFluidInTank(0).getAmount()));
        }
    }

    public boolean canDrink(ItemStack stack, Player player) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

        canDrink = !fluidHandlerItem.getFluidInTank(0).isEmpty() && fluidHandlerItem.getFluidInTank(0).getAmount() >= 250;

        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            canDrink = canDrink && data.getWaterLevel() < 20;
        });

        return canDrink;
    }

}
