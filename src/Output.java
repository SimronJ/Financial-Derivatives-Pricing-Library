/**
 * Contains the results of option pricing calculations.
 * This class stores various metrics computed during the pricing process.
 */
final class Output {
    /** Fair value of the option */
    public double FV;
    /** Time to exercise (fugit) */
    public double fugit;
    /** Implied volatility (if calculated) */
    public double impvol;
    /** Number of iterations for implied volatility calculation */
    public int num_iter;
    
    /**
     * Creates a new Output instance with default values.
     */
    public Output() {
        this.FV = 0.0;
        this.fugit = 0.0;
        this.impvol = 0.0;
        this.num_iter = 0;
    }

    @Override
    public String toString() {
        return String.format("Fair Value: %.4f, Fugit: %.4f, Implied Vol: %.4f, Iterations: %d",
                           FV, fugit, impvol, num_iter);
    }
}