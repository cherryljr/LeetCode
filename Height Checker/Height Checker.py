'''
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
'''

# Approach: Sort and Compare
# 时间复杂度：O(nlogn)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def heightChecker(self, heights: List[int]) -> int:
        sorted_heights, count = sorted(heights), 0
        for i in range(len(heights)):
            if heights[i] != sorted_heights[i]:
                count += 1
        return count