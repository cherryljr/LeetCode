/*
We are given that the string "abc" is valid.
From any valid string V, we may split V into two pieces X and Y such that X + Y (X concatenated with Y) is equal to V.
(X or Y may be empty.)  Then, X + "abc" + Y is also valid.

If for example S = "abc", then examples of valid strings are: "abc", "aabcbc", "abcabc", "abcabcababcc".
Examples of invalid strings are: "abccba", "ab", "cababc", "bac".

Return true if and only if the given string S is valid.

Example 1:
Input: "aabcbc"
Output: true
Explanation:
We start with the valid string "abc".
Then we can insert another "abc" between "a" and "bc", resulting in "a" + "abc" + "bc" which is "aabcbc".

Example 2:
Input: "abcabcababcc"
Output: true
Explanation:
"abcabcabc" is valid after consecutive insertings of "abc".
Then we can insert "abc" before the last letter, resulting in "abcabcab" + "abc" + "c" which is "abcabcababcc".

Example 3:
Input: "abccba"
Output: false

Example 4:
Input: "cababc"
Output: false

Note:
    1. 1 <= S.length <= 20000
    2. S[i] is 'a', 'b', or 'c'
 */

/**
 * Approach 1: Recursion
 * 看完题目第一反应就是用递归...太明显了...
 * 因为是通过 insert "abc" 来组成的字符串，所以必定可以找到一个连续的 "abc" (最后一个插入的)
 * 所以把它去掉之后，继续检查之后的字符串即可。依次递归求解下去。
 * 
 * 先不说利用到了 indexOf 函数，这里还涉及到了大量的 substring，字符串拼接问题。
 * 速度可以说是巨慢无比了...比赛时这么写竟然让我过了...(不知道 LeetCode 现在有没有更新 case)
 */
class Solution {
    public boolean isValid(String S) {
        if (S == null || S.isEmpty()) {
            return true;
        }

        int index = S.indexOf("abc");
        if (index != -1) {
            return isValid(S.substring(0, index) + S.substring(index + 3));
        }
        return false;
    }
}

/**
 * Approach 2: Using Stack
 * 用栈来改写成非递归版本即可...因为是按照 "abc" 顺序的。
 * 所以出栈时的顺序就会使 "cba"
 * 然后按照这个顺序判断就行。
 */
class Solution {
    public boolean isValid(String S) {
        if (S.length() % 3 != 0) {
            return false;
        }

        Stack<Character> s = new Stack<>();
        for (char ch : S.toCharArray()) {
            if (ch == 'c') {
                if (s.isEmpty() || s.pop() != 'b') {
                    return false;
                }
                if (s.isEmpty() || s.pop() != 'a') {
                    return false;
                }
            } else {
                s.push(ch);
            }
        }

        return s.isEmpty();
    }
}