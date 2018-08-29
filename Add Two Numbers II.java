/*
You are given two non-empty linked lists representing two non-negative integers.
The most significant digit comes first and each of their nodes contain a single digit.
Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Follow up:
What if you cannot modify the input lists? In other words, reversing the lists is not allowed.

Example:
Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7
 */

/**
 * Approach 1: Using Stack to Reverse the List
 * 本题中，链表数字顺序并没有进行逆序表示，而是按照我们正常的 高位->低位 的顺序进行存储。
 * 但是我们计算的时候需要从 低位->高位 计算才行，也就是 Add Two Numbers 中的做法。
 *
 * 那么既然要进行逆序，我们很容易就能够想到利用 Stack 来完成这个操作。（因为不能修改原本链表的中的数据和结构）
 * 接下来的与 Add Two Numbers 还存在这一点不同：
 * 那就是我们的答案也是从 高位->低位 来进行表示的，
 * 因此我们在插入结果的时候应该使用 头插法 而不再是 尾插法了。
 *
 * 基于以上两点的分析，我们利用 Stack + 头插法 便可以轻松解决这道题目。
 * 其他的与 Add Two Numbers 基本就一摸一样了。
 *
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(m + n)
 *
 * 相关资料：
 * Add Two Numbers:
 *  https://github.com/cherryljr/LeetCode/blob/master/Add%20Two%20Numbers.java
 * Reverse Linked List:
 *  https://github.com/cherryljr/LintCode/blob/master/Reverse%20Linked%20List.java
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
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        // 对链表进行逆序操作
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        ListNode dummy = new ListNode(-1);
        int carr = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int x = stack1.isEmpty() ? 0 : stack1.pop();
            int y = stack2.isEmpty() ? 0 : stack2.pop();
            // 计算对应位置上两个数相加之和 + 进位
            int sum = x + y + carr;
            // 计算进位的值   
            carr = sum / 10;
            // 利用链表的 头插法 插入数据
            ListNode curr = new ListNode(sum % 10);
            ListNode next = dummy.next;
            dummy.next = curr;
            curr.next = next;
        }
        if (carr != 0) {
            ListNode curr = new ListNode(carr);
            ListNode next = dummy.next;
            dummy.next = curr;
            curr.next = next;
        }

        return dummy.next;
    }
}

/**
 * Approach 2: Recursion + Count The Difference of Length
 * 题目的 Fellow Up 中提到了不能逆序，虽然 Approach 1 中使用了 Stack 来逆序，
 * 从而没有改变原本的数据结构，但是实际上也进行了 逆序 操作，是一个 Cheat 的做法。
 * 因此这里我们采用了另外一种做法来满足要求。
 * 即通过 递归 的方式来实现，对此我们需要算出两个链表之间的长度之差。
 *
 * 看上去并不是很优雅的做法，但是我并没有想到更好的做法了。
 * 
 * 参考资料：
 *  https://leetcode.com/problems/add-two-numbers-ii/discuss/92643/Java-O(n)-recursive-solution-by-counting-the-difference-of-length
 */
class Solution {
    int carr = 0;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode rst;
        int len1 = getLen(l1), len2 = getLen(l2);
        if (len1 < len2) {
            rst = add(l1, l2, len2 - len1);
        } else {
            rst = add(l2, l1, len1 - len2);
        }
        if (carr > 0) {
            ListNode rst1 = rst;
            rst = new ListNode(carr);
            rst.next = rst1;
        }
        return rst;
    }

    public int getLen(ListNode l1) {
        int len = 0;
        while (l1 != null) {
            len++;
            l1 = l1.next;
        }
        return len;
    }

    //l2.length - l1.length == diff;
    public ListNode add(ListNode l1, ListNode l2, int diff) {
        if (l1.next == null && l2.next == null) {
            int sum = l1.val + l2.val;
            carr = sum / 10;
            return new ListNode(sum % 10);
        }
        ListNode rst, next;
        int sum;
        if (diff == 0) {
            next = add(l1.next, l2.next, 0);
            sum = l1.val + l2.val + carr;
        } else {
            next = add(l1, l2.next, diff - 1);
            sum = carr + l2.val;
        }
        carr = sum / 10;
        rst = new ListNode(sum % 10);
        rst.next = next;
        return rst;
    }
}