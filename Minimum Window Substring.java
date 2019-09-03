/*
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
    1. If there is no such window in S that covers all characters in T, return the empty string "".
    2. If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 */

/**
 * Approach: Using Sliding Window Template
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Detail explanation about the template is here:
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // record the min window's start position
        int startIndex = 0, minLen = Integer.MAX_VALUE;
        int count = map.size();
        // left side (left) and right side (right) of sliding window
        for (int left = 0, right = 0; right < s.length(); right++) {
            char cRight = s.charAt(right);
            if (map.containsKey(cRight)) {
                map.put(cRight, map.get(cRight) - 1);
                if (map.get(cRight) == 0) {
                    count -= 1;
                }
            }

            while (count <= 0) {
                char cLeft = s.charAt(left);
                if (map.containsKey(cLeft)) {
                    map.put(cLeft, map.get(cLeft) + 1);
                    // map.get(cLeft) means cLeft is the start character(position) of curr window
                    // pay attention to the duplicated characters.
                    if (map.get(cLeft) >= 1) {
                        count += 1;
                    }
                }
                // Get the min window
                if (right - left + 1 < minLen) {
                    startIndex = left;
                    minLen = right - left + 1;
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(startIndex, startIndex + minLen);
    }
}
