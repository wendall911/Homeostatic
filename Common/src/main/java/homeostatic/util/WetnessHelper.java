package homeostatic.util;

import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;

import homeostatic.common.TagManager;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.platform.Services;

public class WetnessHelper {

    public static void updateWetnessInfo(ServerPlayer sp, float moistureLevel, boolean increase) {
        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        Services.PLATFORM.getWetnessCapability(sp).ifPresent(data -> {
            WetnessInfo wetnessInfo = new WetnessInfo(
                data.getWetnessLevel(),
                data.getMoistureLevel()
            );
            AtomicInteger waterproofing = new AtomicInteger();

            sp.getArmorSlots().forEach(armor -> {
                CompoundTag tags = armor.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

                if (tags.contains("waterproof") || armor.is(TagManager.Items.WATERPROOF_ARMOR)) {
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

            Services.PLATFORM.syncWetnessData(sp, wetnessInfo);
        });
    }

    public static int getWetness(ServerPlayer sp) {
        AtomicInteger wetness = new AtomicInteger();

        Services.PLATFORM.getWetnessCapability(sp).ifPresent(data -> {
            wetness.set(data.getWetnessLevel());
        });

        return wetness.get();
    }

}