/*
Given the root of a binary tree, each node in the tree has a distinct value.
After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
Return the roots of the trees in the remaining forest.  You may return the result in any order.

https://leetcode.com/problems/delete-nodes-and-return-forest/
Example 1:
Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
Output: [[1,2,null,4],[6],[7]]

Constraints:
    1. The number of nodes in the given tree is at most 1000.
    2. Each node has a distinct value between 1 and 1000.
    3. to_delete.length <= 1000
    4. to_delete contains distinct values between 1 and 1000.
 */

/**
 * Approach 1: Divide and Conquer
 * Tree类型的问题，第一反应就是用递归去实现 Divide and Conquer。
 * 思路很清晰：
 *  1. 因为是递归，首先判断递归的结束条件是什么。对于树的结点，通常都是节点为空，或者是叶子节点（两种都可以，写法有一点点差别）
 *  通常情况，没有特殊情况要求，个人偏向使用 null 作为递归的结束条件。
 *  2. 根据题意，判断什么时候需要将节点添加到结果中。
 *  这里可以知道只有当 节点不在删除节点的集合中 且 当前节点是该子树的root时 我们才需要将其加入到结果中。
 * 因此我们还需要一个变量 isNewRoot 来标记是否需要将当前节点加入到结果集中。
 * 针对 是否存在与删除节点集合 和 是否为newRoot，我们可以得出以下处理方案：
 *  1. 不在删除节点的集合中 且 是一个newRoot，则将其加入到结果集中，
 *  同时递归求解左右孩子，因为当前节点是一个root，所以其左右孩子不再是一个 newRoot。
 *  2. 在删除节点的集合中，此时它必定不是一个newRoot，无法将其加入到结果集中。
 *  但是对应的其左右孩子将有机会成为一个 newRoot，最后该节点因为在删除集合中，所以最后 return null。
 *  3. 不在删除节点的集合中 且 不是一个newRoot，该情况的处理与情况1仅仅差别在是否将其加入到结果集中而已。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
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
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> deleteSet = new HashSet<>();
        for (int val : to_delete) {
            deleteSet.add(val);
        }
        List<TreeNode> ans = new ArrayList<>();
        dfs(root, ans, deleteSet, true);
        return ans;
    }

    private TreeNode dfs(TreeNode node, List<TreeNode> ans, Set<Integer> deleteSet, boolean isNewRoot) {
        if (node == null) {
            return null;
        }
        if (!deleteSet.contains(node.val)) {
            if (isNewRoot) {
                ans.add(node);
            }
            node.left = dfs(node.left, ans, deleteSet, false);
            node.right = dfs(node.right, ans, deleteSet, false);
            return node;
        } else {
            dfs(node.left, ans, deleteSet, true);
            dfs(node.right, ans, deleteSet, true);
            return null;
        }
    }
}

/**
 * Approach 2: Divide and Conquer
 * 主体思路是相同的，只不过通过 Approach 1 的分析我们可以知道：
 * 除去一开始的根节点，只有 当前node在deleteSet中 的时候，其左右孩子才有机会成为一个 newRoot。
 * 因此，我们可以对 Approach 1 中的代码进行一些简化。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> deleteSet = new HashSet<>();
        for (int val : to_delete) {
            deleteSet.add(val);
        }
        List<TreeNode> ans = new ArrayList<>();
        dfs(root, ans, deleteSet, true);
        return ans;
    }

    private TreeNode dfs(TreeNode node, List<TreeNode> ans, Set<Integer> deleteSet, boolean isNewRoot) {
        if (node == null) {
            return null;
        }
        boolean inDeleteSet = deleteSet.contains(node.val);
        // 只有当前节点不在删除集合中且为newRoot的时候，才会将当前节点加入到结果中
        if (!inDeleteSet && isNewRoot) {
            ans.add(node);
        }
        // 如果当前节点在删除结果集中，则其左右孩子有可能成为 newRoot。
        node.left = dfs(node.left, ans, deleteSet, inDeleteSet);
        node.right = dfs(node.right, ans, deleteSet, inDeleteSet);
        return inDeleteSet ? null : node;
    }
}