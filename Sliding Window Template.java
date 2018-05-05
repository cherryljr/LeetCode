/**
 * There are so many substring search problems which could be solved by the sliding window algorithm.
 * So I sum up the algorithm template here. wish it will help you!
 *
 * The similar questions are:
 * Problem : https://leetcode.com/problems/find-all-anagrams-in-a-string/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Find%20All%20Anagrams%20in%20a%20String.java
 * Problem : https://leetcode.com/problems/permutation-in-string/description/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Permutation%20in%20String.java
 * Problem : https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20Without%20Repeating%20Characters.java
 * Problem : https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20with%20At%20Most%20Two%20Distinct%20Characters.java
 * Problem : https://leetcode.com/problems/minimum-window-substring/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Window%20Substring.java
 * Problem : https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 * Solution: https://github.com/cherryljr/LeetCode/blob/master/Substring%20with%20Concatenation%20of%20All%20Words.java
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

                // move the left bound forward
                // make the substring(sliding window) invalid / valid again, so we can move on
                begin++;
            }
        }
        return result;
    }
}