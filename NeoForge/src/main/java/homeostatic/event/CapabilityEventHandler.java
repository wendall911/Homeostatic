package homeostatic.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.util.FakePlayer;

import homeostatic.common.capabilities.TemperatureCapability;
import homeostatic.common.capabilities.ThermometerCapability;
import homeostatic.common.capabilities.WaterCapability;
import homeostatic.common.capabilities.WetnessCapability;
import homeostatic.Homeostatic;

public class CapabilityEventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(
                Homeostatic.loc("temperature"),
                new TemperatureCapability.Provider()
            );
            event.addCapability(
                Homeostatic.loc("water"),
                new WaterCapability.Provider()
            );
            event.addCapability(
                Homeostatic.loc("wetness"),
                new WetnessCapability.Provider()
            );
            event.addCapability(
                Homeostatic.loc("thermometer"),
                new ThermometerCapability.Provider()
            );
        }
    }

}
