import java.util.HashSet;

/*
 * Longest Substring Without Repeating Characters
 *
 * This program finds the length of the longest substring without repeating characters in a given string.
 *
 * Problem: Given a string s, find the length of the longest substring without repeating characters.
 *
 * Examples:
 * Input:  "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Input:  "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Input:  "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Algorithm:
 * 1. Use two pointers (left and right) to maintain a sliding window.
 * 2. Use a HashSet to store unique characters in the current window.
 * 3. Expand the window by moving the right pointer and adding characters to the set.
 * 4. If a repeating character is found, remove characters from the left until the window is unique again.
 * 5. Update the maximum length found at each step.
 *
 * Key Insight: The sliding window technique with a HashSet allows for O(1) character lookup and removal, making the solution O(n).
 *
 * Time Complexity: O(n) - Each character is added and removed from the set at most once.
 * Space Complexity: O(min(n, m)) - For the HashSet, where m is the size of the character set.
 */

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxLen = 0;
        int l = 0, r = 0;
        HashSet<Character> set = new HashSet<>();
        while (r < n) {
            if (!set.contains(s.charAt(r))) {
                set.add(s.charAt(r));
                maxLen = Math.max(maxLen, r - l + 1);
                r++;
            } else {
                set.remove(s.charAt(l));
                l++;
            }
        }
        return maxLen;
    }
}