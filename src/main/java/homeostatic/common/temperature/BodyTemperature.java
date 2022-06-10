package homeostatic.common.temperature;

import net.minecraft.server.level.ServerPlayer;

import homeostatic.common.biome.BiomeData;
import homeostatic.common.capabilities.Temperature;
import homeostatic.Homeostatic;
import homeostatic.util.InsulationHelper;
import homeostatic.util.TempHelper;
import static homeostatic.util.TempHelper.TemperatureDirection;
import homeostatic.util.WaterHelper;
import homeostatic.util.WetnessHelper;

public class BodyTemperature {

    public static final float LOW = 1.554216868F;
    public static final float NORMAL = 1.634457832F;
    public static final float HIGH = 1.799397591F;
    public static final float SCALDING = 2.557228916F;

    private final EnvironmentData environmentData;
    private final TemperatureDirection skinTemperatureDirection;
    private final int wetness;
    private float coreTemperature;
    private float skinTemperature;
    private float lastSkinTemperature;

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, Temperature tempData) {
        this(sp, environmentData, tempData, false, false);
    }

    public BodyTemperature(ServerPlayer sp, EnvironmentData environmentData, Temperature tempData, boolean updateCore, boolean updateSkin) {
        this.environmentData = environmentData;
        this.wetness = WetnessHelper.getWetness(sp);
        this.setCoreTemperature(tempData.getCoreTemperature());
        this.skinTemperatureDirection = TempHelper.getSkinTemperatureDirection(environmentData.getLocalTemperature(), tempData.getSkinTemperature());
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

    public float getLastSkinTemperature() {
        return lastSkinTemperature;
    }

    /*
     * Changes based on skinTemperature and ability to stabilize body temperature with food and water.
     * If sufficient levels of either food or water are met, core temperature changes at a slower rate.
     */
    public void updateCoreTemperature() {
        final TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                this.lastSkinTemperature, this.coreTemperature, this.skinTemperature);
        float diff = Math.abs(this.skinTemperature - this.coreTemperature);
        float change;

        if (coreTemperatureDirection.coreRate > 0.0F) {
            change = diff * coreTemperatureDirection.coreRate;
        }
        else {
            change = diff * 0.1F;
        }

        //Homeostatic.LOGGER.debug("updateCore: %s %s %s", this.coreTemperatureDirection, this.coreTemperatureDirection.coreRate, change);

        if (this.skinTemperature < this.coreTemperature) {
            this.coreTemperature -= change;

            // Only cool core rapidly down to NORMAL, then clamp
            if (coreTemperatureDirection == TemperatureDirection.COOLING_RAPIDLY) {
                this.coreTemperature = Math.max(this.coreTemperature, BodyTemperature.NORMAL);
            }
        }
        else {
            this.coreTemperature += change;

            // Only warm core rapidly up to NORMAL, then clamp
            if (coreTemperatureDirection == TemperatureDirection.WARMING_RAPIDLY) {
                this.coreTemperature = Math.min(this.coreTemperature, BodyTemperature.NORMAL);
            }
        }
    }

    private void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    private void setSkinTemperature(ServerPlayer sp, float skinTemperature, boolean updateSkin) {
        float tempChange;
        float localTemperature = environmentData.getLocalTemperature();
        boolean canSweat = skinTemperature >= BodyTemperature.NORMAL && this.wetness == 0;
        boolean inWater = this.environmentData.isSubmerged() || this.environmentData.isPartialSubmersion();

        this.lastSkinTemperature = skinTemperature;
        this.skinTemperature = skinTemperature;

        if (!updateSkin) return;

        double insulationModifier = InsulationHelper.getInsulationModifier(sp, this.wetness, this.skinTemperatureDirection, localTemperature);

        if (inWater) {
            tempChange = getWaterTemperatureSkinChange(insulationModifier);
        }
        else {
            tempChange = getAirTemperatureSkinChange(sp, insulationModifier);
        }

        if (tempChange > 0.0F) {
            switch (skinTemperatureDirection) {
                case COOLING -> {
                    tempChange = Math.max(-(tempChange) * 70.0F, -(BiomeData.MC_DEGREE * 3.0F));

                    if (this.wetness > 0) {
                        tempChange = tempChange * (float) (1.0 + (this.wetness / 20.0));
                    }
                }
                case COOLING_RAPIDLY -> {
                    tempChange = Math.max(-(tempChange) * 100.0F, -(BiomeData.MC_DEGREE * 4.0F));

                    if (this.wetness > 0) {
                        tempChange = tempChange * (float) (2.0 + (this.wetness / 20.0));
                    }
                }
                case COOLING_NORMALLY -> {
                    tempChange = -(tempChange);
                    if (!canSweat) {
                        // Shivering
                        float exhaustion = Math.abs(Math.min(tempChange * 200.0F, 0.2F));

                        Homeostatic.LOGGER.debug("shivering ... adding exhaustion: %s", exhaustion);
                        sp.getFoodData().addExhaustion(exhaustion);
                    }
                }
                case WARMING -> {
                    if (canSweat) {
                        // Moderate Sweating
                        Homeostatic.LOGGER.debug("WARMING sweating: %s", Math.min(tempChange * 150.0F, 0.3F));
                        WaterHelper.updateWaterInfo(sp, Math.min(tempChange * 150.0F, 0.3F));
                    }
                    else {
                        tempChange = Math.min(tempChange * 70.0F, BiomeData.MC_DEGREE * 3.0F);
                    }
                }
                case WARMING_RAPIDLY -> tempChange = Math.min(tempChange * 100.0F, BiomeData.MC_DEGREE * 4.0F);
                case WARMING_NORMALLY -> {
                    if (canSweat) {
                        // Sweating
                        Homeostatic.LOGGER.debug("WARMING_NORMALLY sweating: %s", Math.min(tempChange * 100.0F, 0.1F));
                        WaterHelper.updateWaterInfo(sp, Math.min(tempChange * 100.0F, 0.1F));
                    }
                }
            }
        }

        // Lose water slowly under cooling or normal conditions
        if (localTemperature < Environment.PARITY_HIGH && !canSweat && Homeostatic.RANDOM.nextFloat() < 0.17F) {
            Homeostatic.LOGGER.debug("Random cool water loss: %s", 0.05F);
            WaterHelper.updateWaterInfo(sp, 0.05F);
        }

        // POWDERED SNOW COOLING
        if (sp.isInPowderSnow && this.skinTemperature >= NORMAL) {
            tempChange = -((this.skinTemperature - NORMAL) / 10.0F);
        }

        if (tempChange == 0.0F) {
            if (this.skinTemperature < NORMAL) {
                tempChange = (NORMAL - this.skinTemperature) / 20.0F;
            }
            else if (this.skinTemperature > NORMAL) {
                tempChange = -((this.skinTemperature - NORMAL) / 40.0F);
            }
        }

        this.skinTemperature += tempChange;

        /*
         * Normalize skin temperature for warm temperatures, either through sweat or being wet.
         * Since we don't calculate wind, this is what we get for now.
         */
        if (this.skinTemperature > NORMAL
                && this.skinTemperature > this.lastSkinTemperature
                && localTemperature < this.skinTemperature) {
            float coolingRate = Math.max((this.skinTemperature - NORMAL) / 20.0F, BiomeData.MC_DEGREE);
            if (canSweat) {
                Homeostatic.LOGGER.debug("sweating to normalize: %s", Math.min(tempChange * 150.0F, 0.2F));
                WaterHelper.updateWaterInfo(sp, Math.min(tempChange * 150.0F, 0.2F));
                this.skinTemperature = Math.max(this.skinTemperature - coolingRate, NORMAL);
            }
            else {
                this.skinTemperature = Math.max(this.skinTemperature - (coolingRate * 2.0F), NORMAL);
            }
        }

        //Homeostatic.LOGGER.debug("update skin: %s %s", tempChange, skinTemperatureDirection);
    }

    /*
     * Return cooling/heating rate per mc minute
     */
    public float getWaterTemperatureSkinChange(double insulationModifier) {
        float change = 0.0F;
        float localTemperature = environmentData.getLocalTemperature();
        double localTempF = TempHelper.convertMcTemp(localTemperature, true);
        double parityTempF = TempHelper.convertMcTemp(Environment.PARITY, true);
        double minutes;

        if (this.skinTemperatureDirection == TemperatureDirection.NONE) return change;

        if (localTemperature < Environment.PARITY) {
            double temp = Math.min(localTempF + insulationModifier, parityTempF);

            minutes = 8.845477e-7 * Math.pow(temp, 4.75641);

            change = (NORMAL - LOW) / (float) minutes;
        }
        else {
            double temp = Math.max(localTempF - insulationModifier, parityTempF);

            minutes = 2.981948 + (604.8711 - 2.981948) / (1 + Math.pow((temp / 109.9434), 50.72627));

            change = (HIGH - NORMAL) / (float) minutes;
        }

        if (change != 0.0F && environmentData.isPartialSubmersion()) {
            change = change / 2.0F;
        }

        return change;
    }

    public float getAirTemperatureSkinChange(ServerPlayer sp, double insulationModifier) {
        float localTemperature = environmentData.getLocalTemperature();
        float change = 0.0F;
        double localTempF = TempHelper.convertMcTemp(localTemperature, true);
        double parityTempF = TempHelper.convertMcTemp(Environment.PARITY, true);
        double extremeTempF = TempHelper.convertMcTemp(Environment.EXTREME_HEAT, true);
        double minutes;
        float radiationModifier = (float) (environmentData.getEnvRadiation() / 5000) + 1.0F;
        float moisture = 0.0F;
        double temp;

        if (!sp.isInWaterRainOrBubble()) {
            moisture = 0.2F * (3.0F + radiationModifier);
        }

        if (moisture > 0.0F) {
            WetnessHelper.updateWetnessInfo(sp, moisture, false);
        }

        if (this.skinTemperatureDirection == TemperatureDirection.NONE) return change;

        if (localTemperature < Environment.PARITY) {
            temp = Math.min(localTempF + insulationModifier, parityTempF);

            if (Math.abs(parityTempF - temp) > 5.0) {
                minutes = 383.4897 + (12.38784 - 383.4897) / (1 + Math.pow((temp / 43.26779), 8.271186));

                change = (NORMAL - LOW) / (float) minutes;
            }
        }
        else {
            temp = Math.max(localTempF - insulationModifier, parityTempF);

            if (Math.abs(parityTempF - temp) > 5.0) {
                // It is really, really hot ... increase rapidly
                if (temp > extremeTempF) {
                    change = (float) ((temp - extremeTempF) / 50.0) * 0.0067F;
                } else {
                    minutes = 24.45765 + (599.3552 - 24.45765) / (1 + Math.pow((temp / 109.1499), 27.47623));

                    change = (HIGH - NORMAL) / (float) minutes;
                }
            }
        }

        /*
         * Radiation should have a heating effect, even if the env temperature is low.
         *
         * Once a radiation threshold is met, it starts to get hot fast!
         */
        if ((this.coreTemperature < NORMAL && environmentData.getEnvRadiation() > 0) || radiationModifier > 5.0F) {
            change = change * radiationModifier;
        }

        //Homeostatic.LOGGER.debug("%s %s %s %s %s %s", skinTemperatureDirection, localTempF, insulationModifier, change, temp, radiationModifier);

        return change;
    }

}
