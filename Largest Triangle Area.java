/*
You have a list of points in the plane.
Return the area of the largest triangle that can be formed by any 3 of the points.

Example:
Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
Output: 2
Explanation:
The five points are show in the figure below. The red triangle is the largest.
Picture:
https://s3-lc-upload.s3.amazonaws.com/uploads/2018/04/04/1027.png

Notes:
3 <= points.length <= 50.
No points will be duplicated.
 -50 <= points[i][j] <= 50.
Answers within 10^-6 of the true value will be accepted as correct.
*/

/**
 * Approach: Using Cross Product
 * 关于三角形的面积计算，有非常多的公式....
 * 而在计算机计算中通常使用的就是 Area = 1/2 * |AB| * |AC| * sinA
 * 而 |AB| * |AC| * sinA 就是向量 AB 与 AC 的叉积（外积）
 * 本题提供了各个点的坐标，因此我可以计算出各个向量，然后再计算出 1/2*向量的外积 就是三角形的面积了。
 * 这里用到了 行列式 的计算，不会的快去面壁...
 * 枚举三角形三个点所有组合的方法，计算出最大面积。
 * 时间复杂度为：O(n^3)
 *
 * 对于三角形面积计算这一点，知乎上有个专栏，大家有兴趣可以去看一看：
 * https://zhuanlan.zhihu.com/p/25599977
 *
 * 向量外积的另一个应用：
 * https://github.com/cherryljr/LeetCode/blob/master/Convex%20Polygon.java
 */
class Solution {
    public double largestTriangleArea(int[][] points) {
        double area = 0;
        for (int[] A : points) {
            for (int[] B : points) {
                for (int[] C : points) {
                    area = Math.max(area, 0.5 * calculateArea(A, B, C));
                }
            }
        }
        return area;
    }

    private double calculateArea(int[] A, int[] B, int[] C) {
        int ABx = B[0] - A[0];
        int ABy = B[1] - A[1];
        int ACx = C[0] - A[0];
        int ACy = C[1] - A[1];
        return Math.abs(ABx * ACy - ABy * ACx);
    }
}