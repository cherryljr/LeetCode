/*
In an array A containing only 0s and 1s, a K-bit flip consists of choosing a (contiguous) subarray of length K
and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.

Return the minimum number of K-bit flips required so that there is no 0 in the array.
If it is not possible, return -1.

Example 1:
Input: A = [0,1,0], K = 1
Output: 2
Explanation: Flip A[0], then flip A[2].

Example 2:
Input: A = [1,1,0], K = 2
Output: -1
Explanation: No matter how we flip subarrays of size 2, we can't make the array become [1,1,1].

Example 3:
Input: A = [0,0,0,1,0,1,1,0], K = 3
Output: 3
Explanation:
Flip A[0],A[1],A[2]: A becomes [1,1,1,1,0,1,1,0]
Flip A[4],A[5],A[6]: A becomes [1,1,1,1,1,0,0,0]
Flip A[5],A[6],A[7]: A becomes [1,1,1,1,1,1,1,1]

Note:
    1. 1 <= A.length <= 30000
    2. 1 <= K <= A.length
 */

/**
 * Approach 1: Greedy (Brute Force)
 * 当对一个数进行 flip 的时候，我们只能影响包括这个数之后的 K  个元素。
 * 因此当遇到一个 0 时，如果我们略过了它，不对其进行 flip 操作，那么它将没有可能被转换成 1.
 * 故，我们可以得出一个贪心的做法：
 *  找寻数组中第一次出现 0 的位置，然后进行 K 个元素的 flip 操作，将当前元素转换成 1。
 *  如果该元素为 1，则直接跳过。一直遍历到 A.length - K 位置。
 *  最后检查数组末尾的 K 个元素看是否都为 1.
 *  如果是，则返回 ans；否则说明该数组无法被 flip 成要求的样子，返回-1。
 *
 * 时间复杂度：O(N*K)
 * 空间复杂度：O(1)
 *
 * PS.非常暴力的做法，只需要想到使用贪心就能过。（主要是这道题目的数据并不够大，所以可以勉强过）
 */
class Solution {
    public int minKBitFlips(int[] A, int K) {
        int ans = 0;
        for (int i = 0; i + K <= A.length; i++) {
            if (A[i] == 0) {
                ans++;
                for (int j = 0; j < K; j++) {
                    A[i + j] ^= 1;
                }
            }
        }

        for (int i = A.length - K; i < A.length; i++) {
            if (A[i] == 0) {
                return -1;
            }
        }
        return ans;
    }
}

/**
 * Approach 2: Greedy with Status Record (Lazy Updating)
 * 该解法主要使用到了 状态记录 来节省时间。
 * 我们发现在 Approach 1 中，当我们想要变更一个数的时候，就需要对 K 个元素进行一次 flip 操作。
 * 这个将使用到 O(K) 的时间。并且在后续的过程中，这些元素可能被反复地进行 flip 操作。
 * 但是因为都是对0，1进行 XOR 操作，所以说白就就是 来回变 罢了。
 * 我们并没有必要每次 flip 都对全部 K 个元素都操作一遍。
 * 只需要记录其对应的状态即可（变 或者是 没变），然后当遍历到当前元素时，根据记录的状态进行一次更新即可。
 * （有点类似用一个数去记录变更过程，然后当我们最后需要使用的时候，再把这个数拿出来，一次性更新完成，有点 懒更新 的意味）
 * 以上就是 Approach 2 的优化思路。具体实现可以参考代码详细注释。
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(K)
 */
class Solution {
    public int minKBitFlips(int[] A, int K) {
        // 该数组记录了对应位置上的元素 A[i] 是否进行 flip 操作（0代表不需要，1代表需要）
        // 影响的范围为 A[i]~A[i+K-1]
        int[] flipped = new int[A.length];
        // flipStatus 代表遍历到当前的 flip 状态（0代表不flip，1代表flip）,是一个累计递推的状态
        int flipStatus = 0, ans = 0;

        for (int i = 0; i < A.length; i++) {
            // 如果当前位置 >= K 说明当前状态 flipStatus 将不再受到 flipped[i-K] 状态的影响
            // 所以这里使用 异或操作 去除了 flipped[i-K] 状态的影响 （当前位置已经超出影响范围）
            if (i >= K) {
                flipStatus ^= flipped[i - K];
            }
            // 当有如下两种情况时，我们需要对当前位置进行 flip 操作：
            //  1. A[i]==0 && flipStatus==0 代表当前位置为0，并且不进行flip操作
            //  2. A[i]==1 && flipStatus==1 代表当前位置为1，当时进行过flip操作，使得当前位置为0
            // 而上述两种情况正好可以等同于表达式：A[i] == flipStatus
            if (A[i] == flipStatus) {
                if (i + K > A.length) {
                    return -1;
                }
                // 记录下当前位置需要进行 flip 操作
                flipped[i] = 1;
                // 更新flipStatus
                flipStatus ^= 1;
                ans++;
            }
        }

        return ans;
    }
}

/**
 * Approach 3: Greedy with Status Record (No Extra Space)
 * 清楚了 Approach 2 中的解法之后，我们可以发现，实际上可以通过修改原数据将 flipped[] 是可以被优化掉的。
 * 如果 A[i] 需要翻转，就把它赋值为 2（可以是0，1之外的任意数字，能区分开就行），从而节省掉 flipped[] 的空间。
 * 当然这种修改输入原数据的做法，大家仁者见仁智者见智...这里就按下不表了，仅提供一种做法以供参考。
 *
 * 同样还有不使用 flipStatus 信息，而改为采用 当前需要翻转次数 这个信息的做法。
 * 翻转次数为偶数，代表当前状态不需要进行翻转；翻转次数为奇数，代表当前状态需要进行翻转。
 * 本质上都是相同的。该做法可以参考：
 * https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/discuss/238609/JavaC%2B%2BPython-One-Pass-and-O(1)-Space
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(1)
 */
class Solution {
    public int minKBitFlips(int[] A, int K) {
        int ans = 0, flipStatus = 0;
        for (int i = 0; i < A.length; i++) {
            if (i >= K && A[i - K] == 2) {
                flipStatus ^= 1;
            }
            if (flipStatus == A[i]) {
                if (i + K > A.length) {
                    return -1;
                }
                // Mark A[i] has been flipped
                A[i] = 2;
                flipStatus ^= 1;
                ans++;
            }
        }
        return ans;
    }
}
