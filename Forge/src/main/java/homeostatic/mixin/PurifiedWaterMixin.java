package homeostatic.mixin;

import net.minecraft.world.level.material.FlowingFluid;

import net.minecraftforge.fluids.FluidType;

import org.spongepowered.asm.mixin.Mixin;

import homeostatic.common.fluid.ForgeFluidType;
import homeostatic.common.fluid.PurifiedWater;

@Mixin(PurifiedWater.class)
public abstract class PurifiedWaterMixin extends FlowingFluid {

    @Override
    public FluidType getFluidType() {
        return ForgeFluidType.PURIFIED_WATER_TYPE;
    }

}
