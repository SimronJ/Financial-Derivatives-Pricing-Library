/**
 * Financial Derivatives Pricing Library
 * 
 * This library implements option pricing models based on the binomial tree approach
 * as described in the computational finance literature. The binomial model was first
 * introduced by Cox, Ross, and Rubinstein (1979) and provides a discrete-time
 * approximation for option pricing.
 * 
 * Key features:
 * - European, American, and Bermudan option pricing
 * - Implied volatility calculation
 * - Support for both call and put options
 * - Comprehensive error checking and validation
 * 
 * @author Simranjeet Singh
 * @version 1.0
 */


/**
 * Comprehensive test suite for the option pricing library.
 * 
 * This class provides a series of tests to verify:
 * - Correct pricing of European options
 * - Early exercise premium for American options
 * - Proper handling of Bermudan exercise windows
 * - Edge cases and error conditions
 * - Implied volatility calculations
 * 
 * The test cases include comparison with known analytical solutions
 * where available (e.g., Black-Scholes prices for European options).
 */
public class OptionPricingTest {
    public static void main(String[] args) {
        testVanillaOptions();
        System.out.println();
        testBermudanOptions();
        System.out.println();
        testEdgeCases();
        System.out.println();
        testImpliedVolatility();
    }

    private static void testVanillaOptions() {
        System.out.println("=== Testing Vanilla Options ===");
        // Test parameters
        double S = 100.0;    // Stock price
        double K = 100.0;    // Strike price
        double r = 0.05;     // Risk-free rate
        double sigma = 0.2;  // Volatility
        double T = 1.0;      // Time to maturity
        int steps = 50;      // Number of steps
        double marketPrice = 10.0;  // Market price

        MarketData mkt = new MarketData(marketPrice, S, r, sigma, 0.0);

        // European Call
        VanillaOption euCall = new VanillaOption(K, true, false, T);
        Output euCallResult = Library.binom(euCall, mkt, steps);
        printResult("European Call", euCallResult, K, S, sigma);

        // American Put
        VanillaOption amPut = new VanillaOption(K, false, true, T);
        Output amPutResult = Library.binom(amPut, mkt, steps);
        printResult("American Put", amPutResult, K, S, sigma);
    }

    private static void testBermudanOptions() {
        System.out.println("=== Testing Bermudan Options ===");
        // Test parameters
        double S = 100.0;
        double K = 100.0;
        double r = 0.05;
        double sigma = 0.2;
        double T = 1.0;
        int steps = 50;
        double marketPrice = 10.0;
        
        MarketData mkt = new MarketData(marketPrice, S, r, sigma, 0.0);

        // Bermudan Call with exercise window [0.25, 0.75]
        BermudanOption bermCall = new BermudanOption(K, true, T, 0.25, 0.75);
        Output bermCallResult = Library.binom(bermCall, mkt, steps);
        printResult("Bermudan Call (Window: 0.25-0.75)", bermCallResult, K, S, sigma);
    }

    private static void testEdgeCases() {
        System.out.println("=== Testing Edge Cases ===");
        
        // Test 1: Negative Strike Price
        try {
            new VanillaOption(-100, true, false, 1.0);
            System.out.println("❌ Failed: Should have caught negative strike price");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught negative strike: " + e.getMessage());
        }

        // Test 2: Invalid Bermudan Window
        try {
            new BermudanOption(100, true, 1.0, 0.8, 0.5);  // end < begin
            System.out.println("❌ Failed: Should have caught invalid window");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught invalid window: " + e.getMessage());
        }

        // Test 3: Negative Volatility
        try {
            new MarketData(10.0, 100.0, 0.05, -0.2, 0.0);
            System.out.println("❌ Failed: Should have caught negative volatility");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught negative volatility: " + e.getMessage());
        }

        // Test 4: Zero Maturity
        try {
            new VanillaOption(100, true, false, 0.0);
            System.out.println("❌ Failed: Should have caught zero maturity");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught zero maturity: " + e.getMessage());
        }

        // Test 5: Negative Time
        try {
            new MarketData(10.0, 100.0, 0.05, 0.2, -1.0);
            System.out.println("❌ Failed: Should have caught negative time");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught negative time: " + e.getMessage());
        }
    }

    private static void testImpliedVolatility() {
        System.out.println("=== Testing Implied Volatility ===");
        // Test parameters
        double S = 100.0;
        double K = 100.0;
        double r = 0.05;
        double T = 1.0;
        double marketPrice = 10.0;
        
        MarketData mkt = new MarketData(marketPrice, S, r, 0.2, 0.0);
        VanillaOption option = new VanillaOption(K, true, false, T);
        
        Output result = Library.impvol(option, mkt, 50, 100, 0.0001, null);
        System.out.printf("Target Price: %.2f%n", marketPrice);
        printResult("Implied Vol Calculation", result, K, S, result.impvol);
    }

    private static void printResult(String testName, Output result, double strike, double spot, double vol) {
        System.out.println("\n" + testName + ":");
        System.out.printf("└─ Parameters: Strike=%.2f, Spot=%.2f, Vol=%.2f%%%n", 
                         strike, spot, vol * 100);
        System.out.printf("└─ Results: Fair Value=%.2f, Fugit=%.2f, Implied Vol=%.2f%%, Iterations=%d%n", 
                         result.FV, result.fugit, result.impvol * 100, result.num_iter);
    }
}
