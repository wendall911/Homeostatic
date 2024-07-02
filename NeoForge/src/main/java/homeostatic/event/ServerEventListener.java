package homeostatic.event;

import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import homeostatic.common.biome.BiomeCategory;
import homeostatic.common.biome.BiomeCategoryManager;
import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.common.block.BlockRadiationManager;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.Homeostatic;
import homeostatic.util.RegistryHelper;
import homeostatic.util.WaterHelper;

public class ServerEventListener {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            PlayerEventHandler.onEntityJoinLevel((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(PlayerTickEvent.Pre event) {
        if (event.getEntity() instanceof FakePlayer) return;

        if (!event.getEntity().level().isClientSide()) {
            PlayerEventHandler.onPlayerTickEvent((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        final Player player = event.getEntity() != null ? event.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            PlayerEventHandler.onPlayerRespawn((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            ItemStack stack = event.getItem();
            ServerPlayer sp = (ServerPlayer) player;

            WaterHelper.drink(sp, stack, true);
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        PlayerEventHandler.onEquipmentChange(event.getEntity(), event.getSlot(), event.getFrom(), event.getTo());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(new BiomeCategoryManager());
        event.addListener(new BlockRadiationManager());
        event.addListener(new DrinkingFluidManager());
        event.addListener(new DrinkableItemManager());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStart(final ServerStartedEvent event) {
        Registry<Biome> biomeRegistry = RegistryHelper.getRegistry(event.getServer(), Registries.BIOME);

        for (Map.Entry<ResourceKey<Biome>, Biome> entry : biomeRegistry.entrySet()) {
            ResourceKey<Biome> biomeResourceKey = entry.getKey();
            ResourceLocation biomeName = biomeResourceKey.location();
            Holder<Biome> biomeHolder = biomeRegistry.getHolderOrThrow(biomeResourceKey);
            BiomeCategory.Type biomeCategory = BiomeCategoryManager.getBiomeCategory(biomeHolder);
            BiomeData biomeData = BiomeRegistry.getDataForBiome(biomeHolder);
            Biome biome = biomeHolder.value();
            Biome.Precipitation precipitation = getPrecipitation(biome);
            String temperatureModifier = biomeData.isFrozen() ? "FROZEN" : "NONE";
            float dayNightOffset = biomeData.getDayNightOffset(precipitation);
            double humidity = biomeData.getHumidity(precipitation);

            if (!biomeName.toString().equals("terrablender:deferred_placeholder")) {
                if (biomeCategory == BiomeCategory.Type.MISSING) {
                    Homeostatic.LOGGER.warn("Missing biome in registry, will set to neutral temperature for: {}", biomeName);
                }

                Homeostatic.LOGGER.debug("Biome: " + biomeName
                    + "\nprecipitation_type=" + precipitation
                    + "\ntemperature=" + biomeData.getTemperature(precipitation)
                    + "\ntemperatureModifier=" + temperatureModifier
                    + "\ndownfall=" + biome.getModifiedClimateSettings().downfall()
                    + "\ndayNightOffset=" + dayNightOffset
                    + "\nhumidity=" + humidity
                    + "\nbiomeCategory=" + biomeCategory);
            }
        }
    }

    /*
     * Mock for debugging purposes. Will not be 100% accurate, but should help map to older versions.
     */
    private static Biome.Precipitation getPrecipitation(Biome biome) {
        if (!biome.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        else {
            return biome.getBaseTemperature() <= 0.15F ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

}
