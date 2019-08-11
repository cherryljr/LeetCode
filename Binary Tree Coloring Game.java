/*
Two players play a turn based game on a binary tree.
We are given the root of this binary tree, and the number of nodes n in the tree.
n is odd, and each node has a distinct value from 1 to n.

Initially, the first player names a value x with 1 <= x <= n, and the second player names a value y with 1 <= y <= n and y != x.
The first player colors the node with value x red, and the second player colors the node with value y blue.
Then, the players take turns starting with the first player.
In each turn, that player chooses a node of their color (red if player 1, blue if player 2)
and colors an uncolored neighbor of the chosen node (either the left child, right child, or parent of the chosen node.)

If (and only if) a player cannot choose such a node in this way, they must pass their turn.
If both players pass their turn, the game ends, and the winner is the player that colored more nodes.

You are the second player.  If it is possible to choose such a y to ensure you win the game, return true.
If it is not possible, return false.

Example 1:
https://leetcode.com/problems/binary-tree-coloring-game/
Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
Output: true
Explanation: The second player can choose the node with value 2.

Constraints:
    1. root is the root of a binary tree with n nodes and distinct node values from 1 to n.
    2. n is odd.
    3. 1 <= x <= n <= 100
 */

/**
 * Approach: Divide and Conquer + Greedy
 * 刚刚看到这道题目有点愣愣的，以为会是一个 博奕类 问题。
 * 但实际上确实一道考察 Tree 的简单类问题（不过还是有点意思的）。
 *
 * 关键点在于：当玩家确定一个起点（染色）之后，后续染色的点只能是该点的邻居节点。
 * 这点非常重要，根据它我们可以做出如下推断（我们是二号玩家）：
 *  因为一号玩家已经先染色了 x 节点，因此我们为了最大化自身利益，有三个位置可以选择进行染色：
 *      1. xNode 的父亲节点，染色完该节点之后，整棵树除了以 xNode 为 root 的子树外，其他节点全部都是我们的了；
 *      2. xNode 的左节点，染色完该节点之后，xNode的左子树全部都是我们的了；
 *      3. xNode 的右节点，染色完该节点之后，xNode的右子树全部都是我们的了
 * 至此，答案基本也就呼之欲出了，我们只需要在以上三种方案中选出能够染色节点数最多的方案，然后看个数是否超过 n/2 即可。
 * （题目在限制条件中已经告知 n 为奇数，因此不存在平局的情况）
 *
 * 时间复制度：O(n)
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
    TreeNode xNode = null;
    int leftSize, rightSize;

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        dfs(root, x);
        return Math.max(n - leftSize - rightSize - 1, Math.max(leftSize, rightSize)) > n / 2;
    }

    private int dfs(TreeNode root, int x) {
        if (root == null) {
            return 0;
        }

        int left = dfs(root.left, x);
        int right = dfs(root.right, x);
        if (root.val == x) {
            leftSize = left;
            rightSize = right;
        }
        return left + right + 1;
    }
}