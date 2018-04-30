/*
Given the root of a tree, you are asked to find the most frequent subtree sum.
The subtree sum of a node is defined as the sum of all the node values
formed by the subtree rooted at that node (including the node itself).
So what is the most frequent subtree sum value?
If there is a tie, return all the values with the highest frequency in any order.

Examples 1
Input:
  5
 /  \
2   -3
return [2, -3, 4], since all the values happen only once, return all of them in any order.

Examples 2
Input:

  5
 /  \
2   -5
return [2], since 2 happens twice, however -5 only occur once.

Note: You may assume the sum of values in any subtree is in the range of 32-bit signed integer.
 */

/**
 * Approach: Divide and Conquer
 * 递归求每个结点所有子节点之和然后加上其自身的值就是该节点的 sum 值了。
 * 在计算过程中使用一张 map 来存储各个 sum 值以及对应的出现次数。
 * 然后遍历求出现次数最多的值即可。
 *
 * 这个递归过程其实也可以利用 后序遍历 来实现。（左右中）
 * 具体实现可以参考：
 * https://leetcode.com/problems/most-frequent-subtree-sum/discuss/98664/Verbose-Java-solution-postOrder-traverse-HashMap-(18ms)
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
    Map<Integer, Integer> map = new HashMap<>();
    int countMax = 0;   // 出现的最多次数

    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }

        calSum(root);   // 递归计算各个节点的 sum 值
        List<Integer> list = new LinkedList<>();
        // 将出现次数为 countMax 的值放到 结果(list) 中
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == countMax) {
                list.add(entry.getKey());
            }
        }

        int[] rst = new int[list.size()];
        for (int i = 0; i < rst.length; i++) {
            rst[i] = list.get(i);
        }
        return rst;
    }

    private int calSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 这里虽然可以直接利用 root.val 的空间来作为 sum 值
        // 但是最好还是不要修改源数据的值哈
        int sum = root.val; 
        if (root.left != null) {
            sum += calSum(root.left);
        }
        if (root.right != null) {
            sum += calSum(root.right);
        }
        int count = map.getOrDefault(sum, 0) + 1;
        map.put(sum, count);
        countMax = Math.max(countMax, count);

        return sum;
    }
}


