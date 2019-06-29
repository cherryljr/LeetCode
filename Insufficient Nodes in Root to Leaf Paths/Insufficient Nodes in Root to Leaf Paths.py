'''
Given the root of a binary tree, consider all root to leaf paths: paths from the root to any leaf.  (A leaf is a node with no children.)
A node is insufficient if every such root to leaf path intersecting this node has sum strictly less than limit.
Delete all insufficient nodes simultaneously, and return the root of the resulting binary tree.

https://leetcode.com/problems/insufficient-nodes-in-root-to-leaf-paths/
Example 1:
Input: root = [1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14], limit = 1
Output: [1,2,3,4,null,null,7,8,9,null,14]

Example 2:
Input: root = [5,4,8,11,null,17,4,7,1,null,null,5,3], limit = 22
Output: [5,4,8,11,null,17,4,7,null,null,null,5]

Example 3:
Input: root = [1,2,-3,-5,null,4,null], limit = -1
Output: [1,null,-3,4]

Note:
    1. The given tree will have between 1 and 5000 nodes.
    2. -10^5 <= node.val <= 10^5
    3. -10^9 <= limit <= 10^9
'''

# Approach: Divide and Conquer (Top-Down)
# 时间复杂度：O(n)
# 空间复杂度：O(h) h is the height of tree
# 解法详解参考同名 java 文件

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def sufficientSubset(self, root: TreeNode, limit: int) -> TreeNode:
        if root == None: return None
        if root.left == root.right == None:
            return None if root.val < limit else root
        root.left = self.sufficientSubset(root.left, limit - root.val)
        root.right = self.sufficientSubset(root.right, limit - root.val)
        return None if root.left == root.right == None else root
        