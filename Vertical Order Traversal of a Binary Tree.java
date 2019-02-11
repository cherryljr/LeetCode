/*
Given a binary tree, return the vertical order traversal of its nodes values.
For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1) and (X+1, Y-1).

Running a vertical line from X = -infinity to X = +infinity, whenever the vertical line touches some nodes,
we report the values of the nodes in order from top to bottom (decreasing Y coordinates).

If two nodes have the same position, then the value of the node that is reported first is the value that is smaller.
Return an list of non-empty reports in order of X coordinate.
Every report will have a list of values of nodes.

Example 1:
Input: [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation:
Without loss of generality, we can assume the root node is at position (0, 0):
Then, the node with value 9 occurs at position (-1, -1);
The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
The node with value 20 occurs at position (1, -1);
The node with value 7 occurs at position (2, -2).

Example 2:
Input: [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
The node with value 5 and the node with value 6 have the same position according to the given scheme.
However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.

Note:
    1. The tree will have between 1 and 1000 nodes.
    2. Each node's value will be between 0 and 1000.
 */

/**
 * Approach: PreOrder Traversal + TreeMap + TreeSet
 * 这道题目纯粹就是对 树的遍历 和 TreeMap数据结构 的考察。
 * 根据题目要求设计需要的数据结构，然后利用 PreOrder Traversal 在 tree 中存储下每个点的位置和值。
 * 最后根据要求输出结果即可。
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>> tree = new TreeMap<>();
        dfs(root, 0, 0, tree);

        List<List<Integer>> ans = new ArrayList<>();
        for (TreeMap<Integer, TreeSet<Integer>> map : tree.values()) {
            ans.add(new ArrayList<>());
            for (TreeSet<Integer> set : map.values()) {
                for (Integer value : set) {
                    ans.get(ans.size() - 1).add(value);
                }
            }
        }

        return ans;
    }


    private void dfs(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>> tree) {
        if (root == null) {
            return;
        }

        tree.computeIfAbsent(x, key -> new TreeMap<Integer, TreeSet<Integer>>());
        tree.get(x).computeIfAbsent(y, key -> new TreeSet<Integer>());
        tree.get(x).get(y).add(root.val);
        dfs(root.left, x - 1, y + 1, tree);
        dfs(root.right, x + 1, y + 1, tree);
    }
}