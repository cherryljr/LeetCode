'''
Given the root of a binary tree, find the maximum value V for which there exists different nodes A and B
where V = |A.val - B.val| and A is an ancestor of B.
(A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is an ancestor of B.)

Example 1:
Input: [8,3,10,1,6,null,14,null,null,4,7,13]
Output: 7
Explanation:
We have various ancestor-node differences, some of which are given below :
|8 - 3| = 5
|3 - 7| = 4
|8 - 1| = 7
|10 - 13| = 3
Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.

Note:
    1. The number of nodes in the tree is between 2 and 5000.
    2. Each node will have value between 0 and 100000.
'''

# Appraoch: Divide and Conquer (Top-Down DFS)
# 解法详解参考同名 java 文件 Approach 2
# 介于 python 的特性，我们能重写函数名，避免创建新的 dfs函数
# 并且可以把这些内容比较 geek 地写到一行中。但是算法上和 java 的做法是相同的
# 
# 时间复杂度：O(n)
# 空间复杂度：O(logn)

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def maxAncestorDiff(self, root: TreeNode, mini = 0x3f3f3f3f, maxi = 0) -> int:
        return max(self.maxAncestorDiff(root.left, min(mini, root.val), max(maxi, root.val)), self.maxAncestorDiff(root.right, min(mini, root.val), max(maxi, root.val))) if root else maxi - mini