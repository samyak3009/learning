# Unbounded Knapsack — Recursive + Tabular DP (Java)

## Problem

Given:
- values `val[]`
- weights `wt[]`
- knapsack capacity `capacity`

In **unbounded knapsack**, you can take each item **any number of times** (including 0). Maximize the total value without exceeding the capacity.

Assumptions (typical):
- `val.length == wt.length`
- `wt[i] > 0`, `val[i] >= 0`

## DP State / Recurrence

Let `f(n, W)` be the maximum value using the **first `n` items** (`0..n-1`) with remaining capacity `W`.

Choices for item `n-1`:
- **Skip** item `n-1` → `f(n-1, W)`
- **Take** item `n-1` (since it’s unbounded, you can take it again) → `val[n-1] + f(n, W - wt[n-1])`

So:
- If `W < wt[n-1]` → `f(n, W) = f(n-1, W)`
- Else → `f(n, W) = max( f(n-1, W), val[n-1] + f(n, W - wt[n-1]) )`

Base:
- `n == 0` or `W == 0` → `0`

## Complexity

- Time: `O(n * capacity)`
- Space: `O(n * capacity)` (memo/table)

---

## Pattern 1 — Recursion + Memoization (Top-Down)

Use `Integer[][] dp` where:
- `null` means “not computed yet”
- otherwise store the computed max value.

```java
import java.util.*;

class UnboundedKnapsackRecursive {

    public int knapSack(int[] val, int[] wt, int capacity) {
        int n = wt.length;
        Integer[][] dp = new Integer[n + 1][capacity + 1];
        return unboundedRecursive(val, wt, capacity, n, dp);
    }

    private int unboundedRecursive(int[] val, int[] wt, int W, int n, Integer[][] dp) {
        if (n == 0 || W == 0) return 0;
        if (dp[n][W] != null) return dp[n][W];

        if (W < wt[n - 1]) {
            return dp[n][W] = unboundedRecursive(val, wt, W, n - 1, dp);
        }

        int notTake = unboundedRecursive(val, wt, W, n - 1, dp);
        int take = val[n - 1] + unboundedRecursive(val, wt, W - wt[n - 1], n, dp);

        return dp[n][W] = Math.max(take, notTake);
    }
}
```

---

## Pattern 2 — Tabular DP (Bottom-Up)

Let `t[i][w]` be the maximum value using the first `i` items with capacity `w`.

Transition (unbounded take keeps `i` same):
- If `w < wt[i-1]` → `t[i][w] = t[i-1][w]`
- Else → `t[i][w] = max( t[i-1][w], val[i-1] + t[i][w - wt[i-1]] )`

```java
class UnboundedKnapsackTabular {

    public int knapSack(int[] val, int[] wt, int W) {
        int n = wt.length;
        int[][] t = new int[n + 1][W + 1];

        // Base: t[0][*] = 0 and t[*][0] = 0 by default

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < W+1; j++) {
                if (j < wt[i - 1]) {
                    t[i][j] = t[i - 1][j];
                } else {
                    int notTake = t[i - 1][j];
                    int take = val[i - 1] + t[i][j - wt[i - 1]]; // unbounded => stay at i
                    t[i][j] = Math.max(notTake, take);
                }
            }
        }
        return t[n][W];
    }
}
```

---

## Quick check

If you move to `t[i-1][w - wt[i-1]]` for the “take” case, you get **bounded knapsack**.
For unbounded knapsack, “take” must stay in the same `i` (same item can be reused).