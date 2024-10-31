import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a comprehensive options chart display for different option types
 * with real market-like information
 */
class OptionsChart {
    private String tickerSymbol;
    private double currentPrice;
    private double ivRank;
    private double dividendYield;
    private static final String OUTPUT_FILE = "options_data.txt";
    private StringBuilder outputBuffer;
    
    public static class OptionChain {
        public double strike;
        public double bid;
        public double ask;
        public double lastPrice;
        public int volume;
        public int openInterest;
        public double impliedVol;
        public double delta;
        public String type; // "CALL" or "PUT"
        public String exerciseStyle; // "European", "American", "Bermudan"
        
        public OptionChain(double strike, String type, String exerciseStyle) {
            this.strike = strike;
            this.type = type;
            this.exerciseStyle = exerciseStyle;
        }
    }

    public OptionsChart(String ticker, double price, double ivRank, double divYield) {
        this.tickerSymbol = ticker;
        this.currentPrice = price;
        this.ivRank = ivRank;
        this.dividendYield = divYield;
        this.outputBuffer = new StringBuilder();
    }

    public void displayOptionsChain(double maturity, MarketData mkt) {
        // Clear the buffer at the start of new display
        outputBuffer.setLength(0);
        
        // Add timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        outputBuffer.append("Options Chain Generated: ").append(formatter.format(now)).append("\n\n");

        // Add header information
        outputBuffer.append(String.format("=== Options Chain for %s ===\n", tickerSymbol));
        outputBuffer.append(String.format("Current Price: $%.2f | IV Rank: %.1f%% | Dividend Yield: %.2f%%\n",
                currentPrice, ivRank, dividendYield * 100));
        outputBuffer.append(String.format("Days to Expiration: %.0f | Risk-free Rate: %.1f%%\n\n",
                maturity * 365, mkt.r * 100));

        // Display Calls and Puts
        displayCallSection(maturity, mkt);
        displayPutSection(maturity, mkt);

        // Write to file and console
        writeToFile();
        System.out.println(outputBuffer.toString());
    }

    private void displayCallSection(double maturity, MarketData mkt) {
        outputBuffer.append("=== CALLS ===\n");
        outputBuffer.append("Strike | Type | Bid | Ask | Last | Volume | OI | IV% | Delta\n");
        outputBuffer.append("----------------------------------------------------------\n");

        double[] strikes = generateStrikes(currentPrice);
        
        for (double strike : strikes) {
            VanillaOption europeanCall = new VanillaOption(strike, true, false, maturity);
            VanillaOption americanCall = new VanillaOption(strike, true, true, maturity);
            BermudanOption bermudanCall = new BermudanOption(strike, true, maturity, 
                                                           maturity * 0.3, maturity * 0.8);

            Output resultEuro = Library.binom(europeanCall, mkt, 50);
            Output resultAmer = Library.binom(americanCall, mkt, 50);
            Output resultBerm = Library.binom(bermudanCall, mkt, 50);

            displayOptionRow(strike, "EUR-C", resultEuro);
            displayOptionRow(strike, "AMR-C", resultAmer);
            displayOptionRow(strike, "BER-C", resultBerm);
        }
    }

    private void displayPutSection(double maturity, MarketData mkt) {
        outputBuffer.append("\n=== PUTS ===\n");
        outputBuffer.append("Strike | Type | Bid | Ask | Last | Volume | OI | IV% | Delta\n");
        outputBuffer.append("----------------------------------------------------------\n");

        double[] strikes = generateStrikes(currentPrice);
        
        for (double strike : strikes) {
            VanillaOption europeanPut = new VanillaOption(strike, false, false, maturity);
            VanillaOption americanPut = new VanillaOption(strike, false, true, maturity);
            BermudanOption bermudanPut = new BermudanOption(strike, false, maturity, 
                                                          maturity * 0.3, maturity * 0.8);

            Output resultEuro = Library.binom(europeanPut, mkt, 50);
            Output resultAmer = Library.binom(americanPut, mkt, 50);
            Output resultBerm = Library.binom(bermudanPut, mkt, 50);

            displayOptionRow(strike, "EUR-P", resultEuro);
            displayOptionRow(strike, "AMR-P", resultAmer);
            displayOptionRow(strike, "BER-P", resultBerm);
        }
    }

    private double[] generateStrikes(double currentPrice) {
        // Generate 5 strikes above and below current price
        double[] strikes = new double[11];
        double strikeInterval = currentPrice * 0.025; // 2.5% intervals
        
        for (int i = 0; i < strikes.length; i++) {
            strikes[i] = currentPrice + (i - 5) * strikeInterval;
        }
        return strikes;
    }

    private void displayOptionRow(double strike, String type, Output result) {
        double spread = result.FV * 0.05;
        double bid = result.FV - spread/2;
        double ask = result.FV + spread/2;
        int volume = (int)(Math.random() * 1000);
        int openInterest = (int)(Math.random() * 5000);
        
        outputBuffer.append(String.format("%6.2f | %4s | %5.2f | %5.2f | %5.2f | %6d | %4d | %4.1f | %5.2f\n",
                strike, type, bid, ask, result.FV, 
                volume, openInterest, result.impvol * 100, 
                calculateDelta(result.FV, strike)));
    }

    private double calculateDelta(double optionPrice, double strike) {
        // Simple delta approximation
        return Math.max(-1, Math.min(1, (optionPrice/strike) * 
               (currentPrice > strike ? 1 : -1)));
    }
    
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write(outputBuffer.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
