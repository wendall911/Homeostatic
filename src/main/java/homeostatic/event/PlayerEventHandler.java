package homeostatic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

            WaterHelper.updateWaterInfo(sp, 0.0F);
            WetnessHelper.updateWetnessInfo(sp, 0.0F, true);

            sp.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                BlockPos pos = new BlockPos(sp.getEyePosition(1.0F));
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
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof FakePlayer) return;
        if (event.phase != TickEvent.Phase.START) return;

        if (!event.player.getLevel().isClientSide()) {
            final ServerPlayer sp = (ServerPlayer) event.player;

            if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

            ServerLevel world = sp.getLevel();
            ProfilerFiller profilerfiller = world.getProfiler();

            sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> data.checkWaterLevel(sp));

            profilerfiller.push("tempCalc");
            sp.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                if (sp.tickCount % 2 == 0) {
                    data.checkTemperatureLevel(sp);
                }

                if (sp.tickCount % 16 == 0 || sp.tickCount % 60 == 0) {
                    BlockPos pos = new BlockPos(sp.getEyePosition(1.0F));
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

        final Player player = event.getEntity() != null ? event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            ServerLevel world = sp.getLevel();

            if (sp.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

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
                BlockPos pos = new BlockPos(sp.getEyePosition(1.0F));
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
        final Player player = event.getEntity();

        if (player != null && player.level.isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final Player player = event.getEntity();

        if (player != null && player.level.isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level.isClientSide) {
            ItemStack stack = event.getItem();
            ServerPlayer sp = (ServerPlayer) player;

            WaterHelper.drink(sp, stack, true);
        }
    }

    private static void drinkWater(Player player, PlayerInteractEvent event) {
        final InteractionHand hand = event.getHand();

        if (hand != InteractionHand.OFF_HAND
                || player.getPose() != Pose.CROUCHING
                || !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;

        final LevelAccessor level = event.getLevel();
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

}