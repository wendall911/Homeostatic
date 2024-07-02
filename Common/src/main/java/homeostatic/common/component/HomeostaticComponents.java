package homeostatic.common.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;

import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class HomeostaticComponents {

    public static final DataComponentType<CustomData> ARMOR = DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build();
    public static final DataComponentType<CustomData> WATER_CONTAINER = DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build();

    public static void registerDataComponents() {
        register(loc("armor"), ARMOR);
        register(loc("water_container"), WATER_CONTAINER);
    }

    private static <T> void register(ResourceLocation name, DataComponentType<T> component) {
        Services.PLATFORM.registerDataComponent(name, component);
    }

}
