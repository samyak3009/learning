import java.util.Stack;

class PostEvaluation {

    public static int evaluatePostfix(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        
        Stack<Integer> stack = new Stack<Integer>();

        for (char a : input.toCharArray()) {
            if (Character.isLetterOrDigit(a)) {
                // Convert character to integer (assuming single digit numbers)
                stack.push(Character.getNumericValue(a));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression");
                }
                int second = stack.pop();
                int first = stack.pop();
                int result = doOperation(first, second, a);
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return stack.peek();
    }

    private static int doOperation(int first, int second, char operator) {
        switch (operator) {
            case '+':
                return first + second;
            case '-':
                return first - second;
            case '*':
                return first * second;
            case '/':
                if (second == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return first / second;
            case '^':
                return (int) Math.pow(first, second);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    // Test method
    public static void main(String[] args) {
        try {
            // Test with valid postfix expression: "23*4+" (equivalent to (2*3)+4 = 10)
            String test1 = "23*4+";
            System.out.println("Postfix: " + test1 + " = " + evaluatePostfix(test1));
            
            // Test with another valid expression: "52*3+" (equivalent to (5*2)+3 = 13)
            String test2 = "52*3+";
            System.out.println("Postfix: " + test2 + " = " + evaluatePostfix(test2));
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}