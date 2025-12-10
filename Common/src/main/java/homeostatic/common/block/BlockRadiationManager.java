package homeostatic.common.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;

import homeostatic.Homeostatic;

public class BlockRadiationManager extends SimpleJsonResourceReloadListener<JsonElement> {

    private static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BlockRadiation.class, new BlockRadiation.Serializer()).create();

    public BlockRadiationManager() {
        super(ExtraCodecs.JSON, FileToIdConverter.json("environment/block_radiation"));
    }

    public static JsonElement parseBlockRadiation(BlockRadiation blockRadiation) {
        return GSON.toJsonTree(blockRadiation);
    }

    public static BlockRadiation getBlockRadiation(Block block) {
        return RADIATION_BLOCKS.get(block);
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        RADIATION_BLOCKS.clear();

        for (Map.Entry<Identifier, JsonElement> entry : pObject.entrySet()) {
            try {
                BlockRadiation blockRadiation = GSON.fromJson(entry.getValue(), BlockRadiation.class);
                Optional<Holder.Reference<Block>> block = BuiltInRegistries.BLOCK.get(blockRadiation.loc());

                block.ifPresent(blockReference -> RADIATION_BLOCKS.put(blockReference.value(), blockRadiation));
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse block radiation {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} radiation blocks", RADIATION_BLOCKS.size());
    }

}
