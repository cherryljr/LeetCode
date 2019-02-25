/*
Given the root of a binary tree, each node has a value from 0 to 25 representing the letters 'a' to 'z':
a value of 0 represents 'a', a value of 1 represents 'b', and so on.

Find the lexicographically smallest string that starts at a leaf of this tree and ends at the root.

(As a reminder, any shorter prefix of a string is lexicographically smaller: for example,
"ab" is lexicographically smaller than "aba".  A leaf of a node is a node that has no children.)

Example 1:
    Fig:
    https://leetcode.com/problems/smallest-string-starting-from-leaf/
Input: [0,1,2,3,4,3,4]
Output: "dba"

Example 2:
Input: [25,1,3,1,3,0,2]
Output: "adz"

Example 3:
Input: [2,2,1,null,1,0,null,0]
Output: "abc"

Note:
    1. The number of nodes in the given tree will be between 1 and 1000.
    2. Each node in the tree will have a value between 0 and 25.
 */

/**
 * Approach: Divide and Conquer
 * 看到这个问题，第一反应就是 Divide and Conquer。
 * 用 DFS 去做就行了，可以算是模板题目了。
 * 注意点：
 *  当左/右子树，有一个为空时，即对于空串需要特殊处理。
 *  因为字符串的比较中，空串是最小的，所以在比较的时候会导致返回错误的结果。
 * eg.
 *  节点 1 存在左子树0，右子树为空。此时，结果应该是 "ab".
 *  但是如果直接比较就会因为 右子树返回的结果是空串，从而导致返回的结果错误变成了 "b".
 *
 * 时间复杂度：O(n^2) 虽然算法上时间复杂度为 O(n)，但是DFS过程中 字符串拼接的时间复杂度也是 O(n) 的。
 * 空间复杂度：O(n)
 *
 * 类似的问题：
 * Minimum Depth of Binary Tree:
 *  https://github.com/cherryljr/LintCode/blob/master/Minimum%20Depth%20of%20Binary%20Tree.java
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
    public String smallestFromLeaf(TreeNode root) {
        return dfs(root);
    }

    private String dfs(TreeNode root) {
        if (root == null) {
            return "";
        }

        char value = (char)('a' + root.val);
        String left = dfs(root.left);
        String right = dfs(root.right);
        // 如果左子树为空，返回 右子树的结果+value
        if (left.isEmpty()) {
            return right + value;
        }
        // 如果右子树为空，返回 左子树的结果+value
        if (right.isEmpty()) {
            return left + value;
        }
        // 左右子树均不为空，返回值较小的结果 + value
        return left.compareTo(right) < 0 ? left + value : right + value;
    }
}
