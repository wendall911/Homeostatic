package homeostatic.util;

import java.util.concurrent.atomic.AtomicInteger;

import homeostatic.common.TagManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.level.GameType;
import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.network.NetworkHandler;
import homeostatic.network.WetnessData;

public class WetnessHelper {

    public static void updateWetnessInfo(ServerPlayer sp, float moistureLevel, boolean increase) {
        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        sp.getCapability(CapabilityRegistry.WETNESS_CAPABILITY).ifPresent(data -> {
            WetnessInfo wetnessInfo = new WetnessInfo(
                data.getWetnessLevel(),
                data.getMoistureLevel()
            );
            AtomicInteger waterproofing = new AtomicInteger();

            sp.getArmorSlots().forEach(armor -> {
                CompoundTag tags = armor.getTag();

                if ((tags != null && tags.contains("waterproof")) || armor.is(TagManager.Items.WATERPROOF_ARMOR)) {
                    waterproofing.addAndGet(5);
                }
            });

            if (increase) {
                wetnessInfo.increaseMoisture(moistureLevel, waterproofing.get());
            }
            else {
                wetnessInfo.decreaseMoisture(moistureLevel);
            }

            data.setWetnessData(wetnessInfo);

            NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> sp),
                new WetnessData(wetnessInfo)
            );
        });
    }

    public static int getWetness(ServerPlayer sp) {
        AtomicInteger wetness = new AtomicInteger();

        sp.getCapability(CapabilityRegistry.WETNESS_CAPABILITY).ifPresent(data -> {
            wetness.set(data.getWetnessLevel());
        });

        return wetness.get();
    }

}