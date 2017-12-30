/*
Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
The order of output does not matter.

Example 1:
Input:
s: "cbaebabacd" p: "abc"
Output:
[0, 6]

Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".

Example 2:
Input:
s: "abab" p: "ab"
Output:
[0, 1, 2]

Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
 */

/**
 * This question is almost the same as Permutation in String.
 * We have 4 method to solve it, here we list the better two method.
 * You can get more details and explanations here:
 * https://github.com/cherryljr/LeetCode/blob/master/Permutation%20in%20String.java
 *
 * The Best Method is Sliding Window
 * This method is so classic and beautiful, you can should read the discussion about it here:
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/discuss/92007/
 * (Believe me, it worth reading)
 */

/**
 * Approach 1: Using Array (Similar to HashMap)
 * Instead of making use of a special HashMap data structure just to store the frequency of occurence of characters,
 * we can use a simpler array data structure to store the frequencies.
 * Then we just need to compare the two map is the same or not.
 */
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> rst = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() < p.length()) {
            return rst;
        }

        int[] map_p = new int[26];
        for (int i = 0; i < p.length(); i++) {
            map_p[p.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length() - p.length(); i++) {
            int[] map_s = new int[26];
            for (int j = 0; j < p.length(); j++) {
                map_s[s.charAt(i+j) - 'a']++;
            }
            if (isMatch(map_p, map_s)) {
                rst.add(i);
            }
        }
        return rst;
    }

    public boolean isMatch(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Approach 2: Sliding Window
 * A easy method, you will get it with the comments
 */
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> rst = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() < p.length()) {
            return rst;
        }

        int[] map_p = new int[26];
        int[] map_s = new int[26];
        // Initialize the map / window
        for (int i = 0; i < p.length(); i++) {
            map_p[p.charAt(i) - 'a']++;
        }
        for (int i = 0; i < p.length(); i++) {
            map_s[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length() - p.length(); i++) {
            if (isMatch(map_p, map_s)) {
                rst.add(i);
            }
            // if don't match, we move the sliding window
            // remove the preceding character and add a new succeeding character to the new window
            map_s[s.charAt(i+p.length()) - 'a']++;
            map_s[s.charAt(i) - 'a']--;
        }
        if (isMatch(map_p, map_s)) {
            rst.add(s.length() - p.length());
        }
        return rst;
    }

    public boolean isMatch(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}