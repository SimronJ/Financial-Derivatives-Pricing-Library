# Real World Example

Imagine you're interested in buying Apple stock, which is currently trading at $100. Instead of buying the stock directly, you can buy an "option" that gives you:

- The right (but not obligation) to buy Apple stock at $100 (strike price)
- Within the next year (maturity/expiration)

This project helps calculate how much such options should cost based on various factors.

## Types of Options in the Project

### Vanilla Options (Most Basic Type)

- **European Call**: Right to buy stock at strike price on expiration date only
- **American Put**: Right to sell stock at strike price any time until expiration

```java
// Example in testVanillaOptions():
// Calculates price for option on $100 stock
// Strike price = $100
// Interest rate = 5%
// Volatility = 20%
// Time to expiry = 1 year
```

### Bermudan Options (Middle Ground)

Like American options but can only exercise during specific windows
Example: Can exercise only between 3-9 months of a 1-year option

```java
// Example in testBermudanOptions():
// Similar parameters but with exercise window
// Window: 0.25 (3 months) to 0.75 (9 months)
```

## Test Cases Explained

### Vanilla Options Tests

```java
testVanillaOptions()
// Tests pricing of basic options:
// - European Call (like buying future rights)
// - American Put (like buying insurance)
```

### Bermudan Options Tests

```java
testBermudanOptions()
// Tests options with specific exercise windows
// Like having a coupon that's only valid during certain months
```

### Edge Cases Tests

```java
testEdgeCases()
// Tests invalid inputs that shouldn't be allowed:
// Test 1: Negative Strike Price
// (Like saying you'll buy something for -$100 - impossible)

// Test 2: Invalid Bermudan Window
// (Like saying a sale ends before it begins)

// Test 3: Negative Volatility
// (Like saying market fluctuations are negative - impossible)

// Test 4: Zero Maturity
// (Like having an expired coupon from the start)

// Test 5: Negative Time
// (Like saying the option expired before it was created)
```

### Implied Volatility Tests

```java
testImpliedVolatility()
// Given a market price, figures out what volatility the market expects
// Like reverse engineering how risky the market thinks the stock is
// Example: If option costs $10, what does that tell us about expected price swings?
```

## Example Output Explanation

```
European Call:
└─ Parameters: Strike=100.00, Spot=100.00, Vol=20.00%
└─ Results: Fair Value=10.43, Fugit=1.00, Implied Vol=0.00%, Iterations=0
```

This means:

- Current stock price (Spot) = $100
- You can buy stock (Strike) at $100
- Market volatility = 20%
- This option should cost $10.43
- You have 1 year to exercise (Fugit)

## Real World Connection

- Banks use these calculations to price options they sell
- Investors use it to evaluate if options are fairly priced
- Companies use it for risk management (like airlines hedging fuel prices)
- Traders use implied volatility to understand market expectations

Think of it like insurance pricing:

- More volatile stock = higher option price (like higher insurance for risky drivers)
- Longer time to expiry = more expensive (like longer insurance coverage)
- More flexibility (American vs European) = more expensive (like flexible vs rigid insurance terms)

The project ensures all these calculations are accurate and handles invalid cases (like negative prices) appropriately.