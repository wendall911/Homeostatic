package homeostatic.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import homeostatic.Homeostatic;

public class HomeostaticNeoForgeRegistries {

    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Homeostatic.MODID);

}
