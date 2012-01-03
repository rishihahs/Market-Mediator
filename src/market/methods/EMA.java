package market.methods;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import market.finance.Finance;
import market.finance.FinancialData;
import market.finance.HistoricalData;
import market.util.Graph;
import market.util.NiceScale;
import market.util.Point;
import market.util.Stock;

public class EMA implements Method {

    public double[][] ema(double[] prices, int period, int percent) {
        double[] ema = Finance.exponentialMovingAverage(prices, period);

        double[][] data = new double[4][ema.length];

        int count = 0;
        for (int n = period - 1; n < prices.length; n++) {
            data[0][count++] = prices[n];
        }

        for (int n = 0; n < ema.length; n++)
            data[1][n] = ema[n];

        for (int n = 0; n < ema.length; n++)
            data[2][n] = ema[n] * (1D - (percent / 100D));

        for (int n = 0; n < ema.length; n++)
            data[3][n] = ema[n] * (1D + (percent / 100D));

        return data;
    }

    @Override
    public Graph constructGraph(String symbol) {
        Stock stock = new Stock();

        int period = 10;
        int days = 20;
        int percent = 7;
        List<FinancialData> listdata = null;
        try {
            listdata = new HistoricalData().getData(symbol, period + days);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse(listdata);
        double[] prices = new double[period + days];
        Iterator<FinancialData> i = listdata.iterator();

        int count = 0;
        while (i.hasNext()) {
            FinancialData finance = i.next();
            prices[count++] = finance.close;
        }

        double[][] data = ema(prices, period, percent);

        double min = data[0][0];
        double max = 0;
        for (double[] clove : data)
            for (double price : clove) {
                if (price < min)
                    min = price;
                else if (price > max)
                    max = price;
            }

        NiceScale scale = new NiceScale(min, max);
        String[] ylabels = new String[(int) ((scale.getNiceMax() - scale
                .getNiceMin()) / scale.getTickSpacing() + 1.0)];

        DecimalFormat format = new DecimalFormat("#.##");
        double start = scale.getNiceMin();
        for (int n = 0; n < ylabels.length; n++) {
            ylabels[n] = format.format(start);
            start += scale.getTickSpacing();
        }

        String[] xlabels = new String[data[0].length];
        int xstart = 1;
        for (int n = 0; n < xlabels.length; n++) {
            xlabels[n] = Integer.toString(xstart++);
        }

        List<List<Point>> datas = new ArrayList<List<Point>>();
        count = 0;
        for (double[] clove : data) {
            List<Point> points = new ArrayList<Point>();
            for (double price : clove) {
                points.add(new Point(count++, price));
            }
            datas.add(points);
            count = 0;
        }

        stock.setTitle(symbol);
        stock.setColors(Arrays.asList(new Color[] { Color.BLUE, Color.RED,
                Color.CYAN }));
        stock.setYlabels(ylabels);
        stock.setXlabels(xlabels);
        stock.setInterval(scale.getTickSpacing());
        stock.setData(datas);

        return stock;
    }

}
