/*
Given a string that contains only digits 0-9 and a target value,
return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

Example 1:
Input: num = "123", target = 6
Output: ["1+2+3", "1*2*3"]

Example 2:
Input: num = "232", target = 8
Output: ["2*3+2", "2+3*2"]

Example 3:
Input: num = "105", target = 5
Output: ["1*0+5","10-5"]

Example 4:
Input: num = "00", target = 0
Output: ["0+0", "0-0", "0*0"]

Example 5:
Input: num = "3456237490", target = 9191
Output: []
 */

/**
 * Approach 1: DFS
 * 本题属于 Target Sum 的 Fellow Up.但是需要求出所有具体的表达式。
 * 因此可以推断出本题需要使用 DFS 来解决。
 *
 * 以下的做法虽然能过，但是效果并不好。原因在于：
 * 每次 DFS 的时候，我们都需要对表达式字符串进行 拼接复制 的操作。
 * 该操作将花费 O(n) 的时间复杂度和空间复杂度。
 * 并且在计算当前数值的时候也需要花费 O(len) 的时间复杂度和空间复杂度。
 * 在 Java 中对于字符串的操作还是相当费时的，substring() 操作等。
 *
 * 优化的做法参见 Approach 2.
 *
 * Target Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Target%20Sum.java
 */
class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> rst = new LinkedList<>();
        dfs(num, target, 0, "", 0, 0, rst);
        return rst;
    }

    /**
     * @param num 题目给定的数字字符串
     * @param target 目标值
     * @param startIndex 当前 DFS 的位置
     * @param exp 当前的表达式字符串
     * @param preValue 前一个节点的数值，如：1+2 的preValue就是 2; 1+2*3 的preValue就是6
     * @param currRst 当前表达式的计算结果
     * @param rst 用于存储所有符合条件的表达式
     */
    private void dfs(String num, int target, int startIndex, String exp, long preValue, long currRst, List<String> rst) {
        // 递归的结束条件
        if (startIndex == num.length()) {
            // 当计算结果等于 target 时，记录该表达式
            if (currRst == target) {
                rst.add(exp);
            }
            return;
        }

        for (int l = 1; l <= num.length() - startIndex; l++) {
            String str = num.substring(startIndex, startIndex + l);
            // deal with 0X... numbers
            if (str.charAt(0) == '0' && str.length() > 1) {
                break;
            }
            long value = Long.valueOf(str);
            // 当字符串太长，会出现超出范围的情况，此时直接跳出循环
            if (value > Integer.MAX_VALUE) {
                break;
            }
            // 当该值为表达式的第一个数字时，其前面没有符号，需要特殊处理（等同于 '+' 号）
            if (startIndex == 0) {
                dfs(num, target, l, str, value, value, rst);
                continue;
            }
            // 对于 3 种符号的情况进行 DFS
            dfs(num, target, startIndex + l, exp + "+" + value, value, currRst + value, rst);
            dfs(num, target, startIndex + l, exp + "-" + value, -value, currRst - value, rst);
            dfs(num, target, startIndex + l, exp + "*" + value, preValue * value, currRst - preValue + preValue * value, rst);
        }
    }
}

/**
 * Approach 2: DFS (Optimized)
 * 对做法1中的不足之处进行了优化，使用 字符数组 替代了 字符串。
 * 而该字符数组是全局唯一的。
 */
class Solution {
    // 直接使用成员变量，可以省去传递过程
    private List<String> rst;
    private char[] num;
    private char[] exp;
    private int target;

    public List<String> addOperators(String num, int target) {
        this.num = num.toCharArray();
        this.rst = new ArrayList<>();
        this.target = target;
        this.exp = new char[num.length() * 2];
        dfs(0, 0, 0, 0);
        return rst;
    }

    private void dfs(int startIndex, int len, long preValue, long currRst) {
        if (startIndex == num.length) {
            if (currRst == target)
                rst.add(new String(exp, 0, len));
            return;
        }

        int index = startIndex;
        int operaIndex = len;
        if (index != 0) {
            ++len;
        }

        long n = 0;
        while (startIndex < num.length) {
            // 0X...
            if (num[index] == '0' && startIndex - index > 0) {
                break;
            }
            n = n * 10 + (num[startIndex] - '0');
            // too long
            if (n > Integer.MAX_VALUE) {
                break;
            }
            // copy exp
            exp[len++] = num[startIndex++];
            if (index == 0) {
                dfs(startIndex, len, n, n);
                continue;
            }
            exp[operaIndex] = '+';
            dfs(startIndex, len, n, currRst + n);
            exp[operaIndex] = '-';
            dfs(startIndex, len, -n, currRst - n);
            exp[operaIndex] = '*';
            dfs(startIndex, len, preValue * n, currRst - preValue + preValue * n);
        }
    }
}