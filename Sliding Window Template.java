/**
 * There are so many substring search problems which could be solved by the sliding window algorithm.
 * So I sum up the algorithm template here. wish it will help you!
 *
 * The similar questions are:
 * Find All Anagrams in a String
 *  Q: https://leetcode.com/problems/find-all-anagrams-in-a-string/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Find%20All%20Anagrams%20in%20a%20String.java
 * Minimum Window Substring
 *  Q: https://leetcode.com/problems/minimum-window-substring/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Window%20Substring.java
 * Permutation in String
 *  Q: https://leetcode.com/problems/permutation-in-string/description/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Permutation%20in%20String.java
 * Longest Substring Without Repeating Characters
 *  Q: https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20Without%20Repeating%20Characters.java
 * Longest Substring with At Most Two Distinct Characters
 *  Q: https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20with%20At%20Most%20Two%20Distinct%20Characters.java
 * Subarrays with K Different Integers
 *  Q: https://leetcode.com/problems/subarrays-with-k-different-integers/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Subarrays%20with%20K%20Different%20Integers.java
 * Substring with Concatenation of All Words
 *  Q: https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 *  A: https://github.com/cherryljr/LeetCode/blob/master/Substring%20with%20Concatenation%20of%20All%20Words.java
 */
/*
This Template is based on question: Find All Anagrams in a String
Description
Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
The order of output does not matter.

Example:
Input:
s: "abab" p: "ab"

Output:
[0, 1, 2]

Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
 */

public class Solution {
    public List<Integer> slidingWindowTemplate(String s, String t) {
        // Init a collection or int value to save the result according the question.
        List<Integer> result = new LinkedList<>();
        if (t.length() > s.length()) {
            return result;
        }

        // Create a hashmap to save the Characters of the target substring.
        // map表示还缺少的字符以及对应的个数
        // (K, V) = (Character, Frequency of the Characters)
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // Maintain a counter to check whether match the target string.
        // Must be the map size, NOT the string size because the char may be duplicate.
        int counter = map.size();

        // Two Pointers: begin - left pointer of the window; end - right pointer of the window
        int begin = 0, end = 0;

        // The length of the substring which match the target string.
        int len = Integer.MAX_VALUE;

        // Loop at the beginning of the source string
        while (end < s.length()) {
            // Get a character
            char cEnd = s.charAt(end);

            if (map.containsKey(cEnd)) {
                // Plus or minus one
                map.put(cEnd, map.get(cEnd) - 1);
                // Modify the counter according the requirement (different condition).
                // Plus or minus is all possible
                if (map.get(cEnd) == 0) {
                    counter--;
                }
            }
            // 注意这里的 end++ 操作会导致 end 往后移动一个位置，
            // 因此在后面代码中 end 代表的是 subarray 右边界后面 的那个位置。
            end++;

            // Increase begin pointer to make it invalid/valid again
            while (counter == 0 /* counter condition. different question may have different condition */) {
                // Be careful here: choose the char at begin pointer, NOT the end pointer
                char cBegin = s.charAt(begin);
                if (map.containsKey(cBegin)) {
                    // Plus or minus one
                    map.put(cBegin, map.get(cBegin) + 1);
                    if (map.get(cBegin) > 0) {
                        // Modify the counter according the requirement (different condition).
                        counter++;
                    }
                }

                // save / update(min/max) the result if find a target
                // result collections or result int value
                // Pay Attention Here:
                // 该类型题目（模板）是在这里对结果进行更新或者把subarray加到结果集中，
                // 因为进入该 while 循环的话，说明此时的 subarray 是符合我们要求的。
                // 但是在另外一种写法中（只有一个 String，最开始没有 Build 一个 map。 map表示的是含有的字符以及对应的出现次数），
                // 我们是在 while循环之外 进行结果更新的，
                // 这是因为在那种写法下，不符合条件的 subarray 才会进入 while 循环中。
                // 比如：Longest Substring with At Most Two Distinct Characters 和 Longest Substring Without Repeating Characters

                // move the left bound forward
                // make the substring(sliding window) invalid / valid again, so we can move on
                begin++;
            }

            // 另一种做法是在这里对 result 进行更新
            // 总结就是：哪里条件成立，就在哪里更新结果。
            // 本质是两个坐标代表的含义：
            // begin代表想要的subarray的左边界位置；
            // end代表想要的subarray的右边界的后一个位置。
            // 这个定义在两种做法中都是一样的，因此我们只需要根据定义，在合适的地方更新结果即可
        }
        return result;
    }
}