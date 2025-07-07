import java.util.Stack;

/*
 * Next Smaller Element
 *
 * This program finds the Next Smaller Element (NSE) for each element in a given array.
 * For every element x, the NSE is the first smaller element to the right of x in the array.
 * If no such element exists, the output is -1 for that position.
 *
 * Examples:
 * Input:  [4, 5, 2, 25]
 * Output: [2, 2, -1, -1]
 * Explanation: 
 * - For 4 at index 0: Next smaller is 2 (at index 2)
 * - For 5 at index 1: Next smaller is 2 (at index 2)
 * - For 2 at index 2: No smaller element to the right, so -1
 * - For 25 at index 3: No smaller element to the right, so -1
 *
 * Algorithm:
 * Similar to Next Greater Element but with reversed comparison condition.
 * Process elements from right to left using a stack.
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once.
 * Space Complexity: O(n) - For the output array and the stack.
 */

class NextSmallerElement {
    public static int[] nextSmallerElements(int[] arr) {
        int n = arr.length;
        Stack <Integer> s = new Stack<Integer>();
        int [] output = new int [n];

        // same as NGE-I it just comparison condition changed
        for(int i=n-1; i>=0; i--){
            while(!s.isEmpty() && s.peek()>= arr[i])
                s.pop();

            output[i] = s.isEmpty()? -1 : s.peek();
            s.push(arr[i]);
        }
        return output;
    }
}