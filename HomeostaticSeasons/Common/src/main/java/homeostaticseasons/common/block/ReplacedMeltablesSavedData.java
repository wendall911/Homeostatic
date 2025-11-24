package homeostaticseasons.common.block;

import org.jetbrains.annotations.NotNull;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;

import homeostaticseasons.HomeostaticSeasons;

public class ReplacedMeltablesSavedData extends SavedData {

    Long2ObjectArrayMap<Long2ObjectArrayMap<BlockState>> cunkToReplacedMeltablesMap = new Long2ObjectArrayMap<>();

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        cunkToReplacedMeltablesMap.long2ObjectEntrySet().fastForEach(entry -> {
            if (!entry.getValue().isEmpty()) {
                CompoundTag innerTag = new CompoundTag();

                entry.getValue().long2ObjectEntrySet().fastForEach(innerEntry -> {
                    BlockState.CODEC.encode(innerEntry.getValue(), NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).result().ifPresent(element -> {
                        innerTag.put(innerEntry.getLongKey() + "", element);
                    });
                });

                compoundTag.put(entry.getLongKey() + "", innerTag);
            }
        });

        return compoundTag;
    }

    public BlockState getReplaced(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        Long2ObjectArrayMap<BlockState> replacedMeltablesInChunk = cunkToReplacedMeltablesMap.get(chunkPos.toLong());

        return replacedMeltablesInChunk != null ? replacedMeltablesInChunk.get(pos.asLong()) : null;
    }

    public void setReplaced(BlockPos pos, BlockState replacedState) {
        ChunkPos chunkPos = new ChunkPos(pos);
        long chunkKey = chunkPos.toLong();
        Long2ObjectArrayMap<BlockState> replacedMeltablesInChunk = cunkToReplacedMeltablesMap.get(chunkKey);

        if (replacedMeltablesInChunk != null) {
            if (replacedState != null) {
                replacedMeltablesInChunk.put(pos.asLong(), replacedState);
            }
            else {
                replacedMeltablesInChunk.remove(pos.asLong());
                if (replacedMeltablesInChunk.isEmpty()) {
                    cunkToReplacedMeltablesMap.remove(chunkKey);
                }
            }
        }
        else if (replacedState != null) {
            Long2ObjectArrayMap<BlockState> newMap = new Long2ObjectArrayMap<>();

            newMap.put(pos.asLong(), replacedState);
            cunkToReplacedMeltablesMap.put(chunkKey, newMap);
        }

        setDirty();
    }

    public static ReplacedMeltablesSavedData createFromCompoundTag(CompoundTag compoundTag, Provider provider) {
        ReplacedMeltablesSavedData savedData = new ReplacedMeltablesSavedData();

        compoundTag.getAllKeys().forEach(key -> {
            try {
                long chunkKey = Long.parseLong(key);
                Long2ObjectArrayMap<BlockState> posToBlockStateMap = new Long2ObjectArrayMap<>();
                CompoundTag innerTag = compoundTag.getCompound(key);

                innerTag.getAllKeys().forEach(innerKey -> {
                    long blockKey = Long.parseLong(innerKey);
                    BlockState.CODEC.decode(NbtOps.INSTANCE, innerTag.get(innerKey)).result().ifPresent(result -> {
                        posToBlockStateMap.put(blockKey, result.getFirst());
                    });
                });

                savedData.cunkToReplacedMeltablesMap.put(chunkKey, posToBlockStateMap);
            }
            catch (NumberFormatException e) {
                HomeostaticSeasons.LOGGER.error("Failed to load replaced meltables data for chunk key: {}", key, e);
            }
        });

        return savedData;
    }

    public static SavedData.Factory<ReplacedMeltablesSavedData> getFactory() {
        return new SavedData.Factory<>(ReplacedMeltablesSavedData::new, ReplacedMeltablesSavedData::createFromCompoundTag, null);
    }

}
