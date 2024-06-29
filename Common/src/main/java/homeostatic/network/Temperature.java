package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.TemperatureThreshold;
import homeostatic.util.DamageHelper;

public class Temperature implements ITemperature {

    private float skinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float lastSkinTemperature = TemperatureThreshold.NORMAL.temperature;
    private float coreTemperature = TemperatureThreshold.NORMAL.temperature;
    private float localTemperature = 0.0F;

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
    public void setTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        this.setSkinTemperature(bodyTemperature.getSkinTemperature());
        this.setLastSkinTemperature(bodyTemperature.getLastSkinTemperature());
        this.setCoreTemperature(bodyTemperature.getCoreTemperature());
        this.setLocalTemperature(localTemperature);
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

    @Override
    public ListTag write() {
        ListTag listTag = new ListTag();
        CompoundTag tag = new CompoundTag();

        tag.putFloat("skinTemperature", this.getSkinTemperature());
        tag.putFloat("lastSkinTemperature", this.getLastSkinTemperature());
        tag.putFloat("coreTemperature", this.getCoreTemperature());
        tag.putFloat("localTemperature", this.getLocalTemperature());

        listTag.add(tag);

        return listTag;
    }

    @Override
    public void read(ListTag nbt) {
        CompoundTag tag = nbt.getCompound(0);

        this.setSkinTemperature(tag.getFloat("skinTemperature"));
        this.setLastSkinTemperature(tag.getFloat("lastSkinTemperature"));
        this.setCoreTemperature(tag.getFloat("coreTemperature"));
        this.setLocalTemperature(tag.getFloat("localTemperature"));
    }

}
