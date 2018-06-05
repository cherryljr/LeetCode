/*
Given a positive integer, output its complement number.
The complement strategy is to flip the bits of its binary representation.

Note:
The given integer is guaranteed to fit within the range of a 32-bit signed integer.
You could assume no leading zero bit in the integer’s binary representation.

Example 1:
Input: 5
Output: 2
Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010.
So you need to output 2.

Example 2:
Input: 1
Output: 0
Explanation: The binary representation of 1 is 1 (no leading zero bits),
and its complement is 0. So you need to output 0.
 */

// How to Create an 00..11..1 Mask

/**
 * Approach 1: Bit Manipulation (Using XOR Operation)
 * 这道题目目的就是将 num 的二进制除去前导0的部分进行一个 flip 操作。
 * 那我们都知道，如果需要 flip 的话，可以使用 XOR 1 或者 ~num 的操作。
 * 这里我们就以 XOR 1 操作为例进行说明。
 *
 * 问题就在于如何除去前导0的部分呢？
 * 对此我们需要取得 num 最高有效位1. (Left Most One)
 * 并依此来产生一个：高位全为 0，从 LMO 到 最低位全为 1 的 mask,比如 10 的 mask 就是 (00...01111).
 * 这样将 num ^ mask 的话，只有对去除前导0的部分进行取反，而其他位置保持不变。(num XOR 0 = num)
 *
 * 那么如何产生这个 mask 呢？我最经常用到的方法就是(这个方法很常用，请务必记住)：
 *      (Integer.highestOneBit(num) << 1) - 1
 * 以 10 为例就是：
 *  Integer.highestOneBit(num) << 1 = 00...010000
 *  10000 - 1 = 00...01111
 *
 * 下面代码为了简练写成了只有 1 行的形式，如果理解有困难的话，可以看注释部分的代码。
 */
class Solution {
    public int findComplement(int num) {
//        int mask = (Integer.highestOneBit(num) << 1) - 1;
//        return num ^ mask;
        return num ^ ((Integer.highestOneBit(num) << 1) - 1);
    }
}

/**
 * Approach 2: Bit Manipulation (Using NOT Operation)
 * 理解了 Approach 1 中的做法之后，使用 NOT 操作基本相同。
 * 核心就是如何构 mask,这一步是完全相同的。
 * 区别就在于如果进行 ~num 最后，我们需要进行的就是 去除前导1 了。
 * 因为 mask 为 00...011..1格式，所以我们可以利用 与操作 只保留 ~num 中需要的部分。
 *
 * 注意：这里我们的 mask 并没有进行一次 << 1 的操作，因此 1 是从 LMO 的下一位开始的。
 * 这是因为 num 进行取反后，最高为必定为 0，因此就不进行考虑了。
 *
 * 参考资料：
 *  https://leetcode.com/problems/number-complement/discuss/95992/Java-1-line-bit-manipulation-solution
 */
class Solution {
    public int findComplement(int num) {
        return ~num & (Integer.highestOneBit(num) - 1);
    }
}