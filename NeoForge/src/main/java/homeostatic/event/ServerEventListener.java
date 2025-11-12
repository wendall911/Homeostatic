package homeostatic.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import homeostatic.common.block.BlockRadiationManager;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.util.WaterHelper;

public class ServerEventListener {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            PlayerEventHandler.onEntityJoinLevel((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(PlayerTickEvent.Pre event) {
        if (event.getEntity() instanceof FakePlayer) return;

        if (!event.getEntity().level().isClientSide()) {
            PlayerEventHandler.onPlayerTickEvent((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        final Player player = event.getEntity() != null ? event.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            PlayerEventHandler.onPlayerRespawn((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            ItemStack stack = event.getItem();
            ServerPlayer sp = (ServerPlayer) player;

            WaterHelper.drink(sp, stack, true);
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        PlayerEventHandler.onEquipmentChange(event.getEntity(), event.getSlot(), event.getFrom(), event.getTo());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(new BlockRadiationManager());
        event.addListener(new DrinkingFluidManager());
        event.addListener(new DrinkableItemManager());
    }

}
