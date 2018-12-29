/*
For a binary tree T, we can define a flip operation as follows:
choose any node, and swap the left and right child subtrees.
A binary tree X is flip equivalent to a binary tree Y
if and only if we can make X equal to Y after some number of flip operations.

Write a function that determines whether two binary trees are flip equivalent.
The trees are given by root nodes root1 and root2.

Example 1:
Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
Output: true
Explanation: We flipped at nodes with values 1, 3, and 5.
Flipped Trees Diagram:
https://leetcode.com/contest/weekly-contest-113/problems/flip-equivalent-binary-trees/

Note:
Each tree will have at most 100 nodes.
Each value in each tree will be a unique integer in the range [0, 99].
 */

/**
 * Approach: Divide and Conquer (Recursion)
 * 这道问题实际上是 Same Tree 和 Symmetric Tree 这两道问题的整合升级版。
 * 做法依旧是使用 Divide and Conquer 的思想
 * 分别判断左右孩子的条件是否成立，然后依次递归下去即可。
 * 关于 Divide and Conquer 做法在 Tree 方面的应用以及模板总结可以参见 Same Tree.
 *
 * 时间复杂度：O(min(N1, N2)) N1, N2 为两棵树的节点个数
 * 空间复杂度：O(min(H1, H2)) H1, H2 为两棵树的高度
 *
 * Same Tree:
 *  https://github.com/cherryljr/LeetCode/blob/master/Same%20Tree.java
 * Symmetric Tree:
 *  https://github.com/cherryljr/LeetCode/blob/master/Symmetric%20Tree.java
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
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        // 结束判断条件，如果均为空节点，返回 true
        if (root1 == null && root2 == null) {
            return true;
        }
        // 判断两个节点不相等的情况
        if (root1 == null || root2 == null || (root1.val != root2.val)) {
            return false;
        }

        // Divide and Conquer 左右孩子
        // 分别判断 需要flip 和 不需要flip 的情况，只要有一个成立结果就为 true
        return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right))
                || (flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
    }
}