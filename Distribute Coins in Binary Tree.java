/*
Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.
In one move, we may choose two adjacent nodes and move one coin from one node to another.
(The move may be from parent to child, or from child to parent.)
Return the number of moves required to make every node have exactly one coin.

Example 1:
Input: [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.

Example 2:
Input: [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root [taking two moves].
Then, we move one coin from the root of the tree to the right child.

Example 3:
Input: [1,0,2]
Output: 2

Example 4:
Input: [1,0,0,null,3]
Output: 4

Note:
    1. 1<= N <= 100
    2. 0 <= node.val <= N
 */

/**
 * Approach: Divide and Conquer
 * 这道题目刚刚看到的时候，感觉有点懵，不清楚怎么做。
 * 但是对于 Tree 类的题目，好歹有个大致的处理模板。
 * 因此先按照这个方向去想（使用 Divide and Conquer 的思想，然后利用 Recursion 实现）
 * 不妨设递归函数的作用为：将当前子树调整为平衡状态（每个点的硬币个数为1），并返回需要转移的次数。
 * 而结果就是对转移次数的不断累加。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) (h is the height of the tree)
 *
 * Reference:
 *  https://youtu.be/zQqku1AXVF8
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
    int res = 0;

    public int distributeCoins(TreeNode root) {
        balance(root);
        return res;
    }

    private int balance(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = balance(root.left);    // 使得左子树平衡所需转移的硬币个数（负数代表需要转入，正数代表需要转出）
        int right = balance(root.right);  // 使得右子树平衡所需转移的硬币个数（负数代表需要转入，正数代表需要转出）
        // 结果加上要使得左右子树平衡所需要的转移次数（两边边的流量）
        res += Math.abs(left) + Math.abs(right);
        return root.val - 1 + left + right;
    }

}