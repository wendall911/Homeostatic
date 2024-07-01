package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.common.water.WaterInfo;
import homeostatic.util.DamageHelper;

public class Water implements IWater {

    private int waterLevel = WaterInfo.MAX_WATER_LEVEL;
    private float waterSaturationLevel = WaterInfo.MAX_SATURATION_LEVEL;
    private float waterExhaustionLevel = 0.0F;

    @Override
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public void increaseWaterLevel(int level) {
        this.waterLevel = Math.min(this.waterLevel + level, WaterInfo.MAX_WATER_LEVEL);
    }

    @Override
    public void increaseSaturationLevel(float level) {
        this.waterSaturationLevel = Math.min(this.waterSaturationLevel + level, WaterInfo.MAX_SATURATION_LEVEL);
    }

    @Override
    public void setWaterSaturationLevel(float waterSaturationLevel) {
        this.waterSaturationLevel = waterSaturationLevel;
    }

    @Override
    public void setWaterExhaustionLevel(float waterExhaustionLevel) {
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    @Override
    public void setWaterData(WaterInfo waterInfo) {
        this.setWaterLevel(waterInfo.getWaterLevel());
        this.setWaterSaturationLevel(waterInfo.getWaterSaturationLevel());
        this.setWaterExhaustionLevel(waterInfo.getWaterExhaustionLevel());
    }

    @Override
    public int getWaterLevel() {
        return this.waterLevel;
    }

    @Override
    public float getWaterExhaustionLevel() {
        return this.waterExhaustionLevel;
    }

    @Override
    public float getWaterSaturationLevel() {
        return this.waterSaturationLevel;
    }

    @Override
    public void checkWaterLevel(ServerPlayer player) {
        if (this.waterLevel <= 0) {
            player.hurt(new DamageSource(DamageHelper.getHolder(player.getServer(), HomeostaticDamageTypes.DEHYDRATION_KEY)), 1.0F);
        }
    }

    @Override
    public ListTag write() {
        ListTag listTag = new ListTag();
        CompoundTag tag = new CompoundTag();

        write(tag);
        listTag.add(tag);

        return listTag;
    }

    public void write(CompoundTag tag) {
        tag.putInt("waterLevel", this.getWaterLevel());
        tag.putFloat("waterExhaustion", this.getWaterExhaustionLevel());
        tag.putFloat("waterSaturation", this.getWaterSaturationLevel());
    }

    @Override
    public void read(ListTag nbt) {
        read(nbt.getCompound(0));
    }

    public void read(CompoundTag tag) {
        this.setWaterLevel(tag.getInt("waterLevel"));
        this.setWaterExhaustionLevel(tag.getFloat("waterExhaustion"));
        this.setWaterSaturationLevel(tag.getFloat("waterSaturation"));
    }

}
