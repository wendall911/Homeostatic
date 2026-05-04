package homeostatic.network;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.TemperatureRange;
import homeostatic.common.temperature.TemperatureThreshold;
import homeostatic.util.DamageHelper;

public class Temperature implements ITemperature {

    private float skinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float lastSkinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float coreTemperature = TemperatureThreshold.NORMAL.temperature;
    private float localTemperature = 0.0F;
    private double relativeHumidity = 0.0D;

    @Override
    public void setSkinTemperature(float skinTemperature) {
        this.skinTemperature = skinTemperature;
    }

    @Override
    public void setLastSkinTemperature(float lastSkinTemperature) {
        this.lastSkinTemperature = lastSkinTemperature;
    }

    @Override
    public void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    @Override
    public void setLocalTemperature(float temperature) {
        this.localTemperature = temperature;
    }

    @Override
    public void setRelativeHumidity(double relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    @Override
    public void setTemperatureData(EnvironmentData environmentData, BodyTemperature bodyTemperature) {
        this.setSkinTemperature(bodyTemperature.getSkinTemperature());
        this.setLastSkinTemperature(bodyTemperature.getLastSkinTemperature());
        this.setCoreTemperature(bodyTemperature.getCoreTemperature());
        this.setLocalTemperature(environmentData.getLocalTemperature());
        this.setRelativeHumidity(environmentData.getRelativeHumidity());
    }

    @Override
    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    @Override
    public float getLastSkinTemperature() {
        return lastSkinTemperature;
    }

    @Override
    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    @Override
    public float getLocalTemperature() {
        return this.localTemperature;
    }

    @Override
    public double getRelativeHumidity() {
        return this.relativeHumidity;
    }

    @Override
    public void checkTemperatureLevel(ServerPlayer player) {
        if (this.coreTemperature < TemperatureThreshold.LOW.temperature
                && !player.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.FROST_RESISTANCE))) {
            player.setTicksFrozen(player.getTicksFrozen() + 5);
        }
        else if (this.coreTemperature > TemperatureThreshold.HIGH.temperature) {
            float amount = (1.0F + (this.coreTemperature - TemperatureThreshold.HIGH.temperature)) * 0.5F;

            player.hurtServer(
                player.level(),
                new DamageSource(
                    DamageHelper.getHolder(player.level().getServer(), HomeostaticDamageTypes.HYPERTHERMIA)
                ),
                amount
            );
        }
        if (this.skinTemperature > TemperatureThreshold.SCALDING.temperature) {
            float amount = (1.0F + (this.skinTemperature - TemperatureThreshold.SCALDING.temperature)) * 0.25F;

            player.hurtServer(
                player.level(),
                new DamageSource(
                    DamageHelper.getHolder(player.level().getServer(), HomeostaticDamageTypes.SCALDING)
                ),
                amount
            );
        }
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putFloat("skinTemperature", this.getSkinTemperature());
        tag.putFloat("lastSkinTemperature", this.getLastSkinTemperature());
        tag.putFloat("coreTemperature", this.getCoreTemperature());
        tag.putFloat("localTemperature", this.getLocalTemperature());
        tag.putDouble("relativeHumidity", this.getRelativeHumidity());

        return tag;
    }

    @Override
    public ValueOutput write(ValueOutput valueOutput) {
        valueOutput.putFloat("skinTemperature", this.getSkinTemperature());
        valueOutput.putFloat("lastSkinTemperature", this.getLastSkinTemperature());
        valueOutput.putFloat("coreTemperature", this.getCoreTemperature());
        valueOutput.putFloat("localTemperature", this.getLocalTemperature());
        valueOutput.putDouble("relativeHumidity", this.getRelativeHumidity());

        return valueOutput;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setSkinTemperature(tag.getFloat("skinTemperature").orElseThrow());
        this.setLastSkinTemperature(tag.getFloat("lastSkinTemperature").orElseThrow());
        this.setCoreTemperature(tag.getFloat("coreTemperature").orElseThrow());
        this.setLocalTemperature(tag.getFloat("localTemperature").orElseThrow());
        this.setRelativeHumidity(tag.getDouble("relativeHumidity").orElseThrow());
    }

    @Override
    public void read(ValueInput valueInput) {
        this.setSkinTemperature(valueInput.getFloatOr("skinTemperature", TemperatureThreshold.NORMAL.temperature));
        this.setLastSkinTemperature(valueInput.getFloatOr("lastSkinTemperature", TemperatureThreshold.NORMAL.temperature));
        this.setCoreTemperature(valueInput.getFloatOr("coreTemperature", TemperatureThreshold.NORMAL.temperature));
        this.setLocalTemperature(valueInput.getFloatOr("localTemperature", TemperatureRange.PARITY.temperature));
        this.setRelativeHumidity(valueInput.getDoubleOr("relativeHumidity", 0.0D));
    }

}
