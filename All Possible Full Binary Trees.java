/*
A full binary tree is a binary tree where each node has exactly 0 or 2 children.
Return a list of all possible full binary trees with N nodes.
Each element of the answer is the root node of one possible tree.
Each node of each tree in the answer must have node.val = 0.

You may return the final list of trees in any order.

Example 1:
Input: 7
Output:
    [[0,0,0,null,null,0,0,null,null,0,0],
    [0,0,0,null,null,0,0,0,0],
    [0,0,0,0,0,0,0],
    [0,0,0,0,0,null,null,null,null,0,0],
    [0,0,0,0,0,null,null,0,0]]
Explanation:
    https://leetcode.com/problems/all-possible-full-binary-trees/description/

Note:
1 <= N <= 20
 */

/**
 * Approach 1: Recursion
 * 通过题目的数据量可以推测出本题可以直接使用暴力递归的方法来做。（树的题目通常都是通过递归做的）
 * 枚举所有的左子树和右子树的可能，然后做笛卡儿积即可。
 *
 * 时间复杂度：O(n!)
 * 这是因为实现看到过关于树可能性的计算，实质上就是一个 卡特兰数。
 * 因此时间复杂度为 阶乘 级别。
 *
 * 类似的问题：
 * Get All Possible Binary Trees:
 *  https://github.com/cherryljr/NowCoder/blob/master/Get%20All%20Possible%20Binary%20Trees.java
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
    public List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode> rst = new ArrayList<>();
        if (N == 1) {
            rst.add(new TreeNode(0));
            return rst;
        }

        N -= 1; // 算上当前节点
        // 只有奇数才有可能组成 FBT,因此这里直接跳过偶数。即 i += 2
        for (int i = 1; i < N; i += 2) {
            List<TreeNode> leftSubTree = allPossibleFBT(i);
            List<TreeNode> rightSubTree = allPossibleFBT(N - i);
            for (TreeNode left : leftSubTree) {
                for (TreeNode right : rightSubTree) {
                    TreeNode node = new TreeNode(0);
                    node.left = left;
                    node.right = right;
                    rst.add(node);
                }
            }
        }

        return rst;
    }
}

/**
 * Approach 2: Recursion + Memory Search
 * 在 Approach 1 的基础上添加了 Memory Search 来进行缓存，从而更快计算。
 * 因为当 N 确定的时候，方案都是确定的。
 * 代码基本就加了三行...
 *
 * 参考资料：
 * 	https://www.youtube.com/watch?v=noVVstnQvyY
 */
class Solution {
    private Map<Integer, List<TreeNode>> mem = new HashMap<>();

    public List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode> rst = new ArrayList<>();
        if (mem.containsKey(N)) {
            return mem.get(N);
        }
        if (N == 1) {
            rst.add(new TreeNode(0));
            return rst;
        }

        N -= 1;
        for (int i = 1; i < N; i += 2) {
            List<TreeNode> leftSubTree = allPossibleFBT(i);
            List<TreeNode> rightSubTree = allPossibleFBT(N - i);
            for (TreeNode left : leftSubTree) {
                for (TreeNode right : rightSubTree) {
                    TreeNode node = new TreeNode(0);
                    node.left = left;
                    node.right = right;
                    rst.add(node);
                }
            }
        }
        mem.put(N, rst);

        return rst;
    }
}