package homeostatic.util;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potions;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.Hydration;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;
import homeostatic.network.WaterData;
import homeostatic.overlay.WaterHud;

public class WaterHelper {

    public static void updateWaterInfo(ServerPlayer sp, float sweatLevel) {
        sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            WaterInfo waterInfo = new WaterInfo(
                data.getWaterLevel(),
                data.getWaterSaturationLevel(),
                data.getWaterExhaustionLevel()
            );

            waterInfo.update(sweatLevel);
            data.setWaterData(waterInfo);

            NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> sp),
                new WaterData(waterInfo)
            );
        });
    }

    public static void drink(ServerPlayer sp, ItemStack stack, boolean update) {
        drink(sp, getItemHydration(stack), update);
    }

    public static void drink(ServerPlayer sp, ResourceLocation item, @Nullable ResourceLocation fluid) {
        drink(sp, item, fluid, true);
    }

    public static void drink(ServerPlayer sp, ResourceLocation item, @Nullable ResourceLocation fluid, boolean update) {
        drink(sp, getItemHydration(item, fluid), update);
    }

    public static void drink(ServerPlayer sp, @Nullable Hydration hydration, boolean update) {
        if (hydration != null) {
            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                boolean isDirty = hydration.duration() > 0;

                data.increaseWaterLevel(hydration.amount());
                data.increaseSaturationLevel(hydration.saturation());

                if (isDirty && Homeostatic.RANDOM.nextFloat() < hydration.chance()) {
                    if (!sp.hasEffect(HomeostaticEffects.THIRST.get())) {
                        sp.addEffect(new MobEffectInstance(
                                HomeostaticEffects.THIRST.get(),
                                hydration.duration(),
                                hydration.potency(),
                                false, false, false));
                    }
                }

                if (update) {
                    NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new WaterData(new WaterInfo(
                            data.getWaterLevel(),
                            data.getWaterSaturationLevel(),
                            data.getWaterExhaustionLevel()
                        ))
                    );
                }
            });
        }
    }

    public static Hydration getItemHydration(ResourceLocation item, @Nullable ResourceLocation fluid) {
        DrinkableItem drinkableItem = DrinkableItemManager.get(item);
        DrinkingFluid drinkingFluid = fluid != null ? DrinkingFluidManager.get(fluid) : null;
        Hydration hydration = null;

        if (drinkingFluid != null) {
            hydration = DrinkingFluid.getHydration(drinkingFluid);
        } else if (drinkableItem != null) {
            hydration = DrinkableItem.getHydration(drinkableItem);
        }

        return hydration;
    }

    public static Hydration getItemHydration(ItemStack stack) {
        ResourceLocation item;
        ResourceLocation fluid = null;

        if (stack.getItem() instanceof PotionItem) {
            ResourceLocation water = Registry.POTION.getKey(Potions.WATER);
            String potion = stack.getOrCreateTag().getString("Potion");
            item = ModIntegration.mcLoc("air");

            if (!potion.contentEquals(water.toString())) {
                fluid = Homeostatic.loc("purified_water");
            }
            else {
                fluid = ModIntegration.mcLoc("water");
            }
        }
        else {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
            item = Registry.ITEM.getKey(stack.getItem());

            if (fluidHandlerItem != null) {
                fluid = Registry.FLUID.getKey(fluidHandlerItem.getFluidInTank(0).getFluid());
            }
        }

        return getItemHydration(item, fluid);
    }

    public static void drinkWater(ServerPlayer sp) {
        drinkDirtyWaterItem(sp, false);
    }

    public static void drinkCleanWaterItem(ServerPlayer sp, boolean update) {
        ResourceLocation air = ModIntegration.mcLoc("air");
        ResourceLocation water = Homeostatic.loc("purified_water");

        drink(sp, air, water, update);
    }

    public static void drinkDirtyWaterItem(ServerPlayer sp, boolean update) {
        ResourceLocation air = ModIntegration.mcLoc("air");
        ResourceLocation water = ModIntegration.mcLoc("water");

        drink(sp, air, water, update);
    }

    public static void drawWaterBar(int scaledWidth, int scaledHeight, MobEffectInstance effectInstance, Gui gui, PoseStack matrix, float waterSaturationLevel, int waterLevel, int tickCount) {
        int offsetX = scaledWidth / 2 + 91;
        int offsetY = scaledHeight - 50;
        int pY = offsetY;
        int pV = 0;
        int pU = 0;
        int pUOffset = 0;

        if (effectInstance != null) {
            pU += 18;
            pUOffset += 9;
        }

        for (int i = 0; i < 10; ++i) {
            int pX = offsetX - i * 8 - 9;
            gui.blit(matrix, pX, pY, pUOffset + 36, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);

            if (waterSaturationLevel <= 0.0F && tickCount % (waterLevel * 3 + 1) == 0) {
                pY = offsetY + (Homeostatic.RANDOM.nextInt(3) - 1);
            }

            if (i * 2 + 1 < waterLevel) {
                gui.blit(matrix, pX, pY, pU, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);
            }

            if (i * 2 + 1 == waterLevel) {
                gui.blit(matrix, pX, pY, pU + 9, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);
            }

            if (i * 2 + 1 < waterSaturationLevel) {
                gui.blit(matrix, pX, pY - 1, pU, pV + 9, 9, 9);
                gui.blit(matrix, pX, pY + 1, pU + 9, pV + 9, 9, 9);
            }

            if (i * 2 + 1 == waterSaturationLevel) {
                gui.blit(matrix, pX, pY, pU, pV + 9, 9, 9);
            }
        }
    }

}