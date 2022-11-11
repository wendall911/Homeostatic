package homeostatic.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.FakePlayer;

import homeostatic.common.capabilities.Temperature;
import homeostatic.common.capabilities.Thermometer;
import homeostatic.common.capabilities.Water;
import homeostatic.common.capabilities.Wetness;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(
                Homeostatic.loc("temperature"),
                new Temperature.Provider()
            );
            event.addCapability(
                Homeostatic.loc("water"),
                new Water.Provider()
            );
            event.addCapability(
                Homeostatic.loc("wetness"),
                new Wetness.Provider()
            );
            event.addCapability(
                Homeostatic.loc("thermometer"),
                new Thermometer.Provider()
            );
        }
    }
}
