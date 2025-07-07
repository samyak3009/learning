import java.util.Stack;

/**
 * Redundant Parentheses Detection Algorithm
 * 
 * Time Complexity: O(n) - where n is the length of the input string
 *   - We iterate through each character exactly once
 *   - Each stack operation (push/pop/isEmpty/peek) is O(1)
 * 
 * Space Complexity: O(n) - where n is the length of the input string
 *   - Worst case: all characters are opening brackets
 *   - Stack can grow up to n/2 elements
 */
class RedundantParentheses {

    /**
     * Checks if the given string has redundant parentheses
     * @param S input string containing parentheses and operators
     * @return 1 if redundant parentheses found, 0 otherwise
     */
    public static int checkRedundancy(String S) {
        Stack<Character> s = new Stack<>();
        
        for (char c : S.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                continue;
            } else if (c == ')') {
                if (s.peek() == '(') return 1;
                while (!s.isEmpty() && s.peek() != '(') {
                    s.pop();
                }
                s.pop();
            } else {
                s.push(c);
            }
        }
        return 0;
    }

    // Test method
    public static void main(String[] args) {
        String[] tests = {
            "((a+b))",      // 1 - redundant
            "(a+(b)/c)",    // 1 - redundant (b has redundant parentheses)
            "(a+b)",        // 0 - not redundant
            "((a))",        // 1 - redundant
            "a+b",          // 0 - no parentheses
            "((a+b)+(c+d))", // 0 - not redundant
            "(((a+b)))",    // 1 - redundant
            "((a))",        // 1 - redundant
            "(a)",          // 1 - redundant
            "a",            // 0 - no parentheses
            "",             // 0 - empty string
        };

        for (String test : tests) {
            System.out.println("'" + test + "' has redundant parentheses: " + 
                             (checkRedundancy(test) == 1 ? "Yes" : "No"));
        }
    }
}