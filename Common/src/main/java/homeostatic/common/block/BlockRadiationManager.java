package homeostatic.common.block;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import homeostatic.Homeostatic;
import homeostatic.platform.Services;

public class BlockRadiationManager extends SimpleJsonResourceReloadListener {

    private static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BlockRadiation.class, new BlockRadiation.Serializer()).create();

    public BlockRadiationManager() {
        super(GSON, "environment/block_radiation");
    }

    public static JsonElement parseBlockRadiation(BlockRadiation blockRadiation) {
        return GSON.toJsonTree(blockRadiation);
    }

    public static BlockRadiation getBlockRadiation(Block block) {
        return RADIATION_BLOCKS.get(block);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        RADIATION_BLOCKS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BlockRadiation blockRadiation = GSON.fromJson(entry.getValue(), BlockRadiation.class);
                Block block = Services.PLATFORM.getBlock(blockRadiation.loc());

                if (block != Blocks.AIR && block != null) {
                    RADIATION_BLOCKS.put(block, blockRadiation);
                }
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse block radiation {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} radiation blocks", RADIATION_BLOCKS.size());
    }

}
