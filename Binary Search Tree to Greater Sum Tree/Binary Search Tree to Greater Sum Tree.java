/*
Given the root of a binary search tree with distinct values, modify it so that every node has a new value
equal to the sum of the values of the original tree that are greater than or equal to node.val.

As a reminder, a binary search tree is a tree that satisfies these constraints:
    ·The left subtree of a node contains only nodes with keys less than the node's key.
    ·The right subtree of a node contains only nodes with keys greater than the node's key.
    ·Both the left and right subtrees must also be binary search trees.

Example 1:
https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
Input: [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]

Note:
    1. The number of nodes in the tree is between 1 and 100.
    2. Each node will have value between 0 and 100.
    3. The given tree is a binary search tree.
 */

/**
 * Approach 1: Reverse In-Order Traversal (Recursive)
 * 这道问题与 LeetCode 上面的另外一题 Convert BST to Greater Tree 重复了，直接把代码拷贝过来都能过的那种...
 * 这里直接给出答案不多赘述了...（比赛时就有印象做过，不过当时没想起那道题目叫啥...不过应该可以秒过的来说TAT）
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 *
 * PS.这里只写了两种比较通俗的解法，想知道使用 Morris Traversal 怎么解决的可以参见 Convert BST to Greater Tree 的解答。
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Convert%20BST%20to%20Greater%20Tree.java
 * 类似的问题：
 *  https://github.com/cherryljr/LeetCode/blob/master/Validate%20Binary%20Search%20Tree.java
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
    private int sum = 0;

    public TreeNode bstToGst(TreeNode root) {
        if (root.right != null) {
            bstToGst(root.right);
        }
        sum = root.val += sum;
        if (root.left != null) {
            bstToGst(root.left);
        }
        return root;
    }
}


/**
 * Approach 2: Using Stack (No Recursive)
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 */
class Solution {
    public TreeNode bstToGst(TreeNode root) {
        TreeNode curr = root;
        int sum = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.right;
            }
            curr = stack.pop();
            sum = curr.val += sum;
            curr = curr.left;
        }
        return root;
    }
}