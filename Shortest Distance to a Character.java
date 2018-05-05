/*
Given a string S and a character C, return an array of integers representing
the shortest distance from the character C in the string.

Example 1:
Input: S = "loveleetcode", C = 'e'
Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]

Note:
S string length is in [1, 10000].
C is a single character, and guaranteed to be in string S.
All letters in S and C are lowercase.
 */

/**
 * Approach: Traverse Twice
 * When going left to right, we'll remember the index prev of the last character C we've seen.
 * Then the answer is i - prev.
 * When going right to left, we'll remember the index prev of the last character C we've seen.
 * Then the answer is prev - i.
 * We take the minimum of these two answers to create our final answer.
 *
 * Time Complexity : O(N), where N is the length of S. We scan through the string twice.
 * Space Complexity: O(1)
 */
class Solution {
    public int[] shortestToChar(String S, char C) {
        int len = S.length();
        int[] rst = new int[len];
        int prev = -0x3f3f3f3f;

        // left to right
        for (int i = 0; i < len; i++) {
            if (S.charAt(i) == C) {
                prev = i;
            }
            rst[i] = i - prev;
        }

        // right to left
        prev = 0x3f3f3f3f;
        for (int i = len-1; i >= 0; i--) {
            if (S.charAt(i) == C) {
                prev = i;
            }
            // get the smaller one of the two answers
            rst[i] = Math.min(rst[i], prev - i);
        }

        return rst;
    }
}