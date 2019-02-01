/*
Given two integers A and B, return any string S such that:

S has length A + B and contains exactly A 'a' letters, and exactly B 'b' letters;
The substring 'aaa' does not occur in S;
The substring 'bbb' does not occur in S.

Example 1:
Input: A = 1, B = 2
Output: "abb"
Explanation: "abb", "bab" and "bba" are all correct answers.

Example 2:
Input: A = 4, B = 1
Output: "aabaa"

Note:
    1. 0 <= A <= 100
    2. 0 <= B <= 100
    3. It is guaranteed such an S exists for the given A and B.
 */

/**
 * Approach: Greedy
 * 这里使用的是一个贪心的解法。
 * 因为保证解是存在的，所以我们可以尽量地在结果中 append 数量较多的那个字符。
 * 当两者字符一样多的话，那么就交替地各放一个即可。
 * 比如 'a' 比 'b' 多，那么我们就先放 "aab"，直到二者数量相等了，在交替放入 "ab"
 * 直到个数满足要求。
 *
 * 	Time Complexity: O(m + n)
 * 	Space Complexity: O(1)
 */
class Solution {
    public String strWithout3a3b(int A, int B) {
        StringBuilder res = new StringBuilder();
        // a 代表字母个数较多的字母； b 代表字母个数较少的字母；
        char a = 'a', b = 'b';
        // countA 代表字母数较多的元素的个数； countB 代表字字母数较少的元素的个数
        int countA = A, countB = B;
        if (B > A) {
            countA = B;
            countB = A;
            a = 'b';
            b = 'a';
        }

        while (countA > 0 || countB > 0) {
            if (countA > 0) {
                res.append(a);
                countA--;
            }
            if (countA > countB) {
                res.append(a);
                countA--;
            }
            if (countB > 0) {
                res.append(b);
                countB--;
            }
        }

        return res.toString();
    }
}