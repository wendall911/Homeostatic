package homeostatic.common.temperature;

public record EnvironmentInfo(boolean isUnderground, boolean isSheltered, double radiation, double waterVolume) {

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
