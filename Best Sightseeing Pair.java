/*
Given an array A of positive integers, A[i] represents the value of the i-th sightseeing spot,
and two sightseeing spots i and j have distance j - i between them.

The score of a pair (i < j) of sightseeing spots is (A[i] + A[j] + i - j) :
the sum of the values of the sightseeing spots, minus the distance between them.

Return the maximum score of a pair of sightseeing spots.

Example 1:
Input: [8,1,5,2,6]
Output: 11
Explanation: i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11

Note:
    1. 2 <= A.length <= 50000
    2. 1 <= A[i] <= 1000
 */

/**
 * Approach: Traversal (Tricky Problem)
 * 这道题目看数据规模有 50000，所以算法时间复杂度应该在 O(nlogn) 或者以下。
 * 因为涉及到大小问题，所以想着排序，但是排序后将会丢失掉 index 信息。
 * 所以想到最终解法应该在 O(n) 级别。
 *
 * 这道题目的想法其实挺好玩的：
 *  大家可以想象一下我们的日常生活。比如：你当前位置在 i,去地点 j 能够赚到 A[j] 的钱，
 *  但是过去的路上你要花掉 |i-j| 的车费，所以当你到了 A[j]，你实际上的收益只有 A[j] - |i-j|
 * 有了上述思路，我们可以按照如下的方式来遍历数组：
 *  利用 currBest 来表示目前遇到的景点值最高的景点，但是因为我们每向前移动一步，
 *  距离最佳景点位置的距离就会增加1，这就给我们增加了 1 点的距离消耗，
 *  即相当于景点值 currBest 的收益值就会 -1.
 *  然后不断向后遍历，当前位置的最佳收益为 currBest + A[i]，
 *  向下一个位置移动的时候更新 currBest = Math.max(currBest, A[i]) - 1
 *  
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        int ans = 0, currBest = 0;
        for (int num : A) {
            ans = Math.max(ans, currBest + num);
            currBest = Math.max(currBest, num) - 1;
        }
        return ans;
    }
}