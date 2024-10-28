/**
 * Represents a node in the binomial tree structure.
 * Each node contains information about the stock price and option value
 * at a specific point in time and state.
 */
class Node {
    /** Current stock price at this node */
    public double stockPrice;
    /** Calculated option value at this node */
    public double optionValue;
    /** Reference to the up-movement child node */
    public Node upNode;
    /** Reference to the down-movement child node */
    public Node downNode;
    /** Current time step in the tree (0 = root) */
    public int timeStep;
    
    /**
     * Constructs a new node in the binomial tree.
     * 
     * @param stockPrice The stock price at this node
     * @param timeStep The time step this node represents
     */
    public Node(double stockPrice, int timeStep) {
        this.stockPrice = stockPrice;
        this.optionValue = 0.0;
        this.timeStep = timeStep;
        this.upNode = null;
        this.downNode = null;
    }
}