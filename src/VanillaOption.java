/**
 * Implements standard vanilla options (European and American).
 * This class handles both call and put options with either European
 * or American exercise style.
 */
class VanillaOption extends Derivative {
    /** Strike price of the option */
    protected double strikePrice;
    /** True for call option, false for put option */
    protected boolean isCall;
    /** True for American-style exercise, false for European */
    protected boolean isAmerican;
    
    /**
     * Creates a new vanilla option.
     * 
     * @param strike The strike price
     * @param isCall True for call option, false for put
     * @param isAmerican True for American-style exercise
     * @param maturity Time to expiration
     */
    public VanillaOption(double strike, boolean isCall, boolean isAmerican, double maturity) {
        validateInputs(strike, maturity);
        this.strikePrice = strike;
        this.isCall = isCall;
        this.isAmerican = isAmerican;
        this.maturity = maturity;
    }
    
    private void validateInputs(double strike, double maturity) {
        if (strike <= 0) throw new IllegalArgumentException("Strike price must be positive");
        if (maturity <= 0) throw new IllegalArgumentException("Maturity must be positive");
    }

    @Override
    public void terminalCondition(Node n) {
        n.optionValue = isCall ? 
            Math.max(0, n.stockPrice - strikePrice) :
            Math.max(0, strikePrice - n.stockPrice);
    }

    @Override
    public void valuationTest(Node n, double currentTime) {
        if (isAmerican) {
            double intrinsicValue = isCall ? 
                Math.max(0, n.stockPrice - strikePrice) :
                Math.max(0, strikePrice - n.stockPrice);
            n.optionValue = Math.max(n.optionValue, intrinsicValue);
        }
    }
}
