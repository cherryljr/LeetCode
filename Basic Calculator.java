/*
Implement a basic calculator to evaluate a simple expression string.
The expression string may contain open ( and closing parentheses ),
the plus + or minus sign -, non-negative integers and empty spaces .

You may assume that the given expression is always valid.

Some examples:
    "1 + 1" = 2
    " 2-1 + 2 " = 3
    "(1+(4+5+2)-3)+(6+8)" = 23

Note: Do not use the eval built-in library function.
 */

/**
 * Approach: Using Stack
 * 使用 栈 来模拟中序表达式的计算即可
 */
class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        char[] chars = s.toCharArray();
        int rst = 0;
        int num = 0;
        int sign = 1;
        stack.push(sign);

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                num = num * 10 + (chars[i] - '0');
            } else if (chars[i] == '+' || chars[i] == '-') {
                rst += sign * num;
                sign = stack.peek() * (chars[i] == '+' ? 1 : -1);
                num = 0;
            } else if (chars[i] == '(') {
                stack.push(sign);
            } else if (chars[i] == ')') {
                stack.pop();
            }
        }

        rst += sign * num;
        return rst;
    }
}