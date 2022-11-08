package homeostatic.util;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import homeostatic.config.ConfigHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
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

    public static void drink(ServerPlayer sp, ItemStack stack, @Nullable ResourceLocation fluid) {
        drink(sp, stack, fluid, true);
    }

    public static void drink(ServerPlayer sp, ItemStack stack, @Nullable ResourceLocation fluid, boolean update) {
        drink(sp, getItemHydration(stack, fluid), update);
    }

    public static void drink(ServerPlayer sp, @Nullable Hydration hydration, boolean update) {
        if (hydration != null) {
            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                boolean isDirty = hydration.duration() > 0;
                float chance = hydration.chance();

                data.increaseWaterLevel(hydration.amount());
                data.increaseSaturationLevel(hydration.saturation());

                // Reduce change of getting more thirst effect if player already has the effect
                if (!sp.hasEffect(HomeostaticEffects.THIRST.get())) {
                    chance = chance * 0.5F;
                }

                if (isDirty && Homeostatic.RANDOM.nextFloat() < chance) {
                    sp.addEffect(new MobEffectInstance(
                        HomeostaticEffects.THIRST.get(),
                        hydration.duration(),
                        hydration.potency(),
                        false,
                        false,
                        false
                    ));
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

    public static Hydration getItemHydration(@Nullable ItemStack stack, @Nullable ResourceLocation fluid) {
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
        ResourceLocation fluid = null;

        if (stack.getItem() instanceof PotionItem) {
            ResourceLocation water = Registry.POTION.getKey(Potions.WATER);
            String potion = stack.getOrCreateTag().getString("Potion");
            stack = new ItemStack(Items.AIR);

            if (!potion.contentEquals(water.toString())) {
                fluid = Homeostatic.loc("purified_water");
            }
            else {
                fluid = ModIntegration.mcLoc("water");
            }
        }
        else {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

            if (fluidHandlerItem != null) {
                fluid = Registry.FLUID.getKey(fluidHandlerItem.getFluidInTank(0).getFluid());
            }
        }

        return getItemHydration(stack, fluid);
    }

    public static Hydration getFluidHydration(Fluid fluid) {
        ResourceLocation fluidKey = Registry.FLUID.getKey(fluid);

        return getItemHydration(null, fluidKey);
    }

    public static void drinkWater(ServerPlayer sp) {
        drinkDirtyWaterItem(sp, false);
    }

    public static void drinkDirtyWaterItem(ServerPlayer sp, boolean update) {
        ItemStack air = new ItemStack(Items.AIR);
        ResourceLocation water = ModIntegration.mcLoc("water");

        drink(sp, air, water, update);
    }

    public static void drawWaterBar(int scaledWidth, int scaledHeight, MobEffectInstance effectInstance, Gui gui, PoseStack matrix, float waterSaturationLevel, int waterLevel, int tickCount) {
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

    public static ItemStack getFilledItem(ItemStack stack, Fluid fluid, int amount) {
        ItemStack copy = stack.copy();
        IFluidHandlerItem fluidHandlerItem = copy.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

        fluidHandlerItem.fill(new FluidStack(fluid, amount), IFluidHandler.FluidAction.EXECUTE);
        updateDamage(copy);

        return copy;
    }

    public static ItemStack getFilledItem(ItemStack stack, ResourceLocation key, int amount) {
        Fluid fluid = Registry.FLUID.get(key);

        return getFilledItem(stack, fluid, amount);
    }

    public static void updateDamage(ItemStack stack) {
        if (stack.isDamageableItem()) {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

            stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getMaxDamage() - fluidHandlerItem.getFluidInTank(0).getAmount()));
        }
    }

}