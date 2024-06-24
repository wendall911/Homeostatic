package homeostatic.common.temperature;

public enum TemperatureDirection {

    WARMING(0.025F, "↥"),
    WARMING_NORMALLY(0.00625F, "ˆ"),
    WARMING_RAPIDLY(0.2F, "⇈"),
    NONE(0.0F, "·"),
    COOLING(0.0125F, "↧"),
    COOLING_NORMALLY(0.00625F, "ˬ"),
    COOLING_RAPIDLY(0.2F, "⇊");

    public final float coreRate;
    public final String icon;

    TemperatureDirection(float coreRate, String icon) {
        this.coreRate = coreRate;
        this.icon = icon;
    }

}
