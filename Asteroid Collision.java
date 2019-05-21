/*
We are given an array asteroids of integers representing asteroids in a row.
For each asteroid, the absolute value represents its size, and the sign represents its direction
(positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode.
If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

Example 1:
Input:
asteroids = [5, 10, -5]
Output: [5, 10]
Explanation:
The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.

Example 2:
Input:
asteroids = [8, -8]
Output: []
Explanation:
The 8 and -8 collide exploding each other.

Example 3:
Input:
asteroids = [10, 2, -5]
Output: [10]
Explanation:
The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.

Example 4:
Input:
asteroids = [-2, -1, 1, 2]
Output: [-2, -1, 1, 2]
Explanation:
The -2 and -1 are moving left, while the 1 and 2 are moving right.
Asteroids moving the same direction never meet, so no asteroids will meet each other.

Note:
The length of asteroids will be at most 10000.
Each asteroid will be a non-zero integer in the range [-1000, 1000]..
 */

/**
 * Approach: Stack (Simulation)
 * 根据分析，我们发现结果只可能是 [-, -, -, ... +, +, +]
 * 当然也可能只有一种方向存在。但是如果两种方向都存在的话，必定为左边全为负方向，右边全为正方向。
 * 否则就会发生碰撞。并且我们可以分析出来只有一种情况下会发生碰撞：
 *  前一个方向为 正方形 且当前方向为 负方向 时，此时才可能发生碰撞情况。
 * 其他情况下是不可能发生碰撞的，这个过程和 Stack 的进出非常类似，因此我们可以使用 Stack 来模拟整个过程。
 * 当碰撞发生时，我们根据陨石的大小进行分情况处理，而没发生的时候直接 push 到 Stack 中即可。
 * 碰撞发生时有以下情况：
 *  1. 当前陨石大小 > 栈顶陨石大小即前一个陨石的大小，此时栈顶的陨石被摧毁，即 stack.pop()，后续将 push 当前陨石
 *  2. 当前陨石大小 = 栈顶陨石大小，此时二者均被摧毁，即 stack.pop()，后续不能进行 push 操作
 *  3. 当前陨石大小 < 栈顶陨石大小，此时当前陨石被摧毁，即跳过当前陨石的 push() 操作即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            // 左边的陨石向右，当前的陨石向左，且当前陨石的大小更大
            // 因此栈顶的陨石将被摧毁（pop出栈顶元素）
            while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -asteroid) {
                stack.pop();
            }
            // 否则，如果左边不存在陨石，或者左边陨石方向向左，或者当前陨石方向向右，均不会发生碰撞情况
            if (stack.isEmpty() || asteroid > 0 || stack.peek() < 0) {
                stack.push(asteroid);
            } else if (asteroid < 0 && stack.peek() == -asteroid) {
                // 如果会发生碰撞，并且两个陨石大小相等，则左边的陨石被摧毁，同时当前陨石无法入栈
                stack.pop();
            }
        }

        int[] rst = new int[stack.size()];
        for (int i = rst.length - 1; i >= 0; i--) {
            rst[i] = stack.pop();
        }
        return rst;
    }
}
