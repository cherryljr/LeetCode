/*
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
 */

/**
 * Approach: Divide and Conquer (Top-Down)
 * 该问题与 Binary Tree Path Sum 有一定的类似，属于一个 Follow Up。
 * 对与 Tree 的问题，我们通常都考虑使用 递归 的写法去解决。
 * 因此，首先我们来考虑下算法的结束条件。
 *  if root.left == root.right == null, root is leaf with no child {
 *      if root.val < limit, we return null;
 *      else we return root.
 *  }
 *  if root.left != null, root has left child {
 *      Recursively call sufficientSubset function on left child,
 *      with limit = limit - root.val
 *  }
 *  if root.right != null, root has right child {
 *      Recursively call sufficientSubset function on right child,
 *      with limit = limit - root.val
 *  }
 *  if root.left == root.right == null,
 *  root has no more children, no valid path left,
 *  we return null;
 *  else we return root.
 * 依据上述情况分析，我们可以轻易地写出 Top-Down 的递归解法。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) h is the height of tree
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Path%20Sum.java
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
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return root.val < limit ? null : root;
        }

        root.left = sufficientSubset(root.left, limit - root.val);
        root.right = sufficientSubset(root.right, limit - root.val);
        // 如果处理过后的左右孩子都被删除了，那么对应的该父节点也要被删除掉
        return root.left == root.right ? null : root;
    }
}