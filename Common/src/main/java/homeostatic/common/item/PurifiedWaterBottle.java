package homeostatic.common.item;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import homeostatic.platform.Services;

public class PurifiedWaterBottle extends Item {

    public PurifiedWaterBottle(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {
        return getDrinkingSound();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (canDrink(player)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        }

        stack.setCount(stack.getCount() - 1);

        if (player == null || !player.getAbilities().instabuild) {
            if (stack.isEmpty() && getCraftingRemainingItem() != null) {
                return new ItemStack(getCraftingRemainingItem());
            }

            if (player != null && getCraftingRemainingItem() != null) {
                if (!player.getInventory().add(new ItemStack(getCraftingRemainingItem()))) {
                    player.drop(new ItemStack(getCraftingRemainingItem()), true);
                }
            }
        }

        return stack;
    }

    private boolean canDrink(Player player) {
        AtomicBoolean canDrink = new AtomicBoolean(false);

        Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
            canDrink.set(data.getWaterLevel() < 20);
        });

        return canDrink.get();
    }

}
