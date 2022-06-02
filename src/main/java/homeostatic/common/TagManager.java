package homeostatic.common;

import java.util.Collections;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.registries.ForgeRegistries;

import homeostatic.Homeostatic;

public final class TagManager {

    public static final class Items {
        public static final TagKey<Item> INSULATION = create("insulation");
        public static final TagKey<Item> RADIATION_PROTECTION = create("radiation_protection");
        public static final TagKey<Item> WATERPROOF = create("waterproof");

        private static TagKey<Item> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }

    }

    public static final class Blocks {

        private static TagKey<Block> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Homeostatic.MODID, path);
    }
}
