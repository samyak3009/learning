import java.util.Stack;

/**
 * Parenthesis Balancing Algorithm
 * 
 * Time Complexity: O(n) - where n is the length of the input string
 *   - We iterate through each character exactly once
 *   - Each stack operation (push/pop/isEmpty) is O(1)
 * 
 * Space Complexity: O(n) - where n is the length of the input string
 *   - Worst case: all characters are opening brackets (e.g., "(((((")
 *   - Stack can grow up to n/2 elements
 *   - Best case: O(1) when string has no brackets or only closing brackets
 */
class ParenthesisBalanced {

    public static boolean isBalanced(String input){
        Stack <Character> ch = new Stack<Character>();

        for(char a: input.toCharArray()){
            if(a == '(')
                ch.push(')');
            else if(a == '{')
                ch.push('}');
            else if(a == '[')
                ch.push(']');
            else if( ch.isEmpty() || a != ch.pop())
                return false;
        }

        return ch.isEmpty();
    }

    // Test method
    public static void main(String[] args) {
        String[] tests = {
            "()",           // true
            "()[]{}",       // true
            "(]",           // false
            "([)]",         // false
            "{[]}",         // true
            "(((",          // false
            ")))",          // false
            "",             // true
            "abc",          // true
            "(abc)",        // true
            "[abc}",        // false
        };

        for (String test : tests) {
            System.out.println("'" + test + "' is balanced: " + isBalanced(test));
        }
    }
}