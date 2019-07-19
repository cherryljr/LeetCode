'''
Given the root of a binary tree, each node in the tree has a distinct value.
After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
Return the roots of the trees in the remaining forest.  You may return the result in any order.

https://leetcode.com/problems/delete-nodes-and-return-forest/
Example 1:
Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
Output: [[1,2,null,4],[6],[7]]

Constraints:
    1. The number of nodes in the given tree is at most 1000.
    2. Each node has a distinct value between 1 and 1000.
    3. to_delete.length <= 1000
    4. to_delete contains distinct values between 1 and 1000.
'''

# Approach: Divide and Conquer
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def delNodes(self, root: TreeNode, to_delete: List[int]) -> List[TreeNode]:
        deleteSet = set(to_delete)
        ans = []
        
        def dfs(root: TreeNode, isNewRoot: bool) -> TreeNode:
            if not root: return None
            shouldDelete = root.val in deleteSet
            if not shouldDelete and isNewRoot:
                ans.append(root)
            root.left = dfs(root.left, shouldDelete)
            root.right = dfs(root.right, shouldDelete)
            return None if shouldDelete else root
        
        dfs(root, True)
        return ans