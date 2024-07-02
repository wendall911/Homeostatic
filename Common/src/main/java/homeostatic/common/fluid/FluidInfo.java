package homeostatic.common.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public record FluidInfo(Fluid fluid, long amount) {

    public FluidInfo(Fluid fluid) {
        this(fluid, 1);
    }

    public FluidInfo withFluid() {
        if (fluid instanceof FlowingFluid flowingFluid) {
            return new FluidInfo(flowingFluid.getSource(), amount);
        }

        return this;
    }

    public FluidInfo withAmount(long amount) {
        return new FluidInfo(fluid, amount);
    }

    public boolean isEmpty() {
        return amount <= 0;
    }

}
