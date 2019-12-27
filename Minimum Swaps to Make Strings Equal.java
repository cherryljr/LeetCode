/*
You are given two strings s1 and s2 of equal length consisting of letters "x" and "y" only.
Your task is to make these two strings equal to each other.
You can swap any two characters that belong to different strings, which means: swap s1[i] and s2[j].

Return the minimum number of swaps required to make s1 and s2 equal, or return -1 if it is impossible to do so.

Example 1:
Input: s1 = "xx", s2 = "yy"
Output: 1
Explanation: 
Swap s1[0] and s2[1], s1 = "yx", s2 = "yx".

Example 2: 
Input: s1 = "xy", s2 = "yx"
Output: 2
Explanation: 
Swap s1[0] and s2[0], s1 = "yy", s2 = "xx".
Swap s1[0] and s2[1], s1 = "xy", s2 = "xy".
Note that you can't swap s1[0] and s1[1] to make s1 equal to "yx", cause we can only swap chars in different strings.

Example 3:
Input: s1 = "xx", s2 = "xy"
Output: -1

Example 4:
Input: s1 = "xxyyxyxyxx", s2 = "xyyxyxxxyx"
Output: 4

Constraints:
    1. 1 <= s1.length, s2.length <= 1000
    2. s1, s2 only contain 'x' or 'y'.
*/

/**
 * Approach: Greedy
 * We want to find the minimum swaps so we only consider the different part between the 2 arrays.
 * Plus, it's in binary form, we could express the different part in a single array.
 * So below we consider all possible cases of array d, which is the different part of s1 expressed by 0s and 1s.
 *   d.length == 0: no different, s1 and s2 are identical, return 0
 *   d.length == 1: only 1 different, all other part of s1 and s2 are equal, it's impossible to make them equal, return -1
 *   d.length == 2: 2 differents were found, d is either consists of 2 same value or 2 different value
 *   d = { 00/11 } (d contains same value, either 00 or 11): we need 1 swap to make it equal
 *   d = { 01/10 } (d contains different value): we first need 1 swap to make it equals to the above same value case, and another swap to make it equal, so total 2 swaps
 *   d.length > 2: when more than 2 differents were found, we first reduce the number of differents to 2 by swapping the same value in d to cancel out 2 differents at once,
 *   then consider the case d.length == 2 or d.length == 1
 * If d = { ...0...0... }, we know the counterpart array must be { ...1...1... }, so if we swap the first 0 in d with second 1 in the counterpart array,
 * the result would be d = { ...1...0... } and { ...1...0... }, 2 differents would be cancelled out.
 * 
 * Algorithm
 * Find the number of xs and ys in the different part between the 2 strings.
 * If the sum of xs and ys is an odd number, we must meet the d.length == 1 case since we eliminate 2 differents by 1 swap. Return -1.
 * Get the result by dividing the sum of xs and ys by 2, since 1 swap can cancel out 2 differents, we only need differents / 2 swaps.
 * At last, check how many swaps we need when d.length == 2 by checking whether number of xs or ys is odd number.
 * (If number of xs is odd then that of ys must be odd as well since the sum of them must be even at this point)
 * And add another swap if d contains 2 different values when d.length == 2.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 *
 * Explain in Chinese:
 *  https://leetcode-cn.com/problems/minimum-swaps-to-make-strings-equal/solution/java-tan-xin-suan-fa-xiang-jie-zhi-xing-yong-shi-n/
 */
class Solution {
    public int minimumSwap(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return -1;
        }
        
        int diffX = 0, diffY = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (s1.charAt(i) == 'x') {
                    diffX += 1;
                } else {
                    diffY += 1;
                }
            }
        }
        return (diffX + diffY) % 2 == 0 ? (diffX + 1) / 2 + (diffY + 1) / 2 : -1;
    }
}