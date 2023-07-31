package homeostatic.util;

import net.minecraft.util.Tuple;

import homeostatic.common.temperature.Environment;
import homeostatic.common.temperature.TemperatureDirection;
import homeostatic.common.temperature.TemperatureRange;
import homeostatic.common.temperature.TemperatureThreshold;

public class TempHelper {

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

}
