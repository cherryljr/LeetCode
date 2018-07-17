/*
Given a binary tree rooted at root, the depth of each node is the shortest distance to the root.
A node is deepest if it has the largest depth possible among any node in the entire tree.
The subtree of a node is that node, plus the set of all descendants of that node.
Return the node with the largest depth such that it contains all the deepest nodes in it's subtree.

Example 1:
Input: [3,5,1,6,2,0,8,null,null,7,4]
Output: [2,7,4]
Explanation:
    https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/description/
We return the node with value 2, colored in yellow in the diagram.
The nodes colored in blue are the deepest nodes of the tree.
The input "[3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]" is a serialization of the given tree.
The output "[2, 7, 4]" is a serialization of the subtree rooted at the node with value 2.
Both the input and output have TreeNode type.

Note:
The number of nodes in the tree will be between 1 and 500.
The values of each node are unique.
 */

/**
 * Approach: Divide and Conquer (Recursion)
 * 这道题目可以说是 Lowest Common Ancestor 这道问题的 Fellow Up.
 * 不过这里需要求的是 深度最深的节点们的 最低公共祖先。
 * 显然我们不能先求最深的深度，然后再一个个求公共祖先，这样效率太低了。
 *
 * 对此，我们可以在递归的时候将 深度 信息也添加进来即可。
 * 即递归时包含两个信息：深度最深的节点 + 深度
 * 然后依此进行 递归 即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=q1zk8vZIDw0
 * Lowest Common Ancestor:
 *  https://github.com/cherryljr/LintCode/blob/master/Lowest%20Common%20Ancestor.java
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
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return dfs(root).node;
    }

    private Type dfs(TreeNode root) {
        // 如果节点为空，返回 null, 深度为 -1
        if (root == null) {
            return new Type(null, -1);
        }

        Type left = dfs(root.left);
        Type right = dfs(root.right);
        // 如果 left 和 right 的深度都相同，说明 root 是这两个节点的最近公共祖先
        // (即同时包含这两个节点的 subTree)，否则值为深度更深的一侧。
        TreeNode node = left.depth == right.depth ? root
                : (left.depth > right.depth ? left.node : right.node);

        return new Type(node, Math.max(left.depth, right.depth) + 1);
    }

    class Type {
        TreeNode node;
        int depth;

        Type(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}