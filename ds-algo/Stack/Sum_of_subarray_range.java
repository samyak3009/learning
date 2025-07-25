import java.util.Stack;

/*
 * Sum of Subarray Ranges
 *
 * This program calculates the sum of ranges (max - min) of all possible subarrays of a given array.
 * For each subarray, we find the difference between maximum and minimum elements and add it to the total sum.
 *
 * Problem: Given an integer array nums, return the sum of subarray ranges. The range of a subarray 
 * is the difference between the largest and smallest element in the subarray.
 *
 * Examples:
 * Input:  [1, 2, 3]
 * Output: 4
 * Explanation: 
 * Subarrays are [1], [2], [3], [1,2], [2,3], [1,2,3].
 * Ranges are 1-1=0, 2-2=0, 3-3=0, 2-1=1, 3-2=1, 3-1=2.
 * Sum is 0 + 0 + 0 + 1 + 1 + 2 = 4.
 *
 * Input:  [1, 3, 3]
 * Output: 4
 * Explanation:
 * Subarrays are [1], [3], [3], [1,3], [3,3], [1,3,3].
 * Ranges are 1-1=0, 3-3=0, 3-3=0, 3-1=2, 3-3=0, 3-1=2.
 * Sum is 0 + 0 + 0 + 2 + 0 + 2 = 4.
 *
 * Algorithm:
 * 1. Calculate sum of all subarray maximums using monotonic stack
 * 2. Calculate sum of all subarray minimums using monotonic stack (same as sum_of_subarray_minimum.java)
 * 3. Return the difference: sumSubarrayMax - sumSubarrayMin
 * 4. For each element arr[i], find previous/next greater/smaller elements
 * 5. Calculate subarray counts where arr[i] is max/min and multiply by arr[i]
 *
 * Relation to Sum of Subarray Minimums:
 * This problem extends the sum of subarray minimums concept by also calculating sum of subarray maximums.
 * The range of a subarray = max - min, so sum of ranges = sum of maxs - sum of mins.
 * We reuse the same monotonic stack technique for both calculations.
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once for both max and min calculations
 * Space Complexity: O(n) - For the leftGreater, rightGreater, leftSmaller, rightSmaller arrays and the stacks
 */

class Solution {
    public long subArrayRanges(int[] nums) {
        return sumSubarrayMax(nums) - sumSubarrayMin(nums);
    }

    public long sumSubarrayMin(int[] arr) {
        int [] leftSmaller = prevSmallerElementIndex(arr);
        int [] rightSmaller = nextSmallerElementIndex(arr);
        long result = 0;
        for(int i=0 ; i < arr.length; i++){
            int left = i - leftSmaller[i];
            int right = rightSmaller[i] - i;
            long res = (long)(left * right * (long)arr[i]);
            result+= res;
        }
        return result;
    }

    public long sumSubarrayMax(int[] arr) {
        int [] leftGreater = prevGreaterElementIndex(arr);
        int [] rightGreater = nextGreaterElementIndex(arr);
        long result = 0;
        for(int i=0 ; i < arr.length; i++){
            int left = i - leftGreater[i];
            int right = rightGreater[i] - i;
            long res = (long)(left * right * (long)arr[i]);
            result += res;
        }
        return result;
    }
    public int [] prevSmallerElementIndex(int [] arr){
        Stack<Integer> stack = new Stack<Integer>();
        int n = arr.length;
        int [] result = new int[n];
        for(int i = 0; i<n; i++){
            // here we will pay with the index to store in the stack
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i])
                stack.pop();
            
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return result;
    }
    public int [] nextSmallerElementIndex(int [] arr){
        Stack<Integer> stack = new Stack<Integer>();
        int n = arr.length;
        int [] result = new int[n];
        for(int i = n - 1; i>=0; i-- ){
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i])
                stack.pop();

            result[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return result;
    }
    public int [] prevGreaterElementIndex(int [] arr){
        Stack<Integer> stack = new Stack<Integer>();
        int n = arr.length;
        int [] result = new int[n];
        for(int i = 0; i<n; i++){
            // here we will pay with the index to store in the stack
            while(!stack.isEmpty() && arr[stack.peek()] < arr[i])
                stack.pop();
            
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return result;
    }
    public int [] nextGreaterElementIndex(int [] arr){
        Stack<Integer> stack = new Stack<Integer>();
        int n = arr.length;
        int [] result = new int[n];
        for(int i = n - 1; i>=0; i-- ){
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i])
                stack.pop();

            result[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return result;
    }
}