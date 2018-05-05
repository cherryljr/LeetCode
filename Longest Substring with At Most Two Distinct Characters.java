/*
Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

For example, Given s = “eceba”,

T is "ece" which its length is 3.
 */

/**
 * Using Sliding Window Template
 * Detail explanation about the template is here:
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 *
 * Here is a similar question in LintCode (Just be different from k and two):
 * https://www.lintcode.com/problem/longest-substring-with-at-most-k-distinct-characters/description
 */
public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int begin = 0, end = 0;
        // The number of distinct character.
        int counter = 0;
        // The length of longest substring.
        int maxLen = 0;

        while (end < s.length()) {
            char cEnd = s.charAt(end);
            map.put(cEnd, map.getOrDefault(cEnd, 0) + 1);
            if (map.get(cEnd) == 1) {
                counter++;  //new char
            }
            end++;

            // counter > 2 means that
            // there are more than two distinct characters in the current window.
            // So we should move the sliding window.
            while (counter > 2) {
                char cBegin = s.charAt(begin);
                map.put(cBegin, map.get(cBegin) - 1);
                if (map.get(cBegin) == 0) {
                    counter--;
                }
                begin++;
            }
            // Pay attention here!
            // We don't get/update the result in while loop above
            // Because if the number of distinct character isn't big enough, we won't enter the while loop
            // eg. s = "abc" (We'd better update the result here to avoid getting 0 length)
            maxLen = Math.max(maxLen, end - begin);
        }

        return maxLen;
    }
}