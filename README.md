# Financial Derivatives Pricing Library

## Overview

This Java library implements numerical methods for pricing financial derivatives, with a focus on the binomial tree model for option pricing. The implementation supports European, American, and Bermudan options with robust error handling and comprehensive testing.

## Features

- Binomial tree implementation for option pricing
- Support for multiple option types:
  - European options (calls and puts)
  - American options (calls and puts)
  - Bermudan options (with custom exercise windows)
- Implied volatility calculations
- Comprehensive error handling and input validation
- Extensive test suite

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/financial-derivatives-library.git
   ```
2. Import the project into your fav Java IDE.
3. Add the library to your project's dependencies.

## Quick Start

```java
// Create market data
MarketData mkt = new MarketData(100.0, // Current market price
                               100.0, // Stock price
                               0.05,  // Risk-free rate
                               0.2,   // Volatility
                               0.0);  // Current time

// Create a European call option
VanillaOption euroCall = new VanillaOption(100.0,  // Strike price
                                          true,    // Is call option
                                          false,   // Is American
                                          1.0);    // Time to maturity

// Price the option
Output result = Library.binom(euroCall, mkt, 100);  // 100 time steps
System.out.println("Option Price: " + result.FV);
```

## Usage Examples

### Pricing a European Call Option

```java
VanillaOption euroCall = new VanillaOption(100.0, true, false, 1.0);
Output result = Library.binom(euroCall, mkt, 100);
```

### Pricing an American Put Option

```java
VanillaOption amerPut = new VanillaOption(100.0, false, true, 1.0);
Output result = Library.binom(amerPut, mkt, 100);
```

### Calculating Implied Volatility

```java
Output impliedVol = Library.impvol(option, mkt, 100, 100, 0.0001, null);
System.out.println("Implied Volatility: " + impliedVol.impvol);
```

## Performance Considerations

- The binomial tree algorithm has complexity O(n²) where n is the number of time steps
- Memory usage scales quadratically with the number of time steps
- Recommended range for time steps: 50-1000 for most applications

## Dependencies

- Java 8 or higher
- No external libraries required

## Testing

Run the test suite:
```
java OptionPricingTest
```

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Authors

- Simranjeet Singh

## References

- Cox, J. C., Ross, S. A., & Rubinstein, M. (1979). Option Pricing: A Simplified Approach
- Hull, J. C. Options, Futures, and Other Derivatives