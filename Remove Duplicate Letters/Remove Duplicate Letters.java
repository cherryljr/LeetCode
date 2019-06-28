/*
Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
You must make sure your result is the smallest in lexicographical order among all possible results.

Example 1:
Input: "bcabc"
Output: "abc"

Example 2:
Input: "cbacdcbc"
Output: "acdb"
 */

/**
 * Approach 1: Monotonic Stack (Same as Smallest Subsequence of Distinct Characters)
 * 与 Smallest Subsequence of Distinct Characters 完全一样...
 * 所以具体解析可以参见：
 *  https://github.com/cherryljr/LeetCode/tree/master/Smallest%20Subsequence%20of%20Distinct%20Characters
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        int[] lastIndex = new int[26];
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        boolean[] used = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            if (used[chars[i] - 'a']) {
                continue;
            }
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
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        int[] lastIndex = new int[26];
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        boolean[] used = new boolean[26];
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (used[chars[i] - 'a']) {
                continue;
            }
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