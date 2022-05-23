package homeostatic.event;

import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentTemperature;
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
import net.minecraft.world.level.storage.LevelData;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;
import homeostatic.network.StatsData;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class PlayerEventHandler {

    private static int tick = 0;

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel() instanceof ServerLevel ? (ServerLevel)sp.getLevel() : null;

            if (world == null) return;
            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                float localTemperature = EnvironmentTemperature.get(sp, pos, biome, world);
                float bodyTemperature = data.getBodyTemperature();

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new StatsData(localTemperature, bodyTemperature)
                );
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
        if (event.player instanceof FakePlayer) return;
        if (event.player.getLevel().isClientSide()) return;

        final ServerPlayer sp = (ServerPlayer) event.player;
        ServerLevel world = sp.getLevel() instanceof ServerLevel ? (ServerLevel)sp.getLevel() : null;

        if (world == null) return;
        if (!(world.getLevelData() instanceof LevelData)) return;

        tick++;

        if (tick % 20 == 0) {
            ProfilerFiller profilerfiller = world.getProfiler();
            profilerfiller.push("tempCalc");
            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                float localTemperature = EnvironmentTemperature.get(sp, pos, biome, world);
                float bodyTemperature = data.getBodyTemperature() + 0.0001F;

                data.setLocalTemperature(localTemperature);
                data.setBodyTemperature(bodyTemperature);

                NetworkHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> sp),
                    new StatsData(localTemperature, bodyTemperature)
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
            ServerLevel world = sp.getLevel() instanceof ServerLevel ? (ServerLevel)sp.getLevel() : null;

            if (world == null) return;
            sp.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                float localTemperature = EnvironmentTemperature.get(sp, pos, biome, world);
                float bodyTemperature = BodyTemperature.NORMAL;

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new StatsData(localTemperature, bodyTemperature)
                );
            });
        }
    }

}
