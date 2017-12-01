/*
Reverse bits of a given 32 bits unsigned integer.

For example, given input 43261596 (represented in binary as 00000010100101000001111010011100), 
return 964176192 (represented in binary as 00111001011110000010100101000000).

Follow up:
If this function is called many times, how would you optimize it?

Related problem: Reverse Integer
*/

// Approach 1
/**
 * 该方法是一个非常直接的方法
 * 首先令结果 rst = num
 * 然后每次 rst 左移一位为腾出空位，
 * 先通过与操作取出num的最低位，再通过或操作将其赋值为 rst 的最高位。
 * 最后 num 右移一位。
 * 循环 32 次即可。
 * 
 * 注意点：Java中不存在无符号整数，所以我们必须通过 无符号右移>>> 来实现
 */
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int num) {
        int rst = num;
        for (int i = 31; i >= 0; i--) {
            rst <<= 1;
            rst |= num & 1;
            num >>>= 1;  // must do unsigned shift
        }
        return rst;
    }
}

// Approach 2
/**
 * 分治法
 *  逆序32位分解为两个逆序16位的
 *  逆序16位分解为两个逆序8位的
 *  逆序8位分解为两个逆序4位的
 *  逆序4位分解为两个逆序2位的
 *  最后一个2位的逆序，直接交换即可。也就是分治递归的终止条件。
 *  
 * 代码也很好理解，比如：最后一步的 -- ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1)
 * n & 0xaaaaaaaa 是取偶数位 (a的二进制为：1010); n & 0x55555555 是取偶数位（5的二进制为：0101）
 * 然后偶数位无符号右移一位，奇数位左移一位，再取或，这样便实现了交换奇数偶数位。
 *  
 * 以上过程经过4步，逆序完成。
 * 推而广之，总的时间复杂度为O(logn)，n是二进制的位数。这个方法可以推广到任意位。
 */
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }
}