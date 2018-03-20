/*
Given a Binary Search Tree (BST), convert it to a Greater Tree such that
every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

Example:
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13
Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
 */

/**
 * Approach 1: Reverse In-Order Traversal (Recursive)
 * 想要把给定的 BST 转换成更大的一棵树，我们可以利用 BST 的特性，
 * 从而轻易找出整棵数 最大的节点，然后除该节点以外，
 * 所有的节点加上 所有比自身要大的节点值之和 即可。
 *
 * 当然我们希望能够通过一次遍历就能够搞定，所以需要按照 降序 的方式来处理节点。
 * 我们都知道 BST 的 中序遍历 就是升序，我们只需要把它改成 降序 即可。
 * 我们需要的遍历顺序为：右 -> 中 -> 左
 * 对于这个需求，我们只需要在原来 递归遍历 的核心模板上面稍作修改即可。
 * 确定好 遍历的顺序 和 处理节点的时机，我们便可以轻松写出任何我们需要的 遍历顺序。
 * 我们发现：
 *  右节点 是 第一个 需要处理的节点，所以排除在 第一次到达节点 的时候处理数据；
 *  （第一次到达节点时处理的话，第一个 节点必定是 中）
 *  又因为 中(root)节点 是 第二个 需要处理，所以排除 第三次到达节点 的时候处理数据；
 *  （第三次到达节点时处理的话，最后一个 节点必定是 中）
 *  因此我们需要在 第二次到达节点 的时候处理数据，又因为 右节点 在 左节点 之前处理，
 *  所以遍历顺序为 先遍历右子树。
 * 有了以上分析，便可以轻易写出代码了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 关于 树的递归遍历的本质分析 与 Morris遍历解析 请参考：
 * https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 * 本题 三种 解法的详细分析（英文版）：
 * https://leetcode.com/problems/convert-bst-to-greater-tree/solution/
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
    private int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        anti_Inorder(root);
        return root;
    }

    private void anti_Inorder(TreeNode node) {
        if (node == null) {
            return;
        }
        anti_Inorder(node.right);
        sum += node.val;
        node.val = sum;
        anti_Inorder(node.left);
    }
}

/**
 * Approach 2: Using Stack (No Recursive)
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public TreeNode convertBST(TreeNode root) {
        int sum = 0;
        TreeNode curr = root;

        Stack<TreeNode> stack = new Stack<>();
        while (curr != null || !stack.isEmpty()) {
            // push all nodes up to (and including) this subtree's maximum on the stack.
            while (curr != null) {
                stack.push(curr);
                curr = curr.right;
            }
            curr = stack.pop();
            sum += curr.val;
            curr.val = sum;
            // all nodes with values between the current and its parent lie in the left subtree.
            curr = curr.left;
        }

        return root;
    }
}

/**
 * Approach 3: Morris Traversal
 * 使用了 Morris Traversal 来优化空间复杂度。
 * 同样我们需要根据需要在 Morris Template 进行些许修改。
 * 经过分析我们可以得出如下结论：
 *  在 第二次 到达节点的时候处理数据；
 *  先遍历 右子树。
 * 因此，我们的修改点为如下：
 *  首先查看 右子树 是否存在，不存在则直接遍历 左子树。
 *  （对于不存在 右子树 的节点，两次遍历是在一起发生的）
 *  然后 如果存在 右子树，我们需要获得 当前节点 右子树 的 最左节点。
 *  然后根据 leftMost 的 左指针 进行相应的判断。
 * 具体实现请看代码，如果对我上面在说什么完全搞不懂的话，可以看下 Morris Traversal Template.
 * 相信你会找到需要的答案。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public TreeNode convertBST(TreeNode root) {
        int sum = 0;
        TreeNode curr = root;

        while (curr != null) {
            if (curr.right == null) {
                /*
                 * If there is no right subtree, then we can visit this node and
                 * continue traversing left.
                 */
                sum += curr.val;
                curr.val = sum;
                curr = curr.left;
            } else {
                /*
                 * If there is a right subtree, then there is at least one node that
                 * has a greater value than the current one. therefore, we must
                 * traverse that subtree first.
                 */
                TreeNode leftMost = getLeftMost(curr);
                if (leftMost.left == null) {
                    /*
                     * If the left subtree is null, then we have never been here before.
                     */
                    leftMost.left = curr;
                    curr = curr.right;
                } else {
                    /*
                     * If there is a left subtree, it is a link that we created on a
                     * previous pass, so we should unlink it and visit this node.
                     */
                    leftMost.left = null;
                    sum += curr.val;
                    curr.val = sum;
                    curr = curr.left;
                }
            }
        }

        return root;
    }

    private TreeNode getLeftMost(TreeNode curr) {
        TreeNode node = curr.right;
        while (node.left != null && node.left != curr) {
            node = node.left;
        }
        return node;
    }
}
