/*
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
According to the definition of LCA on Wikipedia:
“The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants
(where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Note:
    1. All of the nodes' values will be unique.
    2. p and q are different and both values will exist in the binary tree.
 */

/**
 * Approach: Divide and Conquer
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 递归终止条件：当 root 为空时返回 null
        // 或者遍历遇到了要找的 p 或者 q，则返回对应的 node
        if (root == null || root == p || root == q) {
            return root;
        }
        /** Divide */
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        /**
         * Conquer
         * 这里利用了 三目运算 使得代码更加简洁，思路就是：
         * 如果 left 为空，则说明在左子树中没找到，因此应该返回右子树；
         * 同理如果 right 为空，则返回 left。
         * 如果 left 和 right 均不为空，说明 p, q 分布在当前节点的左右子树中。
         * 因此当前节点就是要求的 LCA。
         */
        return left == null ? right : right == null ? left : root;
    }
}