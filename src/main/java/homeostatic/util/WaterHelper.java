package homeostatic.util;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.Hydration;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;
import homeostatic.network.WaterData;

public class WaterHelper {

    public static void updateWaterInfo(ServerPlayer sp, float sweatLevel) {
        sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            WaterInfo waterInfo = new WaterInfo(
                data.getWaterLevel(),
                data.getWaterSaturationLevel(),
                data.getWaterExhaustionLevel()
            );

            waterInfo.update(sweatLevel);
            data.setWaterData(waterInfo);

            NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> sp),
                new WaterData(waterInfo)
            );
        });
    }

    public static void drink(ServerPlayer sp, ResourceLocation item, @Nullable ResourceLocation fluid) {
        drink(sp, item, fluid, true);
    }

    public static void drink(ServerPlayer sp, ResourceLocation item, @Nullable ResourceLocation fluid, boolean update) {
        DrinkableItem drinkableItem = DrinkableItemManager.get(item);
        DrinkingFluid drinkingFluid = fluid != null ? DrinkingFluidManager.get(fluid) : null;
        Hydration hydration = null;

        if (drinkingFluid != null) {
            hydration = DrinkingFluid.getHydration(drinkingFluid);
        }
        else if (drinkableItem != null) {
            hydration = DrinkableItem.getHydration(drinkableItem);
        }

        if (hydration != null) {
            Hydration finalHydration = hydration;

            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                boolean isDirty = finalHydration.duration() > 0;

                data.increaseWaterLevel(finalHydration.amount());
                data.increaseSaturationLevel(finalHydration.saturation());

                if (isDirty && Homeostatic.RANDOM.nextFloat() < finalHydration.chance()) {
                    if (!sp.hasEffect(HomeostaticEffects.THIRST.get())) {
                        sp.addEffect(new MobEffectInstance(
                                HomeostaticEffects.THIRST.get(),
                                finalHydration.duration(),
                                finalHydration.potency(),
                                false, false, false));
                    }
                }

                if (update) {
                    NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new WaterData(new WaterInfo(
                            data.getWaterLevel(),
                            data.getWaterSaturationLevel(),
                            data.getWaterExhaustionLevel()
                        ))
                    );
                }
            });
        }
    }

    public static void drinkWater(ServerPlayer sp) {
        drinkDirtyWaterItem(sp, false);
    }

    public static void drinkCleanWaterItem(ServerPlayer sp, boolean update) {
        ResourceLocation air = new ResourceLocation("minecraft", "air");
        ResourceLocation water = new ResourceLocation(Homeostatic.MODID, "purified_water");

        drink(sp, air, water, update);
    }

    public static void drinkDirtyWaterItem(ServerPlayer sp, boolean update) {
        ResourceLocation air = new ResourceLocation("minecraft", "air");
        ResourceLocation water = new ResourceLocation("minecraft", "water");

        drink(sp, air, water, update);
    }

}
