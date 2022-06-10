package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import homeostatic.common.wetness.WetnessInfo;

public class Wetness {

    public static Tag writeNBT(Capability<Wetness> capability, Wetness instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putInt("wetnessLevel", instance.getWetnessLevel());
        tag.putFloat("moistureLevel", instance.getMoistureLevel());

        return tag;
    }

    public static void readNBT(Capability<Wetness> capability, Wetness instance, Direction side, Tag nbt) {
        instance.setWetnessLevel(((CompoundTag) nbt).getInt("wetnessLevel"));
        instance.setMoistureLevel(((CompoundTag) nbt).getFloat("moistureLevel"));
    }

    private int wetnessLevel = 0;
    private float moistureLevel = 0.0F;

    public void setWetnessLevel(int wetnessLevel) {
        this.wetnessLevel = wetnessLevel;
    }

    public void setMoistureLevel(float moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public void setWetnessData(WetnessInfo wetnessInfo) {
        this.setWetnessLevel(wetnessInfo.getWetnessLevel());
        this.setMoistureLevel(wetnessInfo.getMoistureLevel());
    }

    public int getWetnessLevel() {
        return this.wetnessLevel;
    }

    public float getMoistureLevel() {
        return this.moistureLevel;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final Wetness instance;

        private final LazyOptional<Wetness> handler;

        public Provider() {
            instance = new Wetness();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.WETNESS_CAPABILITY.orEmpty(cap, handler);
        }

        public Wetness getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return Wetness.writeNBT(CapabilityRegistry.WETNESS_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Wetness.readNBT(CapabilityRegistry.WETNESS_CAPABILITY, instance, null, nbt);
        }

    }

}
