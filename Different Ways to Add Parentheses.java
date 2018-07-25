/*
Given a string of numbers and operators, return all possible results from
computing all the different possible ways to group numbers and operators.
The valid operators are +, - and *.

Example 1:
Input: "2-1-1"
Output: [0, 2]
Explanation:
((2-1)-1) = 0
(2-(1-1)) = 2

Example 2:
Input: "2*3-4*5"
Output: [-34, -14, -10, -10, 10]
Explanation:
(2*(3-(4*5))) = -34
((2*3)-(4*5)) = -14
((2*(3-4))*5) = -10
(2*((3-4)*5)) = -10
(((2*3)-4)*5) = 10
 */

/**
 * Approach: Recursion + Memory Search
 * 这道题目与 Word Break II 十分类似。
 * 都是枚举分割点，然后分别对左右部分进行计算，最后再加计算出来的结果进行笛卡儿积操作。
 * 因为这是一个无后效性问题，在 DFS 的过程中会存在大量重复运算，因此我们使用了记忆化搜索的做法。
 * 本题中，我们的分割点只可能是运算符。
 * 因此我们只需要在运算符的位置将表达式分割成左右两个部分即可。
 *
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/leetcode/leetcode-241-different-ways-to-add-parentheses/
 * Word Break II:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Break%20II.java
 */
class Solution {
    Map<String, List<Integer>> mem = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        // 如果已经 cache 过了，直接返回结果
        if (mem.containsKey(input)) {
            return mem.get(input);
        }

        List<Integer> rst = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
                List<Integer> leftRst = diffWaysToCompute(input.substring(0, i));
                List<Integer> rightRst = diffWaysToCompute(input.substring(i + 1));
                // 将两个结果进行笛卡尔积计算
                compute(leftRst, rightRst, input.charAt(i), rst);
            }
        }
        // 如果结果为 0 表示当前表达式是不可分的，即一个数字。
        // 则直接将结果 add 到结果集当中
        if (rst.size() == 0) {
            rst.add(Integer.valueOf(input));
        }
        // Cache计算结果
        mem.put(input, rst);
        return rst;
    }

    private void compute(List<Integer> leftRst, List<Integer> rightRst, char op, List<Integer> rst) {
        for (int left : leftRst) {
            for (int right : rightRst) {
                switch (op) {
                    case '+':
                        rst.add(left + right);
                        break;
                    case '-':
                        rst.add(left - right);
                        break;
                    case '*':
                        rst.add(left * right);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}