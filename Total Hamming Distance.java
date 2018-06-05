/*
The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
Now your job is to find the total Hamming distance between all pairs of the given numbers.

Example:
Input: 4, 14, 2

Output: 6

Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just
showing the four bits relevant in this case). So the answer will be:
HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.

Note:
Elements of the given array are in the range of 0 to 10^9
Length of the array will not exceed 10^4.
 */

/**
 * Approach: Bit Manipulation (Deal with it from Bit)
 * 该题属于 Hamming Distance 的一个 Fellow Up.
 * 从题目的测试数据范围可知：数组的长度有 10^4 级别，
 * 因此如果我们采用暴力求解所有 pairs 的 Hamming Distance 然后再相加，
 * 时间复杂度为 O(n^2),肯定会超时。
 *
 * 这就意味着本题只能够使用 O(nlogn) 或者以下的算法。
 * 因为涉及到位操作，因此本题有一个非常重要的隐含条件：
 *  操作的数据类型只有 32 位。
 * 这同样也是一个范围限制，因此我们不妨换个思路，将考虑对象转换成每一个位，而不是每一个数。
 * 我们可以计算出每一位上为 1 的元素有多少个（计算0也可以）记为 count，那么剩下的就是该为上为 0 的元素个数了。
 * 这样二者所产生的笛卡尔积就是该位上所能产生的 Hamming Distance。
 * 而总共只有 32 位，因此我们只需要进行以上操作 32 次即可。
 *
 * 时间复杂度：O(32 * n) => O(n)
 * 空间复杂度：O(1)
 *
 * 故本题的思路总结就是：发掘隐藏限制条件并转换问题依次降低时间复杂度（常用于整数，位操作）
 * 类似解法的问题：
 * Friends Of Appropriate Ages:
 *  https://github.com/cherryljr/LeetCode/blob/master/Friends%20Of%20Appropriate%20Ages.java
 * Beautiful Numbers:
 *  https://github.com/cherryljr/NowCoder/tree/master/Beautiful%20Numbers
 * Unique Letter String (该题只是在转换思路上有点像哈):
 *  https://github.com/cherryljr/LeetCode/blob/master/Unique%20Letter%20String.java
 */
class Solution {
    public int totalHammingDistance(int[] nums) {
        int rst = 0;

        for (int i = 0; i < 32; i++) {
            int count = 0;
            for (int num : nums) {
                // 计算该 bit 上为 1 的元素个数
                count += (num >>> i) & 1;
            }
            // 计算笛卡尔积并累加到结果中
            rst += count * (nums.length - count);
        }

        return rst;
    }
}