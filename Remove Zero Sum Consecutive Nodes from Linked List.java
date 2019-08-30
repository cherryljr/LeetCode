/*
Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.
After doing so, return the head of the final linked list. You may return any such answer.

(Note that in the examples below, all sequences are serializations of ListNode objects.)

Example 1:
Input: head = [1,2,-3,3,1]
Output: [3,1]
Note: The answer [1,2,1] would also be accepted.

Example 2:
Input: head = [1,2,3,-3,4]
Output: [1,2,4]

Example 3:
Input: head = [1,2,3,-3,-2]
Output: [1]

Constraints:
    1. The given linked list will contain between 1 and 1000 nodes.
    2. Each node in the linked list has -1000 <= node.val <= 1000.
 */

/**
 * Approach: PreSum + HashMap
 * 我们不妨假设一下，如果题目给出的是一个数组，而不是链表，那么应该如何去解决呢？
 * 因为每次我要去除一段 SubArray Sum 为 0 的区间，对此可以参考 Subarray Sum Equals K 来进行处理。
 * 解决方案使用到了 PreSum + HashMap。每次遇到已经出现在 Map 中的 PreSum 时，我们就需要将 map.get(preSum)~curr 的这段区间去除掉。
 *
 * 那么回到本题，对于链表我们应当怎么处理呢？
 * 首先因为头部的节点可能被移除掉，我们无法确定链表的 head，所以需要使用到一个 Dummy Node。
 * 并且我们本身就需要对 Map 进行一次 put(0, node) 的操作，正好可以通过初始化 Dummy Node 为 0 来达成。
 * 其次，对于链表而言，去除一部分的区间只需要通过操作节点的 next 指针即可完成，因此当 preSum 已经出现在 map 中时，
 * 我们只需要进行一次 map.get(preSum).next = curr.next 操作，即可去除掉这部分区间。
 * 最后，值得注意的是：我们不能让已经被移除掉的节点影响后续的操作。举个例子：
 *  [1, 3, 2, -3, -2, 5, 5, -5, 1]  ==> preSum=[0, 1, 4, 6, 3, 1, 6, 11, 6, 7]
 * 这种情况下，对于 [5, -5] 因为 preSum[-5] = 6,所以在移除时，将通过 2.next = 1 这个操作来实现。
 * 但是此时，我们的 2 已经在 1.next = 5 这个操作中被移除了。这就会造成本次修改是无法被感知到的。
 * 因此，当遇到 preSum 已经被记录的情况时，除了通过 map.get(preSum).next = curr.next 移除掉这段区间之外，
 * 还需要将这段区间所产生的 preSum 一并移除掉才行。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * References:
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarray%20Sum%20Equals%20K.java
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
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
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0), curr = dummy;
        Map<Integer, ListNode> map = new HashMap<>();
        dummy.next = head;
        int preSum = 0;
        while (curr != null) {
            preSum += curr.val;
            if (map.containsKey(preSum)) {
                ListNode prev = map.get(preSum).next;
                int tempSum = preSum;
                // 去除掉被移除节点所产生 preSum 的影响
                while (prev != curr) {
                    tempSum += prev.val;
                    prev = prev.next;
                    map.remove(tempSum);
                }
                map.get(preSum).next = curr.next;
            } else {
                map.put(preSum, curr);
            }
            curr = curr.next;
        }
        return dummy.next;
    }
}