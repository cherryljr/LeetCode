Algorithm:
    The Solution is simply easy. You can get it from the comment in code.
Complexity Analysis:
    We halve the tree in every recursive step, and we have O(log(n)) steps. 
    Finding a height costs O(log(n)). 
    So overall the time complexity is : O(log(n)^2).

/*
Given a complete binary tree, count the number of nodes.

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, 
and all nodes in the last level are as far left as possible. 
It can have between 1 and 2h nodes inclusive at the last level h.
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
    public int countNodes(TreeNode root) {
        int leftDepth = leftDepth(root);
        int rightDpeth = rightDepth(root);
        
        if (leftDepth == rightDpeth) {  
        // if it's a full binary tree, then the result is 2^h - 1
            return (1 << leftDepth) - 1;
        } else {
        // if it isn't full binary tree, we call it recursively
        // left-subtree and right-subtree and plus 1.
            return countNodes(root.left) + countNodes(root.right) + 1;
        }
    }
    
    // get the left-subtree's height (including root node)
    private int leftDepth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.left;
        }
        return height;
    }
    
    // get the right-subtree's height (including root node)
    private int rightDepth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.right;
        }
        return height;
    }
}