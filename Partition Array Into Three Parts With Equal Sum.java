/*
Given an array A of integers, return true if and only if we can partition the array into three non-empty parts with equal sums.
Formally, we can partition the array if we can find indexes i+1 < j with
(A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1])

Example 1:
Input: [0,2,1,-6,6,-7,9,1,2,0,1]
Output: true
Explanation: 0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1

Example 2:
Input: [0,2,1,-6,6,7,9,-1,2,0,1]
Output: false

Example 3:
Input: [3,3,6,5,-2,2,5,1,-9,4]
Output: true
Explanation: 3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4

Note:
    1. 3 <= A.length <= 50000
    2. -10000 <= A[i] <= 10000
 */

/**
 * Approach: Traversal (Greedy)
 * 这道题目虽然挺简单的，但是埋藏着一些坑，质量还是挺高的。很考察对 corner case 的处理。
 * （不过 LeetCode 貌似 case 还不足够，期待后期的继续补充吧）
 *
 * 思路很简单：先遍历一遍，求出数组和，如果和不能被 3 整除，直接 return false 即可。
 * 然后开始从头遍历，将一个个数进行累加，看其 segmentSum 值是否与 sum/3 相等。
 * 如果相等就说明我们找到了一个符合条件的 subarray，则对应的计数器 count++
 * 注意！！！后面开始就有坑了...
 * 因为这个做法是贪心的，并且数组里面是有负数的。所以就会出现 sum(subarray) == 0 的情况。
 * 这使得对于 subarray 的 partition 并不是唯一的，而该贪心策略找到的是：
 *  最多的，index最靠前的，划分方案个数
 * 所以，如果在返回结果中，采用 return count == 3 的做法，将会是错误的。
 *  比如test case：[1,-1,1,-1,1,-1,1,-1], 因为 sum / 3 == 0 并且后面的 sum(subarray) 也等于 0。
 *  这就使得 虽然它可以被划分成 3 组 sum 相等的 subarray，但实际上按照贪心策略，最多可以划分出 4 组。
 * 因此，我们的判断条件应该是：count >= 3 而不是 count == 3.
 *
 * 不过，因为我们之前已经提前判断过 sum%3 是否为 0 了，因此，只要能够找出 3 组 segmentSum = sum/3 的 subarray.
 * 剩下的部分 sum 值必定为 0，根本没有遍历的必要，可以提前跳出循环，从而节约一定的时间。
 * 这时，可能就会有人反应过来：
 * “为什么一定要找 3 组呢？按照数组剩下部分的算，我们不是找两组就好了吗？剩下的那组 segmentSum 必定为 sum/3 啊”
 * 是的，这个策略粗看上去是没有问题的，而且“剩下的那组 segmentSum 必定为 sum/3”这个结论也是对的。
 * 但是为什么我们不能 当 count>=2 的时候，直接 return true 呢？
 * 原因在于：
 *  虽然“剩下的那组 segmentSum 必定为 sum/3”，但是也得有剩下的元素来组成啊。
 *  比如test case：[0, 1, -1]，该 case 只能被分割成两个符合条件的 subarray。
 *  对于剩下的部分，因为正好 sum == 0，如果不继续判断，就会出错。
 *
 * 总结：这是一道对 corner case 考察较为细致的一道题目。
 * 主要就是引入了负数，需要对 0 这个 case 多加注意，因为0不会对 sum 产生影响。
 * 而产生 0 的方案除了 正负数相加，0值相加，还需要考虑 什么数都不存在 的情况（这个情况下，因为初始化的原因，sum也是0）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public boolean canThreePartsEqualSum(int[] A) {
        int sum = 0;
        for (int num : A) {
            sum += num;
        }
        if (sum % 3 != 0) {
            return false;
        }

        sum /= 3;
        for (int index = 0, count = 0, segmentSum = 0; index < A.length; index++) {
            segmentSum += A[index];
            if (segmentSum == sum) {
                segmentSum = 0;
                count++;
            }
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }
}