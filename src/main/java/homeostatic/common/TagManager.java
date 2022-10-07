package homeostatic.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import homeostatic.Homeostatic;

public final class TagManager {

    public static final class Items {
        public static final TagKey<Item> INSULATION = create("insulation");
        public static final TagKey<Item> RADIATION_PROTECTION = create("radiation_protection");
        public static final TagKey<Item> WATERPROOF = create("waterproof");
        public static final TagKey<Item> FRUITS = createForge("fruits");

        private static TagKey<Item> create(String id) {
            return ItemTags.create(identifier(id));
        }

        private static TagKey<Item> createForge(String id) {
            return ItemTags.create(new ResourceLocation("forge", id));
        }

    }

    public static ResourceLocation identifier(String path) {
        return Homeostatic.loc(path);
    }
}
