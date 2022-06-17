package homeostatic.common.recipe;

import com.google.gson.JsonObject;

import com.mojang.datafixers.util.Pair;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistryEntry;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.Homeostatic;

public class PurifiedLeatherFlask extends CustomRecipe {

    public static final RecipeSerializer<PurifiedLeatherFlask> PURIFIED_LEATHER_FLASK_SERIALIZER = new PurifiedLeatherFlask.Serializer();

    public static void init() {
        PURIFIED_LEATHER_FLASK_SERIALIZER.setRegistryName(new ResourceLocation(Homeostatic.MODID + ":purified_leather_flask"));
    }

    public PurifiedLeatherFlask(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
        ItemStack flask = check.getFirst();
        ItemStack filter = check.getSecond();

        return flask != null && filter != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
        ItemStack flaskCopy = check.getFirst().copy();
        IFluidHandlerItem fluidHandlerItem = flaskCopy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        int amount = fluidHandlerItem.getFluidInTank(0).getAmount();

        fluidHandlerItem.drain(amount, IFluidHandler.FluidAction.EXECUTE);
        fluidHandlerItem.fill(new FluidStack(HomeostaticFluids.PURIFIED_WATER, amount), IFluidHandler.FluidAction.EXECUTE);

        ItemStack filledItem = fluidHandlerItem.getContainer();
        filledItem.setDamageValue(Math.min(flaskCopy.getMaxDamage(), flaskCopy.getMaxDamage() - fluidHandlerItem.getFluidInTank(0).getAmount()));

        return filledItem;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PURIFIED_LEATHER_FLASK_SERIALIZER;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer pContainer) {
        ItemStack flask = null;
        ItemStack filter = null;

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack ingredient = pContainer.getItem(i);

            if (ingredient.getItem().getRegistryName().getPath().contains(HomeostaticItems.WATER_FILTER.getRegistryName().getPath())) {
                filter = ingredient;
            }
            else if (ingredient.getItem() instanceof LeatherFlask) {
                IFluidHandlerItem fluidHandlerItem = ingredient.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

                if (fluidHandlerItem.getFluidInTank(0).getAmount() > 0) {
                    flask = ingredient;
                }
            }
        }

        return Pair.of(flask, filter);
    }

    public static class Type implements RecipeType<PurifiedLeatherFlask> {

        public static final Type INSTANCE = new Type();

        private Type() {}

    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PurifiedLeatherFlask> {

        @Override
        public PurifiedLeatherFlask fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new PurifiedLeatherFlask(pRecipeId);
        }

        @Override
        public PurifiedLeatherFlask fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new PurifiedLeatherFlask(pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PurifiedLeatherFlask pRecipe) {}

    }

}