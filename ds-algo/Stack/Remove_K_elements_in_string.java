import java.util.Stack;

/*
 * Remove K Digits
 *
 * This program removes k digits from a number to create the smallest possible number.
 * The digits must be removed from left to right while maintaining the relative order of remaining digits.
 *
 * Problem: Given string num representing a non-negative integer, and an integer k, return the smallest 
 * possible integer after removing k digits from num. Leading zeros are automatically removed.
 *
 * Examples:
 * Input:  num = "1432219", k = 3
 * Output: "1219"
 * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
 *
 * Input:  num = "10200", k = 1
 * Output: "200"
 * Explanation: Remove the leading 1 and the result is 200.
 *
 * Input:  num = "10", k = 2
 * Output: "0"
 * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 *
 * Algorithm:
 * 1. Use a stack to build the result number digit by digit
 * 2. For each digit in the input number:
 *    - While k > 0 and stack is not empty and top of stack > current digit:
 *      Pop from stack and decrement k (remove larger digits first)
 *    - If stack is empty and current digit is 0, skip it (avoid leading zeros)
 *    - Otherwise, push current digit to stack
 * 3. If k > 0 after processing all digits, remove remaining k digits from right
 * 4. Convert stack to string in reverse order
 * 5. Return "0" if result is empty
 *
 * Key Insight: To get the smallest number, we should remove larger digits that appear before smaller digits.
 * This is a greedy approach - always remove the larger digit when possible.
 *
 * Time Complexity: O(n) - Each digit is pushed and popped from the stack at most once
 * Space Complexity: O(n) - For the stack in worst case when no digits are removed
 */

class Solution {
    public String removeKdigits(String num, int k) {
        Stack <Integer> stack = new Stack<Integer>();
        for( char a : num.toCharArray()){
            int n = Character.getNumericValue(a);
            while(k > 0 && !stack.isEmpty() && stack.peek() > n){
                stack.pop();
                k--;
            }
            if(stack.isEmpty() && n == 0)
                continue;
            else
                stack.push(n);
        }
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }
        if(stack.isEmpty()){
            return "0";
        }else{
            String result = "";
            while(!stack.isEmpty()){
                int data = stack.pop();
                result = String.valueOf(data) + result;
            }
            return result;
        }
    }
}