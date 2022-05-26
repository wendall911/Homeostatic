package homeostatic.common.temperature;

import net.minecraft.server.level.ServerPlayer;

import homeostatic.common.water.WaterData;
import homeostatic.util.TempHelper;

public class BodyTemperature {

    public static final float LOW = 1.554216868F;
    public static final float NORMAL = 1.634457832F;
    public static final float HIGH = 1.799397591F;
    private EnvironmentData environmentData;
    private WaterData waterData;
    private float coreTemperature;
    private float skinTemperature;
    private float lastTempChange;

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, WaterData waterData) {
        this(sp, environmentData, waterData, NORMAL, NORMAL, 0);
    }

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, WaterData waterData, float coreTemp, float skinTemp, int ticks) {
        this.environmentData = environmentData;
        this.waterData = waterData;
        this.setCoreTemperature(coreTemp);
        this.setSkinTemperature(sp, skinTemp, ticks);

        // Update core temperature every 200 ticks
        if (ticks % 200 == 0) {
            this.updateCoreTemperature();
        }
    }

    /*
     * Calculate new body temperature based on core temperature and skin temperature.
     */
    public float getBodyTemperature() {
        return ((this.coreTemperature * 9.0F) + this.skinTemperature) / 10.0F;
    }

    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    public WaterData getWaterData() {
        return this.waterData;
    }

    /*
     * Changes based on skinTemperature and ability to stabilize body temperature with food and water.
     * If sufficient levels of either food or water are met, core temperature changes at a slower rate.
     */
    public void updateCoreTemperature() {
        float rate = 0.05F;

        // Change core cooling rate if warming or cooling
        if (lastTempChange < 0.0F && this.coreTemperature <= NORMAL) { // Cold and cooling
            rate -= 0.03F;
        }
        else if (lastTempChange > 0.0F && this.skinTemperature >= NORMAL) { // Hot and heating
            rate -= 0.03F;
        }

        if (this.skinTemperature > this.coreTemperature) {
            // Warm up core;
            this.coreTemperature += (this.skinTemperature - this.coreTemperature) * rate;
        }
        else if (this.skinTemperature < this.coreTemperature) {
            // Cool down core
            this.coreTemperature -= (this.coreTemperature - this.skinTemperature) * rate;
        }
    }

    private void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    private void setSkinTemperature(ServerPlayer sp, float skinTemperature, int ticks) {
        float tempChange = 0.0F;

        if (ticks == 0) {
            this.lastTempChange = tempChange;
            this.skinTemperature = skinTemperature;
            return;
        }

        if (this.environmentData.isSubmerged()) {
            tempChange = getWaterTemperatureSkinChange(environmentData.getLocalTemperature());
        }
        else {
            tempChange = getAirTemperatureSkinChange(environmentData.getLocalTemperature());
        }

        // Add food or water exhaustion depending on temperature change if warming or cooling
        if (tempChange < 0.0F && this.coreTemperature <= NORMAL) { // shivering
            sp.getFoodData().addExhaustion((tempChange * -1.0F) * 25.0F);
        }
        else if (tempChange > 0.0F && this.coreTemperature >= NORMAL) { // sweating
            this.waterData.update(sp, tempChange * 25.0F);
        }

        this.lastTempChange = tempChange;
        this.skinTemperature = skinTemperature + tempChange;
    }

    /*
     * Return cooling/heating rate per mc minute (16 ticks)
     */
    public float getWaterTemperatureSkinChange(float temperature) {
        float change = 0.0F;
        double tempF = TempHelper.convertMcTemp(temperature, true);
        double minutes;

        if (temperature < Environment.PARITY) {
            minutes = 8.845477e-7 * Math.pow(tempF, 4.75641);

            change = -((this.NORMAL - this.LOW) / (float) minutes);
        }
        else if (temperature > Environment.PARITY) {
            minutes = 2.981948 + (604.8711 - 2.981948)/(1 + Math.pow((tempF/109.9434), 50.72627));

            change = (this.HIGH - this.NORMAL) / (float) minutes;
        }

        return change;
    }

    public float getAirTemperatureSkinChange(float temperature) {
        float change = 0.0F;
        double tempF = TempHelper.convertMcTemp(temperature, true);
        double parityF = TempHelper.convertMcTemp(Environment.PARITY, true);
        double minutes;
        double clothingTemp = 12;

        if (tempF < parityF) {
            tempF += clothingTemp;
            if (tempF >= parityF) return change;

            minutes = 383.4897 + (12.38784 - 383.4897)/(1 + Math.pow((tempF/43.26779), 8.271186));

            change = -((this.NORMAL - this.LOW) / (float) minutes);
        }
        else if (tempF > parityF) {
            tempF -= clothingTemp;
            if (tempF <= parityF) return change;

            minutes = 24.45765 + (599.3552 - 24.45765)/(1 + Math.pow((tempF/109.1499), 27.47623));

            change = (this.HIGH - this.NORMAL) / (float) minutes;
        }

        return change;
    }

}
