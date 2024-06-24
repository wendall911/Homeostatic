package homeostatic.common.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public record FluidInfo(Fluid fluid, long amount, CompoundTag tag) {

    public FluidInfo(Fluid fluid) {
        this(fluid, 1, new CompoundTag());
    }

    public FluidInfo(Fluid fluid, long amount) {
        this(fluid, amount, new CompoundTag());
    }

    public FluidInfo withFluid() {
        if (fluid instanceof FlowingFluid flowingFluid) {
            return new FluidInfo(flowingFluid.getSource(), amount, tag);
        }

        return this;
    }

    public FluidInfo withAmount(long amount) {
        return new FluidInfo(fluid, amount, tag);
    }

    public boolean isEmpty() {
        return amount <= 0;
    }

}
