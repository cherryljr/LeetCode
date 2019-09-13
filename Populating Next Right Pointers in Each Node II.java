/*
Given a binary tree
    struct Node {
      int val;
      Node *left;
      Node *right;
      Node *next;
    }
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.

Example:
Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,
"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":null,"next":null,"right":{"$id":"6","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":null,
"right":null,"val":7},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"6","left":null,"next":null,"right":{"$ref":"5"},"val":3},
"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"6"},"val":1}

Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.

Note:
    1. You may only use constant extra space.
    2. Recursive approach is fine, implicit stack space does not count as extra space for this problem.
 */

/**
 * Approach: DummyNode
 * 本题为 Populating Next Right Pointers in Each Node 的一个Follow Up，去除了原本条件中 完全二叉树 的限制。
 * 这使得题目的难度有所上升，因为我们无法确定下一层的 start 节点就是 当前节点的leftChild。
 * 遇到 Node 无法确定的情况，第一反应当然就是使用 Dummy Node 了。
 * 之后的大体思路与 Populating Next Right Pointers in Each Node 是相同的，把同一层的节点都连起来，
 * 然后再把 root 指向下一层开始的节点位置，继续遍历。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
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
        // dummyHead.next为下一层第一个节点，curr代表正在遍历的 root 的下一层节点（即root的孩子们），并负责将他们连接起来
        // 因此 curr 需要初始化为 dummyHead。ans只是当初用来存储下根节点的位置用于返回。
        Node dummyHead = new Node(0), curr = dummyHead, ans = root;
        while (root != null) {
            // 如果 root 的孩子不为空，就把这些孩子从左到右连接起来
            if (root.left != null) {
                curr.next = root.left;
                curr = curr.next;
            }
            if (root.right != null) {
                curr.next = root.right;
                curr = curr.next;
            }
            root = root.next;

            // root为空代表当前行遍历到了最后一个节点，因此root需要更新为下一层的第一个节点，即 dummyHead.next
            // 而 dummyHead.next 和 curr 则进行一次初始化
            if (root == null) {
                root = dummyHead.next;
                dummyHead.next = null;
                curr = dummyHead;
            }
        }
        return ans;
    }
}