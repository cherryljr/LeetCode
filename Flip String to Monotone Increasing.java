/*
A string of '0's and '1's is monotone increasing if it consists of some number of '0's (possibly 0),
followed by some number of '1's (also possibly 0.)

We are given a string S of '0's and '1's, and we may flip any '0' to a '1' or a '1' to a '0'.
Return the minimum number of flips to make S monotone increasing.

Example 1:
Input: "00110"
Output: 1
Explanation: We flip the last digit to get 00111.

Example 2:
Input: "010110"
Output: 2
Explanation: We flip to get 011111, or alternatively 000111.

Example 3:
Input: "00011000"
Output: 2
Explanation: We flip to get 00000000.

Note:
1 <= S.length <= 20000
S only consists of '0' and '1' characters.
 */

/**
 * Approach: DP Prefix + Suffix
 */
class Solution {
    public int minFlipsMonoIncr(String S) {
        if (S == null || S.length() <= 1) {
            return 0;
        }

        int len = S.length();
        // left[0] 代表不进行任何 '1'->'0' 的转换，left[len] 代表将S中的'1'全部转换成'0'
        int[] left = new int[len + 1];
        // right[0] 代表将S中的'0'全部转换成'1'，right[len]代表不进行任何 '0'->'1' 的转换
        int[] right = new int[len + 1];
        // 将左半部分全部转换成'0'，即计算左半部分有多少个'1'即可
        for (int i = 1; i <= len; i++) {
            left[i] = left[i - 1] + S.charAt(i - 1) - '0';
        }
        // 将右半部分全部转换成'1'，即计算左半部分有多少个'0'即可
        for (int i = len - 1; i >= 0; i--) {
            right[i] = right[i + 1] + '1' - S.charAt(i);
        }

        int rst = Integer.MAX_VALUE;
        for (int i = 0; i <= len; i++) {
            rst = Math.min(rst, left[i] + right[i]);
        }
        return rst;
    }
}