package homeostatic.event;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import homeostatic.common.biome.BiomeCategoryManager;
import homeostatic.common.block.BlockRadiationManager;
import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.platform.Services;
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
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof FakePlayer) return;
        if (event.phase != TickEvent.Phase.START) return;

        if (!event.player.level().isClientSide()) {
            PlayerEventHandler.onPlayerTickEvent((ServerPlayer) event.player);
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
        event.addListener(new BiomeCategoryManager());
        event.addListener(new BlockRadiationManager());
        event.addListener(new DrinkingFluidManager());
        event.addListener(new DrinkableItemManager());
    }

    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide && event.getItem().getItem() instanceof LeatherFlask) {
                ItemStack holding = event.getItem();
                int holdingCapacity = (int) Services.PLATFORM.getFluidCapacity(holding);
                FluidInfo fluidInfo = Services.PLATFORM.getFluidInfo(holding).get();

                if (holdingCapacity != LeatherFlask.LEATHER_FLASK_CAPACITY || fluidInfo.amount() > LeatherFlask.LEATHER_FLASK_CAPACITY ) {
                    ItemStack mainHandItem = player.getMainHandItem();
                    ItemStack offhandItem = player.getOffhandItem();

                    if (mainHandItem.getItem() instanceof LeatherFlask) {
                        player.setItemSlot(EquipmentSlot.MAINHAND, stackFixerUpper(mainHandItem, new ItemStack(HomeostaticItems.LEATHER_FLASK)));
                    }
                    if (offhandItem.getItem() instanceof LeatherFlask) {
                        player.setItemSlot(EquipmentSlot.MAINHAND, stackFixerUpper(offhandItem, new ItemStack(HomeostaticItems.LEATHER_FLASK)));
                    }
                }
            }
        }
    }

    /*
     * Temporary method for 1.20.1. Changed stack size from 5000 to 1000 to match buckets
     */
    @Deprecated
    private static ItemStack stackFixerUpper(ItemStack original, ItemStack replacement) {
        AtomicInteger currentAmount = new AtomicInteger(0);
        AtomicReference<Fluid> currentFluid = new AtomicReference<>(Fluids.WATER);

        original.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).ifPresent(handler -> {
            currentFluid.set(handler.getFluidInTank(0).getFluid());
            currentAmount.set(handler.getTankCapacity(0));
        });

        return WaterHelper.getFilledItem(replacement, currentFluid.get(), currentAmount.get() / 5);
    }

}
