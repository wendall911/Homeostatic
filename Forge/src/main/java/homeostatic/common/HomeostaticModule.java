package homeostatic.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import homeostatic.Homeostatic;

public abstract class HomeostaticModule {

    protected static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Homeostatic.MODID);

    protected HomeostaticModule() {}

    public static void initRegistries(IEventBus bus) {
        CREATIVE_MODE_TAB_REGISTRY.register(bus);
    }

}
