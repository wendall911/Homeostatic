package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import homeostatic.common.water.WaterInfo;
import homeostatic.config.ConfigHandler;

public class Water {

    public static Tag writeNBT(Capability<Water> capability, Water instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putInt("waterLevel", instance.getWaterLevel());
        tag.putFloat("waterExhaustion", instance.getWaterExhaustionLevel());
        tag.putFloat("waterSaturation", instance.getWaterSaturationLevel());

        return tag;
    }

    public static void readNBT(Capability<Water> capability, Water instance, Direction side, Tag nbt) {
        instance.setWaterLevel(((CompoundTag) nbt).getInt("waterLevel"));
        instance.setWaterExhaustionLevel(((CompoundTag) nbt).getFloat("waterExhaustion"));
        instance.setWaterSaturationLevel(((CompoundTag) nbt).getFloat("waterSaturation"));
    }

    private int waterLevel = WaterInfo.MAX_WATER_LEVEL;
    private float waterSaturationLevel = WaterInfo.MAX_SATURATION_LEVEL;
    private float waterExhaustionLevel = 0.0F;

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void increaseWaterLevel() {
        this.waterLevel = Math.min(this.waterLevel + ConfigHandler.Server.drinkAmount(), WaterInfo.MAX_WATER_LEVEL);
    }

    public void increaseCleanWaterLevel() {
        this.waterLevel = Math.min(this.waterLevel + (ConfigHandler.Server.drinkAmount() * 3), WaterInfo.MAX_WATER_LEVEL);
    }

    public void increaseSaturationLevel() {
        this.waterSaturationLevel = Math.min(this.waterSaturationLevel + ConfigHandler.Server.drinkSaturation(), WaterInfo.MAX_SATURATION_LEVEL);
    }

    public void setWaterSaturationLevel(float waterSaturationLevel) {
        this.waterSaturationLevel = waterSaturationLevel;
    }

    public void setWaterExhaustionLevel(float waterExhaustionLevel) {
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public void setWaterData(WaterInfo waterInfo) {
        this.setWaterLevel(waterInfo.getWaterLevel());
        this.setWaterSaturationLevel(waterInfo.getWaterSaturationLevel());
        this.setWaterExhaustionLevel(waterInfo.getWaterExhaustionLevel());
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public float getWaterExhaustionLevel() {
        return this.waterExhaustionLevel;
    }

    public float getWaterSaturationLevel() {
        return this.waterSaturationLevel;
    }

    public void checkWaterLevel(ServerPlayer player) {
        if (this.waterLevel <= 0) {
            player.hurt(new DamageSource("dehydration").bypassArmor().bypassMagic(), 1.0F);
        }
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final Water instance;

        private final LazyOptional<Water> handler;

        public Provider() {
            instance = new Water();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.WATER_CAPABILITY.orEmpty(cap, handler);
        }

        public Water getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return Water.writeNBT(CapabilityRegistry.WATER_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Water.readNBT(CapabilityRegistry.WATER_CAPABILITY, instance, null, nbt);
        }

    }

}
