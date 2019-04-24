'''
We run a preorder depth first search on the root of a binary tree.
At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.
(If the depth of a node is D, the depth of its immediate child is D+1.  The depth of the root node is 0.)
If a node has only one child, that child is guaranteed to be the left child.

Given the output S of this traversal, recover the tree and return its root.

https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
Example 1:
Input: "1-2--3--4-5--6--7"
Output: [1,2,5,3,4,6,7]

Example 2:
Input: "1-2--3---4-5--6---7"
Output: [1,2,5,3,null,6,null,4,null,7]

Example 3:
Input: "1-401--349---90--88"
Output: [1,401,null,349,88,90]

Note:
    1. The number of nodes in the original tree is between 1 and 1000.
    2. Each node will have a value between 1 and 10^9.
'''

# Appraoch 1: Recursion
# 解法详解参考同名 java 文件 Approach 1

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    index = 0
    
    def recoverFromPreorder(self, S: str) -> TreeNode:
        return self.recover(S, 0)
        
    def recover(self, S:str, exceptedDepth:int) -> TreeNode:
        depth = self.getDepth(S)
        if depth != exceptedDepth:
            self.index -= depth
            return None
        root = TreeNode(self.getValue(S))
        root.left = self.recover(S, depth + 1)
        root.right = self.recover(S, depth + 1)
        return root
    
    def getDepth(self, S:str) -> int:
        depth = 0
        while self.index < len(S) and S[self.index] == '-':
            self.index, depth = self.index + 1, depth + 1
        return depth
    
    def getValue(self, S:str) -> int:
        value = ""
        while self.index < len(S) and S[self.index] != '-':
            value += S[self.index]
            self.index += 1
        return int(value)


# Appraoch 2: Using Stack (No Recursion)
# 解法详解参考同名 java 文件 Approach 2
class Solution:
    index = 0
    
    def recoverFromPreorder(self, S: str) -> TreeNode:
        stack = []
        while self.index < len(S):
            depth = self.getDepth(S)
            newNode = TreeNode(self.getValue(S))
            while len(stack) > depth:
                stack.pop()
            if stack and stack[-1].left is None:
                stack[-1].left = newNode
            elif stack:
                stack[-1].right = newNode
            stack.append(newNode)
        return stack[0]
    
    def getDepth(self, S:str) -> int:
        depth = 0
        while self.index < len(S) and S[self.index] == '-':
            self.index, depth = self.index + 1, depth + 1
        return depth
    
    def getValue(self, S:str) -> int:
        value = ""
        while self.index < len(S) and S[self.index] != '-':
            value += S[self.index]
            self.index += 1
        return int(value)