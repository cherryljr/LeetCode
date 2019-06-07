/*
Students are asked to stand in non-decreasing order of heights for an annual photo.
Return the minimum number of students not standing in the right positions.
(This is the number of students that must move in order for all students to be standing in non-decreasing order of height.)

Example 1:
Input: [1,1,4,2,1,3]
Output: 3
Explanation: 
Students with heights 4, 3 and the last 1 are not standing in the right positions.

Note:
    1. 1 <= heights.length <= 100
    2. 1 <= heights[i] <= 100
 */

/**
 * Approach: Sort and Compare
 * 题目要求当前学生身高的排列有几个不在排列后的正确位置上（从小到大排列）。
 * 因为给出的数据量只有 100，所以可以采用排序然后遍历一遍进行比较的方法即可（非常直接的解法，没啥好说的）。
 * 
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int heightChecker(int[] heights) {
        int[] copy = heights.clone();
        Arrays.sort(copy);
        int count = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] != copy[i]) {
                count++;
            }
        }
        return count;
    }
}