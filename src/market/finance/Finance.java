package market.finance;

public class Finance {

    public static double[] exponentialMovingAverage(double[] prices, int time) {
        double ema = 0;

        for (int i = 0; i < time; i++)
            ema += prices[i];

        ema = ema / time;
        double[] emas = new double[prices.length - time + 1];
        double multiplier = (2D / (time + 1));
        emas[0] = ema;

        int count = 1;

        for (int i = time; i < prices.length; i++) {
            ema = (prices[i] - ema) * multiplier + ema;
            emas[count++] = ema;
        }

        return emas;
    }

}
