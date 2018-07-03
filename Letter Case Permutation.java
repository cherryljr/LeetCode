/*
Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.
Return a list of all possible strings we could create.

Examples:
Input: S = "a1b2"
Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

Input: S = "3z4"
Output: ["3z4", "3Z4"]

Input: S = "12345"
Output: ["12345"]

Note:
S will be a string with length at most 12.
S will consist only of letters or digits.
 */

/**
 * Approach: DFS / Backtracking
 * 直接使用 DFS 就能够解决。参考代码注释即可。
 *
 * 这边对本题应用到的一个技巧进行一下说明：
 *  因为同一个英文字符的 ASCII 码所对应的大小写之间差值为 32.
 *  而 2^5 = 32.因此我们可以通过 ch ^= 1 << 5.
 *  来实现将字母 ch 进行大小写的转换。即将其二进制 第五位 上的数与 1 进行异或，
 *  这样原来的 小写 可转为 大写，而 大写字母 就会被转为 小写字母。
 *
 * 时间复杂度：O(2^n)
 */
class Solution {
    public List<String> letterCasePermutation(String S) {
        List<String> rst = new ArrayList<>();
        dfs(S.toCharArray(), 0, rst);
        return rst;
    }

    private void dfs(char[] S, int index, List<String> rst) {
        if (index == S.length) {
            rst.add(String.valueOf(S));
            return;
        }

        // 不管该位上字符是什么，都存在一种不改变当前字符直接向后移动的情况
        dfs(S, index + 1, rst);
        // 当该位上字符是 英文字符，则存在另外一种情况，即将其改变为 大写/小写 字母
        if (Character.isLetter(S[index])) {
            S[index] ^= 1 << 5;
            dfs(S, index + 1, rst);
            // Backtracking
            S[index] ^= 1 << 5;
        }
    }
}