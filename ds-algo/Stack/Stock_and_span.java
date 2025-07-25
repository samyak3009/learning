import java.util.Stack;
import java.util.ArrayList;

/*
 * Stock Span Problem
 *
 * This program implements the Stock Span problem, which calculates the span of stock prices for each day.
 * The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backward)
 * for which the price of the stock was less than or equal to today's price.
 *
 * Problem: Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price for the current day.
 *
 * Examples:
 * Input:  [100, 80, 60, 70, 60, 75, 85]
 * Output: [1, 1, 1, 2, 1, 4, 6]
 * Explanation:
 * - Day 1: 100, span = 1 (only today)
 * - Day 2: 80, span = 1 (only today)
 * - Day 3: 60, span = 1 (only today)
 * - Day 4: 70, span = 2 (70 >= 60)
 * - Day 5: 60, span = 1 (only today)
 * - Day 6: 75, span = 4 (75 >= 60, 70, 60)
 * - Day 7: 85, span = 6 (85 >= 75, 60, 70, 60, 80)
 *
 * Algorithm:
 * 1. Use a stack to keep track of indices of days with prices greater than the current day's price
 * 2. For each new price:
 *    - Pop indices from the stack while the price at those indices is less than or equal to the current price
 *    - The span is the difference between the current index and the index on top of the stack (or index + 1 if stack is empty)
 *    - Push the current index onto the stack
 * 3. Store prices in an ArrayList for index-based access
 *
 * Key Insight: The stack efficiently tracks the previous higher price, allowing O(1) amortized span calculation per day.
 *
 * Time Complexity: O(n) - Each price is pushed and popped from the stack at most once
 * Space Complexity: O(n) - For the stack and prices list
 */

class StockSpanner {

    private int index;
    Stack <Integer> stack; // store the indexes
    ArrayList<Integer> prices; // store the prices
    public StockSpanner() {
        this.index = 0;
        stack = new Stack<Integer>();
        prices = new ArrayList<Integer>();
    }
    
    public int next(int price) {
        prices.add(price);
        while(!stack.isEmpty() && prices.get(stack.peek()) <= price){
            stack.pop();
        }
        int result = stack.isEmpty() ? index + 1 : index - stack.peek();
        stack.push(index);
        index++;

        return result;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */