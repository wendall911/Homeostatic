package homeostatic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.common.water.WaterInfo;
import homeostatic.config.ConfigHandler;
import homeostatic.network.Temperature;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;
import homeostatic.util.WetnessHelper;

public class PlayerEventHandler {

    public static void onEntityJoinLevel(ServerPlayer sp) {
        ServerLevel world = sp.serverLevel();

        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        WaterHelper.updateWaterInfo(sp, 0.0F);
        WetnessHelper.updateWetnessInfo(sp, 0.0F, true);

        Services.PLATFORM.getTemperatureData(sp).ifPresent(data -> {
            Vec3 spPos = sp.getEyePosition(1.0F);
            BlockPos pos = new BlockPos((int)spPos.x(), (int)spPos.y(), (int)spPos.z());
            Holder<Biome> biome = world.getBiome(pos);
            EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
            BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, data);

            Services.PLATFORM.syncTemperatureData(sp, environmentData, bodyTemperature);
        });

    }

    public static void onPlayerTickEvent(ServerPlayer sp) {
        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        ServerLevel world = sp.serverLevel();
        ProfilerFiller profilerfiller = world.getProfiler();

        Services.PLATFORM.getWaterCapabilty(sp).ifPresent(data -> data.checkWaterLevel(sp));

        profilerfiller.push("tempCalc");
        Services.PLATFORM.getTemperatureData(sp).ifPresent(data -> {
            if (sp.tickCount % 2 == 0) {
                data.checkTemperatureLevel(sp);
            }

            if (sp.tickCount % 16 == 0 || sp.tickCount % 60 == 0) {
                Vec3 spPos = sp.getEyePosition(1.0F);
                BlockPos pos = new BlockPos((int)spPos.x(), (int)spPos.y(), (int)spPos.z());
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                boolean updateCore = sp.tickCount % 60 == 0;
                BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, data, updateCore, true);

                data.setTemperatureData(environmentData.getLocalTemperature(), bodyTemperature);

                Services.PLATFORM.syncTemperatureData(sp, environmentData, bodyTemperature);
            }

        });
        profilerfiller.pop();
    }

    public static void onPlayerRespawn(ServerPlayer sp) {
        ServerLevel world = sp.serverLevel();

        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        Services.PLATFORM.getWaterCapabilty(sp).ifPresent(data -> {
            WaterInfo waterInfo = new WaterInfo(
                WaterInfo.MAX_WATER_LEVEL / 2,
                WaterInfo.MAX_SATURATION_LEVEL / 2.0F,
                0.0F
            );

            data.setWaterData(waterInfo);

            Services.PLATFORM.syncWaterData(sp, waterInfo);
        });

        Services.PLATFORM.getTemperatureData(sp).ifPresent(data -> {
            Vec3 spPos = sp.getEyePosition(1.0F);
            BlockPos pos = new BlockPos((int)spPos.x(), (int)spPos.y(), (int)spPos.z());
            Holder<Biome> biome = world.getBiome(pos);
            EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
            // Need to do a new Temperature here, as NeoForge isn't honoring not copying data on death
            BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, new Temperature());

            data.setTemperatureData(environmentData.getLocalTemperature(), bodyTemperature);

            Services.PLATFORM.syncTemperatureData(sp, environmentData, bodyTemperature);
        });
    }

    public static void onEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack previousItem, ItemStack equippedItem) {
        if (ConfigHandler.Common.showTemperatureValues() && ConfigHandler.Common.requireThermometer() && slot == EquipmentSlot.HEAD) {
            if (entity instanceof Player player && !player.level().isClientSide) {
                ServerPlayer sp = (ServerPlayer) player;

                Services.PLATFORM.getThermometerCapability(sp).ifPresent(data -> {
                    boolean equippedHasThermometer = hasThermometer(equippedItem);
                    ThermometerInfo info = new ThermometerInfo(equippedHasThermometer);

                    data.setHasThermometer(equippedHasThermometer);

                    Services.PLATFORM.syncThermometerData(sp, info);
                });
            }
        }
    }

    private static boolean hasThermometer(ItemStack helmet) {
        CompoundTag tag = helmet.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();

        return tag.contains("thermometer");
    }

}