/*
Given a linked list, swap every two adjacent nodes and return its head.
You may not modify the values in the list's nodes, only nodes itself may be changed.

Example:
Given 1->2->3->4, you should return the list as 2->1->4->3.
 */

/**
 * Approach: Reverse LinkedList (Dummy Node and Previous Node)
 * 涉及到链表节点交换的操作，如果不熟悉的话，最好画一下图。
 * 不仅可以帮助理清楚思路，在代码实现上也可以避免错误。
 * 同时因为本题的链表经过处理之后，headNode 就无法再确定了，对此可以使用 Dummy Node 来解决。
 * 这个技巧在 Reverse LinkedList 上是特别常见的。
 *
 * 剩下的其他方面可以参见代码注释即可。
 * 在实现思路上还是相当明确的，属于考察链表的操作能力和代码能力的题目。
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
    public ListNode swapPairs(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        // 涉及到 reverse 操作，因此首先需要一个 previous node 来做跟踪
        ListNode prev = dummyNode;
        prev.next = head;

        // 为了更加清楚地说明，这里以 0->1->2->3 为例说明 (0为prevNode)
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;       // first = 1
            ListNode second = prev.next.next; // second = 2
            first.next = second.next;         // 1->3
            second.next = first;              // 2->1
            // 至此已经完成了一个 pair 的翻转
            prev.next = second;               // 将 0->2->1-3，作用为将两个 pair 连接起来
            prev = first;                     // 将 prevNode 向后移动, prev = 1
        }

        return dummyNode.next;
    }
}