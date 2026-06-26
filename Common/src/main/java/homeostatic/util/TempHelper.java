package homeostatic.util;

import homeostatic.common.temperature.Environment;
import homeostatic.common.temperature.TemperatureDirection;
import homeostatic.common.temperature.TemperatureRange;
import homeostatic.common.temperature.TemperatureThreshold;

public class TempHelper {

    private static final double DEFAULT_EXHALED_TEMP_C = 35.0;
    private static final double DEFAULT_EXHALED_RH = 95.0;

    /*
     * Iterations for ternary search over mixing fraction when checking condensation.
     * RH(f) is unimodal: the mixing line between two sub-saturated air states crosses
     * the convex saturation curve at most twice, guaranteeing a single peak.
     * 10 iterations gives ~10^-5 precision in f — sufficient for simulation purposes.
     */
    private static final int CONDENSATION_SEARCH_ITERATIONS = 10;

    public static TemperatureDirection getCoreTemperatureDirection(float lastSkinTemperature, float coreTemperature, float skinTemperature) {
        TemperatureDirection direction = TemperatureDirection.NONE;

        if (lastSkinTemperature > skinTemperature) {
            direction = TemperatureDirection.COOLING_NORMALLY;

            if (coreTemperature > TemperatureThreshold.NORMAL.temperature) {
                if (skinTemperature < coreTemperature) {
                    direction = TemperatureDirection.COOLING_RAPIDLY;
                } else {
                    direction = TemperatureDirection.COOLING;
                }
            }
        }
        else if (lastSkinTemperature < skinTemperature) {
            direction = TemperatureDirection.WARMING_NORMALLY;

            if (coreTemperature < TemperatureThreshold.NORMAL.temperature) {
                if (skinTemperature > coreTemperature) {
                    direction = TemperatureDirection.WARMING_RAPIDLY;
                } else {
                    direction = TemperatureDirection.WARMING;
                }
            }
        }

        return direction;
    }

    public static TemperatureDirection getSkinTemperatureDirection(float localTemperature, float lastSkinTemperature) {
        TemperatureDirection direction = TemperatureDirection.NONE;

        if (lastSkinTemperature > TemperatureThreshold.NORMAL.temperature) {
            if (localTemperature > Environment.PARITY_HIGH) {
                direction = TemperatureDirection.WARMING_NORMALLY;

                if (localTemperature > Environment.HOT) {
                    direction = TemperatureDirection.WARMING;
                }
            }
            else if (localTemperature < Environment.PARITY_HIGH){
                direction = TemperatureDirection.COOLING;

                if (localTemperature < Environment.PARITY_LOW) {
                    direction = TemperatureDirection.COOLING_RAPIDLY;
                }
            }
        }
        else if (lastSkinTemperature < TemperatureThreshold.NORMAL.temperature) {
            if (localTemperature > Environment.PARITY_LOW) {
                direction = TemperatureDirection.WARMING_NORMALLY;

                if (localTemperature > Environment.EXTREME_HEAT) {
                    direction = TemperatureDirection.WARMING_RAPIDLY;
                }
                else if (localTemperature > Environment.PARITY_HIGH) {
                    direction = TemperatureDirection.WARMING;
                }
            }
            else {
                direction = TemperatureDirection.COOLING_NORMALLY;
            }
        }
        else {
            if (localTemperature > Environment.PARITY_HIGH) {
                direction = TemperatureDirection.WARMING_NORMALLY;
            }
            else if (localTemperature < Environment.PARITY_LOW) {
                direction = TemperatureDirection.COOLING_NORMALLY;
            }
        }

        return direction;
    }

    public static double convertMcTemp(float mcTemp, boolean fahrenheit) {
        double temp = 25.27027027 + (44.86486486 * mcTemp);

        if (!fahrenheit) {
            temp = (temp - 32) * 0.5556;
        }

        return temp;
    }

    public static double convertTemp(double temp, boolean fahrenheit) {
        double toConvert = temp;

        if (!fahrenheit) {
            toConvert = (temp / 0.5556) + 32;
        }

        return (toConvert - 25.27027027) / 44.86486486;
    }

    public static double getHeatIndex(float dryTemp, double rh) {
        double dryTempF = convertMcTemp(dryTemp, true);
        double hIndex;

        if (dryTempF < 80.0) {
            hIndex = 0.5 * (dryTempF + 61.0 +((dryTempF - 68.0) * 1.2)) + (rh*0.094);
        }
        else {
            hIndex = -42.379 + 2.04901523 * dryTempF + 10.14333127 * rh;
            hIndex = hIndex - 0.22475541 * dryTempF * rh - 6.83783 * Math.pow(10, -3) * dryTempF * dryTempF;
            hIndex = hIndex - 5.481717 * Math.pow(10, -2) * rh * rh;
            hIndex = hIndex + 1.22874 * Math.pow(10, -3) * dryTempF * dryTempF * rh;
            hIndex = hIndex + 8.5282 * Math.pow(10, -4) * dryTempF * rh * rh;
            hIndex = hIndex - 1.99 * Math.pow(10, -6) * dryTempF * dryTempF * rh * rh;
        }

        return convertTemp(hIndex, true);
    }

    /*
     * return black globe estimate.
     * https://rmets.onlinelibrary.wiley.com/doi/full/10.1002/met.1631
     * baseRadiation is set to a negative value so that this will be equal to dry
     * temp if there is no radiation effect
     */
    public static double getBlackGlobe(double radiation, float dryTemp, double relativeHumidity) {
        double dryTempC = convertMcTemp(dryTemp, false);

        double blackGlobeTemp = (0.01498 * radiation) + (1.184 * dryTempC) - (0.0789 * (relativeHumidity / 100)) - 2.739;

        return convertTemp(blackGlobeTemp, false);
    }

    public static Tuple<TemperatureRange, Integer> getLocalTemperatureRangeStep(float temperature) {
        Tuple<TemperatureRange, Integer> rangeStep = new Tuple<>(TemperatureRange.COLD, 17);

        if (temperature > Environment.PARITY) {
            if (temperature < Environment.EXTREME_HEAT) {
                rangeStep.setB((int) Math.floor((temperature - Environment.PARITY) / 0.0905F));
            }

            rangeStep.setA(TemperatureRange.HOT);
        }
        else {
            if (temperature > Environment.EXTREME_COLD) {
                rangeStep.setB((int) Math.floor((Environment.PARITY - temperature) / 4.0F));
            }
        }

        return rangeStep;
    }

    public static Tuple<TemperatureRange, Integer> getBodyTemperatureRangeStep(float temperature) {
        Tuple<TemperatureRange, Integer> rangeStep = new Tuple<>(TemperatureRange.COLD, 17);

        if (temperature > TemperatureThreshold.NORMAL.temperature) {
            if (temperature < TemperatureThreshold.HIGH.temperature) {
                rangeStep.setB((int) Math.floor((temperature - TemperatureThreshold.NORMAL.temperature) / 0.0103F));
            }

            rangeStep.setA(TemperatureRange.HOT);
        }
        else {
            if (temperature > TemperatureThreshold.LOW.temperature) {
                rangeStep.setB((int) Math.floor((TemperatureThreshold.NORMAL.temperature - temperature) / 0.005F));
            }
        }

        return rangeStep;
    }

    /*
     * RH above 100% at any mixing ratio means supersaturation, so condensation must occur.
     * Pass exhaled temperature directly in Celsius to skip MC unit round-trip conversion.
     * Math derived from:
     * http://www.sciencebits.com/exhalecondense
     */
    public static boolean isMixedAirCondensing(float ambientMcTemp, double ambientRh) {
        return canExhaledAirCondense(ambientMcTemp, ambientRh, DEFAULT_EXHALED_TEMP_C, DEFAULT_EXHALED_RH);
    }

    /*
     * Returns mixed-air RH in percent for a specific outside-air fraction f.
     * Accepts precomputed endpoint water content and enthalpy to avoid redundant work in loops.
     */
    private static double rhAtMixingFraction(double gEx, double gAmb, double hEx, double hAmb, double f) {
        double gMixed = (1.0 - f) * gEx + f * gAmb;
        double hMixed = (1.0 - f) * hEx + f * hAmb;
        // Rearranged moist-air enthalpy equation to recover mixed dry-bulb temperature in C.
        double mixedTempC = (hMixed + 0.026 - 2.501 * gMixed) / (1.007 + 0.00184 * gMixed);

        return 100.0 * (gMixed / 6.210E-3) / getWaterVaporSaturationPressure(mixedTempC);
    }

    /*
     * Ternary search for the peak of RH(f) over (0,1).
     * Exhaled and ambient states are precomputed once; only the cheap rhAtMixingFraction
     * inner call (dominated by one Math.exp) runs per iteration.
     * Exits immediately if either trisection point already exceeds 100%.
     */
    private static boolean canExhaledAirCondense(float ambientMcTemp, double ambientRh, double exhaledTempC, double exhaledRh) {
        double ambientTempC = convertMcTemp(ambientMcTemp, false);
        // Precompute endpoint states once — constant across all mixing fractions.
        double gExhaled = getWaterContent(exhaledTempC, exhaledRh);
        double gAmbient = getWaterContent(ambientTempC, ambientRh);
        double hExhaled = getMoistAirEnthalpy(exhaledTempC, gExhaled);
        double hAmbient = getMoistAirEnthalpy(ambientTempC, gAmbient);
        double lo = 0.0, hi = 1.0;

        for (int i = 0; i < CONDENSATION_SEARCH_ITERATIONS; i++) {
            double m1 = lo + (hi - lo) / 3.0;
            double m2 = hi - (hi - lo) / 3.0;
            double rh1 = rhAtMixingFraction(gExhaled, gAmbient, hExhaled, hAmbient, m1);
            double rh2 = rhAtMixingFraction(gExhaled, gAmbient, hExhaled, hAmbient, m2);

            if (rh1 > 100.0 || rh2 > 100.0) {
                return true;
            }

            if (rh1 < rh2) {
                lo = m1;
            } else {
                hi = m2;
            }
        }

        return rhAtMixingFraction(gExhaled, gAmbient, hExhaled, hAmbient, (lo + hi) / 2.0) > 100.0;
    }

    private static double getWaterContent(double tempC, double rhPercent) {
        // g [g/kg] = 6.210e-3 * pw [Pa]
        double rh = Math.clamp(rhPercent, 0.0, 100.0) / 100.0;
        double waterVaporPressure = rh * getWaterVaporSaturationPressure(tempC);

        return 6.210E-3 * waterVaporPressure;
    }

    private static double getMoistAirEnthalpy(double tempC, double waterContent) {
        // h [kJ/kg] approximation used by the mixing model.
        return (1.007 * tempC - 0.026) + waterContent * (2.501 + 0.00184 * tempC);
    }

    private static double getWaterVaporSaturationPressure(double tempC) {
        // Magnus-type saturation pressure relation for water vapor (Pa).
        return 610.8 * Math.exp((17.2694 * tempC) / (tempC + 238.3));
    }

}
