package homeostatic.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.FakePlayer;

import homeostatic.common.capabilities.Temperature;
import homeostatic.common.capabilities.Water;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(
                new ResourceLocation(Homeostatic.MODID, "temperature"),
                new Temperature.Provider()
            );
            event.addCapability(
                    new ResourceLocation(Homeostatic.MODID, "water"),
                    new Water.Provider()
            );
        }
    }
}
