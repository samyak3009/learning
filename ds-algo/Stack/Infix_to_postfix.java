/**
 * Infix to Postfix Converter
 * 
 * This program converts mathematical expressions from infix notation (operators between operands)
 * to postfix notation (operators after operands) using a stack-based algorithm.
 * 
 * Examples:
 * - Infix: a + b * c    → Postfix: a b c * +
 * - Infix: (a + b) * c  → Postfix: a b + c *
 * 
 * Algorithm:
 * 1. Scan expression from left to right
 * 2. If operand → add to output
 * 3. If '(' → push to stack
 * 4. If ')' → pop operators until '('
 * 5. If operator → pop higher precedence operators, then push current
 * 6. At end → pop remaining operators
 * 
 * Time Complexity: O(n) - Each character processed once
 * Space Complexity: O(n) - Stack and output both O(n) in worst case
 * 
 */

import java.util.Stack;

class InfixToPostfix {

    public static String convertInfixToPostfix(String input) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // Remove '('

            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') return "Invalid Expression";
            output.append(stack.pop());
        }
        
        return output.toString();
    }

    private static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        String[] tests = {"a+b*c-d", "(a+b)*c", "a+b*c^d"};
        for (String test : tests) {
            System.out.println("Infix: " + test + " → Postfix: " + convertInfixToPostfix(test));
        }
    }
}