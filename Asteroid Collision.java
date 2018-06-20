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
 * 当然也可能只有一种方向存在。但是如果两种方向都存在的话，必定为左边全为负方向，右边全为正方形。
 * 否则就会发生碰撞。并且我们可以分析出来只有一种情况下会发生碰撞：
 *  前一个方向为 正方形 且当前方向为 负方向 时，此时才可能发生碰撞情况。
 * 其他情况下是不可能发生碰撞的，这个过程和 Stack 的进出非常类似，因此我们可以使用 Stack 来模拟整个过程。
 * 当碰撞发生时，我们根据陨石的大小进行分情况处理，而没发生的时候直接 push 到 Stack 中即可。
 * 碰撞发生时有以下情况：
 *  1. 当前陨石大小 > 栈顶陨石大小即前一个陨石的大小，此时栈顶的陨石被摧毁，即 stack.pop(),然后继续判断即可
 *  2. 当前陨石大小 = 栈顶陨石大小，此时二者均被摧毁，即 stack.pop(),同时结束整个循环（不能进行 push 操作）
 *  3. 当前陨石大小 < 栈顶陨石大小，此时当前陨石被摧毁，即跳过当前陨石的 push() 操作即可。
 *
 * 关于上述 2，3 点中的不仅需要跳出循环，并且还需要跳过 push 操作，如果仅仅是使用普通的
 * while...break 操作是没办法搬到的（因为 push 操作是在 while 之外的，
 * 因此这里用到了 代码块 将整个模拟过程都保证进去了，然后使用 break 直接跳出 collision 过程。
 * 这样就能达到目的了。算是本题在 coding 上的一个小 trick.
 * 当然不用这个方法也是可以的，不过写起来会略显麻烦。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            collision: {
                // Top of the stack is going right and the curr ast is going left
                while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
                    // the current ast is bigger, the stack.peek() one will explode
                    if (-asteroid > stack.peek()) {
                        stack.pop();
                        continue;
                    } else if (-asteroid == stack.peek()) {
                        // both size are the same, both are exploding, break the collision
                        stack.pop();
                    }
                    // if the current asteroid explode, break collision directly (skip pushing it into stack)
                    // (current asteroid isn't bigger than stack.peek() asteroid)
                    break collision;
                }
                // In other situation, it won't happen collision
                stack.push(asteroid);
            }
        }

        int[] rst = new int[stack.size()];
        for (int i = rst.length - 1; i >= 0; i--) {
            rst[i] = stack.pop();
        }
        return rst;
    }
}