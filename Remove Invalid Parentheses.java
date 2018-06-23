/*
Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
Note: The input string may contain letters other than the parentheses ( and ).

Example 1:
Input: "()())()"
Output: ["()()()", "(())()"]

Example 2:
Input: "(a)())()"
Output: ["(a)()()", "(a())()"]

Example 3:
Input: ")("
Output: [""]
 */

/**
 * Approach: DFS
 * 首先计算不合法（需要移除括号）的数目。记为 invalidL 和 invalidR.
 * 然后 DFS 每个括号的移除情况即可。
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=2k_rS_u6EBk
 */
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> rst = new ArrayList<>();
        int invalidL = 0, invalidR = 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            } else if (s.charAt(i) == ')') {
                count--;
            }
            // 当发现当前括号串的右括号无法匹配（不符合要求）时，invalidR++
            // 并将 count 置回为 0.表示重新开始一次匹配计算.
            if (count < 0) {
                invalidR++;
                count = 0;
            }
        }
        invalidL = count;

        dfs(s, 0, invalidL, invalidR, rst);
        return rst;
    }

    private void dfs(String s, int index, int invalidL, int invalidR, List<String> rst) {
        if (invalidL == 0 && invalidR == 0) {
            // 不合法的括号已经移除完毕并且当前字符串合法，则加入到结果中
            if (isValid(s)) {
                rst.add(s);
                return;
            }
        }

        for (int i = index; i < s.length(); i++) {
            // 如果有连续的相同括号，则只取第一个，从而实现去重。（与 Subsets II 中的的方式相同）
            if (i > index && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }

            if (s.charAt(i) == ')' && invalidR > 0) {
                // 移除当前右括号
                String nextString = s.substring(0, i) + s.substring(i + 1);
                dfs(nextString, i, invalidL, invalidR - 1, rst);
            }
            if (s.charAt(i) == '(' && invalidL > 0) {
                // 移除当前左括号
                String nextString = s.substring(0, i) + s.substring(i + 1);
                dfs(nextString, i, invalidL - 1, invalidR, rst);
            }
        }
    }

    // 判断当前字符串时候括号匹配（同样也可以使用 Stack 来实现）
    // 详细解析可以参考：Longest Valid Parentheses
    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            }
            if (s.charAt(i) == ')') {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }
}
