package homeostatic.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.water.WaterInfo;
import homeostatic.config.ConfigHandler;
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

    public static void drinkWater(ServerPlayer sp, boolean isDirty, boolean update) {
        sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {


            if (isDirty) {
                data.increaseWaterLevel();
            }
            else {
                data.increaseCleanWaterLevel();
                data.increaseSaturationLevel();
            }

            if (isDirty && Homeostatic.RANDOM.nextFloat() < ConfigHandler.Server.effectChance()) {
                if (!sp.hasEffect(HomeostaticEffects.THIRST.get())) {
                    sp.addEffect(new MobEffectInstance(
                            HomeostaticEffects.THIRST.get(),
                            ConfigHandler.Server.effectDuration(),
                            ConfigHandler.Server.effectPotency(),
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
