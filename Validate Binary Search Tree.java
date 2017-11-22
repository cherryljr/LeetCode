该题应用到了这么一个知识点：
    搜索二叉树按照中序遍历得到的序列，一定是一个递增序列。
因此我们对该 BST 进行一次中序遍历若，然后判断得到的序列是否按照从小到大排列即可。
这也就是 Approach 1. 使用的是 递归遍历的方法。

但是本题可以有更好的解法。 -- Approach 2 (Using Stack)
因为我们需要将当前的遍历节点与它的前一个节点的值进行比较，所以我们如果使用非递归的方法来实现中序遍历的话，
可以在遍历的同时进行两个节点的比较。
因此我们使用栈来实现非递归的BST的中序遍历，同时比较 pre 节点和 curr 节点的值。
若 pre.val >= curr.val, 则说明该树不是一颗 BST。

关于中序遍历更多的应用：（基本与 顺序，大小，后继节点 相关）
https://discuss.leetcode.com/topic/46016/learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-java-solution

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

// Approach 1: Using Recursion
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


// Approach 2: Using Stack
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