package homeostatic.common.water;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import homeostatic.Homeostatic;

public class WaterData {

    private int waterLevel = 20;
    private float waterSaturationLevel;
    private float waterExhaustionLevel;

    public WaterData() {
        this(20, 5.0F, 0.0F);
    }

    public WaterData(int waterLevel, float waterSaturationLevel, float waterExhaustionLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public float getWaterExhaustionLevel() {
        return waterExhaustionLevel;
    }

    public float getWaterSaturationLevel() {
        return waterSaturationLevel;
    }

    public void update(ServerPlayer sp, float sweatLevel) {
        if (this.waterExhaustionLevel > 4.0F) {
            this.waterExhaustionLevel -= 4.0F;
            if (this.waterSaturationLevel > 0.0F) {
                this.waterSaturationLevel = Math.max(this.waterSaturationLevel - 1.0F, 0.0F);
            }
            else {
                this.waterLevel = Math.max(this.waterLevel - 1, 0);
            }
        }

        if (sweatLevel > 0.0F) {
            if (this.waterSaturationLevel > 0.0F) {
                this.addExhaustion(Math.min(this.waterSaturationLevel, sweatLevel));
            }
            else {
                this.addExhaustion(sweatLevel);
            }
        }
        else if (this.waterLevel <= 0) {
            sp.hurt(new DamageSource("Dehydration"), 1.0F);
        }

    }

    public void addExhaustion(float amount) {
        this.waterExhaustionLevel += amount;
    }

}
