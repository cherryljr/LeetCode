/*
Given a binary tree, determine if it is a valid binary search tree (BST).
Assume a BST is defined as follows:
    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.
    
Example 1:
    2
   / \
  1   3
Binary tree [2,1,3], return true.

Example 2:
    1
   / \
  2   3
Binary tree [1,2,3], return false.
*/

/**
 * 该题应用到了这么一个知识点：
 * 搜索二叉树按照 中序遍历 得到的序列，一定是一个递增序列。
 * 因此我们对该 BST 进行一次中序遍历若，然后判断得到的序列是否按照从小到大排列即可。
 * 这也就是 Approach 1. 使用的是 递归遍历的方法。
 *
 * 但是本题可以有更好的解法。 -- Approach 2 (Using Stack)
 * 因为我们需要将当前的遍历节点与它的前一个节点的值进行比较，所以我们如果使用非递归的方法来实现中序遍历的话，
 * 可以在遍历的同时进行两个节点的比较。
 * 因此我们使用栈来实现非递归的BST的中序遍历，同时比较 pre 节点和 curr 节点的值。
 * 若 pre.val >= curr.val, 则说明该树不是一颗 BST。
 * 关于中序遍历更多的应用：（基本与 顺序，大小，后继节点 相关）
 * https://discuss.leetcode.com/topic/46016/learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-java-solution
 *
 * 以上两种方法都将耗费 O(n) 的额外空间，但是通过 Morris遍历 来实现的中序遍历可以只花费 O(1) 的额外空间，
 * 也就是我们的 Approach 3. 时间复杂度仍然为 O(n)
 */

/**
 * Approach 1: Using Recursion 
 * 使用 递归 的方式来实现先序遍历，使用了 O(n) 的额外空间。
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
    List<Integer> inOrderList = new ArrayList<>();

    public boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        inOrder(root);
        return asending(inOrderList);
    }

    private void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        inOrderList.add(root.val);
        inOrder(root.right);
    }

    private boolean asending(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}


/**
 * Approach 2: Using Stack
 * 使用栈来进行中序遍历，使用了 O(n) 的额外空间。
 * 因为通过这种遍历方式，我们可以轻易地获得上一个节点来进行比较。
 * 因此我们更加推荐这种方法
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (pre != null && pre.val >= curr.val) {
                return false;
            }
            pre = curr;
            curr = pre.right;
        }
        return true;
    }
}

/**
 * Approach 3: Morris Traversal
 * 使用了 Morris的中序遍历，解法思想仍然相同。
 * 只是使用了更高级的遍历方式来节约了额外空间。
 *
 * 参考：
 * https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 * https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Inorder%20Traversal.java
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        TreeNode curr = root;
        TreeNode pre = null;

        while (curr != null) {
            if (curr.left == null) {
                if (pre != null && pre.val >= curr.val) {
                    return false;
                }
                pre = curr;
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMost(curr);
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    if (pre != null && pre.val >= curr.val) {
                        return false;
                    }
                    pre = curr;
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }

        return true;
    }

    private TreeNode getRightMost(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }
}

