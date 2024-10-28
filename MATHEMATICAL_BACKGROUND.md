# Mathematical Background for Option Pricing Library

## 1. The Binomial Model Framework

### 1.1 Basic Principles

The binomial model assumes that the stock price follows a discrete multiplicative random walk. At each time step, the stock price can either:
- Move up by a factor u with probability p
- Move down by a factor d with probability (1-p)

### 1.2 Parameter Calculations

The key parameters are calculated as follows:

```
Δt = T/n                     (time step size)
u = exp(σ√Δt)               (up factor)
d = 1/u                     (down factor)
p = (exp(rΔt) - d)/(u - d)  (risk-neutral probability)
```

Where:
- T is time to maturity
- n is number of time steps
- σ is volatility
- r is risk-free rate

## 2. Risk-Neutral Pricing

### 2.1 Theory

The binomial model uses risk-neutral pricing, meaning:
- Future payoffs are discounted at the risk-free rate
- Stock price expected return equals the risk-free rate
- Option value is independent of investor risk preferences

### 2.2 Option Value Calculation

For a European option at any node:

```
V = exp(-rΔt)[pVu + (1-p)Vd]
```

Where:
- V is current option value
- Vu is option value after up move
- Vd is option value after down move

## 3. Early Exercise Features

### 3.1 American Options

For American options, at each node:

```
V = max(intrinsic value, continuation value)
```

Where:
- Intrinsic value = max(0, S - K) for calls
- Intrinsic value = max(0, K - S) for puts
- Continuation value = exp(-rΔt)[pVu + (1-p)Vd]

### 3.2 Bermudan Options

Similar to American options but early exercise is only allowed within specified time windows.

## 4. Implied Volatility Calculation

### 4.1 Newton's Method

The implied volatility σ is found by solving:

```
C(σ) = Market Price
```

Using Newton's method:

```
σn+1 = σn - (C(σn) - Market Price)/Vega(σn)
```

## 5. Convergence Properties

### 5.1 European Options

The binomial model converges to the Black-Scholes price as n → ∞:

```
lim(n→∞) Binomial Price = Black-Scholes Price
```

### 5.2 Rate of Convergence

- Error is O(1/n) for European options
- Convergence rate may be slower for American options

## 6. Greeks Calculation

### 6.1 Delta

```
Δ = (Vu - Vd)/(Su - Sd)
```

### 6.2 Gamma

```
Γ = (Δu - Δd)/(Su - Sd)
```

### 6.3 Theta

```
Θ ≈ (Vt+Δt - Vt)/Δt
```

## 7. Relationship to Black-Scholes Model

The binomial model can be viewed as a discrete approximation to the Black-Scholes model:

```
dS/S = μdt + σdW
```

As Δt → 0, the discrete binomial process converges to this continuous-time process.

## 8. Model Limitations

1. Assumes constant volatility
2. Discrete time steps
3. No transaction costs
4. Perfect market assumptions
5. No dividends (in basic version)

## 9. References

1. Cox, J. C., Ross, S. A., & Rubinstein, M. (1979). Option Pricing: A Simplified Approach. Journal of Financial Economics, 7, 229-263.

2. Hull, J. C. (2018). Options, Futures, and Other Derivatives (10th ed.). Pearson.

3. Wilmott, P. (2006). Paul Wilmott on Quantitative Finance (2nd ed.). Wiley.