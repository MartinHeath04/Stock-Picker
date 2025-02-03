import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class StockAnalysis {
    String symbol;
    List<BigDecimal> prices = new ArrayList<>();

    public StockAnalysis(String symbol) {
        this.symbol = symbol;
    }

    public void addPrice(BigDecimal price) {
        prices.add(price);
    }

    public BigDecimal getMovingAverage(int period) {
        if (prices.size() < period) return BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i));
        }
        return sum.divide(BigDecimal.valueOf(period), BigDecimal.ROUND_HALF_UP);
    }

    public boolean checkBuySignal() {
        if (prices.size() < 10) return false; // Ensure we have enough data
        BigDecimal shortMA = getMovingAverage(5);
        BigDecimal longMA = getMovingAverage(10);
        return shortMA.compareTo(longMA) > 0; // Buy if short MA is above long MA
    }
}

public class StockPicker {
    private static final String API_KEY = "S0934TM51N4BMNU8"; // Replace with your Alpha Vantage key

    public static void main(String[] args) {
        // Fixed list of stock symbols
        String[] symbols = {"AAPL", "NVDA", "MSFT", "TSLA", "AMD", "GOOGL", "NFLX", "DIS", "BA", "NKE"};

        Map<String, StockAnalysis> stockData = new HashMap<>();

        for (String symbol : symbols) {
            List<BigDecimal> prices = fetchStockPrices(symbol);
            if (prices.isEmpty()) {
                System.out.println("Skipping " + symbol + ": No data retrieved.");
                continue;
            }

            StockAnalysis analysis = new StockAnalysis(symbol);
            for (BigDecimal price : prices) {
                analysis.addPrice(price);
            }
            stockData.put(symbol, analysis);
        }

        // Display stocks that pass the buy signal
        System.out.println("\nStocks with Buy Signals:");
        for (String symbol : stockData.keySet()) {
            if (stockData.get(symbol).checkBuySignal()) {
                System.out.println(symbol + " is a BUY!");
            }
        }
    }

    public static List<BigDecimal> fetchStockPrices(String symbol) {
        List<BigDecimal> prices = new ArrayList<>();
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + API_KEY;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // ✅ Use try-with-resources to handle closing automatically
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // ✅ Parse JSON response to extract stock price
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonObject timeSeries = jsonResponse.getAsJsonObject("Time Series (Daily)");

                if (timeSeries == null) {
                    System.out.println("No time series data found for: " + symbol);
                    return prices;
                }

                for (String date : timeSeries.keySet()) {
                    JsonObject dailyData = timeSeries.getAsJsonObject(date);
                    BigDecimal closingPrice = dailyData.get("4. close").getAsBigDecimal();
                    prices.add(closingPrice);
                    // Limit to 10 most recent prices
                    if (prices.size() >= 10) break;
                }

                System.out.println("Fetched prices for " + symbol + ": " + prices);

            }

        } catch (Exception e) {
            System.err.println("Error fetching stock data for: " + symbol);
            e.printStackTrace();
        }

        return prices;
    }
}
