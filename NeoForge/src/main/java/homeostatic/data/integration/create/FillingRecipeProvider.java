package homeostatic.data.integration.create;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.api.data.recipe.FillingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import homeostatic.common.TagManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;
import homeostatic.util.WaterHelper;

import static homeostatic.Homeostatic.prefix;
import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class FillingRecipeProvider extends FillingRecipeGen {

    GeneratedRecipe FLASK = createFlaskRecipe("purified_water", TagManager.Fluids.PURIFIED_WATER, prefix("purified_water")),
            WATER = createFlaskRecipe("water", FluidTags.WATER, loc("minecraft", "water"));

    public FillingRecipeProvider(@NotNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider, Homeostatic.MODID);
    }

    private GeneratedRecipe createFlaskRecipe(String id, TagKey<Fluid> key, ResourceLocation fluid) {
        return create(prefix(id), b -> b.require(key, (int) LeatherFlask.LEATHER_FLASK_CAPACITY)
            .require(HomeostaticItems.LEATHER_FLASK)
            .output(WaterHelper.getFilledItem(new ItemStack(HomeostaticItems.LEATHER_FLASK), fluid, (int) LeatherFlask.LEATHER_FLASK_CAPACITY))
            .whenModLoaded(ModIntegration.CREATE_MODID));
    }

}
