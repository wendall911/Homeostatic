package homeostatic.common.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;

import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.RegisterEvent;

import static homeostatic.Homeostatic.prefix;

public class NeoForgeFluidType {

    public static FluidType PURIFIED_WATER_TYPE;

    public static void initTypes(RegisterEvent.RegisterHelper<FluidType> registryHelper) {
        PURIFIED_WATER_TYPE = new FluidType(FluidType.Properties.create()
                .viscosity(1000)
                .lightLevel(3)
                .density(4000)
                .rarity(Rarity.UNCOMMON)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY));

        registryHelper.register(prefix("purified_water_type"), PURIFIED_WATER_TYPE);
    }

}
