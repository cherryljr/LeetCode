/*
A self-dividing number is a number that is divisible by every digit it contains.
For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
Also, a self-dividing number is not allowed to contain the digit zero.
Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds if possible.

Example 1:
Input:
left = 1, right = 22
Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]

Note:
The boundaries of each input argument are 1 <= left <= right <= 10000.
 */

/**
 * Approach: Brute Force
 * 本题没有什么很好的解法，直接对每个数进行一次判断即可。
 * 用该数 num % 各个位上的数 看是否能整除即可。（记得排除 0）
 *
 * 时间复杂度：O(n)
 */
class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> rst = new LinkedList<>();
        for (int num = left; num <= right; num++) {
            int i = num;
            for (; i > 0; i /= 10) {
                if ((i % 10 == 0) || (num % (i % 10) != 0)) {
                    break;
                }
            }
            if (i == 0) {
                rst.add(num);
            }
        }
        return rst;
    }
}