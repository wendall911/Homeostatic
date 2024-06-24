package homeostatic.common.temperature;

public enum TemperatureRange {

    COLD(0.0F),
    EXTREME_HEAT(2.557F),
    EXTREME_COLD(-0.3403614458F),
    HOT(1.888F),
    PARITY(1.108F),
    PARITY_LOW(0.997F),
    PARITY_HIGH(1.220F);

    public final float temperature;

    TemperatureRange(float temperature) {
        this.temperature = temperature;
    }

}
