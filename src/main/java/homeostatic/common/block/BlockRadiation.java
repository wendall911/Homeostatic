package homeostatic.common.block;

public record BlockRadiation(double maxRadiation) {

    public double getMaxRadiation() {
        return this.maxRadiation;
    }

    public double getBlockRadiation(double distance, boolean obscured, int y) {
        double radiation;

        if (distance <= 1) {
            radiation = this.getMaxRadiation();
        } else {
            radiation = this.getMaxRadiation() / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, getMaxRadiation());
    }

    public double getBlockRadiation(double distance, boolean obscured, double amount, int y) {
        double radiation = 0.0;

        if (amount <= 0) return radiation;

        if (distance <= 1) {
            radiation = this.getMaxRadiation();
        } else {
            radiation = this.getMaxRadiation() * amount / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, this.getMaxRadiation());
    }

}
