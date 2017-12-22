/*
Given a list, rotate the list to the right by k places, where k is non-negative.

Example:

Given 1->2->3->4->5->NULL and k = 2,

return 4->5->1->2->3->NULL.
*/

/**
 * Approach 1: Using the tricks same as Rotate Array
 * 三步翻转法
 * 但是 Java 中因为没有指针，链表实现该方法较为繁琐。
 * 这里有关于旋转字符串的详细介绍：
 * https://github.com/julycoding/The-Art-Of-Programming-By-July/blob/master/ebook/zh/01.01.md
 */

/**
 * Approach 2: Split the list and move it to the end.
 * Since n may be a large number compared to the length of list. So we need to know the length of linked list.
 * Let's  start with an example.
 *   Given [0,1,2], rotate 1 steps to the right -> [2,0,1].
 *   Given [0,1,2], rotate 2 steps to the right -> [1,2,0].
 *   Given [0,1,2], rotate 3 steps to the right -> [0,1,2].
 *   Given [0,1,2], rotate 4 steps to the right -> [2,0,1].
 * From above, we can find that:
 * no matter how big K, the result is always the same as rotating K % n steps to the right.
 *   Ex: {1,2,3} k=2, Move the list after the 1st node to the front
 *   Ex: {1,2,3} k=5, In this case Move the list after (3- 5%3 = 1)st node to the front.
 *
 * So the code has three parts.
 *   1. Get the length
 *   2. Move to the (len - k%len)th node
 *   3. Do the rotation
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
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;
        int len;
        // Get the total length of the list
        for (len = 0; fast.next != null; len++) {
            fast = fast.next;
        }
        // Get the (len - k%len)th node
        for (int j = len - (k % len); j > 0; j--) {
            slow = slow.next;
        }

        // Rotate the list
        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;

        return dummy.next;
    }
}