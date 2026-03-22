# Equal Sum Partition — Tabular DP (Java)

## Problem

Given an integer array `nums`, decide whether the elements can be split into **two disjoint subsets** with **equal sum**.

## Reduction to subset sum

- Let `S = sum(nums)`. Each of the two parts must sum to `S / 2`.
- So: **`S` must be even**, and there must exist a subset summing to **`S / 2`** (each element used at most once → same DP as 0/1 knapsack style).

**Assumptions (typical):** non-negative integers in `nums`.

**Complexity:** Time \(O(n \cdot \text{target})\), Space \(O(n \cdot \text{target})\) with `target = S / 2`, `n = nums.length`.

---

## Tabular DP

**Meaning of `t[i][j]`:** `true` if some subset of the **first `i`** elements sums to **`j`**.

- **Base:** `t[i][0] = true` for all `i` (sum `0` with empty subset).
- **Transition:** For sum `j` and item `arr[i-1]`:
  - If `j < arr[i-1]`, cannot take this item → `t[i][j] = t[i-1][j]`.
  - Else skip or take: `t[i][j] = t[i-1][j] || t[i-1][j - arr[i-1]]`.

Answer: `t[n][target]` after `target = S/2`.

```java
class Solution {

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if ((sum % 2) != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        return isSubsetSum(nums, target, n);
    }

    /** Bottom-up: subset of first n elements sums to sum? */
    private boolean isSubsetSum(int[] arr, int sum, int n) {
        boolean[][] t = new boolean[n + 1][sum + 1];

        for (int i = 0; i < n+1; i++) {
            t[i][0] = true;
        }

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < sum+1; j++) {
                if (j < arr[i - 1]) {
                    t[i][j] = t[i - 1][j];
                } else {
                    t[i][j] = t[i - 1][j] || t[i - 1][j - arr[i - 1]];
                }
            }
        }
        return t[n][sum];
    }
}
```

---

## See also

- **Subset sum** (`02-subsetsum.md`) — same table when the target sum is given directly.
