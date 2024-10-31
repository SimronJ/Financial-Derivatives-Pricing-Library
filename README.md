# Options Finance Code Project

A comprehensive options pricing and visualization library that simulates real-world options trading data using binomial tree models.

## Features

### Core Pricing Models
- European Options (exercise at maturity only)
- American Options (exercise any time until maturity)
- Bermudan Options (exercise during specified windows)
- Implied Volatility calculations
- Greeks calculations (Delta)

### Market Data Visualization
- Real-time options chain display
- Bid-ask spread simulation
- Volume and Open Interest tracking
- Multiple strike prices generation
- Automated data logging system

## Structure

```
├── src/
│   ├── BermudanOption.java     # Bermudan option implementation
│   ├── Derivative.java         # Base derivative class
│   ├── Library.java           # Core pricing algorithms
│   ├── MarketData.java        # Market data container
│   ├── Node.java              # Tree node structure
│   ├── OptionPricingTest.java # Test suite
│   ├── OptionsChart.java      # Options chain visualization
│   ├── Output.java            # Results container
│   └── VanillaOption.java     # European/American options
├── data/
│   └── options_data.txt       # Generated options chain data
└── README.md
```

## Usage

### Basic Option Pricing
```java
// Create market data
MarketData mkt = new MarketData(
    10.0,    // market price
    100.0,   // stock price
    0.05,    // risk-free rate
    0.20,    // volatility
    0.0      // current time
);

// Create and price a European call option
VanillaOption euCall = new VanillaOption(
    100.0,   // strike price
    true,    // is call
    false,   // is American
    1.0      // maturity
);

Output result = Library.binom(euCall, mkt, 50);
```

### Options Chain Visualization
```java
// Create options chart
OptionsChart chart = new OptionsChart(
    "AAPL",  // ticker symbol
    100.0,   // current price
    45.0,    // IV rank
    0.015    // dividend yield
);

// Display and save options chain
chart.displayOptionsChain(0.25, mkt); // 3-month options
```

## Data Output

The program automatically generates an options data file (`options_data.txt`) containing:
- Timestamp of generation
- Current market conditions
- Complete options chain for calls and puts
- Bid/Ask prices
- Volume and Open Interest
- Implied Volatility
- Delta values

Sample output format:
```
Options Chain Generated: 2024-10-30 17:15:23

=== Options Chain for AAPL ===
Current Price: $175.50 | IV Rank: 45.0% | Dividend Yield: 1.50%
Days to Expiration: 91 | Risk-free Rate: 5.0%

=== CALLS ===
Strike | Type | Bid | Ask | Last | Volume | OI | IV% | Delta
----------------------------------------------------------
165.00 | EUR-C | 12.35 | 13.65 | 13.00 |    847 | 3254 | 20.0 |  0.65
...
```

## Implementation Details

### Market Data Generation
- Realistic bid-ask spreads based on option value
- Simulated volume and open interest
- Strike prices generated around current stock price
- Support for different option types and exercise styles

### File Management
- Automatic file creation and overwriting
- Timestamped data entries
- Formatted table output
- Error handling for file operations

## Error Handling

The library includes comprehensive error checking for:
- Negative strike prices
- Invalid volatility values
- Zero or negative maturity
- Invalid Bermudan exercise windows
- File I/O operations

## Testing

Run the test suite using:
```java
java OptionPricingTest
```

The test suite includes:
- Vanilla options pricing
- Bermudan options pricing
- Edge cases validation
- Implied volatility calculations
- Options chain generation

## Dependencies

- Java 8 or higher
- No external libraries required

## Author

Simranjeet Singh

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Notes

- All pricing is based on the binomial tree model
- Market data simulation is for educational purposes
- File output is overwritten on each run
- Timestamps are in local system time