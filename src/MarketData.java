/**
 * Contains market data required for option pricing calculations.
 * This class encapsulates all market-related parameters needed
 * for the binomial model calculations.
 */
final class MarketData {
    /** Current market price of the security */
    public double Price;
    /** Current stock price */
    public double S;
    /** Risk-free interest rate (annual, continuous compounding) */
    public double r;
    /** Stock price volatility (annual) */
    public double sigma;
    /** Current time (usually 0) */
    public double t0;
    
    /**
     * Creates a new MarketData instance with validation.
     * 
     * @param price Current market price of the option
     * @param s Current stock price
     * @param r Risk-free interest rate
     * @param sigma Volatility
     * @param t0 Current time
     * @throws IllegalArgumentException if any parameters are invalid
     */
    public MarketData(double price, double s, double r, double sigma, double t0) {
        validateInputs(price, s, r, sigma, t0);
        this.Price = price;
        this.S = s;
        this.r = r;
        this.sigma = sigma;
        this.t0 = t0;
    }
    
    /**
     * Validates all input parameters for market data.
     * Ensures that all financial parameters are within reasonable bounds.
     */
    private void validateInputs(double price, double s, double r, double sigma, double t0) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive");
        if (s <= 0) throw new IllegalArgumentException("Stock price must be positive");
        if (sigma <= 0) throw new IllegalArgumentException("Volatility must be positive");
        if (t0 < 0) throw new IllegalArgumentException("Current time cannot be negative");
    }
}