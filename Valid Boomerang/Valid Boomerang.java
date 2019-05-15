/*
A boomerang is a set of 3 points that are all distinct and not in a straight line.
Given a list of three points in the plane, return whether these points are a boomerang.

Example 1:
Input: [[1,1],[2,3],[3,2]]
Output: true

Example 2:
Input: [[1,1],[2,2],[3,3]]
Output: false

Note:
    1. points.length == 3
    2. points[i].length == 2
    3. 0 <= points[i][j] <= 100
 */

/**
 * Approach: Mathematics (slope of 2 lines are not equal)
 * 题目要求三个点 各不相同 且 不在同一条直线上。即这三个点能组成一个三角形，记为ABC
 * 对此我们可以利用 斜率 判断三点是否共线。设：
 *  A：Xa = p[0][0]，Ya = p[0][1]
 *  B：Xb = p[1][0]，Yb = p[1][1]
 *  C：Xc = p[2][0]，Yc = p[2][1]
 * 任意取三角形的两条边，要求这两条直线的斜率不能相等，即 slope_AB != slope_AC
 *       (Ya - Yb) / (Xa - Xb) != (Ya - Yc) / (Xa - Xc)
 *  ==>  (Xa - Xc) * (Ya - Yb) != (Xa - Xb) * (Ya - Yc)
 *
 * 这里对表达式进行了变换，从而利用 乘法运算 来替代 除法运算。
 * 原因为：
 *  1. 除法运算存在精确度问题，并且需要将 int 转换成 double 来进行计算，运行效率不高。
 *  2. 除法需要处理 斜率无穷大 和 两个点重复 的 Corner Case 不便于编码
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public boolean isBoomerang(int[][] p) {
        return (p[0][0] - p[2][0]) * (p[0][1] - p[1][1]) != (p[0][0] - p[1][0]) * (p[0][1] - p[2][1]);
    }
}
