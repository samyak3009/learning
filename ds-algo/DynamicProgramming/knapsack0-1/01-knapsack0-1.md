# 0/1 Knapsack — Three Patterns (Java)

## Problem

Given a knapsack capacity `W`, item weights `wt[]`, and values `val[]`, choose each item **at most once** to maximize total value without exceeding capacity.

- **State:** `n` = number of items considered (first `n` items), `W` = remaining capacity.
- **Base case:** `n == 0` or `W == 0` → value `0`.

---

## Pattern 1 — Pure recursion

**Idea:** For each item, either **skip** it or **take** it (if weight fits). Take `max` of both choices.

**Complexity:** Time \(O(2^n)\), Space \(O(n)\) call stack.

**Note:** Always check `W >= wt[n-1]` **before** recursing with “include”; otherwise you recurse with invalid negative capacity.

```java
import java.util.*;

/**
 * 0/1 Knapsack — Pattern 1: Recursion only (no memoization).
 */
public class Knapsack01Recursion {
    public int knapsack(int W, int[] val, int[] wt) {
        int n = wt.length;
        return recursiveKnapsack(W, val, wt, n);
    }

    private int recursiveKnapsack(int W, int[] val, int[] wt, int n) {
        if (W == 0 || n == 0) {
            return 0;
        }

        // Cannot take item (n-1): weight exceeds capacity
        if (W < wt[n - 1]) {
            return recursiveKnapsack(W, val, wt, n - 1);
        }

        int notInclude = recursiveKnapsack(W, val, wt, n - 1);
        int included = val[n - 1] + recursiveKnapsack(W - wt[n - 1], val, wt, n - 1);
        return Math.max(notInclude, included);
    }
}
```

---

## Pattern 2 — Recursion + DP (memoization, top-down)

**Idea:** Same recurrence as recursion, but cache `dp[n][W]` so each subproblem is solved once.

**Complexity:** Time \(O(n \cdot W)\), Space \(O(n \cdot W)\) for table + \(O(n)\) stack.

**State meaning:** `dp[i][w]` = max value using first `i` items with capacity `w`. Initialize with `-1` for “uncomputed”.

```java
import java.util.*;

/**
 * 0/1 Knapsack — Pattern 2: Recursion + memoization (top-down DP).
 */
public class Knapsack01Memoization {

    private int[][] dp;

    public int knapsack(int W, int[] val, int[] wt) {
        int n = wt.length;
        dp = new int[n + 1][W + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        return memoKnapsack(W, val, wt, n);
    }

    private int memoKnapsack(int W, int[] val, int[] wt, int n) {
        if (W == 0 || n == 0) {
            return 0;
        }
        if (dp[n][W] != -1) {
            return dp[n][W];
        }

        if (W < wt[n - 1]) {
            return dp[n][W] = memoKnapsack(W, val, wt, n - 1);
        }

        int notInclude = memoKnapsack(W, val, wt, n - 1);
        int include = val[n - 1] + memoKnapsack(W - wt[n - 1], val, wt, n - 1);
        return dp[n][W] = Math.max(include, notInclude);
    }
}
```

---

## Pattern 3 — Tabular DP (bottom-up)

**Idea:** Fill `t[i][j]` = max value using first `i` items with capacity `j`. Transition matches the same “take / skip” logic.

**Complexity:** Time \(O(n \cdot W)\), Space \(O(n \cdot W)\) (can be optimized to \(O(W)\) with one row).

```java
import java.util.*;

/**
 * 0/1 Knapsack — Pattern 3: Bottom-up tabular DP.
 */
public class Knapsack01Tabular {

    public int knapsack(int W, int[] val, int[] wt) {
        int n = wt.length;
        int[][] t = new int[n + 1][W + 1];

        // Base: 0 items or 0 capacity → 0
        for (int i = 0; i < n+1; i++) {
            for (int j = 0; j < W+1; j++) {
                if (i == 0 || j == 0) {
                    t[i][j] = 0;
                }
            }
        }

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < W+1; j++) {
                if (j < wt[i - 1]) {
                    t[i][j] = t[i - 1][j];
                } else {
                    t[i][j] = Math.max(
                            t[i - 1][j],
                            val[i - 1] + t[i - 1][j - wt[i - 1]]
                    );
                }
            }
        }
        return t[n][W];
    }
}
```

---

## Quick comparison

| Pattern        | Approach        | Time        | Space           |
|----------------|-----------------|-------------|-----------------|
| 1. Recursion   | Brute force     | \(O(2^n)\)  | \(O(n)\) stack  |
| 2. Memoization | Top-down + cache| \(O(nW)\)   | \(O(nW)\)       |
| 3. Tabular     | Bottom-up table| \(O(nW)\)  | \(O(nW)\)       |

---

## Unified driver (optional)

If a single entry point is needed (e.g. for LeetCode-style `Solution` class), delegate to one of the three patterns:

```java
public class Solution {
    public int knapsack(int W, int[] val, int[] wt) {
        return new Knapsack01Tabular().knapsack(W, val, wt);
        // or: new Knapsack01Memoization().knapsack(W, val, wt);
        // or: new Knapsack01Recursion().knapsack(W, val, wt);
    }
}
```
