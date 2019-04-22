/*
A valid parentheses string is either empty (""), "(" + A + ")", or A + B, where A and B are valid parentheses strings,
and + represents string concatenation.  For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.

A valid parentheses string S is primitive if it is nonempty, and there does not exist a way to split it into S = A+B,
with A and B nonempty valid parentheses strings.

Given a valid parentheses string S, consider its primitive decomposition: S = P_1 + P_2 + ... + P_k,
where P_i are primitive valid parentheses strings.

Return S after removing the outermost parentheses of every primitive string in the primitive decomposition of S.

Example 1:
Input: "(()())(())"
Output: "()()()"
Explanation:
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, this is "()()" + "()" = "()()()".

Example 2:
Input: "(()())(())(()(()))"
Output: "()()()()(())"
Explanation:
The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".

Example 3:
Input: "()()"
Output: ""
Explanation:
The input string is "()()", with primitive decomposition "()" + "()".
After removing outer parentheses of each part, this is "" + "" = "".

Note:
    1. S.length <= 10000
    2. S[i] is "(" or ")"
    3. S is a valid parentheses string
 */

/**
 * Approach 1: Count Opened Parentheses
 * 与常见 括号类问题 的解决方法相同，通过计算 左右括号的个数，从而得出目前的 substring 是否一个 valid parentheses string.
 * 当左右括号个数相同时，说明该字符串正好是一个匹配的括号字符串。
 * 而题目要求我们去除最外层的一对括号，所以我们使用 leftIndex 和 rightIndex 来跟踪当 count == 0 (括号完全匹配) 时，
 * 最外层的两个左右括号的位置。
 * 则答案就是 S.substring(leftIndex + 1, rightIndex)
 *
 * 时间复杂度：O(n^2) （因为使用了 substring 方法，产生了 O(n) 的时空复杂度）
 * 空间复杂度：O(n)
 */
class Solution {
    public String removeOuterParentheses(String S) {
        if (S == null || S.length() <= 1) {
            return "";
        }

        StringBuilder ans = new StringBuilder();
        int count = 0, leftIndex = 0;
        for (int rightIndex = 0; rightIndex < S.length(); rightIndex++) {
            count += S.charAt(rightIndex) == '(' ? 1 : -1;
            if (count == 0) {
                ans.append(S.substring(leftIndex + 1, rightIndex));
                leftIndex = rightIndex + 1;
            }
        }

        return ans.toString();
    }
}


/**
 * Approach 2: Count Opened Parentheses
 * 在 Approach 1 中，我们使用了 substring 函数，使得算法的总体时间复杂度并不理想。
 * 因此，我们可以通过将 字符串 转换成 字符数组，然后以 count 作为判别条件来将合适的字符加入到 ans 中。
 * 从而避免使用 substring,进而降低算法所需的时空复杂度。
 * count 值的含义仍然不变，然后我们进行以下讨论：
 *  当 strs[i] == '(' 时，如果 count > 1 说明前面必定还有其他的左括号，即当前左括号必定不是最外层的括号，应该被加入到 ans 中
 *  当 strs[i] == ')' 时，如果 count > 0 说明后面必定还有其他的右括号与前面的左括号进行匹配，因此不是最外层的括号，应该被加入到 ans 中。
 *  注：这里分析的 count 值均为已经完成加减操作后的。
 * 根据上述方法，我们即可在不使用 substring 的情况下完成解题。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public String removeOuterParentheses(String S) {
        if (S == null || S.length() <= 1) {
            return "";
        }

        StringBuilder ans = new StringBuilder();
        int count = 0;
        for (char c : S.toCharArray()) {
            if (c == '(' && ++count > 1) {
                ans.append(c);
            } else if (c == ')' && --count > 0) {
                ans.append(c);
            }
        }

        return ans.toString();
    }
}