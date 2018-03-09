/*
Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

Example:
Given num = 16, return true. Given num = 5, return false.

Follow up: Could you solve it without loops/recursion?
 */

/**
 * Approach 1: Loop Iteration
 * 采用 while 循环一次次地将 n 除以 4，并判断其余数是否为 0.
 * 该方法适用于所有该类型题目，时间复杂度为：O(log4(n))
 */
class Solution {
    public boolean isPowerOfFour(int num) {
        if (num < 1) {
            return false;
        }

        while ((num & 3) == 0) {    // 对 4 取余，等同于 num % 4 == 0
            num >>= 2;              // 除以 4，等同于 num /= 4
        }

        return num == 1 ? true : false;
    }
}

/**
 * Approach 2: Bit Operation
 * 在 Power of Two 中，我们使用了 位运算 对其进行了加速。
 * 而 4 和 2 比较类似，4^n 的二进制也具备：第一位（最高位）为1，其余位均为 0 的这么一个特点，
 * 因此我们想我们是否也可以采用同样的方法来解决呢？
 * 并且我们知道 4 作为 2 的倍数，因此有些 2^n 并不是 4^n.
 * 因此我们需要对 4^n 进行观察，我们可以发现有如下 3 点特点：
 *      1. 首先必须大于 0；
 *      2. 其次与 2^n 的二进制相同，只有最高位为 1. 这一点可以同样利用 (n & (n-1)) == 0 来判断
 *      3. 4^n 的二进制最高位的 1 只在 奇数位 上。
 *      比如：16, 二进制为：00010000. 1 在第 5 位上(从左往右).
 *      因此我们可以将 n 和 0x55555555 进行 与操作 来判断 最高位的1 是否在 奇数位 上。
 *      注：&0x55555555 的作用就是取 int 类型的数的二进制 奇数位上的数，因为 5 的二进制就是 0101
 *      同样的还有取 偶数位 上的数等：如 &0xaaaaaaaa.
 *      应用的题目有：https://github.com/cherryljr/LeetCode/blob/master/Reverse%20Bits.java
 */
class Solution {
    public boolean isPowerOfFour(int num) {
        if (num < 1) {
            return false;
        }

        return num > 0 && (num & (num -1)) == 0 && (num & 0x55555555) != 0;
    }
}