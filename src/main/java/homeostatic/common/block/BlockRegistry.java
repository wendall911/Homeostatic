package homeostatic.common.block;

import java.util.HashMap;
import java.util.Map;

import homeostatic.config.ConfigHandler;

public class BlockRegistry {

    public static final Map<String, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    public static void init() {

        RADIATION_BLOCKS.clear();

        for (String blockData : ConfigHandler.Common.getRadiationBlocksData()) {
            String[] parts = blockData.split("-");

            RADIATION_BLOCKS.put(parts[0], new BlockRadiation(Double.parseDouble(parts[1])));
        }
    }

}