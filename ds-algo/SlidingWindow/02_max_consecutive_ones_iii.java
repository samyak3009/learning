/*
 * Max Consecutive Ones III
 *
 * This program finds the maximum number of consecutive 1's in a binary array if you can flip at most k 0's to 1's.
 *
 * Problem: Given a binary array arr and an integer k, return the maximum number of consecutive 1's in the array
 * if you can flip at most k 0's.
 *
 * Examples:
 * Input:  arr = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * Output: 6
 * Explanation: Flip the last two 0's to get [1,1,1,0,0,1,1,1,1,1,1], which has 6 consecutive 1's.
 *
 * Input:  arr = [0,0,1,1,1,0,0], k = 0
 * Output: 3
 * Explanation: No flips allowed, so the maximum consecutive 1's is 3.
 *
 * Algorithm:
 * 1. Use a sliding window with two pointers (left and right).
 * 2. Expand the window by moving the right pointer. If a 0 is encountered, decrement k (use a flip).
 * 3. If k becomes negative (more than allowed flips used), shrink the window from the left until k is non-negative again.
 * 4. Update the maximum window size at each step.
 *
 * Key Insight: The sliding window always contains at most k zeros. When more than k zeros are in the window, move the left pointer to reduce zeros.
 *
 * Time Complexity: O(n) - Each element is visited at most twice (once by right, once by left pointer).
 * Space Complexity: O(1) - Only a few integer variables are used.
 */


// here we will find the maxLen of the consecutive array which has max -> k 0's
class Solution {
    public int longestOnes(int[] arr, int k) {
        int l = 0, r = 0, maxLen = 0;
        int n = arr.length;

        while (r < n) {
            // If current element is 0, we use one flip
            if (arr[r] == 0) {
                k--;
            }

            // If we used more than k flips, shrink the window from the left
            // We use 'if' (not 'while') because we expand the window one step at a time.
            // If the window becomes invalid (too many zeros), in this case also it will maintain the constant window of len which was valid before some iteration.
            if (k < 0) {
                // If the leftmost element was a 0, we regain a flip
                if (arr[l] == 0) {
                    k++;
                }
                l++; // Move left pointer to shrink the window
            }

            // Update max length of the window
            maxLen = Math.max(maxLen, r - l + 1);
            r++; // Expand the window to the right
        }

        return maxLen;
    }
}
