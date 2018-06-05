/*
The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
Given two integers x and y, calculate the Hamming distance.

Note:
0 ≤ x,y < 2^31.

Example:
Input: x = 1, y = 4
Output: 2

Explanation:
1  (0 0 0 1)
4  (0 1 0 0)
      ↑  ↑

The above arrows point to positions where the corresponding bits are different.
 */

/**
 * Approach: Bit Manipulation
 * 根据 Hamming distance 对于两个整数，它要求的是二进制有几个位上是不同的。
 * 对此我们很容易想到 异或运算。
 * 令 n = x^y，然后我们只需要求 n 的二进制表示中含有几个 1 即可。
 * 这样问题就转换成了
 * Count 1 in Binary:
 *  https://github.com/cherryljr/LintCode/edit/master/Count%201%20in%20Binary.java
 *
 * 这道问题实际上与 LintCode 上的 Flip Bits 完全一样，只是换个方式问罢了：
 *  https://github.com/cherryljr/LintCode/blob/master/Flip%20Bits.java
 */
class Solution {
    public int hammingDistance(int x, int y) {
        int count = 0;
        int n = x ^ y;
        while (n > 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }
}