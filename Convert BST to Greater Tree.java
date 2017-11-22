This question asks us to modify an asymptotically linear number of nodes in a given binary search tree, 
so a very efficient solution will visit each node once. 
The key to such a solution would be a way to visit nodes in descending order, 
keeping a sum of all values that we have already visited and adding that sum to the node's values as we traverse the tree. 
This method for tree traversal is known as a reverse in-order traversal, 
and allows us to guarantee visitation of each node in the desired order. 

Key Point: Reverse in-order traversal, Stack, J. H. Morris Traversal

/*
Given a Binary Search Tree (BST), convert it to a Greater Tree 
such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

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

/*
Approach 1: Using Recursion 
Algorithm
    For the recursive approach, we maintain some minor "global" state so each recursive call can access and modify the current total sum. 
    Essentially, we ensure that the current node exists, recurse on the right subtree, 
    visit the current node by updating its value and the total sum, and finally recurse on the left subtree. 
    If we know that recursing on root.
    right properly updates the right subtree and that recursing on root.
    left properly updates the left subtree, 
    then we are guaranteed to update all nodes with larger values before the current node and all nodes with smaller values after.
Complexity Analysis
    Time complexity : O(n)
    A binary tree has no cycles by definition, so convertBST gets called on each node no more than once. 
    Other than the recursive calls, convertBST does a constant amount of work, 
    so a linear number of calls to convertBST will run in linear time.
    Space complexity : O(n)
    Using the prior assertion that convertBST is called a linear number of times, 
    we can also show that the entire algorithm has linear space complexity. 
    Consider the worst case, a tree with only right (or only left) subtrees. 
    The call stack will grow until the end of the longest path is reached, which in this case includes all n nodes.
*/

// Code Below
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
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }
}

/*
Approach 2: Using Stack
    The main diea is tha same as Approach 1. But here we use a stack to traverse the tree.
    The oreder is reverse inorder traversal.
    So we go to the right subtree firstly, then the mid, and the left subtree last.
Complexity Analysis
    Time complexity : O(n)
        The key observation is that each node is pushed onto and poped out the stack exactly only once. 
    Space complexity : O(n)
    Use a stack whose size is O(n) to contian the node in the tree.
*/

// Code Below
class Solution {
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        int sum = 0;
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

/*
Approach 3： Reverse Morris In-order Traversal 
	There is a clever way to perform an in-order traversal using only linear time and constant space, 
	first described by J. H. Morris in his 1979 paper "Traversing Binary Trees Simply and Cheaply". 
	To apply it to this problem, we can simply swap all "left" and "right" references, which will reverse the traversal.
Algorithm
	We initialize node - current, which points to the root. Then, until current points to null, we repeat the following. 
	First, consider whether the current node has a right subtree. 
	If it does not have a right subtree, then there is no unvisited node with a greater value, so we can visit this node and move into the left subtree. 
	If it does have a right subtree, then there is at least one unvisited node with a greater value, and thus we must visit first go to the right subtree. 
	To do so, we obtain a reference to the in-order successor (the smallest-value node larger than the current) via our helper function getSuccessor.
	This successor node is the node that must be visited immediately before the current node, so it by definition has a null left pointer (otherwise it would not be the successor). 
	Therefore, when we first find a node's successor, we temporarily link it (via its left pointer) to the node and proceed to the node's right subtree. 
	Then, when we finish visiting the right subtree, the leftmost left pointer in it will be our temporary link that we can use to escape the subtree. 
	After following this link, we have returned to the original node that we previously passed through, but did not visit. 
	This time, when we find that the successor's left pointer loops back to the current node, we know that we have visited the entire right subtree, 
	so we can now erase the temporary link and move into the left subtree.
Pseudocode as follows:
	1. Initialize current as root   
	2. While current is not NULL  
	   If current does not have right child  
		  a) visit current’s data  
		  b) Go to the left, i.e., current = current.left  
	   Else  
		  a) Get the successor node of current node
		  If the successor is null
			a) Make current as left child of successor (the leftmost node in current's right subtree)
			b) Go to this right child, i.e., current = current.right
		  Else
			a)  erase the temporary link and move into the left subtree. i.e., current = current.left  
Complexity Analysis
	Time complexity : O(n)
	Although the Morris traversal does slightly more work than the other approaches, it is only by a constant factor. 
	each edge can only be traversed 3 times: once when we move the node pointer, and once for each of the two calls to getSuccessor.
	Space complexity : O(1)
	Because we only manipulate pointers that already exist, the Morris traversal uses constant space.
More Detial Explianation:
https://leetcode.com/articles/convert-bst-to-greater-tree/#
http://blog.csdn.net/ww32zz/article/details/50451999 in-order traversal - J. H. Morris
*/

// Code Below
class Solution {
    public TreeNode convertBST(TreeNode root) {
        TreeNode curr = root;
        int sum = 0;
        
        while (curr != null) {
            /* 
             * If there is no right subtree, then we can visit this node and
             * continue traversing left.
             */
            if (curr.right == null) {
                sum += curr.val;
                curr.val = sum;
                curr = curr.left;
            }
            /* 
             * If there is a right subtree, then there is at least one node that
             * has a greater value than the current one. therefore, we must
             * traverse that subtree first.
             */
            else {
                // Get the leftMost node
                TreeNode succ = getSuccessor(curr);  
                /* 
                 * If the left subtree is null, then we have never been here before.
                 */
                if (succ.left == null) {
                    succ.left = curr;
                    curr = curr.right;
                }
                /* 
                 * If there is a left subtree, it is a link that we created on a
                 * previous pass, so we should unlink it and visit this node to avoid infinite loops
                 */
                else {
                    succ.left = null;
                    sum += curr.val;
                    curr.val = sum;
                    curr = curr.left;
                }
            }
        }
        
        return root;
    }
    
    // Get the node with the smallest value greater than current node. (leftMost node)
    private TreeNode getSuccessor(TreeNode node) {
        TreeNode succ = node.right;
        while (succ.left != null && succ.left != node) {
            succ = succ.left;
        }
        return succ;
    }
}
