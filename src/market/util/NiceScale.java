package market.util;

public class NiceScale {
    private double minPoint;
    private double maxPoint;
    private double maxTicks = 10;
    private double tickSpacing;
    private double range;
    private double niceMin;
    private double niceMax;

    public NiceScale(double min, double max) {
        this.minPoint = min;
        this.maxPoint = max;
        calculate();
    }

    private void calculate() {
        this.range = niceNum(maxPoint - minPoint, false);
        this.tickSpacing = niceNum(range / (maxTicks - 1), true);
        this.niceMin = Math.floor(minPoint / tickSpacing) * tickSpacing;
        this.niceMax = Math.ceil(maxPoint / tickSpacing) * tickSpacing;
    }

    private double niceNum(double range, boolean round) {
        double exponent;
        double fraction;
        double niceFraction;
        exponent = Math.floor(Math.log10(range));
        fraction = range / Math.pow(10, exponent);
        if (round) {
            if (fraction < 1.5)
                niceFraction = 1;
            else if (fraction < 3)
                niceFraction = 2;
            else if (fraction < 7)
                niceFraction = 5;
            else
                niceFraction = 10;
        } else {
            if (fraction <= 1)
                niceFraction = 1;
            else if (fraction <= 2)
                niceFraction = 2;
            else if (fraction <= 5)
                niceFraction = 5;
            else
                niceFraction = 10;
        }
        return niceFraction * Math.pow(10, exponent);
    }

    public void setMinMaxPoints(double minPoint, double maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        calculate();
    }

    public void setMaxTicks(double maxTicks) {
        this.maxTicks = maxTicks;
        calculate();
    }

    public double getTickSpacing() {
        return tickSpacing;
    }

    public double getNiceMin() {
        return niceMin;
    }

    public double getNiceMax() {
        return niceMax;
    }
}
