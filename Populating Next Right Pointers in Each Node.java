Problem about Tree.
Firstly, I think about levelOrder-Traversal, so I need use a queue to solve this problem like Binary Tree Level Order Traversal,
but it's too complicatied for this question when I start to write this code.
There no need for me to use queue for this question apparently.
And when I draw the tree, I find that it question is very easy to solve, due to the relationship between each node.
So, if you want to solve this problem quickly and easily, plz draw it!
You will find we only need a node levelStart to track the head position of each level 
and a node curr to track the current node when we traverse the level.

/*
Given a binary tree
    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.

Note:
You may only use constant extra space.
You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
For example,
Given the following perfect binary tree,
         1
       /  \
      2    3
     / \  / \
    4  5  6  7
After calling your function, the tree should look like:
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \  / \
    4->5->6->7 -> NULL
*/

 ```
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        
        TreeLinkNode levelStart = root;
        while (levelStart != null) {
            TreeLinkNode curr = levelStart;
            while (curr != null) {
                if (curr.left != null) {
                    curr.left.next = curr.right;
                }
                if (curr.right != null && curr.next != null) {
                    curr.right.next = curr.next.left;
                }
                // move the current node to the next position
                curr = curr.next;
            }
            // move the levelStart node the next level's head position
            levelStart = levelStart.left;
        }  
    }
}

```
