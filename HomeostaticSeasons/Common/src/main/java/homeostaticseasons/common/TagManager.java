package homeostaticseasons.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import homeostaticseasons.data.integration.HomeostaticSeasonsIntegration;

import static homeostaticseasons.HomeostaticSeasons.prefix;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public final class TagManager {

    public static final class Blocks {

        public static final TagKey<Block> REPLACEABLE_BY_SNOW = create("replaceable_by_snow");
        public static final TagKey<Block> FS_REPLACEABLE_BY_SNOW = createOptionalTag(HomeostaticSeasonsIntegration.FS_MODID, "replaceable_by_snow");
        public static final TagKey<Block> FLOWERS = commonTag("flowers");

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registries.BLOCK, prefix(id));
        }

        private static TagKey<Block> commonTag(String id) {
            return TagKey.create(Registries.BLOCK, loc("c", id));
        }

        private static TagKey<Block> createOptionalTag(String modId, String id) {
            return TagKey.create(Registries.BLOCK, loc(modId, id));
        }

    }

}
