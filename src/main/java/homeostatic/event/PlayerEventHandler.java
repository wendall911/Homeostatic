package homeostatic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.water.WaterInfo;
import homeostatic.Homeostatic;
import homeostatic.network.DrinkWater;
import homeostatic.network.NetworkHandler;
import homeostatic.network.TemperatureData;
import homeostatic.network.WaterData;
import homeostatic.util.WaterHelper;
import homeostatic.util.WetnessHelper;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            WaterHelper.updateWaterInfo(sp, 0.0F);
            WetnessHelper.updateWetnessInfo(sp, 0.0F, true);

            sp.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, data);

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new TemperatureData(environmentData.getLocalTemperature(), bodyTemperature)
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
        if (event.phase != TickEvent.Phase.START) return;

        if (!event.player.getLevel().isClientSide()) {
            final ServerPlayer sp = (ServerPlayer) event.player;

            ServerLevel world = sp.getLevel();
            ProfilerFiller profilerfiller = world.getProfiler();

            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                data.checkWaterLevel(sp);
            });

            profilerfiller.push("tempCalc");
            sp.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                if (sp.tickCount % 2 == 0) {
                    data.checkTemperatureLevel(sp);
                }

                if (sp.tickCount % 16 == 0 || sp.tickCount % 60 == 0) {
                    BlockPos pos = sp.eyeBlockPosition();
                    Holder<Biome> biome = world.getBiome(pos);
                    EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                    boolean updateCore = sp.tickCount % 60 == 0;
                    BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, data, updateCore, true);

                    data.setTemperatureData(environmentData.getLocalTemperature(), bodyTemperature);

                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(() -> sp),
                            new TemperatureData(environmentData.getLocalTemperature(), bodyTemperature)
                    );
                }

            });
            profilerfiller.pop();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            if (world == null) return;

            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                WaterInfo waterInfo = new WaterInfo(
                        WaterInfo.MAX_WATER_LEVEL / 2,
                        WaterInfo.MAX_SATURATION_LEVEL / 2.0F,
                        0.0F
                );

                data.setWaterData(waterInfo);

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new WaterData(waterInfo)
                );
            });

            sp.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                BlockPos pos = sp.eyeBlockPosition();
                Holder<Biome> biome = world.getBiome(pos);
                EnvironmentData environmentData = new EnvironmentData(sp, pos, biome, world);
                BodyTemperature bodyTemperature = new BodyTemperature(sp, environmentData, data);

                data.setTemperatureData(environmentData.getLocalTemperature(), bodyTemperature);

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new TemperatureData(environmentData.getLocalTemperature(), bodyTemperature)
                );
            });
        }
    }

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        final Player player = event.getPlayer() instanceof Player ? (Player) event.getPlayer() : null;

        if (player != null && player.level.isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final Player player = event.getPlayer() instanceof Player ? (Player) event.getPlayer() : null;

        if (player != null && player.level.isClientSide) {
            drinkWater(player, event);
        }
    }

    private static void drinkWater(Player player, PlayerInteractEvent event) {
        final InteractionHand hand = event.getHand();

        if (hand != InteractionHand.OFF_HAND
                || player.getPose() != Pose.CROUCHING
                || !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;

        final LevelAccessor level = event.getWorld();
        final HitResult hitresult = player.pick(2.0D, 0.0F, true);
        BlockPos pos = ((BlockHitResult)hitresult).getBlockPos();

        if (hitresult.getType() == HitResult.Type.BLOCK && level.getFluidState(pos).getType() == Fluids.WATER) {
            player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                if (data.getWaterLevel() < WaterInfo.MAX_WATER_LEVEL) {
                    player.getLevel().playSound(player, pos, SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.4f, 1.0f);

                    NetworkHandler.INSTANCE.sendToServer(new DrinkWater());
                }
            });
        }
    }

    private static HitResult getPlayerPOVHitResult(LevelAccessor pLevel, Player pPlayer, ClipContext.Fluid pFluidMode) {
        final float xRot = pPlayer.getXRot();
        final float yRot = pPlayer.getYRot();
        final Vec3 eyePos = pPlayer.getEyePosition();
        final float cosRotY = Mth.cos(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        final float sinRotY = Mth.sin(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        final float cosRotX = -Mth.cos(-xRot * ((float)Math.PI / 180F));
        final float sinRotX = Mth.sin(-xRot * ((float)Math.PI / 180F));
        final float pX = sinRotY * cosRotX;
        final float pZ = cosRotY * cosRotX;
        final double reach = pPlayer.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        final Vec3 reachPos = eyePos.add((double)pX * reach, (double)sinRotX * reach, (double)pZ * reach);

        return pLevel.clip(new ClipContext(eyePos, reachPos, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }

}
