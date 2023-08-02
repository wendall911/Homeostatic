package homeostatic.common.temperature;

public enum TemperatureThreshold {

    LOW(1.554216868F),
    WARNING_LOW(1.589879518F),
    NORMAL(1.634457832F),
    HIGH(1.799397591F),
    WARNING_HIGH(1.765963856F),
    SCALDING_WARNING(2.222891566F),
    SCALDING(2.557228916F);

    public final float temperature;

    TemperatureThreshold(float temperature) {
        this.temperature = temperature;
    }

}
