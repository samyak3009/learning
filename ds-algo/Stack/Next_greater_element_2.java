import java.util.Stack;

/*
 * Next Greater Element II (Circular Array)
 *
 * This program finds the Next Greater Element (NGE) for each element in a circular array.
 * For every element x, the NGE is the first greater element to the right of x in the circular array.
 * If no such element exists, the output is -1 for that position.
 * The array is treated as circular, meaning after the last element, we continue from the first element.
 *
 * Examples:
 * Input:  [1, 2, 1]
 * Output: [2, -1, 2]
 * Explanation: 
 * - For 1 at index 0: Next greater is 2 (at index 1)
 * - For 2 at index 1: No greater element in circular array, so -1
 * - For 1 at index 2: Next greater is 2 (wrapping around to index 1)
 *
 * Algorithm:
 * 1. First, push all elements to the stack (simulating circular nature)
 * 2. Then process elements from right to left, similar to NGE-I
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once.
 * Space Complexity: O(n) - For the output array and the stack.
 */

class NextGreaterElement2 {
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int [] output = new int[n];
        Stack<Integer> s = new Stack<Integer>();
        
        //For NGE-II we will first push all element to stack 
        for(int i = n-1; i>=0; i--){
            s.push(nums[i]);
        }
        
        //Rest is same as NGE-I
        for(int i = n-1; i>=0; i--){
            while(!s.isEmpty() && s.peek() <= nums[i])
                s.pop();
    
            output[i] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i]);
        }
        return output;
    }
}