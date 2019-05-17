/*
On an infinite number line, the position of the i-th stone is given by stones[i].
Call a stone an endpoint stone if it has the smallest or largest position.

Each turn, you pick up an endpoint stone and move it to an unoccupied position so that it is no longer an endpoint stone.

In particular, if the stones are at say, stones = [1,2,5], you cannot move the endpoint stone at position 5,
since moving it to any position (such as 0, or 3) will still keep that stone as an endpoint stone.

The game ends when you cannot make any more moves, ie. the stones are in consecutive positions.

When the game ends, what is the minimum and maximum number of moves that you could have made?
Return the answer as an length 2 array: answer = [minimum_moves, maximum_moves]

Example 1:
Input: [7,4,9]
Output: [1,2]
Explanation:
We can move 4 -> 8 for one move to finish the game.
Or, we can move 9 -> 5, 4 -> 6 for two moves to finish the game.

Example 2:
Input: [6,5,4,3,10]
Output: [2,3]
We can move 3 -> 8 then 10 -> 7 to finish the game.
Or, we can move 3 -> 7, 4 -> 8, 5 -> 9 to finish the game.
Notice we cannot move 10 -> 2 to finish the game, because that would be an illegal move.

Example 3:
Input: [100,101,104,102,103]
Output: [0,0]

Note:
    1. 3 <= stones.length <= 10^4
    2. 1 <= stones[i] <= 10^9
    3. stones[i] have distinct values.
 */

/**
 * Approach: Sorting + Sliding Window
 * 该来的还是要来哈~在上一周比赛试题 Moving Stones Until Consecutive 的解答中我已经谈到这道扩展问题，
 * 并提出了使用 滑动窗口 的解法。没看过的可以参见
 * Moving Stones Until Consecutive:
 *  https://github.com/cherryljr/LeetCode/tree/master/Moving%20Stones%20Until%20Consecutive
 *
 * 虽然这道题目的大体解法确实是通过 滑动窗口，但是却改变了一个条件：
 *  每次移动 endPoint 后，它不能被放在 endPoint 的位置上。
 *  比如 [1,2,3,5] 我们无法把 5 移动到 4 的位置，因为 4 仍然是一个 endPoint。
 * 这个条件将对我们求解 minimum_moves, maximum_moves 产生一定的影响，并需要处理相应的 Corner Case。
 *
 * 解法如下：
 * 首先对stones升序排序。
 *
 * 求解maximum_moves：
 *  我们可以把所有的 stones 全部移动到 最右端 或者 最左端，并且每次尽量缩短最小的距离，这样能够得到 maximum_moves.
 *  如果把全部的石头都移动到最左端，那么第一次移动的就应该是最右端的石头，那么第一步一定是移到最右边开始第二个的前面，
 *  即stones[n-2]，n 为 stones 的个数，并且最左的位置是stones[0]，
 *  因此移动距离就是 stones[n-2]-stones[0]-1-(n-3)，也就是当前区间内部的位置，减去内部已经有石头的数量
 *  (有 3个 不在区间内部，分别是区间左右端2个和最后1个stones[n-1])，剩下的空位置就是能移动石头的位置。
 *      例如：[1,3,5,7,12,65]，1到12之间还剩下 7个 空位置，也就是先移动右端，能最多移动 7 次。
 *  同样的，如果把全部的石头都移动到最右端，那么第一步一定是将最左边的石头移动到最左边开始第二个的后面，即stones[1]，
 *  它的最右端即是stones[n-1]，因此移动距离就是 stones[n-1]-stones[1]-1-(n-3)；
 *      例如：[1,3,5,7,12,65]，3到65之间还剩下 58个 空位置，也就是先移动左端，能最多移动 58 次。
 *  上面两个值取最大值就是当前能移动的最大步数。
 *
 * 求解 minimum_moves：
 *  利用 Sliding Window 构造一个区间 [i,j]，使得它满足区间的长度不会大于石头的数量。
 *  即求解在一个长度为 n 的连续区间内，最多能有几个石头。
 *  但是对此，我们需要处理一个特殊情况：
 *      检查是否存在 n-1 个石头相邻，且剩下的另外一个石头与其他石头距离 >= 2 的情况。此时的 minimum_moves = 2。
 *      例如[1,2,3,4,10]，区间[1,2,3,4]是连续的，但外部非连续的只有1个，需要将1放到6的位置，再将10放到5的位置。
 *  如果不是以上的特殊情况，那么我们只需要把剩下的空位使用窗口区间外的石头移动过来进行填补即可，因此移动次数为 n-(j-1+1)。
 *      例如：[1,4,7,9,20,30]，当前i为0，j为1，那么将[7,9,20,30]与[1,4]连续只需要4步
 *      将30放入6的位置，[1,4,6,7,9,20]；
 *      将20放入5的位置，[1,4,5,6,7,9]；
 *      将9放入3的位置，[1,3,4,5,6,7]；
 *      将7放入2的位置，[1,2,3,4,5,6]，结束。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 *
 * References:
 *  https://youtu.be/d9FFzpP9GLk
 */
class Solution {
    public int[] numMovesStonesII(int[] stones) {
        Arrays.sort(stones);
        int n = stones.length, start = 0, minMoves = n;
        int maxMoves = Math.max(stones[n - 2] - stones[0] - n + 2, stones[n - 1] - stones[1] - n + 2);

        for (int end = 1; end < n; end++) {
            while (stones[end] - stones[start] >= n) {
                start++;
            }
            // Corner Case
            if (end - start == n - 2 && stones[end] - stones[start] == n - 2) {
                minMoves = Math.min(minMoves, 2);
            } else {
                minMoves = Math.min(minMoves, n - (end - start + 1));
            }
        }
        return new int[]{minMoves, maxMoves};
    }
}