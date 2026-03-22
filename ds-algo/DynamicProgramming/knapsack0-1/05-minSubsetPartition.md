# Minimum Subset Sum Difference — Tabular DP (Java)

## Problem

Given an array `arr[]`, split its elements into **two subsets** (partition the multiset of values; each element goes to exactly one side). Minimize **|sum(subset A) − sum(subset B)|**.

Let **`range = sum(arr)`**. If one subset has sum **`j`**, the other has sum **`range − j`**, so the difference is:

\[
|(range - j) - j| = |range - 2j|.
\]

So we need: among all **`j`** that are **achievable** as a subset sum of `arr`, minimize **`|range - 2j|`**.

**Complexity:** Building the subset-sum table is \(O(n \cdot \text{range})\) time and space.

---

## Why we use the **last row** `t[n][*]` of the table

We build the classic 0/1 subset-sum DP:

- **`t[i][j] = true`** iff some subset of the **first `i` elements** can sum to **`j`**.

**Row index `i` = how many array elements are allowed in the choice.**

- For **`i < n`**, you only allow prefixes of `arr`; sums you mark as possible might need elements you have not considered yet — so that row is **incomplete** for the whole array.
- For **`i = n`**, you have considered **all `n` elements**. So **`t[n][j]`** is `true` **iff** there exists **some subset of the entire array** whose sum is exactly **`j`**.

That is exactly the set of values **`j`** we are allowed to use in \(|range - 2j|\).  
We do **not** use row `n` because “the last row is special by magic” — we use it because **`i = n` means “all elements may be included or excluded,”** which matches the partition problem.

---

## Tabular subset-sum build

Same recurrence as `02-subsetsum.md` / `03-equal-partition.md`:

- `t[i][0] = true` (empty subset).
- For `j ≥ 1`: take or skip `arr[i-1]`.

Then scan **`j` from `0` to `range`** (all achievable sums) and minimize **`|range - 2j|`**. For readability, **`boolean[] isSumPossible = t[n]`** and loop over `j < isSumPossible.length` (same indices as `0..range`).

**Note:** Only scanning part of `[0, range]` can **miss** the optimal `j`. Consider **every** index `j` where `isSumPossible[j]` (equivalently `t[n][j]`) is true.

```java
class Solution {

    /**
     * Minimum absolute difference between sums of two subsets partitioning {@code arr}.
     */
    public int minDifference(int[] arr) {
        int n = arr.length;
        int range = 0;
        for (int x : arr) {
            range += x;
        }

        // t[i][j] = can first i elements form subset sum j?
        boolean[][] t = buildSubsetSumTable(arr, range, n);

        /*
         * Pull out the LAST ROW: same as t[n][j], easier to read in the scan below.
         * - Row i uses only arr[0..i-1]; row n uses ALL n elements.
         * - isSumPossible[j] == true  =>  some subset of the full array sums to j.
         * Other subset then sums to (range - j); difference |range - 2j| == |(range-j) - j|.
         */
        boolean[] isSumPossible = t[n];

        int result = range; // worst case: one side empty -> diff = range
        for (int j = 0; j <= (isSumPossible.length/2)+1; j++) {
            if (!isSumPossible[j]) {
                continue;
            }
            int subsetSum1 = j;
            int subsetSum2 = range - subsetSum1;
            result = Math.min(result, Math.abs(subsetSum2 - subsetSum1));
        }
        return result;
    }

    /** Standard 0/1 subset-sum DP; dimensions (n+1) x (range+1). */
    private boolean[][] buildSubsetSumTable(int[] arr, int sum, int n) {
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
        return t;
    }
}
```

---

## See also

- **Subset sum (existence)** — `02-subsetsum.md`
- **Equal partition** (`|diff| = 0`) — `03-equal-partition.md`
