import java.util.Stack;

/**
 * Stack Reversal Algorithm using Recursion
 * 
 * Time Complexity: O(n²) - where n is the number of elements in the stack
 *   - For each element, we need to pop and reinsert all elements below it
 *   - This results in O(n²) time complexity
 * 
 * Space Complexity: O(n) - where n is the number of elements in the stack
 *   - Recursion stack space for n function calls
 */
class ReverseStack {

    /**
     * Reverses the given stack using recursion
     * @param s the stack to be reversed
     */
    public static void reverse(Stack<Integer> s) {
        if (s.isEmpty()) {
            return;
        }
        int el = s.pop();
        reverse(s);
        insert(s, el);
    }

    /**
     * Inserts an element at the bottom of the stack using recursion
     * @param s the stack to insert into
     * @param e the element to insert
     */
    public static void insert(Stack<Integer> s, int e) {
        if (s.isEmpty()) {
            s.push(e);
            return;
        }

        int top_element = s.pop();
        insert(s, e);
        s.push(top_element);
    }

}