/*
A message containing letters from A-Z is being encoded to numbers using the following mapping:
'A' -> 1
'B' -> 2
...
'Z' -> 26
Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:
Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).

Example 2:
Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 */

/**
 * Approach: DP
 * 这道题目与 Climbing Stairs 有点像。
 * 我们可以从 递归 的方向来考虑这道问题，这样可能会跟顺利一些。
 * 递归求解时，我们可以去掉当前一个字符，或者两个字符来进行解码。
 * 如果能够正确解码，则当前的方法数就是这两个情况方法数之和。然后对剩下的字符串继续解码。
 * 因为这是一个 无后效性 问题，因此我们可以使用 DP 来解决。
 * 当然也可以使用 递归 + 记忆化搜索 来做，效果是一样的（倒不如说这其实就是 DP）
 *
 * dp[i]表示：前 i 个字符总共有多少中解码的方法数。
 * 其依赖于 dp[i-1] 和 dp[i-2].
 * 我们只需要分别分析：取一个字符解码的情况 和 取两个字符解码的情况 即可。
 * 如果能够被正确解码，就加上对应的方法数。
 * 这边关于初始化，需要说明一下：
 *  dp[0] 初始化为 1，表示当字符串为 空串 时，也具有一种解码。
 *  举个例子："10234", dp[2] = dp[0] = 1
 *  因为此时当我们取 1个 字符时，是无法被正确解码的。
 *  
 * 时间复杂度：O(n) (注意在取字符时不要使用 subString() 不然时空复杂度都会上升到 O(n^2))
 * 空间复杂度：O(n) (可以利用滚动数组优化成 O(1) 的)
 *
 * 视频讲解：
 * https://www.youtube.com/watch?v=OjEHST4SXfE
 */
class Solution {
    public int numDecodings(String s) {
        // 如果字符串为空或者以 '0' 开头，都无法被解码
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }

        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= len; i++) {
            // 取一个字符进行解码
            int oneChar = s.charAt(i - 1) - '0';
            // 取两个字符进行解码
            // 这里如果使用 Integer.valueOf(s.subString(i-2, i)) 会使得时间复杂度上升到 O(n^2)
            // 因为 subString() 是一个线性操作，并且还会产生 O(n^2) 的额外空间
            int twoChar = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            // 一个字符解码合法
            if (1 <= oneChar && oneChar <= 9) {
                dp[i] += dp[i - 1];
            }
            // 两个字符解码合法
            if (10 <= twoChar && twoChar <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[len];
    }
}