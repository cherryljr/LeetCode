/*
Given a chemical formula (given as a string), return the count of each atom.
An atomic element always starts with an uppercase character, then zero or more lowercase letters, representing the name.
1 or more digits representing the count of that element may follow if the count is greater than 1. If the count is 1, no digits will follow. 
For example, H2O and H2O2 are possible, but H1O2 is impossible.

Two formulas concatenated together produce another formula. For example, H2O2He3Mg4 is also a formula.
A formula placed in parentheses, and a count (optionally added) is also a formula. For example, (H2O2) and (H2O2)3 are formulas.

Given a formula, output the count of all elements as a string in the following form: 
the first name (in sorted order), followed by its count (if that count is more than 1), 
followed by the second name (in sorted order), followed by its count (if that count is more than 1), and so on.

Example 1:
Input: 
formula = "H2O"
Output: "H2O"
Explanation: 
The count of elements are {'H': 2, 'O': 1}.

Example 2:
Input: 
formula = "Mg(OH)2"
Output: "H2MgO2"
Explanation: 
The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.

Example 3:
Input: 
formula = "K4(ON(SO3)2)2"
Output: "K4N2O14S4"
Explanation: 
The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.

Note:
All atom names consist of lowercase letters, except for the first character which is uppercase.
The length of formula will be in the range [1, 1000].
formula will only consist of letters, digits, and round parentheses, and is a valid formula as defined in the problem.
 */

/**
 * Approach: Recursion
 * 涉及到括号问题，通常都是需要使用 递归 来解决。
 * 如：括号匹配，计算器等（当然也可以使用栈来模拟递归的过程）
 * 做法就是遇到括号的话，递归调用当前函数去计算括号内的值并返回。
 * 这样可以实现逻辑较为清晰的代码。
 * （即碰到括号我并不管，直接递归调用计算，然后由子过程告诉我括号里的值算完之后是多少就行）
 * 因此总结下来就是一对括号对应一层递归。
 *
 * 介绍完解决括号问题的基本思想之后，我们来具体分析一下这道题目。
 * 其主要涉及到两个 Function. 一个是：求元素的名称；另一个是：求该原子出现的次数。
 * 这两个函数在实现上还是相对简单的，通过遍历字符并判断即可。
 * 因为最后的结果需要按照 字符顺序 进行输出，因此我们需要使用 TreeMap
 * 来统计原子的名称以及对应的出现次数。
 * 然后只需要从头开始遍历该字符串（这里为了遍历将其转为了 字符数组）
 * 遇到 '(' 直接调用子过程去计算括号内的值并返回，同时计算括号后的数值(倍数)，并对括号内的值进行处理即可；
 * 遇到 ')' 直接跳过并返回当前的计算结果
 * 如果是其他情况，说明没有括号，利用 getName 和 getCount 即可完成计算。
 *
 * 参考资料：
 * https://www.youtube.com/watch?v=6nQ2jfs7a7I
 */
class Solution {
    // 因为 Java 中没有指针，所以我们只能通过使用成员变量的方法来使得各个函数都能够 访问并修改 遍历的下标
    private int index;

    public String countOfAtoms(String formula) {
        if (formula.length() <= 1) {
            return formula;
        }

        index = 0;
        Map<String, Integer> map = recursion(formula.toCharArray());
        StringBuilder rst = new StringBuilder();
        // 利用 StringBuilder 整理获得的结果（拼接起来）
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            rst.append(entry.getKey());
            // 当原子出现次数 >1 时后面才需要使用数字表示出现次数
            if (entry.getValue() > 1) {
                rst.append(entry.getValue());
            }
        }
        return rst.toString();
    }

    // 递归函数，统计从 index 开始到 字符串结束 或者 ')' 位置 的各个元素名称以及对应的次数
    private Map<String, Integer> recursion(char[] formula) {
        Map<String, Integer> rst = new TreeMap<>();
        while (index < formula.length) {
            // 当遇到 左括号 时，递归调用函数来计算出 括号内的元素名称以及出现次数 并返回
            if (formula[index] == '(') {
                index++;
                Map<String, Integer> subRst = recursion(formula);
                // 计算 括号内容之后 跟着的数字，并将括号内中所有原子对应的次数进行加倍处理 (*count)
                int count = getCount(formula);
                for (Map.Entry<String, Integer> entry : subRst.entrySet()) {
                    rst.put(entry.getKey(), rst.getOrDefault(entry.getKey(), 0) + entry.getValue() * count);
                }
            } else if (formula[index] == ')') {
                // 当遇到 右括号 说明当前递归应该结束了，下标向后移动一位跳过 ')' 并返回结果
                index++;
                return rst;
            } else {
                // 如果是没有 括号 的普通情况，那么我们直接利用 getName 和 getCount 计算即可
                String name = getName(formula);
                rst.put(name, rst.getOrDefault(name, 0) + getCount(formula));
            }
        }
        return rst;
    }

    // 获取从 index 开始的元素名称是什么
    private String getName(char[] formula) {
        StringBuilder name = new StringBuilder().append(formula[index++]);
        while (index < formula.length && Character.isLowerCase(formula[index])) {
            name.append(formula[index++]);
        }
        return name.toString();
    }

    // 获取从 index 开始的数字是什么（原子对应的出现次数）
    private int getCount(char[] formula) {
        StringBuilder number = new StringBuilder();
        while (index < formula.length && Character.isDigit(formula[index])) {
            number.append(formula[index++]);
        }
        // 如果没有次数，说明出现次数为 1
        return number.length() == 0 ? 1 : Integer.valueOf(number.toString());
    }
}