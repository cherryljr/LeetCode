We nedd to find the longest subsequence in the dictionary. So it means that
we can't sort the string s => can't calculate the occurence of every character then match them.

There are two solutions:
Solution 1:
We sort the input dictionary by longest length and lexicography. -> Rewrite the Comparator
Then, we iterate through the dictionary exactly once. 
In the process, the first dictionary word in the sorted dictionary 
which appears as a subsequence in the input string s must be the desired solution.

An alternate, more efficient solution which avoids sorting the dictionary. (sort will cost much time)
Traverse the dictionary as Solution 1.
Update the result if it need.
1. the length of current string > the result's length
2. the length of cur and rst is the same && the current string has smaller lexicographical order.
Time Complexity: O(nk), where n is the length of string s and k is the number of words in the dictionary.

中文解释：http://www.cnblogs.com/grandyang/p/6523344.html

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

// Solution 1: Sort the Dictionary
class Solution {
    public String findLongestWord(String s, List<String> d) {
        Collections.sort(d, new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.length() == b.length()) {
                    // smaller lexicographical order
                    return a.compareTo(b);
                }
                // longer length
                return b.length() - a.length();
            }
        });
        
        for (String dic : d) {
            int i = 0;
            for (char c : s.toCharArray()) {
                if (i < dic.length() && c == dic.charAt(i)) {
                    i++;
                }
            }
            if (i == dic.length()) {
                return dic;
            }
        }
        return "";
    }
}

// Solution 2: No Sort
class Solution {
    public String findLongestWord(String s, List<String> d) {
        String longest = "";
        
        for (String dic : d) {
            int i = 0;
            // the same as Solution 1, traverse the dictionary
            for (char c : s.toCharArray()) {
                if (i < dic.length() && c == dic.charAt(i)) {
                    i++;
                }
            }
            
            if (i == dic.length() && dic.length() >= longest.length()) {
                // if current string has longer length or smaller lexicographical order (the same length)
                if (dic.length() > longest.length() || dic.compareTo(longest) < 0) {
                    longest = dic;
                }
            }
        }
        
        return longest;
    }
}

