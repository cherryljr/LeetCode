/*
Given an encoded string, return it's decoded string.
The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k.
For example, there won't be input like 3a or 2[4].

Examples:
s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */

/**
 * Approach: Stack
 * 看到这道题目，很容易就能想到使用 Stack 去解决问题。（括号匹配，嵌套问题）
 * 这里需要分析好对不同情况的处理方法即可。（详见代码注释）
 *
 * 这里道题目的做法以及字符串的处理 与 简单计算器 非常类似。
 * （对于循环时使用 while 还是 for 可以根据具体的场景调整，这里使用 while 会更加好看一些，当然 for 也完全可以）
 * 而对于涉及到 括号类 问题以及如何使用 Stack 来解决，可以参考 Longest Valid Parentheses 中的详细分析。
 *
 * References:
 * Longest Valid Parentheses:
 *  https://github.com/cherryljr/LeetCode/blob/master/Longest%20Valid%20Parentheses.java
 * 简单计算器:
 *  https://github.com/cherryljr/NowCoder/blob/master/%E7%AE%80%E5%8D%95%E8%AE%A1%E7%AE%97%E5%99%A8.java
 */
class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        // 初始化 字符串栈，以应对以字符串开头的情况 (eg. ab3[a]de)
        strStack.push("");

        int index = 0;
        while (index < s.length()){
            // 对于数字的处理，计算字符串需要重复的次数
            if (Character.isDigit(s.charAt(index))) {
                int num = 0;
                while (index < s.length() && Character.isDigit(s.charAt(index))) {
                    num = 10 * num + (s.charAt(index++) - '0');
                }
                numStack.push(num);
            }
            // 对于字符的处理，将该字符追加到上个字符串的末尾
            else if (Character.isLetter(s.charAt(index))) {
                strStack.push(strStack.pop() + s.charAt(index++));
            }
            // 对于 '[' 的处理，代表着一个新的字符串的开始，因此 push 一个空串到 strStack 中，以用于之后的拼接
            else if (s.charAt(index) == '[') {
                strStack.push("");
                index++;
            }
            // 对于 ']' 的处理，代表一个 subString 的结束，需要统计其重复出现的次数，然后拼接到之前的字符串上
            else if (s.charAt(index) == ']') {
                int num = numStack.pop();
                String str = strStack.pop();
                StringBuilder sb = new StringBuilder();
                for (int count = 0; count < num; count++) {
                    sb.append(str);
                }
                // 这里输入保证是合法的，因此该逻辑下，strStack 必定非空（我们初始化时已经 push 了一个空串进来）
                strStack.push(strStack.pop() + sb);
                index++;
            }
        }
        return strStack.pop();
    }
}