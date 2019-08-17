/*
Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list's nodes, only nodes itself may be changed.

Example 1:
Given 1->2->3->4, reorder it to 1->4->2->3.

Example 2:
Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
 */

/**
 * Approach: Split, Reverse and Merge LinkedList
 * 这道题目难度不高，但是是一道对链表考察得比较综合的题目，值得一练。
 * 看完题目和范例，基本就能想出怎么做了，答案得最终格式为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 * 因此，解题步骤可以分为如下三步：
 *  1. 首先利用 find middle point in LinkedList 的方法，找出链表得中点；
 *  2. 将后半段的链表进行一次 reverse 操作；
 *  3. 将这两个链表交替 merge 起来
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Reorder%20List.java
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
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode head2 = reverse(slow.next);
        slow.next = null;
        while (head != null && head2 != null) {
            ListNode next1 = head.next;
            ListNode next2 = head2.next;
            head.next = head2;
            head2.next = next1;
            head = next1;
            head2 = next2;
        }
    }

    private ListNode reverse(ListNode node) {
        ListNode prev = null;
        while (node != null) {
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

}