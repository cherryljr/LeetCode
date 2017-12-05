/*
Given two strings s and t, write a function to determine if t is an anagram of s.

For example,
s = "anagram", t = "nagaram", return true.
s = "rat", t = "car", return false.

Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?
*/

/* 
 * The main idea is the same as: HashMap
 * It creates a size 26 int arrays as buckets for each letter in alphabet. 
 * It increments the bucket value with String s and decrement with string t. 
 * So if they are anagrams, all buckets should remain with initial value which is zero. 
 * So just checking that and return
 * 
 * Note:
 * This question is similar to: https://github.com/cherryljr/LintCode/blob/master/String%20Permutation.java
 * You can get more solutions and explianations in the URL.
 * 
 * More Harder Related Problem: Permutation in String
 * https://github.com/cherryljr/LeetCode/blob/master/Permutation%20in%20String.java
 */
public class Solution {
    public boolean isAnagram(String s, String t) {
        int[] alphabet = new int[26];
        
        for (int i = 0; i < s.length(); i++) {
            alphabet[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            alphabet[t.charAt(i) - 'a']--;
        }
        for (int i : alphabet) {
            if (i != 0) {
                return false;
            }
        }
        
        return true;
    }
}