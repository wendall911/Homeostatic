package homeostatic.mixin;

import java.nio.file.Path;

import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.RecipeProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeProvider.class)
public interface RecipeProviderAccessor {

    @Invoker("inventoryTrigger")
    static InventoryChangeTrigger.TriggerInstance homeostatic_condition(ItemPredicate... predicates) {
        throw new IllegalStateException("");
    }

    @Invoker
    static void callSaveRecipe(CachedOutput cache, JsonObject json, Path path) {
        throw new IllegalStateException();
    }

    @Invoker("saveAdvancement")
    void homeostatic$saveAdvancement(CachedOutput cache, JsonObject json, Path path);

}