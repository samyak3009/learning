import java.util.Stack;

/*
 * Number of Next Greater Elements
 *
 * This program finds the number of Next Greater Elements (NGE) for each element in a given array.
 * For every element x, we count how many elements to the right of x are greater than x.
 * If no such element exists, the output is 0 for that position.
 *
 * Example:
 * Input:  [4, 5, 2, 25]
 * Output: [2, 1, 1, 0]
 * Explanation:
 * - For 4: 2 greater elements (5, 25)
 * - For 5: 1 greater element (25)
 * - For 2: 1 greater element (25)
 * - For 25: 0 greater elements (none to the right)
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once.
 * Space Complexity: O(n) - For the output array, main stack, and count stack.
 */

class NumberOfNextGreaterElements {

    public static int[] numberOfNextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];
        Stack<Integer> s = new Stack<Integer>();
        Stack<Integer> countStack = new Stack<Integer>(); // Track count of greater elements

        for (int i = n - 1; i >= 0; i--) {
            int count = 0;
            
            // Remove elements <= current and accumulate their counts
            while (!s.isEmpty() && s.peek() <= nums[i]) {
                s.pop();
                count += countStack.pop() + 1; // Add count from popped element + 1 (the element itself)
            }
            
            output[i] = count;
            s.push(nums[i]);
            countStack.push(count);
        }
        return output;
    }
} 