package homeostatic.common.item;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import homeostatic.platform.Services;

public class PurifiedWaterBottle extends Item {

    public PurifiedWaterBottle(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if (canDrink(player)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof ServerPlayer sp) {
            CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
        }

        stack.setCount(stack.getCount() - 1);

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
