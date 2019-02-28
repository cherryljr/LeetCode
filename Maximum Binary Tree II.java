/*
We are given the root node of a maximum tree:
a tree where every node has a value greater than any other value in its subtree.

Just as in the previous problem, the given tree was constructed from an list A (root = Construct(A))
recursively with the following Construct(A) routine:
    1. If A is empty, return null.
    2. Otherwise, let A[i] be the largest element of A.  Create a root node with value A[i].
    3. The left child of root will be Construct([A[0], A[1], ..., A[i-1]])
    4. The right child of root will be Construct([A[i+1], A[i+2], ..., A[A.length - 1]])
    5. Return root.
Note that we were not given A directly, only a root node root = Construct(A).

Suppose B is a copy of A with the value val appended to it.  It is guaranteed that B has unique values.
Return Construct(B).

Example 1:
https://leetcode.com/problems/maximum-binary-tree-ii/
Input: root = [4,1,3,null,null,2], val = 5
Output: [5,4,null,1,3,null,null,2]
Explanation: A = [1,4,2,3], B = [1,4,2,3,5]

Example 2:
Input: root = [5,2,4,null,1], val = 3
Output: [5,2,4,null,1,null,3]
Explanation: A = [2,1,5,4], B = [2,1,5,4,3]

Example 3:
Input: root = [5,2,3,null,1], val = 4
Output: [5,2,4,null,1,3]
Explanation: A = [2,1,5,3], B = [2,1,5,3,4]

Note:
1 <= B.length <= 100
 */

/**
 * Approach: Recursion
 * 这道题目算是 Maximum Binary Tree 的一个扩展吧。
 * 但是其实只是接用了一下它的定义，想要做一个 insert 操作罢了。
 * 整体上并没有什么难度，质量上明显不如 Maximum Binary Tree。
 *
 * 解决方法用递归即可解决。因为新增的 val 必定在数的末尾。
 * 所以根据 Max Tree 的定义，如果 val 比 root.val 小的话，
 * 它必定在 root 的右子树中。这样我们就可以对其进行递归求解了。
 * 过程看代码和注释即可。比较简单，就不多说了。
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(1)
 *
 * Maximum Binary Tree
 * 	https://github.com/cherryljr/LeetCode/blob/master/Maximum%20Binary%20Tree.java
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
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val > root.val) {
            // 如果 val 的值大于 root.val
            TreeNode insertNode = new TreeNode(val);
            // 将当前的 root 放在 insertNode 的左子树中
            insertNode.left = root;
            // 返回 insertNode（因为此时的 insertNode是上一次递归节点的右孩子）
            return insertNode;
        } else {
            // 如果 root.val 更大，递归求解右子树即可
            root.right = insertIntoMaxTree(root.right, val);
        }
        return root;
    }
}
