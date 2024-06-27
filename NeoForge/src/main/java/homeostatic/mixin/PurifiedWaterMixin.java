package homeostatic.mixin;

import net.minecraft.world.level.material.FlowingFluid;

import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;

import homeostatic.common.fluid.NeoForgeFluidType;
import homeostatic.common.fluid.PurifiedWater;

@Mixin(PurifiedWater.class)
public abstract class PurifiedWaterMixin extends FlowingFluid {

    @Override
    public FluidType getFluidType() {
        return NeoForgeFluidType.PURIFIED_WATER_TYPE;
    }

}
