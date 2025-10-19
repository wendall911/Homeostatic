package homeostatic.common.book;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import handbook.client.book.BookContentsBuilder;
import handbook.client.book.BookEntry;
import handbook.client.book.ClientBookRegistry;
import handbook.client.book.page.PageCrafting;

import homeostatic.util.GuidebookHelper;

import static homeostatic.Homeostatic.prefix;

public class PageCustomCrafting extends PageCrafting {

	public static void init() {
		ClientBookRegistry registry = ClientBookRegistry.INSTANCE;

		registry.pageTypes.put(prefix("custom_crafting"), PageCustomCrafting.class);
	}

	@Override
	public Recipe<?> loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation loc, boolean linkRecipe) {
		return GuidebookHelper.getRecipe(level, loc);
	}

}
