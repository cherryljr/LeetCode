/*
You are given two non-empty linked lists representing two non-negative integers.
The digits are stored in reverse order and each of their nodes contain a single digit.
Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example:
Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
 */

/**
 * Approach: Add Two Integer LinkedList in Reverse Order
 * Just like how you would sum two numbers on a piece of paper,
 * we begin by summing the least-significant digits, which is the head of l1 and l2.
 * Since each digit is in the range of 0…9, summing two digits may "overflow".
 * For example 5 + 7 = 12.
 * In this case, we set the current digit to 2 and bring over the carry = 1 to the next iteration.
 * carry must be either 0 or 1 because the largest possible sum of two digits (including the carry) is 9 + 1 = 19.
 *
 * The pseudocode is as following:
 *  1. Initialize current node to dummy head of the returning list.
 *  2. Initialize carry to 0.
 *  3. Initialize p1 and p2 to head of l1 and l2 respectively.
 *  4. Loop through lists l1 and l2 until you reach both ends.
 *  5. Set x to node p1's value. If p1 has reached the end of l1, set to 0.
 *  6. Set y to node p2's value. If p2 has reached the end of l2, set to 0.
 *  7. Set sum = x + y + carry.
 *  8. Update carry = sum / 10.
 *  9. Create a new node with the digit value of (sum mod 10) and set it to current node's next, then advance current node to next.
 *  10. Advance both p1 and p2.
 *  11. Check if carry = 1, if so append a new node with digit 11 to the returning list.
 *  12. Return dummy head's next node.
 *
 * Note that we use a dummy head to simplify the code.
 * Without a dummy head, you would have to write extra conditional statements to initialize the head's value.
 * Take extra caution of the following cases:
 *  Test case	Explanation
 *  l1=[0,1]
 *  l2=[0,1,2]	When one list is longer than the other.
 *  l1=[]
 *  l2=[0,1]	When one list is null, which means an empty list.
 *  l1=[9,9]
 *  l2=[1]	    The sum could have an extra carry of one at the end, which is easy to forget.
 *
 * Complexity Analysis
 *  Time complexity : O(max(m,n)).
 *  Assume that m and n represents the length of l1 and l2 respectively,
 *  the algorithm above iterates at most max(m,n) times.
 *  Space complexity : O(1).
 *
 * Follow up
 * What if the the digits in the linked list are stored in non-reversed order? For example:
 * (3→4→2)+(4→6→5)=8→0→7
 *
 * Add Two Numbers II:
 *  https://github.com/cherryljr/LeetCode/blob/master/Add%20Two%20Numbers%20II.java
 *
 * Reference:
 *  https://leetcode.com/problems/add-two-numbers/solution/
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        int carr = 0;

        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            // 计算对应位置上两个数相加之和 + 进位
            int sum = x + y + carr;	
            // 计算进位的值
            carr = sum / 10;
            // 利用链表的 尾插法 插入数据
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carr != 0) {
            curr.next = new ListNode(carr);
        }

        return dummy.next;
    }
}