/*
Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular region.
Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is represented as [1,1,2,2].
(coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).

https://leetcode.com/problems/perfect-rectangle/
Example 1:
rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [3,2,4,4],
  [1,3,2,4],
  [2,3,3,4]
]
Return true. All 5 rectangles together form an exact cover of a rectangular region.

Example 2:
rectangles = [
  [1,1,2,3],
  [1,3,2,4],
  [3,1,4,2],
  [3,2,4,4]
]
Return false. Because there is a gap between the two rectangular regions.

Example 3:
rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [1,3,2,4],
  [3,2,4,4]
]
Return false. Because there is a gap in the top center.

Example 4:
rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [1,3,2,4],
  [2,2,4,4]
]
Return false. Because two of the rectangles overlap with each other.
 */

/**
 * Approach: Geometry
 * 这是一道 几何学 方面的问题。如果给出的各个矩形能够组成一个完美矩形，那么它们应该满足以下两个条件：
 *  1. 所有 小矩形的面积之和 应该等于组成的 大矩形的面积
 *  2. 小矩形的所有顶点中除了大矩形的四个顶点只能出现一次之外，其他的顶点都必定会出现 2次 或者 4次。
 * 以上两个条件缺一不可。
 * 条件1是为了防止利用多个相同的小矩形进行叠加，从而得到完美矩形的情况。（相同矩形叠加可以满足条件2，但是不能满足条件1）
 * 条件2是为了防止利用重合从而达到总面积等于各个小矩形的面积之和但是并不能组成一个完美矩形的情况。
 * 进一步的证明和图示可以参考下面给出的链接。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://leetcode.com/problems/perfect-rectangle/discuss/87181/Really-Easy-Understanding-Solution(O(n)-Java)
 */
class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        int left = Integer.MAX_VALUE, right = 0, bottom = Integer.MAX_VALUE, top = 0;
        int sumArea = 0;
        Set<String> pointSet = new HashSet<>();
        for (int[] rectangle : rectangles) {
            left = Math.min(left, rectangle[0]);
            right = Math.max(right, rectangle[2]);
            bottom = Math.min(bottom, rectangle[1]);
            top = Math.max(top, rectangle[3]);
            sumArea += (rectangle[2] - rectangle[0]) * (rectangle[3] - rectangle[1]);
            // left-bottom point
            String leftBottom = rectangle[0] + "," + rectangle[1];
            // right-bottom point
            String rightBottom = rectangle[0] + "," + rectangle[3];
            // right-top point
            String rightTop = rectangle[2] + "," + rectangle[3];
            // left-top point
            String leftTop = rectangle[2] + "," + rectangle[1];
            for (String point : new String[]{leftBottom, rightBottom, rightTop, leftTop}) {
                if (!pointSet.add(point)) {
                    pointSet.remove(point);
                }
            }
        }

        if ((right - left) * (top - bottom) != sumArea || pointSet.size() != 4 || !pointSet.contains(left + "," + bottom) || !pointSet.contains(left + "," + top) || !pointSet.contains(right + "," + bottom) || !pointSet.contains(right + "," + top)) {
            return false;
        }
        return true;
    }
}