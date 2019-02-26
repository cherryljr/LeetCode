/*
On a broken calculator that has a number showing on its display, we can perform two operations:

Double: Multiply the number on the display by 2, or;
Decrement: Subtract 1 from the number on the display.
Initially, the calculator is displaying the number X.

Return the minimum number of operations needed to display the number Y.

Example 1:
Input: X = 2, Y = 3
Output: 2
Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.

Example 2:
Input: X = 5, Y = 8
Output: 2
Explanation: Use decrement and then double {5 -> 4 -> 8}.

Example 3:
Input: X = 3, Y = 10
Output: 3
Explanation:  Use double, decrement and double {3 -> 6 -> 5 -> 10}.

Example 4:
Input: X = 1024, Y = 1
Output: 1023
Explanation: Use decrement operations 1023 times.

Note:
    1. 1 <= X <= 10^9
    2. 1 <= Y <= 10^9
 */

/**
 * Approach: Think Backwards (Greedy Similar to Race Car)
 * 这道题目使用到了 贪心 的做法。主要思想与 Race Car 类似。
 * 但是难度要比 Race Car 低了不少。
 *
 * 因为这里的计算方式只有两种：
 *  1. X *= 2;    2. X -=1
 * 因此只有 一种 使值变大的方法 和 一种 使值变小的方法。
 * 故，当 Y > X 时，我们可以利用 乘法 操作使得 X 的值尽快趋近 Y。
 * 但是，这里存在这一个问题：
 *  从 X 变到 Y，有两种方式：
 *  1. 先利用 乘法 得到一个比 Y 大的数，然后再使用减法
 *  2. 先利用 减法 得到一个较小的数，然后使用 乘法 正好获得Y
 * 这里也非常类似于 Race Car 和 Least Operators to Express Number。
 *
 * 做到这里难免会想采用和 Race Car 一样的做法（BFS | DP）去处理这个问题。
 * 但是，我们发现题目的数据规模有 10^9 这么大，因此采用 BFS | DP 肯定是会超时的。
 * 所以，我们考虑 只用贪心 的做法。
 * 因为从 X ==> Y 这条路走不通（不确定 X 什么时候进行 -1 操作）
 * 所以，我们不妨换个思路，从 Y ==> X。
 * 这样操作就转换成了：
 *  1. Y /= 2;    2. Y +=1
 * 这个时候我们发现，确定条件下，每一步的操作是确定的。贪心做法为：
 *  只有当 Y 无法被 整除 时，只能进行 +1 操作，故此时 Y += 1，
 *  否则就利用 除法 让 Y 最快地向 X 靠近。（只有Y是偶数时，才能进行 /2 操作，因为 2*X 操作结果必定为偶数）
 *  当 Y < X 时，我们只能通过 Y += 1，最后让 Y == X.
 *
 * 贪心的证明：为什么当 Y > X 且 Y 为偶数时，我们需要进行 /2 操作，而不进行 +1 操作。
 * 因为如果当 Y 是偶数时，对其进行 +1 操作，那么我们将获得一个 奇数。
 * 那么为了进行下一次的 除法 操作，我们必须还要进行一次 +1 操作。
 * 即 (Y + 1 + 1) / 2 == (Y + 2) / 2 == Y / 2 + 1
 * 由 分配率 可以清楚地看到 如果使用 /2 操作，完成上述过程只需要两步；而 +1 需要 3 步。
 *
 * 时间复杂度：O(logY)
 * 空间复杂度：O(1)
 *
 * Note:
 * 本题的数据规模是一个很大的提示。根据推测出来的算法时间复杂度(O(logn) | O(1))
 * 并结合之前的解题经验，我们可以很快把算法确定在贪心上面。
 * Race Car:
 *  https://github.com/cherryljr/LeetCode/blob/master/Race%20Car.java
 * Least Operators to Express Number:
 *  https://github.com/cherryljr/LeetCode/blob/master/Least%20Operators%20to%20Express%20Number.java
 */
class Solution {
    public int brokenCalc(int X, int Y) {
        int ans = 0;
        while (Y > X) {
            // 利用 除法 最快地让 Y 向 X 趋近
            if ((Y & 1) == 0) {
                Y >>= 1;
            } else {
                // 当 Y 无法被 2 整除时，就 +1，使得下一次能被整除
                Y += 1;
            }
            ans++;
        }
        // 当 Y 减小到小于 X 的时候，只能用过 +1 操作使得 Y == X
        return ans + X - Y;
    }
}