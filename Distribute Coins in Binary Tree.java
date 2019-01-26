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
 * 不妨设递归函数的作用为：将当前子树调整为平衡状态（每个点的硬币个数为1），并返回需要转移的硬币个数。
 * 注意，这里返回的是个数，可以用 正负数 来表示。
 * 正数代表：多余 x 个； 负数代表：不足 x 个。
 * 这样我们才能根据返回的结果对上一层的进行一个分配。
 * 而我们最终需要的结果可以作为一个全局变量，在硬币发生转移的时候不断累加。
 * 即在本题中我们需要两个信息：
 *  1. 硬币转移的次数
 *  2. 将当前子树balance之后，需要的硬币个数。
 * 第一个信息可以在递归过程中直接进行累加；
 * 而第二个信息需要通过递归调用返回，从而帮助我们对当前情况进行判断。
 * 而这也是该类型题目较难的原因，或者是与简单模板题的不同之处。
 *  即返回的通常并不是题目直接需要的结果，而是一个状态；
 *  然后我们需要通过该状态来对当前情况进行判断，从而影响最终结果。
 *  而最终结果的计算是直接在递归过程中进行的。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) (h is the height of the tree)
 *
 * Reference:
 *  https://youtu.be/zQqku1AXVF8
 * 类似的问题：
 * Binary Tree Cameras:
 *  https://github.com/cherryljr/LeetCode/blob/master/Binary%20Tree%20Cameras.java
 * 模板题分析：
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