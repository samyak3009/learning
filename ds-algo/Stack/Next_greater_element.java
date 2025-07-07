import java.util.Stack;

/*
 * Next Greater Element
 *
 * This program finds the Next Greater Element (NGE) for each element in a given array.
 * For every element x, the NGE is the first greater element to the right of x in the array.
 * If no such element exists, the output is -1 for that position.
 *
 * Example:
 * Input:  [4, 5, 2, 25]
 * Output: [5, 25, 25, -1]
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once.
 * Space Complexity: O(n) - For the output array and the stack.
 */

class NextGreaterElement{

    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int [] output = new int[n];
        Stack<Integer> s = new Stack<Integer>();

        for(int i = n-1; i>=0; i--){
            // remove the elem until it is greater than the current in stack
            while(!s.isEmpty() && s.peek() <= nums[i])
                s.pop();
    
            output[i] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i]);
        }
        return output;
    }
}
