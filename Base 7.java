/*
Given an integer, return its base 7 string representation.

Example 1:
Input: 100
Output: "202"

Example 2:
Input: -7
Output: "-10"

Note: The input will be in range of [-1e7, 1e7].
 */

/**
 * Approach 1: Convert Radix
 * 使用取余，整除的方法一步步走下去即可。
 * 注意该方法先算出来的是高位，所以需要一次 reverse.
 * （需要处理负数）
 */
class Solution {
    public String convertToBase7(int num) {
        int abs = Math.abs(num);
        StringBuilder sb = new StringBuilder();
        while (abs >= 7) {
            sb.append(abs % 7);
            abs /= 7;
        }
        sb.append(abs).reverse();
        return num >= 0 ? sb.toString() : "-" + sb.toString();
    }
}

/**
 * Approach 2: Using Native Method
 * 直接使用 Integer 自带的 toString() 转换过去即可
 * 本题为 由 整数 -> 字符串
 *
 * 类似的问题还有：
 *  进制转换：https://github.com/cherryljr/NowCoder/blob/master/%E8%BF%9B%E5%88%B6%E8%BD%AC%E6%8D%A2.java
 *  由 字符串 -> 整数
 */
class Solution {
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}

