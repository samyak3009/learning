import java.util.Stack;

/*
 * Largest Rectangle in Histogram
 *
 * This program finds the largest rectangular area that can be formed in a histogram.
 * The histogram is represented by an array where each element represents the height of a bar.
 *
 * Problem: Given an array of integers heights representing the histogram's bar height where 
 * the width of each bar is 1, return the area of the largest rectangle in the histogram.
 *
 * Examples:
 * Input:  [2, 1, 5, 6, 2, 3]
 * Output: 10
 * Explanation: The largest rectangle is shown in the shaded area, which has an area = 10 units.
 *              The rectangle is formed by bars with heights [5, 6] and width 2.
 *
 * Input:  [2, 4]
 * Output: 4
 * Explanation: The largest rectangle has area = 4 units (height = 2, width = 2).
 *
 * Algorithm:
 * 1. For each bar height[i], find the previous smaller element (PSE) and next smaller element (NSE)
 * 2. Calculate the width of rectangle that can be formed with height[i] as the minimum height:
 *    - Width = (NSE index - PSE index - 1)
 * 3. Calculate area = height[i] * width
 * 4. Keep track of the maximum area found
 * 5. Use monotonic stack to efficiently find PSE and NSE for each element
 *
 * Key Insight: For each bar, the largest rectangle that can be formed with that bar as the 
 * minimum height extends from the previous smaller bar to the next smaller bar.
 *
 * Time Complexity: O(n) - Each element is pushed and popped from the stack at most once
 * Space Complexity: O(n) - For the previousSmallest, nextSmallest arrays and the stack
 */

class Solution {
    public int largestRectangleArea(int[] arr) {
        int n = arr.length;
        int result = 0;

		int previousSmallest[] = previousSmallerIndex(arr,n);
		int nextSmallest[] = nextSmallerIndex(arr,n);
        
		for(int i=0;i<n;i++){
            // remember this as this is the main logic
			int area = (nextSmallest[i]-previousSmallest[i]-1)*arr[i];
			result = Math.max(area, result);
		}	
		return result;
    }
    public int [] nextSmallerIndex(int arr [] , int n){
        Stack <Integer> s = new Stack<Integer>();
        int [] res = new int [n];
        for(int i=n-1;i>=0;i--){
            while(!s.empty() && arr[s.peek()]>=arr[i])
                s.pop();
            int e = (s.empty() ? n : s.peek());
            res[i] = e;
            s.push(i);
        }
        return res;
    }
     public int [] previousSmallerIndex(int arr [] , int n){
        Stack <Integer> s = new Stack<Integer>();
        int [] res = new int [n];
        for(int i=0;i<n;i++){
            while(!s.empty() && arr[s.peek()]>=arr[i])
                s.pop();
            int e = (s.empty() ? -1 : s.peek());
            res[i] = e;
            s.push(i);
        }
         return res;
    }
}