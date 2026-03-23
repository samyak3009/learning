# Target Sum — Tabular DP (Java)

## Problem

Given an array `nums` and an integer `target`, assign either **`+`** or **`-`** sign to every number such that the final expression equals `target`.

Return the **number of ways** to do this.

## Reduction to “count subset sum”

Let:

- `P` = sum of numbers assigned **`+`**
- `N` = sum of numbers assigned **`-`**
- `range = sum(nums) = P + N`

The expression equals:

`P - N = target`

Now solve:

```text
P - N = target
P + N = range

2P = range + target
P = (range + target) / 2
```

So we need to:

- Check `range >= |target|` (otherwise impossible)
- Check `(range + target)` is even (so `P` is an integer)
- Count the number of subsets whose sum is exactly `P`

That subset tells which elements go into the **`+`** group. The remaining elements go into the **`-`** group.

## DP meaning (tabular)

We count subsets (ways), not boolean feasibility.

- `t[i][j]` = number of ways to form sum `j` using the **first `i` elements**

Base:

- `t[i][0] = 1` for all `i` (empty subset always forms sum `0`)

Transition for item `nums[i-1]`:

- If `j < nums[i-1]` → cannot take this item  
  `t[i][j] = t[i-1][j]`
- Else:
  - skip: `t[i-1][j]`
  - take: `t[i-1][j - nums[i-1]]`
  - total ways: `t[i][j] = t[i-1][j] + t[i-1][j - nums[i-1]]`

Answer: `t[n][P]`.

## Complexity

- Time: `O(n * P)` where `P = (range + target)/2`
- Space: `O(n * P)`

## Code (Java)

```java
class Solution {

    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int range = 0;
        for (int x : nums) range += x;

        if (target > range) return 0;

        // P = (range + target) / 2
        if ((range + target) % 2 != 0) return 0;
        int targetSum = (range + target) / 2;

        return countSubsetSum(nums, targetSum, n);
    }

    private int countSubsetSum(int[] nums, int sum, int n) {
        int[][] t = new int[n + 1][sum + 1];

        // base condition: sum=0 -> one way (empty subset)
        for (int i = 0; i < n+1; i++) {
            t[i][0] = 1;
        }

        for (int i = 1; i < n+1; i++) {
            for (int j = 0; j < sum+1; j++) {
                if (j < nums[i - 1]) {
                    t[i][j] = t[i - 1][j]; // not included
                } else {
                    t[i][j] = t[i - 1][j] + t[i - 1][j - nums[i - 1]]; // not include + include
                }
            }
        }

        return t[n][sum];
    }
}
```

## See also

- `06-countPartition.md` (same subset-sum counting idea with `diff`)
- `04-countSubsetEqualToTarget.md` (count subsets for a target sum)
- `02-subsetsum.md` / `03-equal-partition.md` (boolean variants using `||`)