package homeostaticseasons.common.block;

import org.jetbrains.annotations.NotNull;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.LongArraySet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import homeostaticseasons.HomeostaticSeasons;

public class PlacedMeltablesSavedData extends SavedData {

    Long2ObjectArrayMap<LongArraySet> chunkToPlacedMeltablesMap = new Long2ObjectArrayMap<>();

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        chunkToPlacedMeltablesMap.long2ObjectEntrySet().fastForEach(entry -> {
            if (!entry.getValue().isEmpty()) {
                compoundTag.put(entry.getLongKey() + "", new LongArrayTag(entry.getValue().toLongArray()));
            }
        });

        return compoundTag;
    }

    public boolean isManuallyPlaced(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        LongArraySet placedMeltablesInChunk = chunkToPlacedMeltablesMap.get(chunkPos.toLong());

        return placedMeltablesInChunk != null && placedMeltablesInChunk.contains(pos.asLong());
    }

    public void setManuallyPlaced(BlockPos pos, Boolean manuallyPlaced) {
        ChunkPos chunkPos = new ChunkPos(pos);
        long chunkKey = chunkPos.toLong();
        LongArraySet placedMeltablesInChunk = chunkToPlacedMeltablesMap.get(chunkKey);

        if (placedMeltablesInChunk != null) {
            if (manuallyPlaced) {
                placedMeltablesInChunk.add(pos.asLong());
            }
            else {
                placedMeltablesInChunk.remove(pos.asLong());
                if (placedMeltablesInChunk.isEmpty()) {
                    chunkToPlacedMeltablesMap.remove(chunkKey);
                }
            }
        }
        else if (manuallyPlaced) {
            LongArraySet newSet = new LongArraySet();

            newSet.add(pos.asLong());
            chunkToPlacedMeltablesMap.put(chunkKey, newSet);
        }

        setDirty();
    }

    public static PlacedMeltablesSavedData createFromCompoundTag(CompoundTag compoundTag, Provider provider) {
        PlacedMeltablesSavedData savedData = new PlacedMeltablesSavedData();

        compoundTag.getAllKeys().forEach(key -> {
            // Load data from compoundTag if needed
            try {
                long longKey = Long.parseLong(key);
                long[] longArray = compoundTag.getLongArray(key);

                savedData.chunkToPlacedMeltablesMap.put(longKey, new LongArraySet(longArray));
            }
            catch (NumberFormatException e) {
                HomeostaticSeasons.LOGGER.error("Error loading PlacedMeltablesSavedData key: {}", key, e);
            }
        });

        return savedData;
    }

    public static SavedData.Factory<PlacedMeltablesSavedData> getFactory() {
        return new SavedData.Factory<>(PlacedMeltablesSavedData::new, PlacedMeltablesSavedData::createFromCompoundTag, null);
    }

}
