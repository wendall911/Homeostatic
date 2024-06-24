package homeostatic.util;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.Hydration;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.overlay.WaterHud;
import homeostatic.platform.Services;

public class WaterHelper {

    public static void updateWaterInfo(ServerPlayer sp, float sweatLevel) {
        if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        Services.PLATFORM.getWaterCapabilty(sp).ifPresent(data -> {
            WaterInfo waterInfo = new WaterInfo(
                data.getWaterLevel(),
                data.getWaterSaturationLevel(),
                data.getWaterExhaustionLevel()
            );

            waterInfo.update(sweatLevel);
            data.setWaterData(waterInfo);

            Services.PLATFORM.syncWaterData(sp, waterInfo);
        });
    }

    public static void drink(ServerPlayer sp, ItemStack stack, boolean update) {
        drink(sp, getItemHydration(stack), update);
    }

    public static void drink(ServerPlayer sp, ItemStack stack, @Nullable Fluid fluid) {
        drink(sp, stack, fluid, true);
    }

    public static void drink(ServerPlayer sp, ItemStack stack, @Nullable Fluid fluid, boolean update) {
        drink(sp, getItemHydration(stack, fluid), update);
    }

    public static void drink(ServerPlayer sp, @Nullable Hydration hydration, boolean update) {
        if (hydration != null) {
            Services.PLATFORM.getWaterCapabilty(sp).ifPresent(data -> {
                boolean isDirty = hydration.duration() > 0;
                float chance = hydration.chance();

                data.increaseWaterLevel(hydration.amount());
                data.increaseSaturationLevel(hydration.saturation());

                // Reduce change of getting more thirst effect if player already has the effect
                if (!sp.hasEffect(HomeostaticEffects.THIRST)) {
                    chance = chance * 0.5F;
                }

                if (isDirty && Homeostatic.RANDOM.nextFloat() < chance) {
                    sp.addEffect(new MobEffectInstance(
                        HomeostaticEffects.THIRST,
                        hydration.duration(),
                        hydration.potency(),
                        false,
                        false,
                        false
                    ));
                }

                if (update) {
                    Services.PLATFORM.syncWaterData(sp, new WaterInfo(
                        data.getWaterLevel(),
                        data.getWaterSaturationLevel(),
                        data.getWaterExhaustionLevel()
                    ));
                }
            });
        }
    }

    public static Hydration getItemHydration(@Nullable ItemStack stack, @Nullable Fluid fluid) {
        DrinkableItem drinkableItem = stack != null ? DrinkableItemManager.get(stack) : null;
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
        Fluid fluid = null;

        if (stack.getItem() instanceof PotionItem) {
            ResourceLocation water = RegistryHelper.getRegistry(Registries.POTION).getKey(Potions.WATER);
            String potion = stack.getOrCreateTag().getString("Potion");
            stack = new ItemStack(Items.AIR);

            if (!potion.contentEquals(water.toString())) {
                fluid = HomeostaticFluids.PURIFIED_WATER;
            }
            else {
                fluid = Fluids.WATER;
            }
        }
        else {
            Optional<FluidInfo> fluidInfoOptional = Services.PLATFORM.getFluidInfo(stack);

            if (fluidInfoOptional.isPresent()) {
                fluid = fluidInfoOptional.get().fluid();
            }
        }

        return getItemHydration(stack, fluid);
    }

    public static Hydration getFluidHydration(Fluid fluid) {
        return getItemHydration(null, fluid);
    }

    public static void drinkWater(ServerPlayer sp) {
        drinkDirtyWaterItem(sp, false);
    }

    public static void drinkDirtyWaterItem(ServerPlayer sp, boolean update) {
        ItemStack air = new ItemStack(Items.AIR);

        drink(sp, air, Fluids.WATER, update);
    }

    public static void drawWaterBar(ResourceLocation sprite, int scaledWidth, int scaledHeight, MobEffectInstance effectInstance, Gui gui, GuiGraphics guiGraphics, float waterSaturationLevel, int waterLevel, int tickCount) {
        int offsetX;
        int offsetY;
        int pV = 0;
        int pU = 0;
        int pUOffset = 0;

        if (ConfigHandler.Client.forceWaterBarPosition()) {
            offsetX = Alignment.getX(ConfigHandler.Client.globePosition(), scaledWidth, 9,
                    ConfigHandler.Client.waterBarOffsetX());
            offsetY = Alignment.getY(ConfigHandler.Client.waterBarPosition(), scaledHeight, ConfigHandler.Client.waterBarOffsetY());
        }
        else {
            offsetX = scaledWidth / 2 + 91;
            offsetY = scaledHeight;
        }

        int pY = offsetY;

        if (effectInstance != null) {
            pU += 18;
            pUOffset += 9;
        }

        for (int i = 0; i < 10; ++i) {
            int pX = offsetX - i * 8 - 9;
            guiGraphics.blit(sprite, pX, pY, pUOffset + 36, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);

            if (waterSaturationLevel <= 0.0F && tickCount % (waterLevel * 3 + 1) == 0) {
                pY = offsetY + (Homeostatic.RANDOM.nextInt(3) - 1);
            }

            if (i * 2 + 1 < waterLevel) {
                guiGraphics.blit(sprite, pX, pY, pU, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);
            }

            if (i * 2 + 1 == waterLevel) {
                guiGraphics.blit(sprite, pX, pY, pU + 9, pV, WaterHud.BAR_WIDTH, WaterHud.BAR_HEIGHT);
            }

            if (i * 2 + 1 < waterSaturationLevel) {
                guiGraphics.blit(sprite, pX, pY - 1, pU, pV + 9, 9, 9);
                guiGraphics.blit(sprite, pX, pY + 1, pU + 9, pV + 9, 9, 9);
            }

            if (i * 2 + 1 == waterSaturationLevel) {
                guiGraphics.blit(sprite, pX, pY, pU, pV + 9, 9, 9);
            }
        }
    }

    public static ItemStack getFilledItem(ItemStack stack, Fluid fluid, int amount) {
        return Services.PLATFORM.fillFluid(stack, fluid, amount);
    }

    public static ItemStack getFilledItem(ItemStack stack, ResourceLocation key, int amount) {
        Fluid fluid = RegistryHelper.getRegistry(Registries.FLUID).get(key);

        return getFilledItem(stack, fluid, amount);
    }

}
