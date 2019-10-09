/*
Let's define a function f(s) over a non-empty string s, which calculates the frequency of the smallest character in s.
For example, if s = "dcce" then f(s) = 2 because the smallest character is "c" and its frequency is 2.

Now, given string arrays queries and words, return an integer array answer,
where each answer[i] is the number of words such that f(queries[i]) < f(W), where W is a word in words.

Example 1:
Input: queries = ["cbd"], words = ["zaaaz"]
Output: [1]
Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").

Example 2:
Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
Output: [1,2]
Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both > f("cc").

Constraints:
    1. 1 <= queries.length <= 2000
    2. 1 <= words.length <= 2000
    3. 1 <= queries[i].length, words[i].length <= 10
    4. queries[i][j], words[i][j] are English lowercase letters.
 */

/**
 * Approach: Binary Search
 * Time Complexity: O(nlogn + logn)
 * Space Complexity: O(1)
 */
class Solution {
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int n = words.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = getCount(words[i]);
        }
        Arrays.sort(arr);

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = n - upperBound(arr, getCount(queries[i])) - 1;
        }
        return ans;
    }

    private int upperBound(int[] arr, int target) {
        int left = -1, right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1 >> 1);
            if (arr[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    private int getCount(String word) {
        int count = 0;
        char c = 'z';
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) < c) {
                c = word.charAt(i);
                count = 1;
            } else if (word.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}