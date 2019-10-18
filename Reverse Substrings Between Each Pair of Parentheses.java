/*
You are given a string s that consists of lower case English letters and brackets. 
Reverse the strings in each pair of matching parentheses, starting from the innermost one.
Your result should not contain any brackets.

Example 1:
Input: s = "(abcd)"
Output: "dcba"

Example 2:
Input: s = "(u(love)i)"
Output: "iloveu"
Explanation: The substring "love" is reversed first, then the whole string is reversed.

Example 3:
Input: s = "(ed(et(oc))el)"
Output: "leetcode"
Explanation: First, we reverse the substring "oc", then "etco", and finally, the whole string.

Example 4:
Input: s = "a(bcdefghijkl(mno)p)q"
Output: "apmnolkjihgfedcbq"

Constraints:
    1. 0 <= s.length <= 2000
    2. s only contains lower case English characters and parentheses.
    3. It's guaranteed that all parentheses are balanced.
*/

/**
 * Approach 1: Stack
 * 括号类问题，自然而然想到使用 栈 或者使用 递归 来解决。
 * 
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
class Solution {
    public String reverseParentheses(String s) {
        Deque<StringBuilder> stack = new ArrayDeque<>();
        // In case the first char is NOT '(', need an empty StringBuilder.
        stack.push(new StringBuilder());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                // need a new StringBuilder to save substring in brackets pair
                stack.push(new StringBuilder());
            } else if (s.charAt(i) == ')') {
                // found a matched brackets pair and reverse the substring between them.
                StringBuilder reverse = stack.pop().reverse();
                stack.peek().append(reverse);
            } else {
                // append the char to the last StringBuilder.
                stack.peek().append(s.charAt(i));
            }
        }
        return stack.peek().toString();
    }
}

/**
 * Approach 2: Recursion
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
class Solution {
    private int index = 0;
    
    public String reverseParentheses(String s) {
        StringBuilder ans = new StringBuilder();
        while (index < s.length()) {
            if (s.charAt(index) == '(') {
                ++index;
                ans.append(reverseParentheses(s));
            } else if (s.charAt(index) == ')') {
                index++;
                return ans.reverse().toString();
            } else {
                ans.append(s.charAt(index++));
            }
        }
        return ans.toString();
    }
}

/**
 * Approach 3: Stack 
 * 上述解法中进行了一些不必要的重复reverse，因此可以推断出本题应该存在更优的 O(n) 解法
 * 设当前字符串所在的括号嵌套层数为 n,如果 n为偶数 说明括号内的字符翻转经过 n 次翻转后仍然是本身
 * 因此对于这些字符串我们并不需要进行翻转
 * 当 n 为奇数时，说明需要进行一次翻转，则我们从后向前读取即可。
 * 对于括号的统计，可以利用 Stack 统计出每对括号对应的位置
 */
class Solution {
    public String reverseParentheses(String s) {
        char[] chars = s.toCharArray();
        int[] pair = new int[chars.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else if (chars[i] == ')') {
                int j = stack.pop();
                pair[j] = i;
                pair[i] = j;
            }
        }
        
        StringBuilder ans = new StringBuilder();
        int step = 1;
        for (int i = 0; i < chars.length; i += step) {
            if (chars[i] == '(' || chars[i] == ')') {
                i = pair[i];
                step = -step;
            } else {
                ans.append(chars[i]);
            }
        }
        return ans.toString();
    }
}