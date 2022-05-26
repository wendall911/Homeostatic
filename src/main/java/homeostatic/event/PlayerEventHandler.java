package homeostatic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.water.WaterData;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;
import homeostatic.network.StatsData;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class PlayerEventHandler {

    private static int ticks = 0;
    private static boolean loggedIn = false;

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                WaterData waterData;
                BodyTemperature bodyTemperature;

                // Check if first time logging in
                if (data.getCoreTemperature() > 0.0F) {
                    waterData = new WaterData(
                            data.getWaterLevel(),
                            data.getWaterSaturationLevel(),
                            data.getWaterExhaustionLevel()
                    );
                    bodyTemperature = new BodyTemperature(sp, environmentData, waterData,
                            data.getCoreTemperature(), data.getSkinTemperature(), ticks);
                }
                else {
                    waterData = new WaterData();
                    bodyTemperature = new BodyTemperature(sp, environmentData, waterData);
                }

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new StatsData(environmentData.getLocalTemperature(), bodyTemperature)
                );

                loggedIn = true;
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            final Player player = event.getPlayer();
            final ResourceKey<Level> worldKey = event.getTo();

            if (!worldKey.location().toString().contains(DimensionType.OVERWORLD_EFFECTS.toString())) {
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (!loggedIn) return;
        if (event.player instanceof FakePlayer) return;
        if (event.player.getLevel().isClientSide()) return;

        final ServerPlayer sp = (ServerPlayer) event.player;
        ServerLevel world = sp.getLevel();

        ticks++;

        // 16 ticks ~= mc minute
        if (ticks % 16 == 0) {
            ProfilerFiller profilerfiller = world.getProfiler();
            profilerfiller.push("tempCalc");
            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                WaterData waterData;
                BodyTemperature bodyTemperature;

                waterData = new WaterData(
                        data.getWaterLevel(),
                        data.getWaterSaturationLevel(),
                        data.getWaterExhaustionLevel()
                );
                bodyTemperature = new BodyTemperature(sp, environmentData, waterData,
                        data.getCoreTemperature(), data.getSkinTemperature(), ticks);

                data.setTemperatureData(bodyTemperature, environmentData.getLocalTemperature());
                data.setWaterData(waterData);

                NetworkHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> sp),
                        new StatsData(environmentData.getLocalTemperature(), bodyTemperature)
                );
            });
            profilerfiller.pop();
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(EntityJoinWorldEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            if (world == null) return;
            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                WaterData waterData;
                BodyTemperature bodyTemperature;

                waterData = new WaterData(10, 2.5F, 0.0F);
                bodyTemperature = new BodyTemperature(sp, environmentData, waterData,
                        BodyTemperature.NORMAL, BodyTemperature.NORMAL, ticks);

                data.setTemperatureData(bodyTemperature, environmentData.getLocalTemperature());
                data.setWaterData(waterData);

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new StatsData(environmentData.getLocalTemperature(), bodyTemperature)
                );
            });
        }
    }

}
