/*
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3
But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3

Note:
Bonus points if you could solve it both recursively and iteratively.
 */

/**
 * Approach: Divide and Conquer
 * 这道题目刚刚看上去是考察 单棵树 的问题。
 * 但是因为考察的是判断这棵树是否是对称的，所以很显然我们可以将它转换成一道 两棵树 之间关系的题目。
 * 只不过这里的两棵树都是它本身。并且因为这里不涉及到数据的修改，所以不必对数据进行复制。
 * 之后我们只需要按照 Divide and Conquer 的做法去处理其左右子树即可。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 * 
 * Divide and Conquer 在 Tree 中的应用与模板总结可以参见：
 *  https://github.com/cherryljr/LeetCode/blob/master/Same%20Tree.java
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
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return mirror(root, root);
    }

    private boolean mirror(TreeNode root1, TreeNode root2) {
        // 递归的终止条件
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }

        // Divide
        boolean left = mirror(root1.left, root2.right);
        boolean right = mirror(root1.right, root2.left);
        // Conquer
        return left && right;
    }
}