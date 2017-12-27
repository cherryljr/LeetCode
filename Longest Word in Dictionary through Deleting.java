/*
Given a string and a string dictionary,
find the longest string in the dictionary that can be formed by deleting some characters of the given string.
If there are more than one possible results, return the longest word with the smallest lexicographical order.
If there is no possible result, return the empty string.

Example 1:
Input:
s = "abpcplea", d = ["ale","apple","monkey","plea"]

Output:
"apple"

Example 2:
Input:
s = "abpcplea", d = ["a","b","c"]

Output:
"a"

Note:
All the strings in the input will only contain lower-case letters.
The size of the dictionary won't exceed 1,000.
The length of all the strings in the input won't exceed 1,000.
*/

/**
 * Approach 1: Sorting and Checking Subsequence
 * Algorithm
 * The matching condition in the given problem requires that
 * we need to consider the matching string in the dictionary with the longest length and in case of same length,
 * the string which is smallest lexicographically.
 *To ease the searching process, we can sort the given dictionary's strings based on the same criteria,
 * such that the more favorable string appears earlier in the sorted dictionary.
 *
 * Now, instead of performing the deletions in s,
 * we can directly check if any of the words given in the dictionary(say x) is a subsequence of the given string s,
 * starting from the beginning of the dictionary.
 * This is because, if xx is a subsequence of s, we can obtain x by performing delete operations on s.
 * If x is a subsequence of s every character of x will be present in s.
 *
 * Complexity Analysis
 * Time complexity : O(x*n*logn + n*x).
 * Here n refers to the number of strings in list d and x refers to average string length.
 * Sorting takes O(nlogn) and isSubsequence takes O(x) to check whether a string is a subsequence of another string or not.
 * Space complexity : O(logn). Sorting takes O(logn) space in average case.
 */
class Solution {
    public String findLongestWord(String s, List<String> d) {
        Collections.sort(d, (a, b) -> a.length() == b.length() ?
            a.compareTo(b) : b.length() - a.length());

        for (String word : d) {
            if (isSubsequence(word, s)) {
                return word;
            }
        }
        return "";
    }

    public boolean isSubsequence(String str1, String str2) {
        int index1 = 0;
        for (int index2 = 0; index2 < str2.length() && index1 < str1.length(); index2++) {
            if (str1.charAt(index1) == str2.charAt(index2)) {
                index1++;
            }
        }
        return index1 == str1.length();
    }
}

/**
 * Approach 2: Without Sorting
 * Since sorting the dictionary could lead to a huge amount of extra effort,
 * we can skip the sorting and directly look for the strings x in the unsorted dictionary d such that x is a subsequence in s.
 * If such a string x is found,
 * we compare it with the other matching strings found till now based on the required length and lexicographic criteria.
 * Thus, after considering every string in d, we can obtain the required result.
 *
 * Complexity Analysis
 * Time complexity : O(n∗x).
 * One iteration over all strings is required.
 * Here n refers to the number of strings in list d and x refers to average string length.
 * Space complexity : O(x). max_str variable is used.
 */
class Solution {
    public String findLongestWord(String s, List<String> d) {
        String max_str = "";
        for (String word : d) {
            if (isSubsequence(word, s)) {
                if (word.length() > max_str.length()
                        || (word.length() == max_str.length() && word.compareTo(max_str) < 0) ) {
                    max_str = word;
                }
            }
        }
        return max_str;
    }

    public boolean isSubsequence(String str1, String str2) {
        int index1 = 0;
        for (int index2 = 0; index2 < str2.length() && index1 < str1.length(); index2++) {
            if (str1.charAt(index1) == str2.charAt(index2)) {
                index1++;
            }
        }
        return index1 == str1.length();
    }
}