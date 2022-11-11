package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class Thermometer {

    private boolean hasThermometer = false;

    public static Tag writeNBT(Capability<Thermometer> capability, Thermometer instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("thermometer", instance.hasThermometer());

        return tag;
    }

    public static void readNBT(Capability<Thermometer> capability, Thermometer instance, Direction side, Tag nbt) {
        instance.setHasThermometer(((CompoundTag) nbt).getBoolean("thermometer"));
    }

    public boolean hasThermometer() {
        return hasThermometer;
    }

    public void setHasThermometer(boolean hasThermometer) {
        this.hasThermometer = hasThermometer;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final Thermometer instance;

        private final LazyOptional<Thermometer> handler;

        public Provider() {
            instance = new Thermometer();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CapabilityRegistry.THERMOMETER_CAPABILITY.orEmpty(cap, handler);
        }

        public Thermometer getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return Thermometer.writeNBT(CapabilityRegistry.THERMOMETER_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Thermometer.readNBT(CapabilityRegistry.THERMOMETER_CAPABILITY, instance, null, nbt);
        }

    }

}