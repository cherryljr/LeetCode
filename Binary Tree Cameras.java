/*
Given a binary tree, we install cameras on the nodes of the tree.
Each camera at a node can monitor its parent, itself, and its immediate children.
Calculate the minimum number of cameras needed to monitor all nodes of the tree.

Example 1:
Input: [0,0,null,0,0]
Output: 1
Explanation: One camera is enough to monitor all nodes if placed as shown.

Example 2:
Input: [0,0,null,0,null,0,null,null,0]
Output: 2
Explanation: At least two cameras are needed to monitor all nodes of the tree.
The above image shows one of the valid configurations of camera placement.

Note:
    1. The number of nodes in the given tree will be in the range [1, 1000].
    2. Every node has value 0.
 */

/**
 * Approach: Divide and Conquer with Greedy
 * 基本上所有 树 类型的题目，都是差不多这个套路。
 * 不过本题难点在于能不能想出这个贪心的处理。
 * （其实也不难，因为在推理状态的时候，其实挺自然地就能想出来了）
 * 本道题目其实算是 Distribute Coins in Binary Tree 的一个升级变形题。
 *
 * 首先我们定义一个 Func 作用为：在当前node的子树中放置照相机，并保证每个节点都能被监控到。
 * 同时，定义一个全局变量 res，每次放置相机的时候，加上需要的个数即可。
 * 然后，我们就会遇到本题的难点：
 *  放置相机之后，其能够监听 当前节点 + 左右孩子 + 节点的父亲。
 * 因此我们需要知道在 Func 处理之后，每个节点的状态是什么。（照相机到底放在哪个点上）
 * 而放置方法就用到了贪心的策略：
 *  因为放在 叶子节点的父亲节点 上能监控到的点比放在 叶子节点 上要多，
 *  故我们在放置相机的时候，会选择放在 left.parent 上。
 *  然后将已经被监控的节点去除掉，再考虑未被监控的点。
 *  这其实也就是 递归调用 的一个过程。
 * 因此这里定义：
 *  return 0：代表当前节点是 叶子 节点
 *  return 1：代表当前节点是 放置相机 的节点（即叶子节点的父亲）
 *  return 2：代表当前节点是 已经被监控 的节点（可以理解为放置了相机的节点的父亲）
 * 然后，只有当 left == 0 || right == 0.即当前节点为 leaf.parent 时，我们需要放置相机，此时 res++
 * 最后，值得注意的一点是：
 *  当我们对 root 处理之后，我们需要查询 root 是否已经被监控到。
 *  如果没有的话，我们需要多放置一台相机来监控 root.
 *  eg. 整颗树只有一个节点，此时我们对 root 处理完后发现，dfs(root) 返回值为 0
 *  代表去除被监控的点后，root作为leaf还未被监控，此时我们需要对结果加一。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * Reference:
 *  https://leetcode.com/problems/binary-tree-cameras/discuss/211180/JavaC%2B%2BPython-Greedy-DFS
 * Distribute Coins in Binary Tree:
 *  https://github.com/cherryljr/LeetCode/blob/master/Distribute%20Coins%20in%20Binary%20Tree.java
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

    public int minCameraCover(TreeNode root) {
        int state = dfs(root);
        return res + (state == 0 ? 1 : 0);
    }

    // Return 0 if it's a leaf.
    // Return 1 if it's a parent of a leaf, with a camera on this node
    // Return 2 if it's coverd, without a camera on this node
    // (like the parent node of a node with camera)
    private int dfs(TreeNode root) {
        if (root == null) {
            return 2;
        }
        int left = dfs(root.left);
        int right = dfs(root.right);
        if (left == 0 || right == 0) {
            res++;
            return 1;
        }
        return left == 1 || right == 1 ? 2 : 0;
    }
}