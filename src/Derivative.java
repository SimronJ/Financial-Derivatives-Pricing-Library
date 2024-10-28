/**
 * Abstract base class for all derivative instruments.
 * This class defines the common interface for all financial derivatives
 * that can be priced using the binomial model.
 */
abstract class Derivative {
    /** Market data used for pricing */
    protected MarketData marketData;
    /** Time to maturity of the derivative */
    protected double maturity;
    
    /**
     * Calculates the terminal payoff of the derivative.
     * This method is called at the final nodes of the binomial tree.
     * 
     * @param n The node at which to calculate the terminal condition
     */
    public abstract void terminalCondition(Node n);
    
    /**
     * Tests for early exercise opportunity at a given node.
     * This method is called at each node during the backward induction process.
     * 
     * @param n The node to test
     * @param currentTime The current time in the tree
     */
    public abstract void valuationTest(Node n, double currentTime);
    
    public void setMarketData(MarketData data) {
        this.marketData = data;
    }
    
    public double getMaturity() {
        return maturity;
    }
}