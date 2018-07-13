/*
Description
Given a binary tree where every node has a unique value, and a target key k,
find the value of the nearest leaf node to target k in the tree.If there is more than one answer,
return to the leftmost.

Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree.
Also, a node is called a leaf if it has no children.
1.root represents a binary tree with at least 1 node and at most 1000 nodes.
2.Every node has a unique node.val in range [1, 1000].
3.There exists some node in the given binary tree for which node.val == k.

Example
Example 1:
Given:
root = {1, 3, 2}, k = 1
Diagram of binary tree:
          1
         / \
        3   2
Return: 2 (or 3)
Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.

Example 2:
Given:
root = {1}, k = 1
Return: 1
Explanation: The nearest leaf node is the root node itself.

Example 3:
Given:
root = {1,2,3,4,#,#,#,5,#,6}, k = 2
Diagram of binary tree:
             1
            / \
           2   3
          /
         4
        /
       5
      /
     6
Return: 3
Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node
 */

/**
 * Approach: BFS
 * 这道题目与 All Nodes Distance K in Binary Tree 十分类似。
 * 首先，建立一张树图（因为可以从子节点访问父节点，所以是一张无向图，无权值/权值为1）
 * 建图的方法使用 DFS 即可。
 *
 * 然后就可以利用 BFS 寻找距离 Target 最近的 leaf 节点即可。
 * 因为不需要距离信息，所以没有必要保存 step.
 * 根据 BFS 的特性，第一个遍历到的 leaf 节点，就是我们需要的答案。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * All Nodes Distance K in Binary Tree:
 *  https://github.com/cherryljr/LeetCode/blob/master/All%20Nodes%20Distance%20K%20in%20Binary%20Tree.java
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
    TreeNode startNode = null;

    /**
     * @param root: the root
     * @param k: an integer
     * @return: the value of the nearest leaf node to target k in the tree
     */
    public int findClosestLeaf(TreeNode root, int k) {
        buildTreeGraph(null, root, k);

        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        queue.offer(startNode);
        visited.add(startNode);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left == null && curr.right == null) {
                return curr.val;
            }
            if (graph.containsKey(curr)) {
                for (TreeNode neigh : graph.get(curr)) {
                    if (visited.contains(neigh)) {
                        continue;
                    }
                    queue.offer(neigh);
                    visited.add(neigh);
                }
            }
        }

        return -1;
    }

    private void buildTreeGraph(TreeNode parent, TreeNode child, int k) {
        if (parent != null) {
            graph.computeIfAbsent(parent, x -> new ArrayList<>()).add(child);
            graph.computeIfAbsent(child, x -> new ArrayList<>()).add(parent);
        }
        // 记录起始节点
        if (child.val == k) {
            startNode = child;
        }

        if (child.left != null) {
            buildTreeGraph(child, child.left, k);
        }
        if (child.right != null) {
            buildTreeGraph(child, child.right, k);
        }
    }
}
