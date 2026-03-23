# House Robber — DP (Java)

## Problem (House Robber I)

You are given an array `nums` where `nums[i]` is the money in the `i-th` house.

You must **not** rob two adjacent houses. Return the **maximum** amount of money you can rob.

## DP idea (Top-down memoization)

Define:

- `f(i)` = maximum money you can rob from houses `0..i` (linear arrangement).

At index `i`, you have 2 choices:

- **Take** house `i`: `nums[i] + f(i-2)`
- **Not take** house `i`: `f(i-1)`

So:

- `f(i) = max(nums[i] + f(i-2), f(i-1))`

Base case:

- if `i < 0`, profit is `0`

Memo:

- store results in `dp[i]` (`Integer[]`) so `null` means “not computed yet”.

## Solution 1: Linear houses (`HouseRobber1`)

```java
class HouseRobber1 {
    public int rob(int[] nums) {
        int n = nums.length;
        return findRob(nums, n - 1, new Integer[n]);
    }

    public int findRob(int[] nums, int i, Integer[] dp) {
        if (i < 0) return 0;
        if (dp[i] != null) return dp[i];

        int take = nums[i] + findRob(nums, i - 2, dp);
        int notTake = findRob(nums, i - 1, dp);

        return dp[i] = Math.max(take, notTake);
    }
}
```

## Problem (House Robber II)

In the circular version, the first and last houses are also adjacent.

## DP reduction (circular -> two linear runs)

If houses are circular, you cannot take both:

- house `0` and house `n-1`

So the answer is:

- max(rob houses `0..n-2`, rob houses `1..n-1`)

Your helper `findRob(nums, start, n, dp)` computes the linear DP for a given range.

## Solution 2: Circular houses (`HouseRobber2`)

```java
class HouseRobber2 {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        // Case 1: don't take last house => consider range [0 .. n-2]
        // Case 2: don't take first house => consider range [1 .. n-1]
        return Math.max(
                findRob(nums, 0, n - 2, new Integer[n]),
                findRob(nums, 1, n - 1, new Integer[n])
        );
    }

    public int findRob(int[] nums, int start, int i, Integer[] dp) {
        if (i < start) return 0;
        if (dp[i] != null) return dp[i];

        int take = nums[i] + findRob(nums, start, i - 2, dp);
        int notTake = findRob(nums, start, i - 1, dp);
        return dp[i] = Math.max(take, notTake);
    }
}
```

## Complexity

- Time: `O(n)` for each variant (each index computed once via memoization)
- Space: `O(n)` for the memo array + recursion stack