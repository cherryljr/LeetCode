/*
Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:
       1         3     3      2      1
        \       /     /      / \      \
         3     2     1      1   3      2
        /     /       \                 \
       2     1         2                 3
 */

/**
 * Approach: Recursion + Memory Search
 * 这个问题与 All Possible Full Binary Trees 和 Get All Possible Binary Trees 非常类似。
 * 都是通过当前给定的数据，计算（枚举）出所有可能的结果。
 * 方法也无一例外，均是使用 递归+记忆化搜索。
 *
 * 首先枚举所有可能的 root 节点值，则小于 root.val 的数将组成其左子树，大于 root.val 的数将组成其右子树。
 * 同样右子树和左子树都有多种可能，对此我们需要将他们进行一次笛卡尔积，这样就可以得到我们需要的所有结果。
 * 因为当组成树所需数值的范围确定时，其答案也是确定的，所以可以使用一个 Map 把这些结果 cache 起来，以加快计算过程。
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(C(2n, n)/(n+1)) 卡特兰数，实际上就是 Unique Binary Search Trees 的答案
 *
 * Reference:
 *  All Possible Full Binary Trees:
 *  https://github.com/cherryljr/LeetCode/blob/master/All%20Possible%20Full%20Binary%20Trees.java
 *  Get All Possible Binary Trees:
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
    Map<String, List<TreeNode>> mem = new HashMap<>();

    public List<TreeNode> generateTrees(int n) {
        // deal with the corner case
        if (n <= 0) return new ArrayList<>();
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> ans = new ArrayList<>();
        if (start > end) {
            ans.add(null);
            return ans;
        }
        String key = start + "#" + end;
        if (mem.containsKey(key)) {
            return mem.get(key);
        }

        // 枚举所有可能的当前root节点
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftSubtrees = generateTrees(start, i - 1);
            List<TreeNode> rightSubtrees = generateTrees(i + 1, end);
            // Cartesian product
            for (TreeNode leftSubtree : leftSubtrees) {
                for (TreeNode rightSubtree : rightSubtrees) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftSubtree;
                    node.right = rightSubtree;
                    ans.add(node);
                }
            }
        }
        mem.put(key, ans);
        return ans;
    }
}