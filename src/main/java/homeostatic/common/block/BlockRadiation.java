package homeostatic.common.block;

public final class BlockRadiation {

    private final double maxRadiation;
    private final boolean isFluid;

    BlockRadiation(double maxRadiation, boolean isFluid) {
        this.maxRadiation = maxRadiation;
        this.isFluid = isFluid;
    }

    public double getMaxRadiation() {
        return this.maxRadiation;
    }

    public boolean isFluid() {
        return this.isFluid;
    }

    public double getBlockRadiation(double distance, boolean obscured) {
        double radiation;

        if (distance <= 1) {
            radiation = this.getMaxRadiation();
        }
        else {
            radiation = this.getMaxRadiation() / distance;
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, getMaxRadiation());
    }

    public double getBlockRadiation(double distance, boolean obscured, double amount) {
        double radiation = 0.0;

        if (amount <= 0) return radiation;

        if (distance <= 1) {
            radiation = this.getMaxRadiation();
        }
        else {
            radiation = this.getMaxRadiation() * amount / distance;
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, this.getMaxRadiation());
    }

}
