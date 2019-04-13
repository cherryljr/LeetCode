/*
We are given a linked list with head as the first node.  Let's number the nodes in the list: node_1, node_2, node_3, ... etc.
Each node may have a next larger value:
for node_i, next_larger(node_i) is the node_j.val such that j > i, node_j.val > node_i.val, and j is the smallest possible choice.
If such a j does not exist, the next larger value is 0.

Return an array of integers answer, where answer[i] = next_larger(node_{i+1}).

Note that in the example inputs (not outputs) below, arrays such as [2,1,5] represent
the serialization of a linked list with a head node value of 2, second node value of 1, and third node value of 5.

Example 1:
Input: [2,1,5]
Output: [5,5,0]

Example 2:
Input: [2,7,4,3,5]
Output: [7,0,5,5,0]

Example 3:
Input: [1,7,5,1,9,2,5,1]
Output: [7,9,9,9,0,5,0,0]

Note:
    1. 1 <= node.val <= 10^9 for each node in the linked list.
    2. The given list has length in the range [0, 10000].
 */

/**
 * Approach: Monotonic Stack
 * 与 Next Greater Element I 基本相同，只不过是把 数组 换成了 链表 而已。
 * 所以我们只用把链表遍历一遍，然后用一个数组存起来，题目就完全和 Next Greater Element I 一样了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * Next Greater Element I:
 *  https://github.com/cherryljr/LeetCode/tree/master/Next%20Greater%20Element%20I
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
    public int[] nextLargerNodes(ListNode head) {
        List<Integer> nums = new ArrayList<>();
        for (; head != null; head = head.next) {
            nums.add(head.val);
        }

        int[] ans = new int[nums.size()];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums.size(); i++) {
            while (!stack.isEmpty() && nums.get(i) > nums.get(stack.peek())) {
                ans[stack.pop()] = nums.get(i);
            }
            stack.push(i);
        }
        return ans;
    }
}