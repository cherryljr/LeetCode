/*
Return the lexicographically smallest subsequence of text
that contains all the distinct characters of text exactly once.

Example 1:
Input: "cdadabcc"
Output: "adbc"

Example 2:
Input: "abcd"
Output: "abcd"

Example 3:
Input: "ecbacba"
Output: "eacb"

Example 4:
Input: "leetcode"
Output: "letcod"

Note:
    1. 1 <= text.length <= 1000
    2. text consists of lowercase English letters.
 */

/**
 * Approach 1: Monotonic Stack
 * Find the index of last occurrence for each character
 * Use a monotonic stack to keep the characters for result.
 * Loop on each character in the input string S,
 * if the current character is smaller than the last character in the stack,
 * and the last character exists in the following stream,
 * we can pop the last character to get a smaller result.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class Solution {
    public String smallestSubsequence(String text) {
        char[] chars = text.toCharArray();
        // record the index of last occurrence for each character
        int[] lastIndex = new int[26];
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        boolean[] used = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            // if the current character has been used, skip it
            if (used[chars[i] - 'a']) {
                continue;
            }
            // if the current character is smaller than stack.peek()
            // and the last index of stack.peek() is larger than i, it means we can use it later,
            // so we pop it out and mark used as false
            while (!stack.isEmpty() && stack.peek() > chars[i] && lastIndex[stack.peek() - 'a'] > i) {
                used[stack.pop() - 'a'] = false;
            }
            stack.push(chars[i]);
            used[chars[i] - 'a'] = true;
        }

        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()){
            ans.append(stack.pop());
        }
        return ans.reverse().toString();
    }
}

/**
 * Approach 2: Just Using Mind (No Stack)
 * In Approach 1, we use the stack to help us slove this problem and cost O(n) extra space.
 * But in fact, we can only use the mind of Monotonic Stack, and do the operation on StringBuilder.
 * So the extra space would be saved.
 * Owe to this method, the reverse operation could also be saved, so it can run faster.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
class Solution {
    public String smallestSubsequence(String text) {
        char[] chars = text.toCharArray();
        // record the index of last occurrence for each character
        int[] lastIndex = new int[26];
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        boolean[] used = new boolean[26];
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            // if the current character has been used, skip it
            if (used[chars[i] - 'a']) {
                continue;
            }
            // if the current character is smaller than the last character in StringBuilder (mark it as c)
            // and the last index of c is larger than i, it means we can use it later,
            // so we delete it(c) and mark used as false
            while (ans.length() > 0 && ans.charAt(ans.length() - 1) > chars[i] && lastIndex[ans.charAt(ans.length() - 1) - 'a'] > i) {
                used[ans.charAt(ans.length() - 1) - 'a'] = false;
                ans.deleteCharAt(ans.length() - 1);
            }
            ans.append(chars[i]);
            used[chars[i] - 'a'] = true;
        }
        return ans.toString();
    }
}