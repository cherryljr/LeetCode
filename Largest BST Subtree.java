采用bottom-up的方法，简历新的class, 用来存储
    当前节点为root的subtree是否是BST (用size的符号表示，若为负号则表示不是BST)
    若是，最小val 和最大val.
    size是当前subtree的大小。
然后从下到上更新，若是中间过程中size 比 res大，就更新res.

Time Complexity: O(n). 每个点不会访问超过两遍. 
Space: O(logn). Recursion stack’s size.

/*
Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), 
where largest means subtree with largest number of nodes in it.

Note:
A subtree must include all of its descendants.
Here's an example:
    10
    / \
   5  15
  / \   \ 
 1   8   7
The Largest BST Subtree in this case is the highlighted one. 
The return value is the subtree's size, which is 3.

Hint:
You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, 
which will result in O(nlogn) time complexity.

Follow up:
Can you figure out ways to solve it with O(n) time complexity?
*/

/*
    in brute-force solution, we get information in a top-down manner.
    for O(n) solution, we do it in bottom-up manner, meaning we collect information during backtracking. 
*/
public class Solution {
    // (size, min, max) -- size of current tree, range of current tree [min, max]
    class Result {
        int size;
        int min;
        int max;
        public Result(int size, int min, int max) {
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }
    
    public int largestBSTSubtree(TreeNode root) {
        Result rst = BSTSubstree(root);
        return Math.abs(rst.size);
    }
    
    private Result BSTSubstree(TreeNode root) {
        if (root == null) {
            return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        
        Result left = BSTSubstree(root.left);
        Result right = BSTSubstree(root.right);
        
        //The sign of size field indicates whether the returning node is root of a BST or not.
        if (left.size < 0 || right.size < 0 || root.val < left.max || root.val > right.min) {
            return new Result(Math.max(Math.abs(left.size), Math.abs(right.size)) * -1, 0, 0);
        } else {
            return new Result(left.size + right.size + 1, Math.min(root.val, left.min), Math.max(root.val, right.max));
        }
    }
}
