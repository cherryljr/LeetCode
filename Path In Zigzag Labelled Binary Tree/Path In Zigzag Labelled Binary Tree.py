'''
In an infinite binary tree where every node has two children, the nodes are labelled in row order.
In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right,
while in the even numbered rows (second, fourth, sixth,...), the labelling is right to left.

Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with that label.

https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
Example 1:
Input: label = 14
Output: [1,3,4,14]

Example 2:
Input: label = 26
Output: [1,2,6,10,26]

Constraints:
    1. 1 <= label <= 10^6
'''

# Approach: The Property of Full Binary Tree
# 时间复杂度：O(logn)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def pathInZigZagTree(self, label: int) -> List[int]:
        level = 1
        while ((1 << level) <= label): level += 1
        ans = []
        while label >= 1:
            ans.append(label)
            label = ((1 << level) - 1 + (1 << level - 1) - label) >> 1
            level -= 1
        return ans[::-1]