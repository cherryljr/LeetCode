/*
You need to construct a string consists of parenthesis and integers from a binary tree with the preorder traversing way.
The null node needs to be represented by empty parenthesis pair "()".
And you need to omit all the empty parenthesis pairs that don't affect the one-to-one mapping relationship
between the string and the original binary tree.

Example 1:
Input: Binary tree: [1,2,3,4]
       1
     /   \
    2     3
   /
  4
Output: "1(2(4))(3)"
Explanation: Originallay it needs to be "1(2(4)())(3()())",
but you need to omit all the unnecessary empty parenthesis pairs.
And it will be "1(2(4))(3)".

Example 2:
Input: Binary tree: [1,2,3,null,4]
       1
     /   \
    2     3
     \
      4
Output: "1(2()(4))(3)"
Explanation: Almost the same as the first example,
except we can't omit the first parenthesis pair to break the one-to-one mapping relationship between the input and the output.
*/

/**
 * Approach: Recursion
 * We need to do the preorder traversal of the given Binary Tree. To do the preorder traversal, we make use of recursion.
 * We print the current node and call the same given function for the left and the right children of the node in that order(if they exist).
 * For every node encountered, the following cases are possible.
 *     Case 1: Both the left child and the right child exist for the current node.
 *     In this case, we need to put the braces () around both the left child's preorder traversal output
 *     and the right child's preorder traversal output.
 *     Case 2: None of the left or the right child exist for the current node.
 *     In this case, as shown in the figure below, considering empty braces for the null left and right children is redundant.
 *     Hence, we need not put braces for any of them.
 *     Case 3: Only the left child exists for the current node. As the figure below shows, putting empty braces for the right child
 *     in this case is unnecessary while considering the preorder traversal.
 *     This is because the right child will always come after the left child in the preorder traversal.
 *     Thus, omitting the empty braces for the right child also leads to same mapping between the string and the binary tree.
 *     Case 4: Only the right child exists for the current node.
 *     In this case, we need to consider the empty braces for the left child.
 *     This is because, during the preorder traversal, the left child needs to be considered first.
 *     Thus, to indicate that the child following the current node is a right child we need to put a pair of empty braces for the left child.
 * Complexity Analysis
 *     Time complexity : O(n). The preorder traversal is done over the nn nodes of the given Binary Tree.
 *     Space complexity : O(n). The depth of the recursion tree can go upto nn in case of a skewed tree.
 *
 * Another Approach which using stack is here: https://leetcode.com/problems/construct-string-from-binary-tree/solution/
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
    public String tree2str(TreeNode t) {
        StringBuilder ans = new StringBuilder();
        dfs(ans, t);
        return ans.toString();
    }

    private void dfs(StringBuilder ans, TreeNode t) {
        if (t == null) {
            return;
        }
        ans.append(t.val);
        if (t.right != null) {
            // if the right child isn't empty, we still need to append '()' although the left child is empty
            dfs(ans.append('('), t.left);
            ans.append(')');
            dfs(ans.append('('), t.right);
            ans.append(')');
        } else if (t.left != null) {
            dfs(ans.append('('), t.left);
            ans.append(')');
        }
    }
}
