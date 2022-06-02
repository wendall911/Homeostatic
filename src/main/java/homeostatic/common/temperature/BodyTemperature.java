package homeostatic.common.temperature;

import net.minecraft.server.level.ServerPlayer;

import homeostatic.common.capabilities.Temperature;
import homeostatic.Homeostatic;
import homeostatic.util.InsulationHelper;
import homeostatic.util.TempHelper;
import homeostatic.util.WaterHelper;
import homeostatic.util.WetnessHelper;

public class BodyTemperature {

    public static final float LOW = 1.554216868F;
    public static final float NORMAL = 1.634457832F;
    public static final float HIGH = 1.799397591F;
    public static final float SCALDING = 2.557228916F;
    private EnvironmentData environmentData;
    private float coreTemperature;
    private float skinTemperature;
    private float lastTempChange;
    private int wetness;

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, Temperature tempData) {
        this(sp, environmentData, tempData, false, false);
    }

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, Temperature tempData, boolean updateCore, boolean updateSkin) {
        this.environmentData = environmentData;
        this.wetness = WetnessHelper.getWetness(sp);
        this.setCoreTemperature(tempData.getCoreTemperature());
        this.setSkinTemperature(sp, tempData.getSkinTemperature(), updateSkin);

        if (updateCore) {
            this.updateCoreTemperature();
        }
    }

    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    /*
     * Changes based on skinTemperature and ability to stabilize body temperature with food and water.
     * If sufficient levels of either food or water are met, core temperature changes at a slower rate.
     */
    public void updateCoreTemperature() {
        float rate = 0.01F;
        float temperature = environmentData.getLocalTemperature();

        // Change core cooling rate if warming or cooling
        if (temperature < Environment.PARITY) {
            rate = 0.08F; // Much faster rate for cold vs hot
            if (this.coreTemperature > this.NORMAL) {
                if (this.skinTemperature < this.coreTemperature) {
                    Homeostatic.LOGGER.warn("cooling down faster");
                    rate = rate * 4.0F; // Cool down faster
                } else {
                    Homeostatic.LOGGER.warn("warm up slower");
                    rate = rate / 2.0F; // Warm up slower
                }
            }
        }
        else if (temperature > Environment.PARITY) {
            if (this.coreTemperature < this.NORMAL) {
                if (this.skinTemperature > this.coreTemperature) {
                    Homeostatic.LOGGER.warn("warm up faster");
                    rate = rate * 4.0F; // Warm up faster
                } else {
                    Homeostatic.LOGGER.warn("cooling down slower");
                    rate = rate / 2.0F; // Cool down slower
                }
            }
        }

        float change = Math.abs(this.skinTemperature - this.coreTemperature) * rate;

        if (this.skinTemperature > this.coreTemperature) {
            // Warm up core;
            this.coreTemperature += change;
        }
        else if (this.skinTemperature < this.coreTemperature) {
            // Cool down core
            this.coreTemperature -= change;
        }
    }

    private void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    private void setSkinTemperature(ServerPlayer sp, float skinTemperature, boolean updateSkin) {
        float tempChange = 0.0F;
        float temperature = environmentData.getLocalTemperature();

        this.lastTempChange = tempChange;
        this.skinTemperature = skinTemperature;

        if (!updateSkin) return;

        if (this.environmentData.isSubmerged() || this.environmentData.isPartialSubmersion()) {
            tempChange = getWaterTemperatureSkinChange(temperature);
        }
        else {
            tempChange = getAirTemperatureSkinChange(sp, skinTemperature);
        }

        // Add food or water exhaustion depending on temperature change if warming or cooling
        if (temperature < Environment.PARITY) {
            tempChange = -(tempChange);

            if (this.skinTemperature <= this.NORMAL) { // shivering
                sp.getFoodData().addExhaustion(Math.abs(tempChange * 25.0F));
            }
            else if (this.skinTemperature > this.NORMAL) {
                tempChange = tempChange * 25.0F;
            }

            // Lose water very slowly when cooling
            if (Homeostatic.RANDOM.nextFloat() < 0.17F) {
                WaterHelper.updateWaterInfo(sp, 0.3F);
            }
        }
        else if (sp.getTicksFrozen() > 0 && this.skinTemperature >= this.NORMAL) {
            tempChange = -((this.skinTemperature - this.NORMAL) / 10.0F);
        }
        else {
            if (this.skinTemperature >= this.NORMAL) { // sweating
                WaterHelper.updateWaterInfo(sp, tempChange * 50.0F);
            }
            else if (this.skinTemperature < this.NORMAL) {
                tempChange = tempChange * 25.0F;
            }
        }

        this.lastTempChange = tempChange;
        this.skinTemperature += tempChange;

        if (temperature > this.LOW && this.skinTemperature > temperature) {
            this.skinTemperature = temperature;
        }
    }

    /*
     * Return cooling/heating rate per mc minute
     */
    public float getWaterTemperatureSkinChange(float temperature) {
        float change = 0.0F;
        double tempF = TempHelper.convertMcTemp(temperature, true);
        double minutes;



        if (temperature < Environment.PARITY) {
            minutes = 8.845477e-7 * Math.pow(tempF, 4.75641);

            change = (this.NORMAL - this.LOW) / (float) minutes;
        }
        else if (temperature > Environment.PARITY) {
            minutes = 2.981948 + (604.8711 - 2.981948)/(1 + Math.pow((tempF/109.9434), 50.72627));

            change = (this.HIGH - this.NORMAL) / (float) minutes;
        }

        if (change != 0.0F && environmentData.isPartialSubmersion()) {
            change = change / 2.0F;
        }

        return change;
    }

    public float getAirTemperatureSkinChange(ServerPlayer sp, float skinTemperature) {
        float temperature = environmentData.getLocalTemperature();
        float change = 0.0F;
        double localTempF = TempHelper.convertMcTemp(temperature, true);
        double parityF = TempHelper.convertMcTemp(Environment.PARITY, true);
        double minutes;
        float radiationModifier = (float) (environmentData.getEnvRadiation() / 5000) + 1.0F;
        double insulationModifier = InsulationHelper.getInsulationModifier(sp, this.wetness, temperature);
        float moisture = 0.0F;

        if (localTempF + insulationModifier < parityF) {
            localTempF += insulationModifier;

            minutes = 383.4897 + (12.38784 - 383.4897)/(1 + Math.pow((localTempF/43.26779), 8.271186));

            change = (this.NORMAL - this.LOW) / (float) minutes;
        }
        else if (localTempF - insulationModifier > parityF) {
            localTempF -= insulationModifier;

            minutes = 24.45765 + (599.3552 - 24.45765)/(1 + Math.pow((localTempF/109.1499), 27.47623));

            change = (this.HIGH - this.NORMAL) / (float) minutes;
        }

        /*
         * Radiation should have a heating effect, even if the env temperature is low.
         *
         * Once a radiation threshold is met, it starts to get hot fast!
         */
        if ((this.coreTemperature < this.NORMAL && environmentData.getEnvRadiation() > 0) || radiationModifier > 5.0F) {
            change = change * radiationModifier;
        }

        if (!sp.isInWaterRainOrBubble()) {
            moisture = 0.2F * (3.0F + radiationModifier);
        }

        if (moisture > 0.0F) {
            WetnessHelper.updateWetnessInfo(sp, moisture, false);
        }

        return change;
    }

}
