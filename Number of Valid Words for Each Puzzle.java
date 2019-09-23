/*
With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:
word contains the first letter of puzzle.
For each letter in word, that letter is in puzzle.
For example, if the puzzle is "abcdefg", then valid words are "faced", "cabbage", and "baggage"; while invalid words are "beefed" (doesn't include "a")
and "based" (includes "s" which isn't in the puzzle).
Return an array answer, where answer[i] is the number of words in the given word list words that are valid with respect to the puzzle puzzles[i].

Example :
Input:
words = ["aaaa","asas","able","ability","actt","actor","access"],
puzzles = ["aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"]
Output: [1,1,3,2,4,0]
Explanation:
1 valid word for "aboveyz" : "aaaa"
1 valid word for "abrodyz" : "aaaa"
3 valid words for "abslute" : "aaaa", "asas", "able"
2 valid words for "absoryz" : "aaaa", "asas"
4 valid words for "actresz" : "aaaa", "asas", "actt", "access"
There're no valid words for "gaswxyz" cause none of the words in the list contains letter 'g'.

Constraints:
    1. 1 <= words.length <= 10^5
    2. 4 <= words[i].length <= 50
    3. 1 <= puzzles.length <= 10^4
    4. puzzles[i].length == 7
    5. words[i][j], puzzles[i][j] are English lowercase letters.
    6. Each puzzles[i] doesn't contain repeated characters.
 */

/**
 * Approach: State Compression + Subset
 * 题目的详细分析可以参考给出的链接，这里提出一个 Follow Up。
 * 如果 puzzles 里面含有重复的元素，那么该怎么处理呢？
 * （其实很简单，就是从 Subset 变成了 SubSet II，关键在于如何高效处理该问题，能否不利用 排序 或者 Set 来解决？）
 * 例子：words=["abbbbc", "acccb"]; puzzles=["abcb"]
 *
 * 时间复杂度：O(2^7 * n) n 为 puzzles 的长度
 * 空间复杂度：O(m) m 为 words 的长度
 *
 * References:
 *  https://youtu.be/qOrmhPX-gAU
 *  https://github.com/cherryljr/LintCode/blob/master/Subset.java
 *  https://github.com/cherryljr/LintCode/blob/master/Count%201%20in%20Binary.java
 */
class Solution {
    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String word : words) {
            int mask = 0;
            for (char c : word.toCharArray()) {
                mask |= 1 << c - 'a';
            }
            map.put(mask, map.getOrDefault(mask, 0) + 1);
        }

        List<Integer> ans = new ArrayList<>();
        for (String puzzle : puzzles) {
            int mask = 0, first = 1 << puzzle.charAt(0) - 'a';
            for (char c : puzzle.toCharArray()) {
                mask |= 1 << c - 'a';
            }
            int curr = mask, total = 0;
            // 求 SubSet 的第三种解法，与 Count 1 in Binary 用到了相近的数学原理（除去 DFS 和 枚举0~1<<n 详见 SubSet参考链接）
            // 更加适用于一个集合中只能使用某些特定元素的情况，
            // 且此处通过 mask 实现了去重（该做法同样的一个 num 不会被枚举两次）
            while (curr > 0) {
                // 所求的 subset 必须包含第一个字符
                if ((curr & first) == first && map.containsKey(curr)) {
                    total += map.get(curr);
                }
                curr = (curr - 1) & mask;
            }
            ans.add(total);
        }
        return ans;
    }
}