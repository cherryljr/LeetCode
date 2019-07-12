/*
Return the result of evaluating a given boolean expression, represented as a string.
An expression can either be:
    "t", evaluating to True;
    "f", evaluating to False;
    "!(expr)", evaluating to the logical NOT of the inner expression expr;
    "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
    "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...

Example 1:
Input: expression = "!(f)"
Output: true

Example 2:
Input: expression = "|(f,t)"
Output: true

Example 3:
Input: expression = "&(t,f)"
Output: false

Example 4:
Input: expression = "|(&(t,f,t),!(t))"
Output: false

Constraints:
    1. 1 <= expression.length <= 20000
    2. expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
    3. expression is a valid expression representing a boolean, as given in the description.
 */

/**
 * Approach 1: Recursion
 * Parse类问题与括号类问题相同，通常都是通过 递归 的方式来解决，很明显，这题也不例外。
 * 思路比较直接，因为题目保证给出的字符串都是 合法 的。
 * 因此，我们可以直接根据不同字符所对应的不同处理方式进行处理即可。（总共就这么几种，挨个分析一遍即可）
 * 由于表达式是可以嵌套的，所以这里非常适合使用 递归 来进行处理，而对于递归来说，我们首先就是要分析它的终止条件。
 * 根据题意，本题的终止条件有 3 个，分别为 't', 'f' 和 ')'。具体分析如下：
 *  遇到 '(' 就代表出现了一个子问题，直接利用递归扔给子过程求解。
 *  遇到 ')' 代表一个子过程的结束，则返回当前所得结果。
 *  因为题目中代表 true | false 的只有字符 't' 和 'f'。并且表达式合法，即不会 't' 和 'f' 不会相邻，则说明：
 *  遇到 't' 代表一个表达式的结束，意义为 true，可以直接返回
 *  遇到 'f' 代表一个表达式的结束，意义为 false，可以直接返回
 *  遇到 '!' 代表对后面括号内表达式的取反，直接递归调用子过程即可。（注意跳过左括号）
 *  遇到 '&' 代表对后面括号内表达式进行与运算，同样调用子过程。（注意跳过左括号）
 *  遇到 '|' 代表对后面括号内表达式进行或运算，同样调用子过程。（注意跳过左括号）
 * 值得注意的是：
 *  1. &,| 这两个操作后面括号中的表达式个数可以为多个，因此这里使用了死循环来处理，直到遇到 右括号 才跳出循环。
 *  2. 对于 & 运算可以令 ans 初始值为 true，对于 | 运算可以令初始值为 false。这样就不会影响到后面表达式的计算结果了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://youtu.be/y2kFBqj_J08
 */
class Solution {
    int index = 0;
    public boolean parseBoolExpr(String exp) {
        char c = exp.charAt(index++);
        if (c == 't') {
            return true;
        } else if (c == 'f') {
            return false;
        } else if (c == '!') {
            // 跳过左括号
            index++;
            boolean ans = !parseBoolExpr(exp);
            // 跳过右括号
            index++;
            return ans;
        }

        // 与操作和或操作的处理情况可以通过一个布尔值整合在一起
        boolean isAnd = (c == '&');
        boolean ans = isAnd;
        // 跳过左括号
        index++;
        while (true) {
            if (isAnd) {
                ans &= parseBoolExpr(exp);
            } else {
                ans |= parseBoolExpr(exp);
            }
            // 如果是','直接跳过，如果是')'则说明处理完毕，可以跳出
            if (exp.charAt(index++) == ')') {
                break;
            }
        }
        return ans;
    }
}

/**
 * Approach 2: Using Stack + HashSet
 * 此类问题除了使用 递归 解决以外，还有另外一种非常经典的解决方案：栈。
 * 本题可以使用 栈 这个数据结构帮助我们对表达式进行计算。（带括号，递归转非递归的必然操作）
 * 同时为了表达式的计算方便，我们还是用了 Set 这个数据解构。
 * 实现过程十分简单直观，直接参考代码注释即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public boolean parseBoolExpr(String exp) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == ')') {
                // Set用于存储一对括号表达式中的内容（除了','）
                Set<Character> set = new HashSet<>();
                while (!stack.isEmpty() && stack.peek() != '(') {
                    set.add(stack.pop());
                }
                // 去除匹配的左括号
                stack.pop();
                Character operator = stack.pop();
                if (operator == '&') {
                    stack.push(set.contains('f') ? 'f' : 't');
                } else if (operator == '|') {
                    stack.push(set.contains('t') ? 't' : 'f');
                } else {
                    // 如果操作符是 '!'
                    stack.push(set.contains('t') ? 'f' : 't');
                }
            } else if (exp.charAt(i) != ',') {
                // 除了 ',' 其余信息均需要入栈
                stack.push(exp.charAt(i));
            }
        }
        return stack.peek() == 't';
    }
}