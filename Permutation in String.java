/*
Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
In other words, one of the first string's permutations is the substring of the second string.

Example 1:
Input:s1 = "ab" s2 = "eidbaooo"
Output:True
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input:s1= "ab" s2 = "eidboaoo"
Output: False

Note:
The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].
*/

This Problem is similar to String Permutation in LintCode

/**
 * Approach 1: Using Sorting -- (TLE)
 * Algorithm
 * The idea behind this approach is that one string will be a permutation of another string
 * only if both of them contain the same characters the same number of times. 
 * One string s1 is a permutation of other string s2 only if sorted(s1) = sorted(s2).
 *  
 * In order to check this, we can sort the two strings and compare them. 
 * We sort the short string s1 and all the substrings of s2, sort them and compare them with the sorted s1 string. 
 * If the two match completely, s1's permutation is a substring of s2, otherwise not.
 *
 * Complexity Analysis
 * Time complexity : O(l_1log(l_1) + (l_2-l_1) * l_1log(l_1)). 
 * where l_1 is the length of string s1 and l_2 is the length of string s2.
 * Space complexity : O(l_1). t array is used .
 */

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length()) {
            return false;
        }
        

        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            s1 = sort(s1);
            if (s1.equals(sort(s2.substring(i, i + s1.length())))) {
                return true;
            }
        }
        return false;
    }
    
    private String sort(String str) {
        char[] temp = str.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
}

/**
 * Approach 2: HashMap
 * Algorithm -- the same as the Solution-4 of String Permutation in LintCode 
 * one string will be a permutation of another string only if both of them contain the same charaters with the same frequency. 
 * We can consider every possible substring in the long string s2 of the same length as that of s1 
 * and check the frequency of occurence of the characters appearing in the two. 
 * If the frequencies of every letter match exactly, then only s1's permutation can be a substring of s2s2.
 * 
 * In order to implement this approach, instead of sorting and then comparing the elements for equality, 
 * we make use of a hashmap s1map which stores the frequency of occurence of all the characters in the short string s1. 
 * We consider every possible substring of s2 of the same length as that of s1, find its corresponding hashmap as well, namely s2map. 
 * Thus, the substrings considered can be viewed as a window of length as that of s1 iterating over s2. 
 * If the two hashmaps obtained are identical for any such window, 
 * we can conclude that s1's permutation is a substring of s2, otherwise not.
 * 
 * Complexity Analysis
 * Time complexity : O(l_1 + 26*l_1*(l_2-l_1)). 
 * hashmap contains atmost 26 keys. where l_1 is the length of string s1 and l_2 is the length of string s2.
 * Space complexity : O(1). hashmap contains at most 26 key-value pairs.
 */
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s2 == null || s2.length() == 0) {
            return s2.equals(s1);
        }
        if (s1.length() > s2.length()) {
            return false;
        }
        
        Map<Character, Integer> s1map = charCount(s1);
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            // iterator every possible substring of s2 of the same length of s1
            if (mapCompare(s1map, charCount(s2.substring(i, i + s1.length())))) {
                return true;
            }
        }
        return false;
    }
    
    // Make use of a hashmap to store the frequency of occurence of all the characters in the string s.
    public Map<Character, Integer> charCount(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            // make use of new method "getOrDefault" to optimized the code in JDK 1.8.
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        return map;
    }
    
    // Compare the two hashmap
    public boolean mapCompare(Map<Character, Integer> map1, Map<Character, Integer> map2) {
        // if the size of map is different, the two map must be different.
        // But we don't need this function here, cuz the two map's size must be the same.
        // if (map1.size() != map2.size()) {
        //     return false;
        // }
        for (Character ch : map1.keySet()) {
        // make use of new method "getOrDefault" to optimized the code in JDK 1.8.
            if (map1.get(ch) - map2.getOrDefault(ch, -1) != 0) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Approach 3: Using Array instead of HashMap
 * Algorithm - almost the same as the Solution-4 of String Permutation in LintCode 
 * Instead of making use of a special HashMap data structure just to store the frequency of occurence of characters, 
 * we can use a simpler array data structure to store the frequencies. 
 * Given strings contains only lower case alphabets ('a' to 'z'). 
 * So we need to take an array of size 26.
 * The rest of the process remains the same as the hashmap.
 * 
 * Complexity Analysis
 * Time complexity : O(l_1 + 26*l_1*(l_2-l_1)). where l_1 is the length of string s1 and l_2 is the length of string s2.
 * Space complexity : O(1). s1map and s2map of size 26 is used.
 */
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s2 == null || s2.length() == 0) {
            return s2.equals(s1);
        }
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;            
        }
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] s2map = new int[26];
            // iterator every possible substring of s2 of the same length of s1
            for (int j = 0; j < s1.length(); j++) {
                s2map[s2.charAt(i + j) - 'a']++;
            }
            if (matches(s1map, s2map))
                return true;
        }
        
        return false;
    }
    
    public boolean matches(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            if (s1map[i] != s2map[i]) {
                return false;                
            }
        }
        return true;
    }
}

/**
 * Approach 4: Using Sliding Window
 * Algorithm 
 * Instead of generating the hashmap afresh for every window considered in s2, we can create the hashmap just once for the first window in s2. 
 * Then, later on when we slide the window, we know that we remove one preceding character 
 * and add a new succeeding character to the new window considered. 
 * Thus, we can update the hashmap by just updating the indices associated with those two characters only. 
 * Again, for every updated hashmap, we compare all the elements of the hashmap for equality to get the required result.
 * 
 * Complexity Analysis
 * Time complexity : O(l_1+26*(l_2-l_1)), where l_1 is the length of string s1 and l_2 is the length of string s2.
 * Space complexity : O(1). Constant space is used.
 */
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s2 == null || s2.length() == 0) {
            return s2.equals(s1);
        }
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            if (match(s1map, s2map)) {
                return true;
            }
            // if don't match, we move the sliding window
            // remove the preceding character and add a new succeeding character to the new window 
            s2map[s2.charAt(i + s1.length()) - 'a']++;
            s2map[s2.charAt(i) - 'a']--;
        }
        return match(s1map, s2map);
    }
    
    public boolean match(int[] arr1, int[] arr2) {
        for (int i = 0; i < 26; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}