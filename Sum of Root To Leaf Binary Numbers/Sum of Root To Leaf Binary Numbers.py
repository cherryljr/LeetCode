'''
Given a binary tree, each node has value 0 or 1. Each root-to-leaf path represents a binary number starting with the most significant bit.
For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary, which is 13.

For all leaves in the tree, consider the numbers represented by the path from the root to that leaf.

Return the sum of these numbers.

Example 1:
Input: [1,0,1,0,1,0,1]
Output: 22
Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22

Note:
    1. The number of nodes in the tree is between 1 and 1000.
    2. node.val is 0 or 1.
    3. The answer will not exceed 2^31 - 1.
'''

# Approach: Divide and Conquer (Top-Down DFS)
# 解法详解参考同名 java 文件
# 时间复杂度：O(n)
# 空间复杂度：O(logn)

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def sumRootToLeaf(self, root: TreeNode, prevSum = 0) -> int:
        if not root: return 0
        prevSum = prevSum << 1 | root.val
        if not root.left and not root.right:
            return prevSum
        return self.sumRootToLeaf(root.left, prevSum) + self.sumRootToLeaf(root.right, prevSum)
        