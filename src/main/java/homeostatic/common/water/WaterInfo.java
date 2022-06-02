package homeostatic.common.water;

import net.minecraft.server.level.ServerPlayer;

public class WaterInfo {

    public static final int MAX_WATER_LEVEL = 20;
    public static final float MAX_SATURATION_LEVEL = 5.0F;

    private int waterLevel;
    private float waterSaturationLevel;
    private float waterExhaustionLevel;

    public WaterInfo(int waterLevel, float waterSaturationLevel, float waterExhaustionLevel) {
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

    public void update(float sweatLevel) {
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
            this.addExhaustion(sweatLevel);
        }

    }

    public void addExhaustion(float amount) {
        this.waterExhaustionLevel += amount;
    }

}
