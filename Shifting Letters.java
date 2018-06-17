/*
We have a string S of lowercase letters, and an integer array shifts.
Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.
Return the final string after all such shifts to S are applied.

Example 1:
Input: S = "abc", shifts = [3,5,9]
Output: "rpl"
Explanation:
We start with "abc".
After shifting the first 1 letters of S by 3, we have "dbc".
After shifting the first 2 letters of S by 5, we have "igc".
After shifting the first 3 letters of S by 9, we have "rpl", the answer.

Note:
1 <= S.length = shifts.length <= 20000
0 <= shifts[i] <= 10 ^ 9
 */

/**
 * Approach: Reverse Calculation (Suffix Sum)
 * 比较简单的一道题目，个人认为定为 Medium 有点高了。
 * 根据题目要求，我们应该从后面往前进行计算。
 * 即 最后一位 应该移动 shifts[len-1] 次，
 * 倒数第二位移动 shifts[len-1] + shifts[len-2] 次。
 * 典型的使用 后缀和 解决的题目，但是根据题意 shifts[i] <= 10^9 所以相加时我们需要进行一次 %26 操作。
 * 然后我们还需要完成 字符类型 与 整形之间的转换。
 *
 * 总结本题的考点在于：
 *  1. 后缀和
 *  2. 根据题意进行取模运算（考虑到溢出情况）
 *  3. 字符类型与整数类型的转换
 *
 * 时间复杂度：O(n)
 */
class Solution {
    public String shiftingLetters(String S, int[] shifts) {
        char[] chars = S.toCharArray();
        int shift = 0;
        for (int i = shifts.length - 1; i >= 0; i--) {
            // 取模运算，防止溢出
            shift = (shift + shifts[i]) % 26;
            // 利用 int 进行 shift 计算，完成后转回 char 类型
            chars[i] = (char)((chars[i] - 'a' + shift) % 26 + 'a');
        }
        return String.valueOf(chars);
    }
}