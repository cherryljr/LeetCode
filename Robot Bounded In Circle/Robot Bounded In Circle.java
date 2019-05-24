/*
On an infinite plane, a robot initially stands at (0, 0) and faces north.  The robot can receive one of three instructions:
    "G": go straight 1 unit;
    "L": turn 90 degrees to the left;
    "R": turn 90 degress to the right.
The robot performs the instructions given in order, and repeats them forever.

Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.

Example 1:
Input: "GGLLGG"
Output: true
Explanation:
The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.

Example 2:
Input: "GG"
Output: false
Explanation:
The robot moves north indefinetely.

Example 3:
Input: "GL"
Output: true
Explanation:
The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...

Note:
    1. 1 <= instructions.length <= 100
    2. instructions[i] is in {'G', 'L', 'R'}
 */

/**
 * Approach: Judge Repeated Route In Circle
 * 题目求一个机器人在原点(0,0)，默认方向朝北，然后按顺序执行一串指令，并且重复执行下去。
 * 问机器人是否会在一个固定路线转圈？
 *
 * 对此我们可以想到：既然机器人是重复执行这串指令，若在固定路线转圈，则肯定是重复若干次后，回到了原点。
 * 接下来看什么时候可以回到原点。
 *  情况1：第一次执行完一串指令后，就在原点，则可以保证路线成环，直接返回 true 即可。
 *  情况2：执行完一串指令后，不在原点，假设此时新的方向是dir。
 * 如果dir不是北方，则一定可以回到原点。
 *
 * 证明如下：
 * 如果 dir 指向南方，则下一次执行完，机器人就回到原点（方向为180°镜像）。
 * 如果 dir 指向东方或西方，则第四次执行完，机器人就回到原点（方向为90°，形成90°的环形路径）。
 *
 * 因此只需要在执行完一次指令后判断，当前位置是否为 [0,0] 或者 dir 不为北方即可。
 * 当然也可以让机器人重复执行四次指令串，然后判断是否在原点即可。（因为题目的数据规模小，所以这个方法也能轻松 AC）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public boolean isRobotBounded(String instructions) {
        // 方向数组的顺序依次为：北，东，南，西（顺时针方向）
        int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int index = 0, x = 0, y = 0;
        for (int i = 0; i < instructions.length(); i++) {
            if (instructions.charAt(i) == 'L') {
                index = (index + 3) % 4;
            } else if (instructions.charAt(i) == 'R') {
                index = (index + 1) % 4;
            } else {
                x += DIRS[index][0];
                y += DIRS[index][1];
            }
        }
        return (x == 0 && y == 0) || index > 0;
    }
}