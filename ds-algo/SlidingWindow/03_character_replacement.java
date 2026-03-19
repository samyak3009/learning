/*
 * Longest Repeating Character Replacement
 *
 * This program finds the length of the longest substring that can be obtained by replacing at most k characters
 * in the input string so that all the characters in the substring are the same.
 *
 * Problem: Given a string s and an integer k, return the length of the longest substring containing the same letter
 * you can get after performing at most k character replacements.
 *
 * Examples:
 * Input:  s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace two 'A's with 'B's or two 'B's with 'A's to get "AAAA" or "BBBB".
 *
 * Input:  s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace one 'A' with 'B' to get "AABBBBA". The longest substring with the same letter is length 4.
 *
 * Algorithm:
 * (Window Size - maxCount) should be less than K this is the main algo of this code
 * 1. Use a sliding window with two pointers (left and right).
 * 2. Maintain a count array for the frequency of each character in the current window.
 * 3. Track the count of the most frequent character in the window (maxCount).
 * 4. If the window size minus maxCount is greater than k, shrink the window from the left.
 * 5. Update the maximum window size at each step.
 *
 * Key Insight: The window is always adjusted to ensure that at most k replacements are needed to make all characters the same.
 *
 * Time Complexity: O(n) - Each character is visited at most twice (once by right, once by left pointer).
 * Space Complexity: O(1) - The count array size is fixed (26 for uppercase English letters).
 */

// Note: here input consists of only uppercase English letters.

class Solution {
    public int characterReplacement(String s, int k) {
        int n = s.length();
        int[] count = new int[26]; // Frequency of each character in the window
        int l = 0, r = 0;
        int maxCount = 0; // Max frequency of a single character in the window
        int maxLen = 0;
        while (r < n) {
            int right = s.charAt(r) - 'A';
            int left =  s.charAt(l) - 'A';
            count[right]++;
            maxCount = Math.max(maxCount, count[right]);
            
            // If more than k replacements are needed, shrink the window from the left
            // (Window size - maxCount) should be less than K this is the main algo of this code

            if((r - l + 1) - maxCount > k) {
                count[left]--;
                l++;
            }
            // Update the maximum window size
            maxLen = Math.max(maxLen, r - l + 1);
            r++;
        }
        return maxLen;
    }
}