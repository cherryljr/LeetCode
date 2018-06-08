/*
Given an array A, we may rotate it by a non-negative integer K
so that the array becomes A[K], A[K+1], A{K+2], ... A[A.length - 1], A[0], A[1], ..., A[K-1].
Afterward, any entries that are less than or equal to their index are worth 1 point.

For example, if we have [2, 4, 1, 3, 0], and we rotate by K = 2, it becomes [1, 3, 0, 2, 4].
This is worth 3 points because 1 > 0 [no points], 3 > 1 [no points], 0 <= 2 [one point], 2 <= 3 [one point], 4 <= 4 [one point].

Over all possible rotations, return the rotation index K that corresponds to the highest score we could receive.
If there are multiple answers, return the smallest such index K.

Example 1:
Input: [2, 3, 1, 4, 0]
Output: 3
Explanation:
Scores for each K are listed below:
K = 0,  A = [2,3,1,4,0],    score 2
K = 1,  A = [3,1,4,0,2],    score 3
K = 2,  A = [1,4,0,2,3],    score 3
K = 3,  A = [4,0,2,3,1],    score 4
K = 4,  A = [0,2,3,1,4],    score 3
So we should choose K = 3, which has the highest score.

Example 2:
Input: [1, 3, 0, 2, 4]
Output: 0
Explanation:  A will always have 3 points no matter how it shifts.
So we will choose the smallest K, which is 0.

Note:
A will have length at most 20000.
A[i] will be in the range [0, A.length].
 */

/**
 * Approach 1: Brute Force (Time Limit Exceeded)
 * 枚举所有的旋转位置 O(n),然后再求解该种情况下的得分是多少 O(n)
 * 最后求出得分最大值所对应的旋转位置 index 即可。
 * 总体时间复杂度为：O(n^2)
 *
 * n 的最大值为 20000,估计为 4e8.这个时间毫无疑问是过不去的。
 */
class Solution {
    public int bestRotation(int[] A) {
        int max = Integer.MIN_VALUE, rst = 0;

        for (int rotatePos = 0; rotatePos < A.length; rotatePos++) {
            int count = 0;
            for (int i = 0; i < A.length; i++) {
                if (i < rotatePos) {
                    if (A[i] <= i + A.length - rotatePos) {
                        count++;
                    }
                } else {
                    if (A[i] <= i - rotatePos) {
                        count++;
                    }
                }
            }
            if (count > max) {
                max = count;
                rst = rotatePos;
            } else if (count == max && rotatePos < rst) {
                rst = rotatePos;
            }
        }

        return rst;
    }
}

/**
 * Approach 2: Focus on How many Points Lose
 * 参考网上的解法，以 K=0 的分值为基准，分析每次 K++ 后，rotate分值的变化情况。
 * 因为每次旋转都会将 0位置的数 放到 A.length-1 位置上，而所有数值的范围在 0~A.length-1 之间，因此必定会有 1 的得分。
 * 知道了 加分 的情况，我们主要需要分析的就是旋转位置在哪的时候，会发生扣分的情况。
 * 这样求得每个位置相比与 K=0 状态的分数变化信息之后，我们就能够得出在哪个位置旋转，得分是最大的了。
 * 
 * 分析可得：对于 ith 位置上的元素 A[i] 我们只有当旋转位置在 (i - A[i] + 1 + N) % N 的时候，才会出现 扣分 的情况。
 * 这里 +N 并对 N 进行取模是为了保证下标 index 在 0~N-1 上，防止越界。是一个较为常用的技巧。
 * 根据上述方式，我们计算出每个旋转位置的分值变化情况即可。
 * 此时计算出来的 changes[] 是基于上一个状态的比较，(分析的是每次 K++ 的分值变化情况)
 * 因此最后我们需要将 chagnes[] 累加起来，这样其代表的就是以 K=0 状态为基准的分值变化数组了。
 * 最后只需要取出 加分最大值 所对应的下标即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  https://leetcode.com/problems/smallest-rotation-with-highest-score/discuss/118725/Easy-and-Concise-5-lines-Solution-C++JavaPython?page=2
 */
class Solution {
    public int bestRotation(int[] A) {
        int N = A.length;
        int[] changes = new int[N];
        for (int i = 0; i < N; i++) {
            changes[(i - A[i] + 1 + N) % N] -= 1;
        }

        int maxRotatePos = 0;
        for (int i = 1; i < N; i++) {
            // Each time when we rotate, we make index 0 to index N-1, then we get one more point
            // Accumulated changes so we get the changed score for every K value compared to K=0
            changes[i] += changes[i - 1] + 1;
            maxRotatePos = changes[i] > changes[maxRotatePos] ? i : maxRotatePos;
        }
        return maxRotatePos;
    }
}