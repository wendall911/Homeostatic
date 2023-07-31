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

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.TemperatureThreshold;
import homeostatic.util.DamageHelper;

public class Temperature {

    private float skinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float lastSkinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float coreTemperature = TemperatureThreshold.NORMAL.temperature;
    private float localTemperature = 0.0F;

    public static Tag writeNBT(Capability<Temperature> capability, Temperature instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("skinTemperature", instance.getSkinTemperature());
        tag.putFloat("lastSkinTemperature", instance.getLastSkinTemperature());
        tag.putFloat("coreTemperature", instance.getCoreTemperature());
        tag.putFloat("localTemperature", instance.getLocalTemperature());

        return tag;
    }

    public static void readNBT(Capability<Temperature> capability, Temperature instance, Direction side, Tag nbt) {
        instance.setSkinTemperature(((CompoundTag) nbt).getFloat("skinTemperature"));
        instance.setLastSkinTemperature(((CompoundTag) nbt).getFloat("lastSkinTemperature"));
        instance.setCoreTemperature(((CompoundTag) nbt).getFloat("coreTemperature"));
        instance.setLocalTemperature(((CompoundTag) nbt).getFloat("localTemperature"));
    }

    public void setSkinTemperature(float skinTemperature) {
        this.skinTemperature = skinTemperature;
    }

    public void setLastSkinTemperature(float lastSkinTemperature) {
        this.lastSkinTemperature = lastSkinTemperature;
    }

    public void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    public void setLocalTemperature(float temperature) {
        this.localTemperature = temperature;
    }

    public void setTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        this.setSkinTemperature(bodyTemperature.getSkinTemperature());
        this.setLastSkinTemperature(bodyTemperature.getLastSkinTemperature());
        this.setCoreTemperature(bodyTemperature.getCoreTemperature());
        this.setLocalTemperature(localTemperature);
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    public float getLastSkinTemperature() {
        return lastSkinTemperature;
    }

    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    public float getLocalTemperature() {
        return this.localTemperature;
    }

    public void checkTemperatureLevel(Player player) {
        if (this.coreTemperature < TemperatureThreshold.LOW.temperature) {
            player.setTicksFrozen(player.getTicksFrozen() + 5);
        }
        else if (this.coreTemperature > TemperatureThreshold.HIGH.temperature) {
            float amount = (1.0F + (this.coreTemperature - TemperatureThreshold.HIGH.temperature)) * 0.5F;

            player.hurt(new DamageSource(DamageHelper.getHolder(player.getServer(), HomeostaticDamageTypes.HYPERTHERMIA_KEY)), amount);
        }
        if (this.skinTemperature > TemperatureThreshold.SCALDING.temperature) {
            float amount = (1.0F + (this.skinTemperature - TemperatureThreshold.SCALDING.temperature)) * 0.25F;

            player.hurt(new DamageSource(DamageHelper.getHolder(player.getServer(), HomeostaticDamageTypes.SCALDING_KEY)), amount);
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
