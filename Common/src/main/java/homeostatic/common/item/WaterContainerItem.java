package homeostatic.common.item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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

import homeostatic.common.fluid.FluidInfo;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;

public class WaterContainerItem extends Item implements IItemStackFluid {

    protected final int capacity;

    public WaterContainerItem(Properties properties, int capacity) {
        super(properties.durability(capacity));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSingleUse() {
        return capacity / 20;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        Fluid targetFluid = blockState.getFluidState().getType();
        Optional<FluidInfo> fluidInfoOptional = Services.PLATFORM.getFluidInfo(stack);

        if (fluidInfoOptional.isEmpty()) return InteractionResultHolder.fail(player.getItemInHand(hand));

        FluidInfo fluidInfo = fluidInfoOptional.get();
        boolean isEmpty = fluidInfo.isEmpty();

        if ((isEmpty || fluidInfo.fluid().isSame(targetFluid)) && fluidInfo.amount() != capacity) {
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

        if (!isEmpty && canDrink(player, fluidInfo)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        return Services.PLATFORM.drainFluid(stack, getCapacity() / 20L);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        Component textComponent = Component.translatable("tooltip.water_container.empty").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, components, tooltipFlag);

        Optional<FluidInfo> fluidInfoOptional = Services.PLATFORM.getFluidInfo(stack);

        if (fluidInfoOptional.isPresent() && fluidInfoOptional.get().amount() > 0L) {
            textComponent = Services.PLATFORM.getDisplayName(fluidInfoOptional.get().fluid());
            long amount = fluidInfoOptional.get().amount();

            components.add(Component.translatable(textComponent.getString() + String.format(": %d uses.", amount / getSingleUse()))
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        }
        else {
            components.add(textComponent);
        }
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    public ItemStack getFilledItem(ItemStack stack, Player player, Fluid fluid, int amount) {
        ItemStack copy = WaterHelper.getFilledItem(stack, fluid, amount);

        player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));

        return copy;
    }

    public boolean canDrink(Player player, FluidInfo fluidInfo) {
        AtomicBoolean canDrink = new AtomicBoolean(WaterHelper.getFluidHydration(fluidInfo.fluid()) != null && fluidInfo.amount() >= getSingleUse());

        Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
            canDrink.set(canDrink.get() && data.getWaterLevel() < 20);
        });

        return canDrink.get();
    }

}