/*
Given an array of strings, group anagrams together.

For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
Return:
[
  ["ate", "eat","tea"],
  ["nat","tan"],
  ["bat"]
]

Note: All inputs will be in lower-case.
 */

/**
 * Approach 1: Categorize by Sorted String
 * Intuition
 * Two strings are anagrams if and only if their sorted strings are equal.
 *
 * Complexity Analysis
 *  Time Complexity: O(NKlog(K)), where N is the length of strs, and K is the maximum length of a string in strs.
 *  The outer loop has complexity O(N) as we iterate through each string.
 *  Then, we sort each string in O(KlogK) time.
 *  Space Complexity: O(N*K), the total information content stored in ans.
 *  This Method is quiet apparently and easy.
 *  You can get the code and explanations here:
 *  https://leetcode.com/articles/group-anagrams/
 */

/**
 * Approach 2: Categorize by Count
 * Intuition
 * Two strings are anagrams if and only if their character counts
 * (respective number of occurrences of each character) are the same.
 *
 * Algorithm
 * We can transform each string s into a character count, currMap, consisting of 26 non-negative integers representing
 * the number of a's, b's, c's, etc.
 * We use these counts as the basis for our hash map.
 * the hashable representation of our count will be a string delimited with '#' characters.
 * For example, "abbccc" will be #1#2#3#0#0#0...#0 where there are 26 entries total.
 *
 * Why we use '#' to split the frequencies of each character?
 * Eg. Without '#', the currMap is "12".
 * Then it could represent 12 a's or 1 a and 2 b's. It make us confused.
 *
 * Complexity Analysis
 *  Time Complexity: O(N*K),
 *  where N is the length of strs, and K is the maximum length of a string in strs.
 *  Counting each string is linear in the size of the string, and we count every string.
 *  Space Complexity: O(N*K), the total information content stored in ans.
 */
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }

        Map<String, List<String>> map = new HashMap<>();
        for (String word : strs) {
            String currMap = countCharacter(word);
            // if (!map.containsKey(currMap)) {
            //     map.put(currMap, new ArrayList<>());
            // }
            // map.get(currMap).add(word);
            
            // Use the new method "computeIfAbsent" and Lambda Expression in JDK1.8
            map.computeIfAbsent(currMap, x -> new ArrayList<>()).add(word);
        }

        return new ArrayList<>(map.values());
    }

    public String countCharacter(String str) {
        int[] map = new int[26];
        for (char c : str.toCharArray()) {
            map[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : map) {
            sb.append('#');
            sb.append(i);
        }
        return sb.toString();
    }
}