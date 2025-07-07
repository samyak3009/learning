import java.util.Stack;

class PostfixToInfix {

    public static String convertPostfixToInfix(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        
        Stack<String> stack = new Stack<String>();
 
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                // Operand - push to stack
                stack.push(String.valueOf(c));
            } else {
                // Operator - pop two operands and form infix expression
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid input expression");
                }
                
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                
                // Build infix expression with parentheses
                String infixExpression = "(" + operand1 + c + operand2 + ")";
                stack.push(infixExpression);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid input expression");
        }

        return stack.peek();
    }

} 