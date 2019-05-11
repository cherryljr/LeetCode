/*
Three stones are on a number line at positions a, b, and c.
Each turn, you pick up a stone at an endpoint (ie., either the lowest or highest position stone),
and move it to an unoccupied position between those endpoints.
Formally, let's say the stones are currently at positions x, y, z with x < y < z.
You pick up the stone at either position x or position z, and move that stone to an integer position k, with x < k < z and k != y.

The game ends when you cannot make any more moves, ie. the stones are in consecutive positions.
When the game ends, what is the minimum and maximum number of moves that you could have made?
Return the answer as an length 2 array: answer = [minimum_moves, maximum_moves]

Example 1:
Input: a = 1, b = 2, c = 5
Output: [1,2]
Explanation: Move the stone from 5 to 3, or move the stone from 5 to 4 to 3.

Example 2:
Input: a = 4, b = 3, c = 2
Output: [0,0]
Explanation: We cannot make any moves.

Example 3:
Input: a = 3, b = 5, c = 1
Output: [1,2]
Explanation: Move the stone from 1 to 4; or move the stone from 1 to 2 to 4.

Note:
    1. 1 <= a <= 100
    2. 1 <= b <= 100
    3. 1 <= c <= 100
    4. a != b, b != c, c != a
 */

/**
 * Approach: Sorting (May Use Sliding Window in N Stones)
 * 由题意可知，a, b, c 三个数不互相等。
 * 每次可以取最大或最小的数将其放在区间(min, max)中的空位上。
 * 因此，首先我们可以对 a, b, c 进行一次排序。
 * 然后对于这三个石头的位置，作如下分析：
 *  情况一：  XXX
 *  三个石头的位置两两相邻，此时无法进行移动，因此 minimum_moves = 0, maximum_moves = 0
 *  情况二： XX_X    X_XX    X_X_X    X_X___X
 *  三个石头非两两相邻，并且至少存在两个石头之间的间距 ≤1.
 *  此时对于 minimum_moves 而言，只需要一次即可将石头移动到对应空缺的那个位置上，即 minimum_moves = 1
 *  对于 maximum_moves 而言，每次移动缩小 1 的间距，因此 maximum_moves = max - min - 2
 *  情况三： X__X__X
 *  三个石头均互不相邻且间距君 > 1，对于 minimum_moves 而言，它需要先移动一次转换成情况二，然后再移动一次，因此 minimum_moves = 2
 *  对于 maximum_moves 而言，依旧每次移动缩小 1 的间距，因此 maximum_moves = max - min - 2
 *
 * 另外一种较为直观的想法是：
 *  我们的目的是找一个连续的 3 个坑，把这些石头都放进去。因此，
 *  如果连续的 3 个坑里最多有 3 个石头的话，答案就是{0，0}；
 *  如果连续的 3 个坑里最多有 2 个石头的话，答案就是{1，max-min-2}；
 *  如果连续的 3 个坑里最多有 1 个石头的话，答案就是{2，max-min-2}；
 * 有点类似 Sliding Window 的思想，即找一段长度为 N 的连续区间使得里面尽可能多地包含石头，设为 M 个。
 * 然后将其他位置的石头移动过来以填补空缺的位置，这样就能够使得移动的次数最少，为 N-M 次。
 * 根据这个思想，就可以将问题推广到 N 个石头的情况了（不知道 LeetCode 之后会不会出现这个问题哈~）
 * 
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] numMovesStones(int a, int b, int c) {
        int[] stones = {a, b, c};
        Arrays.sort(stones);
        if (stones[2] - stones[0] == 2) {
            return new int[]{0, 0};
        }
        return new int[]{Math.min(stones[1] - stones[0], stones[2] - stones[1]) <= 2 ? 1 : 2, stones[2] - stones[0] - 2};
    }
}