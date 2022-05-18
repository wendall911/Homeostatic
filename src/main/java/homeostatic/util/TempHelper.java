package homeostatic.util;

public class TempHelper {

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

}
