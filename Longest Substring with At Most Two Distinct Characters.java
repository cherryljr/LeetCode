/*
Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

For example, Given s = “eceba”,

T is "ece" which its length is 3.
 */

/**
 * Using Sliding Window Template
 * Detail explanation about the template is here:
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int begin = 0, end = 0;
        // The number of distinct character.
        int counter = 0;
        // The length of longest substring.
        int len = 0;
		
        while (end < s.length()) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (map.get(c) == 1) {
                counter++;  //new char
            }
            end++;

            // counter > 2 means that 
            // there are more than two distinct characters in the current window.
            // So we should move the sliding window.
            while (counter > 2) {
                char cTemp = s.charAt(begin);
                map.put(cTemp, map.get(cTemp) - 1);
                if (map.get(cTemp) == 0) {
                    counter--;
                }
                begin++;
            }
            len = Math.max(len, end-begin);
        }
		
        return len;
    }
}