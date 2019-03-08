/*
In a given integer array A, we must move every element of A to either list B or list C. (B and C initially start empty.)

Return true if and only if after such a move, it is possible that
the average value of B is equal to the average value of C, and B and C are both non-empty.

Example :
Input:
[1,2,3,4,5,6,7,8]
Output: true
Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have the average of 4.5.

Note:
    1. The length of A will be in the range [1, 30].
    2. A[i] will be in the range of [0, 10000].
 */

/**
 * Approach: Subset Sum + Pruning
 * 首先，很明显这道问题考察了 Subset Sum。
 * 相信大家都对经典问题 Subset 非常熟悉了吧。
 *
 * 但是根据题目的数据规模，我们发现 A.length 有 30 的级别这么大。
 * 因此如果直接暴力 DFS 肯定是会超时的。
 * 但是 30 这个大小其实也并不算多大，远没有达到使用 DP 来解决的程度。
 * 因此，根据这个提示，我们可以想到：通过 剪枝 优化掉不需要计算的过程，从而降低代码的运行时间。
 *
 * 那么问题来了，剪枝的条件是什么呢？
 * 这个其实就是本题的一个考点了，为了发现它，我们需要对表达式进行一个转换。
 * （有点类似 Target Sum 中利用已知条件将问题转换成了 01背包问题）
 * 这里我们记数组 A 的和为 S, 划分出来的数组 B 的和为 X 总共包含了 K 个元素。
 * 并且我们知道：average(B) = average(C) 因此有如下表达式：
 *  X/K = (S−X)/(N−K)
 *  X(N-K) = (S-X)K
 *  XN -XK = SK -XK
 *  X = SK / N
 * 有上述表达式，我们可知 数组B的和 = 数组A的和 * 数组B的元素个数 / 数组A的元素个数（总的元素个数）
 * 并且 数组 中的元素值均为 整数，因此：
 *  数组A的和 * 数组B的元素个数 / 数组A的元素个数（总的元素个数） == 整数
 * 即： sum(A) * K % A.length == 0
 * 根据这个条件，我们在遍历数组B中的元素个数K时，就可以进行很大程度上的剪枝了。
 *
 * 然后就是对于 Subset Sum 方面的剪枝，基本可以参考 Subsets II 问题。
 * 首先题目没说明元素值是 unique 的，所以我们需要进行一个去重。对此我们需要对数组 A 进行一次排序。
 * 并且，我们是将 A[] 分成两组，枚举 B[] 的长度，因此只需要枚举 A.length/2 的元素个数即可。（剩下的自然就是 C[] 的元素了）
 * 同时，我们再利用 target (X) 在求解 Subset 过程中进行一些剪枝。
 * 最后运行结果 3ms...可以发现，效率还是非常高的。
 * 
 * 时间复杂度：O(2^(N/2)) = O(2^N)
 */
class Solution {
    public boolean splitArraySameAverage(int[] A) {
        if (A == null || A.length <= 1) {
            return false;
        }

        int sum = 0, n = A.length;
        for (int num : A) {
            sum += num;
        }

        Arrays.sort(A);
        // 枚举数组 B[] 的元素个数 K
        for (int K = 1; K <= n / 2; K++) {
            // First Pruning, 只有满足这个条件，才有可能值得最后的条件成立
            if (sum * K % n == 0) {
                if (checkSubsetSum(A, sum * K / n, K, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSubsetSum(int[] A, int target, int K, int startIndex) {
        // 使用了 K 个元素，正好凑出和为 target
        if (target == 0 && K == 0) {
            return true;
        }

        if (K > 0) {
            for (int i = startIndex; i <= A.length - K; i++) {
                // 去重
                if (i > startIndex && A[i] == A[i - 1]) {
                    continue;
                }
                // 因为 A[] 已经排序好了，因此可以利用和的上下界，再次进行 Pruning
                if (target < A[i] * K || target > A[A.length - 1] * K) {
                    continue;
                }

                if (checkSubsetSum(A, target - A[i], K - 1, i + 1)) {
                    return true;
                }
            }
        }

        return false;
    }
}