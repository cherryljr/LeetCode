/*
Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.
We repeatedly make duplicate removals on S until we no longer can.

Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.

Example 1:
Input: "abbaca"
Output: "ca"
Explanation:
For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".

Note:
    1. 1 <= S.length <= 20000
    2. S consists only of English lowercase letters.
 */

/**
 * Approach 1: Recursion
 * 题目对字符串的处理为：如果碰到相邻两个一样的字符就消去，然后剩下的字符串连接在一起，
 * 按照上述要求继续进行处理，直到无法消去为止。
 * 显而易见，上述的过程就是一个递归处理的过程。
 * 因此，可以直接采用递归的写法来解决这道问题。代码非常简洁明了。
 * 当然，这个写法的执行效率也很差就是了，特别是涉及到了字符串的拼接。
 */
class Solution {
    public String removeDuplicates(String S) {
        for (int i = 1; i < S.length(); i++) {
            if (S.charAt(i) == S.charAt(i - 1)) {
                return removeDuplicates(S.substring(0, i - 1) + S.substring(i + 1));
            }
        }
        return S;
    }
}

/**
 * Approach 2: Stack
 * 使用 栈 来模拟递归求解的过程即可。与 Asteroid Collision 问题有些类似。
 * 不过情况简单了很多，只需要当 栈顶元素==当前元素 那么就把栈顶元素 pop，同时跳过当前元素的 push 即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LeetCode/blob/master/Asteroid%20Collision.java
 */
class Solution {
    public String removeDuplicates(String S) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : S.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            ans.append(stack.pollLast());
        }
        return ans.toString();
    }
}

/**
 * Approach 3: Using Array instead of Stack
 * 利用 数组 来替代 栈，进一步提升执行效率。
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public String removeDuplicates(String S) {
        char[] stack = S.toCharArray();
        int index = 0;
        for (int j = 0; j < S.length(); ++j) {
            if (index > 0 && stack[index - 1] == S.charAt(j)) {
                index--;
            } else {
                stack[index++] = S.charAt(j);
            }
        }
        return new String(stack, 0, index);
    }
}