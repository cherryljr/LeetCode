/*
Given a binary tree with the following rules:
    root.val == 0
    If treeNode.val == x and treeNode.left != null, then treeNode.left.val == 2 * x + 1
    If treeNode.val == x and treeNode.right != null, then treeNode.right.val == 2 * x + 2
Now the binary tree is contaminated, which means all treeNode.val have been changed to -1.

You need to first recover the binary tree and then implement the FindElements class:
FindElements(TreeNode* root) Initializes the object with a contamined binary tree, you need to recover it first.
bool find(int target) Return if the target value exists in the recovered binary tree.

Example 1:
Input
["FindElements","find","find"]
[[[-1,null,-1]],[1],[2]]
Output
[null,false,true]
Explanation
FindElements findElements = new FindElements([-1,null,-1]); 
findElements.find(1); // return False 
findElements.find(2); // return True 

Example 2:
Input
["FindElements","find","find","find"]
[[[-1,-1,-1,-1,-1]],[1],[3],[5]]
Output
[null,true,true,false]
Explanation
FindElements findElements = new FindElements([-1,-1,-1,-1,-1]);
findElements.find(1); // return True
findElements.find(3); // return True
findElements.find(5); // return False

Example 3:
Input
["FindElements","find","find","find","find"]
[[[-1,null,-1,-1,null,-1]],[2],[3],[4],[5]]
Output
[null,true,false,false,true]
Explanation
FindElements findElements = new FindElements([-1,null,-1,-1,null,-1]);
findElements.find(2); // return True
findElements.find(3); // return False
findElements.find(4); // return False
findElements.find(5); // return True

Constraints:
    1. TreeNode.val == -1
    2. The height of the binary tree is less than or equal to 20
    3. The total number of nodes is between [1, 10^4]
    4. Total calls of find() is between [1, 10^4]
    5. 0 <= target <= 10^6
*/

/**
 * Approach: Divide and Conquer + Complete Binary Tree
 * Time Complexity: O(n) + O(k * logn) k is the query times
 * Space Complexity: O(1)
 *
 * Reference: av76080725 (05:00)
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
class FindElements {
    
    private TreeNode root = null;

    public FindElements(TreeNode root) {
        dfs(root, 0);
        this.root = root;
    }
    
    private void dfs(TreeNode root, int val) {
        if (root == null) {
            return;
        }
        dfs(root.left, 2 * val + 1);
        dfs(root.right, 2 * val + 2);
    }
    
    public boolean find(int target) {
        target++;
        StringBuilder sb = new StringBuilder();
        while (target > 0) {
            sb.append(target & 1);
            target >>= 1;
        }
        
        return find(sb.toString());
    }
    
    private boolean find(String str) {
        TreeNode curr = root;
        for (int i = str.length() - 2; i >= 0; i--) {
            if (curr == null) break;
            if (str.charAt(i) == '0') {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return curr != null;
    }
}

/**
 * Your FindElements object will be instantiated and called as such:
 * FindElements obj = new FindElements(root);
 * boolean param_1 = obj.find(target);
 */