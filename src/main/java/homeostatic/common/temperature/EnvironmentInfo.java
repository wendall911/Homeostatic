package homeostatic.common.temperature;

public class EnvironmentInfo {

    private final boolean isUnderground;
    private final boolean isSheltered;
    private final double radiation;
    private final double waterVolume;

    public EnvironmentInfo(boolean isUnderground, boolean isSheltered, double radiation, double waterVolume) {
        this.isUnderground = isUnderground;
        this.isSheltered = isSheltered;
        this.radiation = radiation;
        this.waterVolume = waterVolume;
    }

    public boolean isUnderground() {
        return isUnderground;
    }

    public boolean isSheltered() {
        return isSheltered;
    }

    public double getRadiation() {
        return radiation;
    }

    public double getWaterVolume() {
        return waterVolume;
    }


    public String toString() {
        return "isUnderground: " + isUnderground + " isSheltered: " + isSheltered
                + " radiation: " + radiation + " waterVolume: " + waterVolume;
    }

}
