package market.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoricalData {

    private static final String URL = "http://ichart.finance.yahoo.com/table.csv?s=";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");

    private HttpURLConnection connection;

    public List<FinancialData> getData(String symbol, int days)
            throws IOException, ParseException {
        URL uri = new URL(URL + symbol.toUpperCase());
        List<FinancialData> list = new ArrayList<FinancialData>();
        connection = (HttpURLConnection) uri.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        try {

            String line;
            br.readLine(); // Title line in API response
            int count = 0;
            while ((line = br.readLine()) != null && count < days) {
                FinancialData financialData = new FinancialData();
                financialData.symbol = symbol;
                process(line, financialData);
                list.add(financialData);
                count++;
            }

        } finally {
            br.close();
            connection.disconnect();
        }

        return list;
    }

    private void process(String line, FinancialData financialData)
            throws ParseException {
        String[] data = line.split(",");
        int count = 0;
        financialData.date = FORMAT.parse(data[count++]);
        financialData.open = Double.parseDouble(data[count++]);
        financialData.high = Double.parseDouble(data[count++]);
        financialData.low = Double.parseDouble(data[count++]);
        financialData.close = Double.parseDouble(data[count++]);
        financialData.volume = Double.parseDouble(data[count++]);
        financialData.adj_close = Double.parseDouble(data[count++]);
    }

}
