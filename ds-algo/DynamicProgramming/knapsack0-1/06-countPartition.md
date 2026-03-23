# Count Partitions With Given Difference — Tabular DP (Java)

## Problem

Given an array `arr` and an integer `diff`, count how many ways you can partition `arr` into two subsets:

- `subset1` and `subset2` use all elements
- `subset1Sum - subset2Sum` (absolute difference in most problem statements) equals `diff`

This is the **count of valid partitions**, not just a yes/no answer.

## Reduction to “Count Subset Sum”

Let `range = sum(arr)`.

Assume we use the common derivation:

- `diff = subsetSum2 - subsetSum1`
- `range = subsetSum1 + subsetSum2`

Then:

```text
diff = range - subsetSum1 - subsetSum1
diff = range - 2 * subsetSum1
subsetSum1 = (range - diff) / 2
```

So we:

1. Check `range >= diff` (otherwise impossible)
2. Check `(range - diff)` is even (otherwise impossible)
3. Count how many subsets have sum `targetSum = (range - diff)/2`

## DP definition (Tabular)

We count subsets (not boolean reachability).

- `t[i][j]` = number of ways to pick a subset from the **first `i` elements** with sum `j`

Base case:

- `t[i][0] = 1` for all `i` because there is exactly **one** way to make sum `0`: take the empty subset.

Transition (0/1 choice):

- If `j < arr[i-1]` → cannot include this element  
  `t[i][j] = t[i-1][j]`
- Else can skip or take →  
  `t[i][j] = t[i-1][j] + t[i-1][j - arr[i-1]]`

Answer: `t[n][targetSum]`

## Complexity

Time: `O(n * targetSum)`  
Space: `O(n * targetSum)`

## Code (with your same comment pattern)

```java
class Solution {
    public int countPartitions(int[] arr, int diff) {
        int n = arr.length;
        int range = 0;
        for (int item : arr) range += item;

        if (range < diff) return 0;

        // diff = subsetSum2 - subsetSum1;
        // range = subsetSum1 + subsetSum2;
        // diff = range - subsetSum1 - subsetSum1;
        // diff = range - 2 * subsetSum1;
        // subsetSum1 = (range - diff) / 2;

        if ((range - diff) % 2 != 0) return 0; // not possible => no integer targetSum exists

        // we want the no of subset which have targetSum
        int targetSum = (range - diff) / 2;

        return countSubsetEqualSum(arr, targetSum, n);
    }

    private int countSubsetEqualSum(int[] arr, int sum, int n) {
        int[][] t = new int[n + 1][sum + 1]; // default counts are 0

        // base condition
        for (int i = 0; i < n + 1; i++) {
            t[i][0] = 1; // one way to make sum 0 (empty subset)
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (j < arr[i - 1]) {
                    t[i][j] = t[i - 1][j]; // not included;
                } else {
                    t[i][j] = t[i - 1][j] + t[i - 1][j - arr[i - 1]];
                    // not include + include (all possible combinations)
                }
            }
        }

        return t[n][sum];
    }
}
```

## See also

- **Count Subset Sum == Target** — `04-countSubsetEqualToTarget.md`
- **Subset Sum (boolean)** — `02-subsetsum.md`
