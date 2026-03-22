# Count Subsets With Given Sum — Tabular DP (Java)

## Problem

Given an array `nums` and an integer `target`, count how many **subsets** have elements that sum to **exactly** `target`. Each element may be used **at most once** (0/1 choice per index).

**Difference from subset-sum (boolean):** here `t[i][j]` stores a **count**, not `true`/`false`.

**Assumptions (typical):** non-negative integers. You must iterate **`j` from `0`** so that **`target == 0`** and **zeros in `nums`** are handled (e.g. subsets `[24]` and `[0, 24]` both sum to `24` when `0` is present).

**Complexity:** Time \(O(n \cdot \text{target})\), Space \(O(n \cdot \text{target})\).

**Overflow:** The count can exceed `int`; use `long[][]` (and return type / cast) if needed. Some platforms ask for the answer **mod** \(10^9 + 7\).

---

## Tabular DP

**Meaning of `t[i][j]`:** number of subsets of the **first `i`** elements that sum to **`j`**.

- **Transition** for item `nums[i-1]`:
  - If `j < nums[i-1]`: cannot take this element → `t[i][j] = t[i-1][j]`.
  - Else: skip **or** take → `t[i][j] = t[i-1][j] + t[i-1][j - nums[i-1]]`.

Answer: `t[n][target]`.

```java
class Solution {

    /**
     * Count subsets of nums with sum exactly equal to target.
     * (GeeksForGeeks / similar: sometimes named perfectSum.)
     */
    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        return subsetSumCount(nums, target, n);
    }

    private int subsetSumCount(int[] nums, int sum, int n) {
        int[][] t = new int[n + 1][sum + 1];
        // base case
        for(int i = 0; i < n+1; i++)
            t[i][0] = 1;

        for (int i = 1; i < n+1; i++) {
            // j must start at 0: handles target 0 and nums [24, 0, 28]  containing 0 as in this case sum(j) = 0 is a valid sum.
            for (int j = 0; j < sum+1; j++) {
                if (j < nums[i - 1]) {
                    t[i][j] = t[i - 1][j]; // not include
                } else {
                    t[i][j] = t[i - 1][j] + t[i - 1][j - nums[i - 1]];
                }
            }
        }
        return t[n][sum];
    }
}
```
