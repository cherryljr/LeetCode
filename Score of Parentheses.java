/*
Given a balanced parentheses string S, compute the score of the string based on the following rule:
() has score 1
AB has score A + B, where A and B are balanced parentheses strings.
(A) has score 2 * A, where A is a balanced parentheses string.

Example 1:
Input: "()"
Output: 1

Example 2:
Input: "(())"
Output: 2

Example 3:
Input: "()()"
Output: 2

Example 4:
Input: "(()(()))"
Output: 6

Note:
S is a balanced parentheses string, containing only ( and ).
2 <= S.length <= 50
 */

/**
 * Approach 1: Stack
 * 括号类问题，通常使用递归就可以解决。
 * 而这个过程又可以用过使用 Stack 来转换成非递归的方式。
 * 这里使用 Stack 进行模拟计算还是相当清楚的。
 * 
 * Time Complexity: O(N), where NN is the length of S.
 * Space Complexity: O(N), the size of the stack. 
 *
 * 关于递归做法和解析可以参考：
 *  https://leetcode.com/problems/score-of-parentheses/solution/
 */
class Solution {
    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        // The score of the current frame
        stack.push(0);

        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                stack.push(0);
            } else {
                int x = stack.pop();
                int y = stack.pop();
                stack.push(y + Math.max(2 * x, 1));
            }
        }
        return stack.pop();
    }
}

/**
 * Approach 2: Count Balanced Parentheses
 * 因为这道题目中仅仅包含 左括号字符 和 右括号字符。
 * 因此我们可以考虑是否有更好的解法，可以在 O(1) 的额外空间内解决问题。
 * 通过观察我们可以发现，为结果贡献数组的只有 平衡括号组。
 * 因此说白了我们就是去找平衡括号的最小单元"()"，然后进行 相加 / 相乘 的操作。
 * 当一个 "()" 被嵌套在其他的 "()" 里面时，其数值就会 *2.
 * 因此我们只需要数一个 "()" 前面有几个 ')' 即可，就可以说明有几层嵌套。
 * 最后把结果加起来即可。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int scoreOfParentheses(String S) {
        int rst = 0;
        // 嵌套的层数(因为需要排除自身的一层'('，所以初始值为 -1)
        int count = -1;
        for (int i = 0; i < S.length(); i++) {
            count += S.charAt(i) == '(' ? 1 : -1;
            if (S.charAt(i) == '(' && S.charAt(i + 1) == ')') {
                rst += 1 << count;
            }
        }
        return rst;
    }
}