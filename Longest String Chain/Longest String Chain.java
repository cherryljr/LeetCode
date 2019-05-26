/*
Given a list of words, each word consists of English lowercase letters.
Let's say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 to make it equal to word2.
For example, "abc" is a predecessor of "abac".
A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1,
where word_1 is a predecessor of word_2, word_2 is a predecessor of word_3, and so on.

Return the longest possible length of a word chain with words chosen from the given list of words.

Example 1:
Input: ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: one of the longest word chain is "a","ba","bda","bdca".

Note:
    1. 1 <= words.length <= 1000
    2. 1 <= words[i].length <= 16
    3. words[i] only consists of English lowercase letters.
 */

/**
 * Approach: DP Based on HashMap
 * 根据题目说明：如果一个字符串在任意一个位置插入一个字符后可以变成另外一个字符串，这当前字符串就是另外那个字符串的 predecessor。
 * 求所能够组成的最长字符串链（word1 -> word2 -> word3...）
 *
 * 这道题目可以很容易现出来就是一个 DP 问题。
 * 首先我们对 words 按照 字符串长度 进行排序（从小到大），从而保证当前 word 的 predecessors 状态在之前已经被求解。
 * dp[word]代表以当前 word 作为结尾的最长字符串链的长度。
 * 则状态转移方程就需要我们去遍历所有 word 的 predecessor ，求最长的长度，结果为
 *  dp[word] = max(dp[predecessor] + 1)
 * 最终答案就 max(dp[word])
 *
 * 时间复杂度：O(N * K) (N为字符串个数，K为字符串长度)
 * 空间复杂度：O(N)
 */
class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        Map<String, Integer> dp = new HashMap<>();

        int max = 1;
        for (String word : words) {
            // record the current word
            dp.put(word, 1);
            // delete one char from current word to find the previous string
            for (int i = 0; i < word.length(); i++) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                if (dp.containsKey(prev)) {
                    dp.put(word, Math.max(dp.get(word), dp.get(prev) + 1));
                }
            }
            max = Math.max(max, dp.get(word));
        }
        return max;
    }
}