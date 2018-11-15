/*
We have two integer sequences A and B of the same non-zero length.
We are allowed to swap elements A[i] and B[i].
Note that both elements are in the same index position in their respective sequences.

At the end of some number of swaps, A and B are both strictly increasing.
(A sequence is strictly increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)

Given A and B, return the minimum number of swaps to make both sequences strictly increasing.
It is guaranteed that the given input always makes it possible.

Example:
Input: A = [1,3,5,4], B = [1,2,3,7]
Output: 1
Explanation:
Swap A[3] and B[3].  Then the sequences are:
A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
which are both strictly increasing.

Note:
A, B are arrays with the same length, and that length will be in the range [1, 1000].
A[i], B[i] are integer values in the range [0, 2000].
 */

/**
 * Approach: DP
 * 根据数据规模可以推测出来本题的解法是使用DP.
 * 因为当前状态是依赖于之前状态的，所以我们可以对当前情况进行分析，
 * 然后依据子状态来推测出当前状态。
 * 而本题的难点就是在于：我们不能直接定义一个 dp[] 来表示 0~ith 所需的最少 swap 次数。
 * 因为我们并不知道最优解是否是通过 swap 得到的。
 * 如果是 swap 得到的，那么就意味着当前序列发生了变换，这将对之后的结果产生影响。
 * 这也就以为这不再是一个 无后效性 问题了。
 * 所以我们必须记录下当前状态是否进行了 swap.这样就能够使用 DP 顺利解决了。
 * 具体情况分析可以看代码注释。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考视频：https://www.youtube.com/watch?v=__yxFFRQAl8
 * PS.这道问题实际上是 House Robber 问题的一个变型和升级版。
 * 同样都是需要记录下 当前状态是如何而来 的。
 * 从而将该问题转换成一个 无后效性 问题。
 *  House Robber: https://github.com/cherryljr/LintCode/blob/master/House%20Robber.java
 */
class Solution {
    public int minSwap(int[] A, int[] B) {
        int len = A.length;
        int[] keep = new int[len];
        int[] swap = new int[len];
        // Initialize the dp array
        Arrays.fill(keep, Integer.MAX_VALUE);
        Arrays.fill(swap, Integer.MAX_VALUE);
        keep[0] = 0;
        swap[0] = 1;

        for (int i = 1; i < len; i++) {
            if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
                // Good case, no swapping needed.
                keep[i] = keep[i - 1];
                // Swapped A[i - 1] with B[i - 1] and swap A[i] with B[i] as well
                swap[i] = swap[i - 1] + 1;
            }
            if (A[i] > B[i - 1] && B[i] > A[i - 1]) {
                // A[i - 1] and B[i - 1] weren't swapped
                swap[i] = Math.min(swap[i], keep[i - 1] + 1);
                // Swapped A[i - 1] with B[i - 1], no swap needed for A[i] and B[i]
                keep[i] = Math.min(keep[i], swap[i - 1]);
            }
        }

        return Math.min(keep[len - 1], swap[len - 1]);
    }
}