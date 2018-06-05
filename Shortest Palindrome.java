/*
Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it.
Find and return the shortest palindrome you can find by performing this transformation.

For example:
Given "aacecaaa", return "aaacecaaa".
Given "abcd", return "dcbabcd".
 */

/**
 * Approach: KMP + Reverse
 * 看到 回文串，我们通常会使用到的一个技巧就是逆序。利用逆序后的字符串与原字符串相等，通常可以解决不少问题。
 * 因此我们不妨先将 字符串的逆序 求出来，然后观察发现：
 * 我们要求的其实就是：逆序字符串除去 其后缀部分 与 原字符串前缀部分的 最长相同字符串 后的剩余部分。
 * 这样我们需要添加的字符就是最少的，然后将 去除掉最长前后缀 的逆序字符串 添加到原字符串前面即可得到我们的答案了。
 * 那么说到求最长相同前后缀，毫无疑问就是使用 KMP 算法中 next 数组的求解方法了。
 * 但是这是两个不同的字符串，next[] 是针对单一字符串的，我们要如何将它们 拼接 起来呢？
 * 这里：我们可以利用一个小小的技巧来解决这个问题。
 *
 * 具体过程：
 * 设输入字符串为 s. 则首先，我们建立一个如下的字符串：
 *      str = s + '#' + reverse(s)
 * 然后我们对 str 字符串计算 next 数组。
 * 我们都知道 next 数组的含义就是：当前字符之前字符串的 最长相同前后缀的长度。
 * 因此通过计算 str 终止位置的 next 值，我们便能够得到 需要去除的字符串部分，
 * 也就是 s 的 最长回文子串(从首字母开始). -- 该题实质上也等价于：求 字符串s 从首字母开始的最长回文子串。
 * 故最终结果为：reverse(s).substring(0, len-endNext) + s 或者是 reverse(s.substring(endNext)) + s
 *
 * 这边用到的一个小技巧就是拼接字符串时，在 s 之后先添加上一个字符串中不存在的字符 '#', 然后再添加上 逆序字符串。
 * 这样可以使得我们在求 next 数组进行匹配的时候，强制从 reverse(s) 的第一个字符开始。
 *
 * 该题与 京东的一道校招题“含有连续两个str作为子串的最短字符串”有一定的类似。
 * （当然本题结合上了 回文串 的特点，并且需要读者挖掘题目的真正具体考察点，难度会稍微高一些）
 *
 * PS.
 *  大家可以顺便考虑一下，如果题目改成 在字符串的后面 添加上最少的字符，使其变成回文串的话该怎么做呢？
 *  答案是：求解实质变成了求：原字符串后缀 与 逆序串前缀 的 最长相同部分。 （s的最长回文字串，结尾为最后一个字符）
 *  因此解法与本题基本相同，只需要在构建字符串 str 时，将其改成： str = reverse(s) + '#' + s.
 *  最终结果为：s + reverse(s).substring(endNext)
 *  题目请参照：添加回文串
 *   https://github.com/cherryljr/NowCoder/blob/master/%E6%B7%BB%E5%8A%A0%E5%9B%9E%E6%96%87%E4%B8%B2.java
 */
class Solution {
    public String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s);
        sb.append('#').append(new StringBuilder(s).reverse());
        int endNext = endNextLength(sb.toString().toCharArray());

        return new StringBuilder(s.substring(endNext)).reverse() + s;
    }

    private int endNextLength(char[] arr) {
        int[] next = new int[arr.length + 1];
        next[0] = -1;
        int pos = 2, cn = 0;
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next[next.length - 1];
    }
}
