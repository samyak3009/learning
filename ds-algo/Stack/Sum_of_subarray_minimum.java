import java.util.Stack;

/*
 * Sum of Subarray Minimums
 *
 * This program calculates the sum of minimum elements of all possible subarrays of a given array.
 * For each subarray, we find the minimum element and add it to the total sum.
 *
 * Problem: Given an array of integers arr, find the sum of min(b), where b ranges over every 
 * (contiguous) subarray of arr. Since the answer may be large, return the answer modulo 10^9 + 7.
 *
 * Examples:
 * Input:  [3, 1, 2, 4]
 * Output: 17
 * Explanation: 
 * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
 * Sum is 3 + 1 + 2 + 4 + 1 + 1 + 2 + 1 + 1 + 1 = 17.
 *
 * Algorithm:
 * 1. For each element arr[i], find the previous smaller element (PSE) and next smaller element (NSE)
 * 2. Calculate how many subarrays have arr[i] as their minimum:
 *    - Left count = i - PSE index
 *    - Right count = NSE index - i
 *    - Total subarrays = left count * right count
 * 3. Add (arr[i] * total subarrays) to the result
 * 4. Use monotonic stack to find PSE and NSE efficiently
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once
 * Space Complexity: O(n) - For the leftSmaller and rightSmaller arrays and the stack
 */

class SumOfSubarrayMinimum {
    public int sumSubarrayMins(int[] arr) {
        int [] leftSmaller = prevSmallerElementIndex(arr);
        int [] rightSmaller = nextSmallerElementIndex(arr);
        long result = 0;
        long mod = (long) 1e9 + 7;
        for(int i=0 ; i < arr.length; i++){
            int left = i - leftSmaller[i];
            int right = rightSmaller[i] - i;
            long res = (long)(left * right * (long)arr[i]) % mod;
            result += res;
            result%= mod;
        }
        return (int)result;
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
}