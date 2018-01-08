/*
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the empty string "".
If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 */

/**
 * Using Sliding Window Template
 *
 * Change a little code above to solve the question “Find All Anagrams in a String”:
 *  change
 *      if (end-begin == t.length()) {
 *          result.add(begin);
 *      }
 *  to
 *      if (end-begin < len) {
 *          len = end - begin;
 *          head = begin;
 *      }
 *
 * Detail explanation about the template is here:
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
class Solution {
    public String minWindow(String s, String t) {
        String rst = "";
        if (t.length() > s.length()) {
            return "";
        }

        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int counter = map.size();
        int begin = 0, end = 0; // left side (begin) and right side (end) of sliding window
        int head = 0;   // record the min window's start position
        int len = Integer.MAX_VALUE;
        while (end < s.length()) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    counter--;
                }
            }
            end++;

            while (counter == 0) {
                char tempc = s.charAt(begin);
                if (map.containsKey(tempc)) {
                    map.put(tempc, map.get(tempc) + 1);
                    // map.get(tempc) means tempc is the start character(position) of curr window
                    // pay attention to the duplicated characters.
                    if (map.get(tempc) > 0) {
                        counter++;
                    }
                }

                // Get the min window
                if (end - begin < len) {
                    len = end - begin;
                    head = begin;
                }

                begin++;
            }
        }

        if (len == Integer.MAX_VALUE) {
            return "";
        }
        return s.substring(head, head + len);
    }
}