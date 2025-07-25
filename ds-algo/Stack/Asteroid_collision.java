import java.util.Stack;

/*
 * Asteroid Collision
 *
 * This program simulates asteroid collisions where asteroids move in a line and collide based on their directions.
 * Positive integers represent asteroids moving to the right, negative integers represent asteroids moving to the left.
 *
 * Problem: We are given an array asteroids of integers representing asteroids in a row. For each asteroid, 
 * the absolute value represents its size, and the sign represents its direction (positive meaning right, 
 * negative meaning left). Each asteroid moves at the same speed. Find out the state of the asteroids 
 * after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, 
 * both will explode. Two asteroids moving in the same direction will never meet.
 *
 * Examples:
 * Input:  [5, 10, -5]
 * Output: [5, 10]
 * Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
 *
 * Input:  [8, -8]
 * Output: []
 * Explanation: The 8 and -8 collide and explode.
 *
 * Input:  [10, 2, -5]
 * Output: [10]
 * Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.
 *
 * Input:  [-5, 10]
 * Output: [-5, 10]
 * Explanation: The -5 and 10 are moving in opposite directions but never collide because -5 moves left first.
 *
 * Algorithm:
 * 1. Use a stack to keep track of asteroids that haven't been destroyed
 * 2. For each asteroid:
 *    - If positive (moving right): push to stack (no collision possible yet)
 *    - If negative (moving left): 
 *      a) Pop all positive asteroids smaller than current asteroid (they get destroyed)
 *      b) If stack is empty or top is negative: push current asteroid
 *      c) If top asteroid equals current asteroid: both destroy (pop from stack)
 *      d) If top asteroid is larger: current asteroid gets destroyed (don't push)
 * 3. Convert stack to array in reverse order
 *
 * Key Insight: Only collisions between positive (right-moving) and negative (left-moving) asteroids matter.
 * Asteroids moving in the same direction never collide.
 *
 * Time Complexity: O(n) - Each asteroid is pushed and popped from the stack at most once
 * Space Complexity: O(n) - For the stack in worst case when no collisions occur
 */

class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        
        for (int a : asteroids) {
            if (a > 0) {
                stack.push(a);
            } else {
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -a) {
                    stack.pop();
                }

                if (stack.isEmpty() || stack.peek() < 0)  {
                    stack.push(a);
                }

                if (stack.peek() == -a) {
                    stack.pop();
                }
            }
        }

        int[] res = new int[stack.size()];
        int i = stack.size() - 1;

        while(!stack.isEmpty()) {
            res[i--] = stack.pop();
        }

        return res;   
    }
}