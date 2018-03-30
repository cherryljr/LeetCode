/*
Implement a basic calculator to evaluate a simple expression string.
The expression string contains only non-negative integers, +, -, *, / operators and empty spaces.
The integer division should truncate toward zero.

You may assume that the given expression is always valid.

Some examples:
"3+2*2" = 7
" 3/2 " = 1
" 3+5 / 2 " = 5

Note: Do not use the eval built-in library function.
 */

/**
 * Approach: Using Stack
 * 本题输入格式并不明确，即每个字符之间可能有或者没有 空格。
 * 这就给我们处理数据带来了一定的麻烦。
 * 遇到这种的输入，我们通常将其转换成 char[] 的格式，然后逐字符进行处理。
 * 并自己实现 字符串=>整数 的转换，与 空格 的处理。
 *
 * 解法是：利用 栈 模拟 中序表达式的计算过程。
 * 本题中没有 括号，因此我们只需要考虑 加减 和 乘除 这两个不同的优先级即可。
 * 而加减我们可以通过 sign 这个参数，转换为 正负数 之间的加法，
 * 那么剩下的只是利用 stack 解决 乘除 的优先级问题了。
 * 具体实现参见代码以及相关注释。
 *
 * Fellow Up:
 * https://github.com/cherryljr/NowCoder/blob/master/%E7%AE%80%E5%8D%95%E8%AE%A1%E7%AE%97%E5%99%A8.java
 */
class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        char[] chars = s.toCharArray();
        int num = 0;        // 用于实现 字符串=>整数
        char sign = '+';    // 将 sign 初始值设置为正。（全是非负数运算，第一个数必定非负）
        int rst = 0;

        for (int i = 0; i < chars.length; i++) {
            // 如果是数字的话，将其转换成整数。
            if (Character.isDigit(chars[i])) {
                num = num * 10 + chars[i] - '0';
            }
            // 如果该字符为 操作符 或是 最后一个字符的话，进行如下运算
            if ((!Character.isDigit(chars[i]) && chars[i] != ' ') || i == chars.length - 1) {
                // 注意 sign 代表的是数字之前的那个运算符
                if (sign == '-') {
                    stack.push(-num);
                } else if (sign == '+') {
                    stack.push(num);
                } else if (sign == '*') {   // 乘除 优先级更高，利用 stack 进行处理
                    // 先从结果中减去栈顶的元素
                    rst -= stack.peek();
                    stack.push(stack.pop() * num);
                } else if (sign == '/') {
                    // 先从结果中减去栈顶的元素
                    rst -= stack.peek();
                    stack.push(stack.pop() / num);
                }
                sign = chars[i];
                num = 0;
                rst += stack.peek();
            }
        }

        return rst;
    }
}