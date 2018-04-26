/*
We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)".
Then, we removed all commas, decimal points, and spaces, and ended up with the string S.
Return a list of strings representing all possibilities for what our original coordinates could have been.

Our original representation never had extraneous zeroes,
so we never started with numbers like "00", "0.0", "0.00", "1.0", "001", "00.01",
or any other number that can be represented with less digits.
Also, a decimal point within a number never occurs without at least one digit occuring before it,
so we never started with numbers like ".1".

The final answer list can be returned in any order.
Also note that all coordinates in the final answer have exactly one space between them (occurring after the comma.)

Example 1:
Input: "(123)"
Output: ["(1, 23)", "(12, 3)", "(1.2, 3)", "(1, 2.3)"]

Example 2:
Input: "(00011)"
Output:  ["(0.001, 1)", "(0, 0.011)"]
Explanation:
0.0, 00, 0001 or 00.01 are not allowed.

Example 3:
Input: "(0123)"
Output: ["(0, 123)", "(0, 12.3)", "(0, 1.23)", "(0.1, 23)", "(0.1, 2.3)", "(0.12, 3)"]

Example 4:
Input: "(100)"
Output: [(10, 0)]
Explanation:
1.0 is not allowed.

Note:
4 <= S.length <= 12.
S[0] = "(", S[S.length - 1] = ")", and the other elements in S are digits.
 */

/**
 * Approach: Cartesian Product(笛卡尔积)
 * 对于每一个 String 我们都可以将其分成两个部分 left, right.
 * 比如："1234",就可以被分为："1" 和 "234"; "12" 和 "34"; "123" 和 "4".
 * 因此对于这两个单独的部分，我们可以考虑其可以组成几种 有效 的数字。
 * 比如："123",就可以组成 "1.23", "12.3", or "123" 这几个有效数字。
 * 然后将 左部分的所有有效数字 乘以 有部分的所有有效数字 就能够得出最终结果了。（分别进行匹配）
 * 这就是 笛卡尔积，这个知识点在许多题目中都有所应用，比如：
 * Get All Possible Binary Trees: https://github.com/cherryljr/NowCoder/blob/master/Get%20All%20Possible%20Binary%20Trees.java
 * Binary Trees With Factors:
 *
 * 至于有效数字，其实就是题目中要求的：不能有多余的 0.
 * 对此我们只需要进行分类讨论即可：
 *  1. 一个数的 第一位 和 最后一位 不可能同时为 0，除非这个数字就是 0.
 *  2. 一个数的 第一位 是0，那么它只有一种可能，那就是 0.xxx
 *  3. 一个数的 最后一位 是0，那么它只可能是 xxx0 (根据不能有多余0的要求...不能够有 "12.30" 这种数)
 *  4. 一个数的头尾都不是0，那么它的可能性就有 ("xxx","x.xx","xx.x") 我们枚举 "." 的位置即可
 */
class Solution {
    public List<String> ambiguousCoordinates(String S) {
        // Remove the parenthese of S
        S = S.substring(1, S.length() - 1);
        List<String> rst = new LinkedList<>();
        for (int i = 1; i < S.length(); i++) {
            for (String left : make(S.substring(0, i))) {
                for (String right : make(S.substring(i))) {
                    rst.add("(" + left + ", " + right + ")");
                }
            }
        }
        return rst;
    }

    private List<String> make(String s) {
        List<String> rst = new LinkedList<>();
        if (s.charAt(0) == '0' && s.charAt(s.length() - 1) == '0') {
            if (s.length() == 1) {
                rst.add("0");
            }
            return rst;
        }
        // "0xxx" The only valid result is "0.xxx"
        if (s.charAt(0) == '0') {
            rst.add("0." + s.substring(1));
            return rst;
        }
        // "xxx0" The only valid result is itself
        if (s.charAt(s.length() - 1) == '0') {
            rst.add(s);
            return rst;
        }

        rst.add(s); // Add itself
        // "xxx" -> "x.xx", "xx.x"
        for (int i = 1; i < s.length(); i++) {
            rst.add(s.substring(0, i) + '.' + s.substring(i));
        }
        return rst;
    }
}