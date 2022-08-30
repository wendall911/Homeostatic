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

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockRadiationManager extends SimpleJsonResourceReloadListener {

    public static final Map<ResourceLocation, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BlockRadiation.class, new BlockRadiation.Serializer()).create();
    private static BlockRadiationManager INSTANCE;

    public BlockRadiationManager() {
        super(GSON, "environment/block_radiation");
    }

    public static JsonElement parseBlockRadiation(BlockRadiation blockRadiation) {
        return GSON.toJsonTree(blockRadiation);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        RADIATION_BLOCKS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BlockRadiation blockRadiation = GSON.fromJson(entry.getValue(), BlockRadiation.class);

                RADIATION_BLOCKS.put(blockRadiation.getLocation(), blockRadiation);
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse block radiation %s %s", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded %d radiation blocks", RADIATION_BLOCKS.size());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(INSTANCE = new BlockRadiationManager());
    }

}