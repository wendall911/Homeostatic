package homeostatic.common.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

import net.minecraftforge.registries.DeferredRegister;

import homeostatic.Homeostatic;

public class HomeostaticRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_REGISTRY =
            DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Homeostatic.MODID);

    public HomeostaticRecipes() {}

}
