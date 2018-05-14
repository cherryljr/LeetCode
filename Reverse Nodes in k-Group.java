/*
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
k is a positive integer and is less than or equal to the length of the linked list.
If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:
Given this linked list: 1->2->3->4->5
For k = 2, you should return: 2->1->4->3->5
For k = 3, you should return: 3->2->1->4->5

Note:
Only constant extra memory is allowed.
You may not alter the values in the list's nodes, only nodes itself may be changed.
 */

/**
 * Approach: Recursion
 * 链表题目对于时间复杂度上通常是没啥要求的，因为操作较为简单，
 * 而考点其通常在与 常数级别 的时间复杂度。（别小看这个，这绝对不是一个简单的考点）
 * 这道题目是在 Reverse Linked List 上面的升级版。
 * 如果想要解决好这道问题的话，我们必须要使用 递归 的办法（非递归也能做就是了，但是麻烦）
 * 我们需要将 每k个节点作为一段，然后进行一个逆序操作。（不够k个节点的不进行逆序）
 * 如果仅仅是链表的逆序操作，这还是非常简单的，但是本题的难点就是在于：
 *  如何处理好每个逆序段之间的拼接问题。
 * 而这里使用 递归 可以非常漂亮地帮我们解决这个问题。
 *  reverseKGroup() 函数的作用就是将 curr 节点之后的 k 个节点进行逆序，并返回逆序段的头节点。
 * 因此通过递归调用它，我们可以对整个链表实现按 k 大小进行分段的逆序操作。
 * 递归调用，所以需要从后完全分析哦。
 * 过程比较抽象，所以链表的题目都建议画图进行分析，特别这道题目还涉及到了 递归。
 * 所以 画图 是一个非常好的选择。
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;
        // find the k+1 node
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }

        // if k+1 node exists
        if (count == k) {
            // reverse list with k+1 node as head
            curr = reverseKGroup(curr, k);
            // head - head-pointer to direct part, (like curr node in Reverse Linked List)
            // curr - head-pointer to reversed part (like pre node in Reverse Linked List)
            while (count-- > 0) {      // reverse current k-group
                ListNode temp = head.next;  // temp - next head in direct part
                head.next = curr; // pre appending "direct" head to the reversed list
                curr = head; // move head of reversed part to a new node
                head = temp; // move "direct" head to the next node in direct part
            }
            head = curr; // make head point to curr (important)
        }
        return head;
    }
}
