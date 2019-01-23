/*
You are given an integer array A.  From some starting index, you can make a series of jumps.
The (1st, 3rd, 5th, ...) jumps in the series are called odd numbered jumps,
and the (2nd, 4th, 6th, ...) jumps in the series are called even numbered jumps.

You may from index i jump forward to index j (with i < j) in the following way:
During odd numbered jumps (ie. jumps 1, 3, 5, ...), you jump to the index j such that A[i] <= A[j] and A[j] is the smallest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
During even numbered jumps (ie. jumps 2, 4, 6, ...), you jump to the index j such that A[i] >= A[j] and A[j] is the largest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
(It may be the case that for some index i, there are no legal jumps.)
A starting index is good if, starting from that index, you can reach the end of the array (index A.length - 1) by jumping some number of times (possibly 0 or more than once.)

Return the number of good starting indexes.

Example 1:
Input: [10,13,12,14,15]
Output: 2
Explanation:
From starting index i = 0, we can jump to i = 2 (since A[2] is the smallest among A[1], A[2], A[3], A[4] that is greater or equal to A[0]), then we can't jump any more.
From starting index i = 1 and i = 2, we can jump to i = 3, then we can't jump any more.
From starting index i = 3, we can jump to i = 4, so we've reached the end.
From starting index i = 4, we've reached the end already.
In total, there are 2 different starting indexes (i = 3, i = 4) where we can reach the end with some number of jumps.

Example 2:
Input: [2,3,1,1,4]
Output: 3
Explanation:
From starting index i = 0, we make jumps to i = 1, i = 2, i = 3:
During our 1st jump (odd numbered), we first jump to i = 1 because A[1] is the smallest value in (A[1], A[2], A[3], A[4]) that is greater than or equal to A[0].
During our 2nd jump (even numbered), we jump from i = 1 to i = 2 because A[2] is the largest value in (A[2], A[3], A[4]) that is less than or equal to A[1].  A[3] is also the largest value, but 2 is a smaller index, so we can only jump to i = 2 and not i = 3.
During our 3rd jump (odd numbered), we jump from i = 2 to i = 3 because A[3] is the smallest value in (A[3], A[4]) that is greater than or equal to A[2].

We can't jump from i = 3 to i = 4, so the starting index i = 0 is not good.
In a similar manner, we can deduce that:
From starting index i = 1, we jump to i = 4, so we reach the end.
From starting index i = 2, we jump to i = 3, and then we can't jump anymore.
From starting index i = 3, we jump to i = 4, so we reach the end.
From starting index i = 4, we are already at the end.
In total, there are 3 different starting indexes (i = 1, i = 3, i = 4) where we can reach the end with some number of jumps.

Example 3:
Input: [5,1,3,4,2]
Output: 3
Explanation:
We can reach the end from starting indexes 1, 2, and 4.

Note:
1 <= A.length <= 20000
0 <= A[i] < 100000
 */

/**
 * Approach: DP + TreeMap (Binary Search)
 * 这道题目个人感觉是一道很好玩，含金量较高的一道题目。
 * 因此这边也会比较详细地记录下比赛时的解题思路，大家可以参考下。
 * 首先，我们从题目的数据规模（20000）可以判断出来解法的时间复杂度应该 <= O(nlogn)
 * 并且我们还从题目要求的跳跃方式，记 A[j] 为跳跃到的位置，则：
 *  up jump：当前为第 奇数 次跳跃的话，A[j] >= A[i] 且 A[j] 要尽量小；在有序数组中就是 第一个 大于等于当前数的元素的位置（lowerBound）
 *  low jump：当前为地 偶数 次跳跃的话，A[j] <= A[i] 且 A[j] 要尽量大；在有序数组中就是 最后一个 小于等于当前数的元素的位置（upperBound）
 * 这里我们发现了跳跃的方式与 Binary Search 的两种查找方式非常类似。
 * （这边说类似是因为不是完全相同，在 upperBound 中，本题要求的是如果遇到相同元素的情况，我们应该选择最早出现的位置）
 * 有了以上分析，我们可以把算法时间复杂度敲定在 O(nlogn) 级别了。
 *
 * 然后继续分析，我们可以发现这个问题是一个 无后效性 问题。
 * 当 跳跃次数(odd or even) 和 位置 确定时，是否能够到达终点这个结果就已经确定了，与如何到达当前位置的方式无关。
 * 因此自然想到使用 DP 来解决这道题目。状态定义与 House Robber 这类问题很类似（这道题目太经典了哈）
 *  dp[i][0]:表示当前位置是 第奇数次 跳跃，因此下一步的位置应该按照 lowerBound 的方法去寻找
 *  dp[i][1]:表示当前位置是 第偶数次 跳跃，因此下一步的位置应该按照 upperBound 的方法去寻找
 *  二者均代表以这个方式能够到达最后终点位置。
 * 因此可以推出状态转换方程为：
 *  dp[i][0] = dp[higher.index][1] 当前位置是 up jump，则下一个位置就是 down jump
 *  dp[i][1] = dp[lower.index][0]  当前位置是 down jump，则下一个位置就是 up jump
 *
 * 但是在代码实现的时候，在 logn 的查找优化处遇到了问题。
 * 因为本题中的元素是无序的，因此如果要使用排序的话，必须要记录原本的数值对应的位置。
 * 此时自然而然地想到使用 Map 这个数据结构。
 * 并且如果 逆序 遍历的话，可以保证找到的值的 index 就是当前情况下 第一次 出现的位置。
 * （正好这与 DP 的方向也是符合的）
 * 但是，我们每走一步都需要一次排序，这样的话，时间复杂度无疑是不符合要求的。
 * 不过推到这里，我们可以发现两个非常重要的点：
 *  1. 记录元素的值与对应的index. => 使用 Map
 *  2. 保证遍历过程中，集合的实时有序性，这样才能在 O(logn) 的时间内完成需要的查找
 * 此时，我们可以自然而然地想到了一个很好用的数据结构：TreeMap
 * 并且因为是 树型 结构，我们在 lowerBound 和 upperBound 查找的时候，可以把时间复杂度控制在 O(logn) 级别。
 * （而且 TreeMap 直接就为我们提供了对应的方法，不需要自己写轮子了~~）
 *  PS.虽然在最后我们没有使用到 Binary Search，但是以它作为切入点，一步步推到这里，就是我的解题思路。
 *  如果能够直接想出使用 TreeMap 自然是最好的啦~
 *
 * 最后，我们只需要把所有 dp[x][0] == true 的位置加起来，就是能够以 x 为起点并最后到达结尾的个数了。
 * 这里只计算 dp[x][0] 是因为：我们要计算的是 起点，即第一次跳跃方式必须是 up jump。
 * 而 dp[x][1] 是 中间状态，并不是我们需要的最终结果。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * References:
 *  https://youtu.be/MEqDu4hA_Wo
 *  https://leetcode.com/problems/odd-even-jump/discuss/217981/JavaC%2B%2BPython-DP-idea-Using-TreeMap-or-Stack
 */
class Solution {
    public int oddEvenJumps(int[] A) {
        int n = A.length, res = 1;
        boolean[][] dp = new boolean[n][2];
        // 初始化 DP 的起始值
        dp[n - 1][0] = dp[n - 1][1] = true;
        // key:数组中元素的值; value:元素对应的下标（当前情况下第一次出现的位置）
        // 使用 TreeMap 进行存储可以优化我们在跳的时候对下一个位置的查找速度。
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(A[n - 1], n - 1);

        // 采用逆序的方式对数据进行遍历，DP的方向为 Bottom to Up
        // 然后实时更新 map 中的位置，就能够保证元素值对应的 index 是当前情况下第一次出现的位置
        for (int i = n - 2; i >= 0; --i) {
            Integer higher = map.ceilingKey(A[i]), lower = map.floorKey(A[i]);
            // 如果当前位置是第 奇数 次跳跃的话，寻找下一个位置是否存在
            if (higher != null) {
                dp[i][0] = dp[map.get(higher)][1];
            }
            // 如果当前位置是第 偶数 次跳跃的话，寻找下一个位置是否存在
            if (lower != null) {
                dp[i][1] = dp[map.get(lower)][0];
            }
            // 如果能够以当前位置作为起始位置跳到终点的话（即第奇数次跳跃），结果加一
            res += dp[i][0] ? 1 : 0;
            // 更新 map
            map.put(A[i], i);
        }

        return res;
    }
}
