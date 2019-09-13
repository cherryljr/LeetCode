/*
You are given a perfect binary tree where all leaves are on the same level,
and every parent has two children. The binary tree has the following definition:
    struct Node {
      int val;
      Node *left;
      Node *right;
      Node *next;
    }
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.

Example:
Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},
"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},
"next":null,"right":{"$id":"5","left":{"$id":"6","left":null,"next":null,"right":null,"val":6},
"next":null,"right":{"$id":"7","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}
Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,
"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,
"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,"val":6},"right":null,"val":5},
"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,"right":{"$ref":"6"},"val":3},
"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}

Explanation:
https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
Given the above perfect binary tree (Figure A),
your function should populate each next pointer to point to its next right node, just like in Figure B.

Note:
    1. You may only use constant extra space.
    2. Recursive approach is fine, implicit stack space does not count as extra space for this problem.
 */

/**
 * Approach: Tree
 * Firstly, I think about levelOrder-Traversal, so I need use a queue to solve this problem like Binary Tree Level Order Traversal,
 * but it's too complicated for this question when I start to write this code.
 * There no need for me to use queue for this question apparently.
 * And when I draw the tree, I find that it question is very easy to solve, due to the relationship between each node.
 * So, if you want to solve this problem quickly and easily, plz draw it!
 * You will find we only need a node levelStart to track the head position of each level
 * and a node curr to track the current node when we traverse the level.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val,Node _left,Node _right,Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        Node levelStart = root;
        while (levelStart != null) {
            Node curr = levelStart;
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
        return root;
    }
}