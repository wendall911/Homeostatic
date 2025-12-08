package climatesettings.platform.services;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

import climatesettings.common.biome.HomeostaticClimateSettings;
import climatesettings.network.IPacket;

public interface IClimate {

    HomeostaticClimateSettings getClimateSettings(Holder<Biome> biomeHolder);

    void syncDataToPlayer(IPacket packet, ServerPlayer player);

}
