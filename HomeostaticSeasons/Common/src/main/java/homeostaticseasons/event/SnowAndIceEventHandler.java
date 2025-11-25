package homeostaticseasons.event;

import it.unimi.dsi.fastutil.longs.LongArraySet;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

import homeostaticseasons.common.block.PlacedMeltablesSavedData;
import homeostaticseasons.common.block.ReplacedMeltablesSavedData;

public class SnowAndIceEventHandler {

    private static final LongArraySet meltableCache = new LongArraySet();

    public static void onEndServerTick() {
        meltableCache.clear();
    }

    public static void cacheMeltableBlock(BlockPos pos) {
        meltableCache.add(pos.asLong());
    }

    public static boolean isCachedMeltableBlock(BlockPos pos) {
        return meltableCache.contains(pos.asLong());
    }

    public static PlacedMeltablesSavedData getPlacedMeltablesSavedData(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();

        return storage.computeIfAbsent(PlacedMeltablesSavedData.getFactory(), "homeostaticseasons_placed_meltables");
    }

    public static ReplacedMeltablesSavedData getReplacedMeltablesSavedData(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();

        return storage.computeIfAbsent(ReplacedMeltablesSavedData.getFactory(), "homeostaticseasons_replaced_meltables");
    }

}
