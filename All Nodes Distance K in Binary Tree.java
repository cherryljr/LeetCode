/*
We are given a binary tree (with root node root), a target node, and an integer value `K`.
Return a list of the values of all nodes that have a distance K from the target node.
The answer can be returned in any order.

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
Output: [7,4,1]
Explanation:
The nodes that are a distance 2 from the target node (with value 5)
have values 7, 4, and 1.
picture: https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/

Note that the inputs "root" and "target" are actually TreeNodes.
The descriptions of the inputs above are just serializations of these objects.

Note:
The given tree is non-empty.
Each node in the tree has unique values 0 <= node.val <= 500.
The target node is a node in the tree.
0 <= K <= 1000.
 */

/**
 * Approach: Build Tree Graph + BFS
 * 想要求到树某一个节点 distance 为 K 的所有点。
 * 很容易想到就是使用 BFS。根据要求，我们在遍历的时候应该
 * 将整棵树看作是一个 无向图 来进行比遍历。
 * 因此首先我们需要构建出这张图才行。
 *
 * 做法比较简单，使用 Map<Integer, List<Integer>> 即可。
 * 因为树中每一个节点的值都是唯一的，所以我们可以直接以节点值作为每个节点的标记（代表）
 * 来构建这张无向图。构造的过程其实就是一个递归遍历的过程。
 * 具体注意事项可以参考注释。
 * 总的来说就是：把一棵树转换成一个 无向图 然后进行 Graph Serach.属于比较常规的做法
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 类似的问题：
 * Closest Leaf in a Binary Tree：
 *  
 *
 * 参考资料（存在第二种解法）：
 *  http://zxi.mytechroad.com/blog/tree/leetcode-863-all-nodes-distance-k-in-binary-tree/
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
    Map<Integer, List<Integer>> graph = new HashMap<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        // 采用这个方法可以在不产生多余边的情况下，构建出树图
        buildGraph(null, root);

        // BFS
        List<Integer> rst = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        int distance = 0;
        queue.offer(target.val);
        visited.add(target.val);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                if (distance == K) {
                    rst.add(curr);
                }
                if (graph.containsKey(curr)) {
                    for (int neigh : graph.get(curr)) {
                        if (visited.contains(neigh)) {
                            continue;
                        }
                        queue.offer(neigh);
                        visited.add(neigh);
                    }
                }
            }
            distance++;
        }

        return rst;
    }

    private void buildGraph(TreeNode parent, TreeNode child) {
        if (parent != null) {
            // 因为是 无向图 所以边的关系是双向的
            graph.computeIfAbsent(parent.val, x -> new LinkedList<>()).add(child.val);
            graph.computeIfAbsent(child.val, x -> new LinkedList<>()).add(parent.val);
        }
        if (child.left != null) {
            buildGraph(child, child.left);
        }
        if (child.right != null) {
            buildGraph(child, child.right);
        }
    }
}

