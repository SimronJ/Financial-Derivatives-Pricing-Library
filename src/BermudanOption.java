/**
 * Implements Bermudan options, which allow early exercise only within
 * a specified time window. This is a hybrid between European and
 * American options.
 */
class BermudanOption extends VanillaOption {
    /** Start of the exercise window */
    public double window_begin;
    /** End of the exercise window */
    public double window_end;
    
    /**
     * Creates a new Bermudan option.
     * 
     * @param strike The strike price
     * @param isCall True for call option, false for put
     * @param maturity Time to expiration
     * @param windowBegin Start of exercise window
     * @param windowEnd End of exercise window
     */
    public BermudanOption(double strike, boolean isCall, double maturity, 
                         double windowBegin, double windowEnd) {
        super(strike, isCall, true, maturity);
        validateWindows(windowBegin, windowEnd, maturity);
        this.window_begin = windowBegin;
        this.window_end = windowEnd;
    }
    
    private void validateWindows(double begin, double end, double maturity) {
        if (begin >= end) 
            throw new IllegalArgumentException("Window begin must be less than window end");
        if (end > maturity)
            throw new IllegalArgumentException("Window end must not exceed maturity");
        if (begin < 0)
            throw new IllegalArgumentException("Window begin must be non-negative");
    }

    @Override
    public void valuationTest(Node n, double currentTime) {
        if (currentTime >= window_begin && currentTime <= window_end) {
            super.valuationTest(n, currentTime);
        }
    }
}