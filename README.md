# Option Pricing Calculator

## Overview
This Java project implements a binomial tree model for pricing various types of financial options. It provides accurate pricing calculations for European, American, and Bermudan options using industry-standard numerical methods.

## Features
- Multiple option types support:
  - European Options (exercise at maturity only)
  - American Options (exercise any time until maturity)
  - Bermudan Options (exercise during specified windows)
- Implied volatility calculations
- Comprehensive error handling and input validation
- Flexible binomial tree implementation
- Professional-grade numerical computations

## Technical Details

### Core Components

#### Market Data (`MarketData.java`)
Handles market-related parameters:
- Current stock price
- Risk-free interest rate
- Volatility
- Time parameters

#### Option Types
- `VanillaOption.java`: Base implementation for European/American options
- `BermudanOption.java`: Implementation for window-based exercise options
- `Derivative.java`: Abstract base class for all derivatives

#### Pricing Engine (`Library.java`)
Implements core pricing algorithms:
- Binomial tree model
- Implied volatility calculator
- Risk calculations

## Usage Example

```java
// Create market data
MarketData mkt = new MarketData(
    10.0,   // Market price
    100.0,  // Stock price
    0.05,   // Risk-free rate
    0.2,    // Volatility
    0.0     // Current time
);

// Create a European call option
VanillaOption euCall = new VanillaOption(
    100.0,  // Strike price
    true,   // Is call option
    false,  // Is not American
    1.0     // 1 year to maturity
);

// Price the option
Output result = Library.binom(euCall, mkt, 50);  // 50 time steps
```

## Test Suite

The project includes comprehensive tests (`OptionPricingTest.java`) covering:

1. **Vanilla Options**
   - European calls and puts
   - American puts
   ```java
   // Example output:
   European Call:
   └─ Parameters: Strike=100.00, Spot=100.00, Vol=20.00%
   └─ Results: Fair Value=10.43, Fugit=1.00
   ```

2. **Bermudan Options**
   - Window-based exercise rights
   - Custom exercise periods

3. **Edge Cases**
   - Negative strike prices
   - Invalid exercise windows
   - Negative volatility
   - Zero maturity
   - Negative time values

4. **Implied Volatility**
   - Market price to volatility conversion
   - Newton-Raphson iteration method

## Installation and Setup

1. Clone the repository:
```bash
git clone https://github.com/SimronJ/Financial-Derivatives-Pricing-Library/tree/main
```

2. Compile the Java files:
```bash
javac -d bin src/*.java
```

3. Run the tests:
```bash
java -cp bin OptionPricingTest
```

## Dependencies
- Java JDK 23 or higher
- No external libraries required

## Mathematical Background
The project implements the Cox-Ross-Rubinstein binomial model with:
- Risk-neutral pricing
- Backward induction
- Newton's method for implied volatility

## Error Handling
- Input validation for all parameters
- Comprehensive edge case handling
- Clear error messages for debugging

## Performance Considerations
- Optimized tree building
- Memory-efficient calculations
- Iterative methods for large datasets

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## Acknowledgments
- Cox-Ross-Rubinstein for the binomial model framework