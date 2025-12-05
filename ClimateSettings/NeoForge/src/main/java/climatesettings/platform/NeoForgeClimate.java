package climatesettings.platform;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

import net.neoforged.neoforge.network.PacketDistributor;

import climatesettings.common.biome.HomeostaticClimateSettings;
import climatesettings.network.IPacket;
import climatesettings.platform.services.IClimate;

public class NeoForgeClimate implements IClimate {

    @Override
    public HomeostaticClimateSettings getClimateSettings(Holder<Biome> biomeHolder) {
        Biome.ClimateSettings climateSettings = biomeHolder.value().getModifiedClimateSettings();

        return new HomeostaticClimateSettings(
            biomeHolder,
            climateSettings.hasPrecipitation(),
            climateSettings.temperature(),
            climateSettings.temperatureModifier(),
            climateSettings.downfall()
        );
    }

    @Override
    public void syncDataToPlayer(IPacket packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

}
