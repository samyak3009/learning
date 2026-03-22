# Subset Sum — Three Patterns (Java)

## Problem

Given an array `arr[]` and an integer `sum`, decide whether there is a **subset** of elements whose values add up to **exactly** `sum`. Each element may be used **at most once** (same 0/1 choice structure as knapsack).

**Assumptions (typical):** non-negative integers in `arr` and `sum ≥ 0`. If elements can be negative, the classic \(O(n \cdot \text{sum})\) DP does not apply as stated.

- **State:** `n` = consider first `n` items (indices `0 .. n-1`), `sum` = remaining target.
- **Base cases:** `sum == 0` → `true` (empty subset). `n == 0` and `sum > 0` → `false`.

---

## Pattern 1 — Pure recursion

**Idea:** For the last considered item, either **don’t take** it (subset from first `n-1` with same `sum`) or **take** it (if `arr[n-1] ≤ sum`, subset from first `n-1` with `sum - arr[n-1]`). Answer is `OR` of the two.

**Complexity:** Time \(O(2^n)\), Space \(O(n)\) call stack.

```java
/**
 * Subset Sum — Pattern 1: Recursion only (no memoization).
 */
public class SubsetSumRecursion {

    public boolean isSubsetSum(int[] arr, int sum) {
        if (sum < 0) {
            return false;
        }
        int n = arr.length;
        return recursiveSubset(arr, sum, n);
    }

    private boolean recursiveSubset(int[] arr, int sum, int n) {
        if (sum == 0) {
            return true; // base condition
        }
        if (n == 0) {
            return false; // base condition
        }

        if (sum < arr[n - 1]) {
            return recursiveSubset(arr, sum, n - 1);
        }

        boolean include = recursiveSubset(arr, sum - arr[n - 1], n - 1);
        boolean notInclude = recursiveSubset(arr, sum, n - 1);
        return include || notInclude;
    }
}
```

---

## Pattern 2 — Recursion + DP (memoization, top-down)

**Idea:** Same recurrence; cache `dp[n][sum]` so each `(n, sum)` is computed once.

**Complexity:** Time \(O(n \cdot \text{sum})\), Space \(O(n \cdot \text{sum})\).

**Why `Boolean[][]` (wrapper)?** Use **`null`** = not computed yet; `Boolean.TRUE` / `Boolean.FALSE` = cached result. Primitives `boolean[][]` cannot represent “unset”.

**Bug fix:** When `sum < arr[n-1]`, you must **store** the result in `dp[n][sum]`. Returning without assigning leaves `null` and causes **wrong recomputation / stack blow-ups**.

```java
import java.util.*;

/**
 * Subset Sum — Pattern 2: Recursion + memoization (top-down DP).
 */
public class SubsetSumMemoization {

    private Boolean[][] dp;

    public boolean isSubsetSum(int[] arr, int sum) {
        if (sum < 0) {
            return false;
        }
        int n = arr.length;
        dp = new Boolean[n + 1][sum + 1];
        return memoSubset(arr, sum, n);
    }

    private boolean memoSubset(int[] arr, int sum, int n) {
        // Base condition
        if (sum == 0) {
            return true;
        }
        if (n == 0) {
            return false;
        }
        if (dp[n][sum] != null) {
            return dp[n][sum];
        }

        if (sum < arr[n - 1]) {
            return dp[n][sum] = memoSubset(arr, sum, n - 1);
        } else {
            boolean include = memoSubset(arr, sum - arr[n - 1], n - 1);
            boolean notInclude = memoSubset(arr, sum, n - 1);
            return dp[n][sum] = include || notInclude;
        }
    }
}
```

---

## Pattern 3 — Tabular DP (bottom-up)

**Idea:** `t[i][j]` = `true` if a subset of the first `i` elements can sum to `j`. First column `j == 0` is always `true`; row `i == 0` and `j > 0` stays `false`.

**Complexity:** Time \(O(n \cdot \text{sum})\), Space \(O(n \cdot \text{sum})\) (can compress to one row).

```java
/**
 * Subset Sum — Pattern 3: Bottom-up tabular DP.
 */
public class SubsetSumTabular {

    public boolean isSubsetSum(int[] arr, int sum, int n) {
        boolean[][] t = new boolean[n + 1][sum + 1];

        // Base condition here is calculated when it reached to t[i][0] as here sum become zero;
        for (int i = 0; i < n+1; i++) {
            t[i][0] = true;
        }

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < sum+1; j++) {
                if (j < arr[i - 1]) {
                    t[i][j] = t[i - 1][j]; // not include
                } else {
                    t[i][j] = t[i - 1][j] || t[i - 1][j - arr[i - 1]]; // either include OR not include
                }
            }
        }
        return t[n][sum];
    }
}
```

---

## Quick comparison

| Pattern        | Approach          | Time              | Space            |
|----------------|-------------------|-------------------|------------------|
| 1. Recursion   | Brute force       | \(O(2^n)\)        | \(O(n)\) stack   |
| 2. Memoization | Top-down + cache  | \(O(n \cdot sum)\)| \(O(n \cdot sum)\)|
| 3. Tabular     | Bottom-up table   | \(O(n \cdot sum)\)| \(O(n \cdot sum)\)|

**Relation to 0/1 knapsack:** Subset sum is the **decision** version: “max value ≥ target?” becomes “exists subset with sum = target?” — same take/skip structure, `||` instead of `max`.

---

## Optional unified `Solution` (pick one pattern)

```java
public class Solution {

    public static boolean isSubsetSum(int[] arr, int sum) {
        return new SubsetSumTabular().isSubsetSum(arr, sum);
        // or: new SubsetSumMemoization().isSubsetSum(arr, sum);
        // or: new SubsetSumRecursion().isSubsetSum(arr, sum);
    }
}
```
