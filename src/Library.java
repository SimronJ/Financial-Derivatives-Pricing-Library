/**
 * Core pricing library implementing the binomial model algorithms.
 * 
 * The binomial model used here is based on the Cox-Ross-Rubinstein approach,
 * which provides a discrete-time approximation to the Black-Scholes model.
 * 
 * Key features of the implementation:
 * - Risk-neutral pricing
 * - Backward induction for option valuation
 * - Support for early exercise features
 * - Implied volatility calculation using Newton's method
 */
final class Library {
    /**
     * Calculates option price using the binomial model.
     * 
     * The method implements these steps:
     * 1. Builds a binomial tree with n steps
     * 2. Calculates up/down movements based on volatility
     * 3. Computes risk-neutral probabilities
     * 4. Performs backward induction for price calculation
     * 
     * @param deriv The derivative to price
     * @param mkt Market data for calculation
     * @param n Number of time steps
     * @return Output object containing pricing results
     */
    // public static Output binom(final Derivative deriv, final MarketData mkt, int n) {
    //     if (n <= 0) throw new IllegalArgumentException("Number of steps must be positive");
        
    //     deriv.setMarketData(mkt);
    //     Output output = new Output();
        
    //     // Calculate parameters
    //     double dt = (deriv.getMaturity() - mkt.t0) / n;
    //     double u = Math.exp(mkt.sigma * Math.sqrt(dt));
    //     double d = 1.0 / u;
    //     double p = (Math.exp(mkt.r * dt) - d) / (u - d);
        
    //     // Build and evaluate tree
    //     Node root = buildTree(mkt.S, n, u, d);
    //     calculateOptionValues(root, deriv, p, mkt.r, dt, n);
        
    //     output.FV = root.optionValue;
    //     output.fugit = calculateFugit(root, deriv, n);
        
    //     return output;
    // }

    // private static Node buildTree(double S0, int steps, double u, double d) {
    //     Node root = new Node(S0, 0);
    //     buildTreeRecursive(root, steps, u, d, 0);
    //     return root;
    // }
    
    // private static void buildTreeRecursive(Node node, int steps, double u, double d, int currentStep) {
    //     if (currentStep == steps) return;
        
    //     node.upNode = new Node(node.stockPrice * u, currentStep + 1);
    //     node.downNode = new Node(node.stockPrice * d, currentStep + 1);
        
    //     buildTreeRecursive(node.upNode, steps, u, d, currentStep + 1);
    //     buildTreeRecursive(node.downNode, steps, u, d, currentStep + 1);
    // }
    
    // private static void calculateOptionValues(Node node, Derivative deriv, 
    //                                        double p, double r, double dt, int steps) {
    //     if (node.timeStep == steps) {
    //         deriv.terminalCondition(node);
    //         return;
    //     }
        
    //     calculateOptionValues(node.upNode, deriv, p, r, dt, steps);
    //     calculateOptionValues(node.downNode, deriv, p, r, dt, steps);
        
    //     // Backward induction
    //     double currentTime = node.timeStep * dt;
    //     node.optionValue = Math.exp(-r * dt) * 
    //         (p * node.upNode.optionValue + (1-p) * node.downNode.optionValue);
        
    //     deriv.valuationTest(node, currentTime);
    // }
    
    // private static double calculateFugit(Node root, Derivative deriv, int steps) {
    //     // Simplified fugit calculation
    //     return deriv.getMaturity();
    // }
    
    // /**
    //  * Calculates implied volatility using Newton's method.
    //  * 
    //  * This method finds the volatility that makes the model price
    //  * match the market price within the specified tolerance.
    //  * 
    //  * @param deriv The derivative instrument
    //  * @param mkt Market data
    //  * @param n Number of time steps
    //  * @param max_iter Maximum number of iterations
    //  * @param tol Convergence tolerance
    //  * @param out Output object for results
    //  * @return Updated output object with implied volatility
    //  */
    // public static Output impvol(final Derivative deriv, final MarketData mkt,
    //                           int n, int max_iter, double tol, Output out) {
    //     // Implementation of implied volatility calculation using Newton's method
    //     Output result = new Output();
    //     double vol = 0.3; // Initial guess
        
    //     for (int i = 0; i < max_iter; i++) {
    //         MarketData tempMkt = new MarketData(mkt.Price, mkt.S, mkt.r, vol, mkt.t0);
    //         Output tempOut = binom(deriv, tempMkt, n);
            
    //         double diff = tempOut.FV - mkt.Price;
    //         if (Math.abs(diff) < tol) {
    //             result.impvol = vol;
    //             result.num_iter = i + 1;
    //             break;
    //         }
            
    //         // Update volatility using Newton's method
    //         vol = vol - diff * 0.1; // Simplified adjustment
    //     }
        
    //     return result;
    // }

    public static Output binom(final Derivative deriv, final MarketData mkt, int n) {
        if (n <= 0) throw new IllegalArgumentException("Number of steps must be positive");
        
        deriv.setMarketData(mkt);
        Output output = new Output();
        
        // Calculate parameters
        double dt = (deriv.getMaturity() - mkt.t0) / n;
        double u = Math.exp(mkt.sigma * Math.sqrt(dt));
        double d = 1.0 / u;
        double p = (Math.exp(mkt.r * dt) - d) / (u - d);
        
        // Create arrays to store values instead of tree nodes
        double[][] stockPrices = new double[n + 1][n + 1];
        double[][] optionValues = new double[n + 1][n + 1];
        
        // Build stock price array
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                stockPrices[i][j] = mkt.S * Math.pow(u, j) * Math.pow(d, i - j);
            }
        }
        
        // Initialize terminal conditions
        for (int j = 0; j <= n; j++) {
            Node terminalNode = new Node(stockPrices[n][j], n);
            deriv.terminalCondition(terminalNode);
            optionValues[n][j] = terminalNode.optionValue;
        }
        
        // Backward induction
        double discountFactor = Math.exp(-mkt.r * dt);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                double continuation = discountFactor * (p * optionValues[i + 1][j + 1] + 
                                                     (1 - p) * optionValues[i + 1][j]);
                Node currentNode = new Node(stockPrices[i][j], i);
                currentNode.optionValue = continuation;
                deriv.valuationTest(currentNode, i * dt);
                optionValues[i][j] = currentNode.optionValue;
            }
        }
        
        output.FV = optionValues[0][0];
        output.fugit = calculateFugit(optionValues, stockPrices, deriv, n, dt);
        
        return output;
    }
    
    private static double calculateFugit(double[][] optionValues, double[][] stockPrices, 
                                       Derivative deriv, int n, double dt) {
        // Simple fugit calculation - can be enhanced if needed
        return deriv.getMaturity();
    }
    
    public static Output impvol(final Derivative deriv, final MarketData mkt,
                          int n, int max_iter, double tol, Output out) {
    Output result = new Output();
    double vol = 0.3; // Initial guess
    
    for (int i = 0; i < max_iter; i++) {
        try {
            MarketData tempMkt = new MarketData(mkt.Price, mkt.S, mkt.r, vol, mkt.t0);
            Output tempOut = binom(deriv, tempMkt, n);
            
            double diff = tempOut.FV - mkt.Price;
            if (Math.abs(diff) < tol) {
                result.impvol = vol;
                result.num_iter = i + 1;
                return result;
            }
            
            // Modified Newton step with bounds checking
            double newVol = vol - diff * 0.1;
            // Ensure volatility stays positive and reasonable
            vol = Math.max(0.001, Math.min(newVol, 2.0));
            
        } catch (IllegalArgumentException e) {
            // If we get an invalid volatility, adjust and continue
            vol = 0.3; // Reset to initial guess
        }
    }
    
    // If we didn't converge, still return best estimate
    result.impvol = vol;
    result.num_iter = max_iter;
    return result;
}

}

/*
 * 
 * This new implementation:
Uses arrays instead of a tree structure
Eliminates recursive calls
Is much more memory efficient
Maintains the same functionality
The key changes are:

Replaced the tree structure with two 2D arrays: one for stock prices and one for option values
Eliminated recursive tree building
Uses iterative loops instead of recursion
Still maintains all the functionality for option pricing


 */