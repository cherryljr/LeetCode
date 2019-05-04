/*
Given the root of a binary tree, find the maximum value V for which there exists different nodes A and B
where V = |A.val - B.val| and A is an ancestor of B.
(A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is an ancestor of B.)

Example 1:
Input: [8,3,10,1,6,null,14,null,null,4,7,13]
Output: 7
Explanation:
We have various ancestor-node differences, some of which are given below :
|8 - 3| = 5
|3 - 7| = 4
|8 - 1| = 7
|10 - 13| = 3
Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.

Note:
    1. The number of nodes in the tree is between 2 and 5000.
    2. Each node will have value between 0 and 100000.
 */

/**
 * Approach 1: Divide and Conquer (Bottom-Up DFS)
 * 根据题目，需要求节点与其祖先节点们之间的最大差值（相减的绝对值最大）
 * 因此可以分析出，根据 Divide and Conquer 的思路（这里采用由下至上的分析方法），
 * 我们只需要左右子树中的 最小值 和 最大值，然后与当前节点值 相减取绝对值 即可。
 * 最终结果必定在这里面产生。（详见注释）
 *
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
    private int ans = Integer.MIN_VALUE;

    public int maxAncestorDiff(TreeNode root) {
        dfs(root);
        return ans;
    }

    // 返回当前节点子树中的 最小值(index=0) 和 最大值(index=1)
    private int[] dfs(TreeNode root) {
        // 递归的终止条件
        if (root == null) {
            return null;
        }

        int min = root.val, max = root.val;
        if (root.left != null) {
            // 递归求解左子树
            int[] left = dfs(root.left);
            // 更新结果
            ans = Math.max(ans, Math.max(Math.abs(root.val - left[0]), Math.abs(root.val - left[1])));
            // 根据递归求解的结果，更新当前子树的 max, min 值
            max = Math.max(max, left[0]);
            min = Math.min(min, left[1]);
        }
        if (root.right != null) {
            // 递归求解右子树
            int[] right = dfs(root.right);
            // 更新结果
            ans = Math.max(ans, Math.max(Math.abs(root.val - right[0]), Math.abs(root.val - right[1])));
            // 根据递归求解的结果，更新当前子树的 max, min 值
            max = Math.max(max, right[0]);
            min = Math.min(min, right[1]);
        }

        return new int[]{max, min};
    }
}


/**
 * Approach 2: Divide and Conquer (Top-Down DFS)
 * Approach 1 中 Bottom-Up 的思路实际上是有点逆着题目意思来的。
 * 这道题目实际上更加希望我们使用 Top-Down 的思路解决问题（问的是 祖先节点们 与 对应的子节点）
 * 因此，我们可以 由上至下 计算当前节点所对应的 祖先节点们 当前的 最小值 和 最大值。
 * 然后通过更新维持 max, min 的值，求得最终的结果。
 * 同时介于思路的转换，我们可以通过传参的方法，大幅简化本体的代码。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 */
class Solution {
    public int maxAncestorDiff(TreeNode root) {
        // 题目已经说明树非空，不需要进行判断~
        return dfs(root, root.val, root.val);
    }

    // 返回当前子树中，节点和对应祖先节点之间最大的差值
    private int dfs(TreeNode root, int min, int max) {
        if (root == null) {
            return max - min;
        }

        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        return Math.max(dfs(root.left, min, max), dfs(root.right, min, max));
    }
}