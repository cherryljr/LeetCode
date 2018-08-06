/*
Find the total area covered by two rectilinear rectangles in a 2D plane.
Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.

Rectangle Area
https://leetcode.com/problems/rectangle-area/description/

Example:
Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
Output: 45

Note:
Assume that the total area is never beyond the maximum possible value of int.
 */

/**
 * Approach: Geometry
 * 单纯几何问题的计算，判断两个矩形是否相交，
 * 如果相交减去重叠部分的面积即可。
 * 
 * 同时这里也提供了一种判断矩形是否相交的做法，
 * 除此之外我们还有其他的方法可以用来判断两个矩阵是否相交。
 * 详情可以参考：
 *  https://github.com/cherryljr/LeetCode/blob/master/Rectangle%20Overlap.java
 */
class Solution {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int left = Math.max(A, E);  // 最靠近右侧的左端点
        int right = Math.min(C, G); // 最靠近左侧的右端点
        int bottom = Math.max(B, F);// 最靠近上方的下端点
        int top = Math.min(D, H);   // 最靠近下方的上端点

        int overlap = 0;
        // 判断两个矩形是否相交
        if (left < right && bottom < top) {
            overlap = (right - left) * (top - bottom);
        }
        return (C - A) * (D - B) + (G - E) * (H - F) - overlap;
    }
}
