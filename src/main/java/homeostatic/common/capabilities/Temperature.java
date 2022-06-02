package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import homeostatic.common.temperature.BodyTemperature;

public class Temperature {

    public static Tag writeNBT(Capability<Temperature> capability, Temperature instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("skinTemperature", instance.getSkinTemperature());
        tag.putFloat("coreTemperature", instance.getCoreTemperature());
        tag.putFloat("localTemperature", instance.getLocalTemperature());

        return tag;
    }

    public static void readNBT(Capability<Temperature> capability, Temperature instance, Direction side, Tag nbt) {
        instance.setSkinTemperature(((CompoundTag) nbt).getFloat("skinTemperature"));
        instance.setCoreTemperature(((CompoundTag) nbt).getFloat("coreTemperature"));
        instance.setLocalTemperature(((CompoundTag) nbt).getFloat("localTemperature"));
    }

    private float skinTemperature = BodyTemperature.NORMAL;
    private float coreTemperature = BodyTemperature.NORMAL;
    private float localTemperature = 0.0F;

    public void setSkinTemperature(float skinTemperature) {
        this.skinTemperature = skinTemperature;
    }

    public void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    public void setLocalTemperature(float temperature) {
        this.localTemperature = temperature;
    }

    public void setTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        this.setCoreTemperature(bodyTemperature.getCoreTemperature());
        this.setSkinTemperature(bodyTemperature.getSkinTemperature());
        this.setLocalTemperature(localTemperature);
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    public float getLocalTemperature() {
        return this.localTemperature;
    }

    public void checkTemperatureLevel(Player player) {
        if (this.coreTemperature < BodyTemperature.LOW) {
            player.setTicksFrozen(player.getTicksFrozen() + 5);
        }
        else if (this.coreTemperature > BodyTemperature.HIGH) {
            float amount = (1.0F + (this.coreTemperature - BodyTemperature.HIGH)) * 0.5F;

            player.hurt(new DamageSource("hyperthermia").bypassArmor().bypassMagic(), amount);
        }
        if (this.skinTemperature > BodyTemperature.SCALDING) {
            float amount = (1.0F + (this.skinTemperature - BodyTemperature.SCALDING)) * 0.35F;

            player.hurt(new DamageSource("heat").bypassArmor().bypassMagic(), amount);
        }
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final Temperature instance;

        private final LazyOptional<Temperature> handler;

        public Provider() {
            instance = new Temperature();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.TEMPERATURE_CAPABILITY.orEmpty(cap, handler);
        }

        public Temperature getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return Temperature.writeNBT(CapabilityRegistry.TEMPERATURE_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Temperature.readNBT(CapabilityRegistry.TEMPERATURE_CAPABILITY, instance, null, nbt);
        }

    }

}
