package homeostatic.common.item;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jspecify.annotations.NonNull;

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
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        if (canDrink(player)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResult.FAIL;
    }

    @Override
    public @NonNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity) {
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
