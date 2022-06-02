package homeostatic.common.wetness;

public class WetnessInfo {

    public static final int MAX_WETNESS_LEVEL = 20;
    public static final float MAX_MOISTURE_LEVEL = 20.0F;
    public static final float MIN_MOISTURE_LEVEL = -20.0F;

    private int wetnessLevel;
    private float moistureLevel;

    public WetnessInfo(int wetnessLevel, float moistureLevel) {
        this.wetnessLevel = wetnessLevel;
        this.moistureLevel = moistureLevel;
    }

    public int getWetnessLevel() {
        return wetnessLevel;
    }

    public void setWetnessLevel(int wetnessLevel) {
        this.wetnessLevel = Math.max(wetnessLevel, 0);
    }

    public float getMoistureLevel() {
        return moistureLevel;
    }

    public void increaseMoisture(float moistureLevel) {
        if (this.moistureLevel > 4.0F) {
            this.moistureLevel -= 4.0F;
            this.setWetnessLevel(Math.min(this.wetnessLevel + 1, this.MAX_WETNESS_LEVEL));
        }

        if (moistureLevel > 0.0F) {
            this.addMoisture(moistureLevel);
        }
    }

    public void decreaseMoisture(float moistureLevel) {
        if (this.moistureLevel < -4.0F) {
            this.moistureLevel += 4.0F;
            this.setWetnessLevel(Math.max(this.wetnessLevel - 1, 0));
        }

        if (moistureLevel > 0.0F) {
            this.removeMoisture(moistureLevel);
        }
    }

    public void addMoisture(float amount) {
        this.moistureLevel = Math.min(this.moistureLevel + amount, this.MAX_MOISTURE_LEVEL);
    }

    public void removeMoisture(float amount) {
        if (this.wetnessLevel > 0) {
            this.moistureLevel = Math.max(this.moistureLevel - amount, this.MIN_MOISTURE_LEVEL);
        }
        else {
            this.moistureLevel = 0.0F;
        }
    }

}
