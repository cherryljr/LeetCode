/*
Given a singly linked list, return a random node's value from the linked list.
Each node must have the same probability of being chosen.

Follow up:
What if the linked list is extremely large and its length is unknown to you?
Could you solve this efficiently without using extra space?

Example:

// Init a singly linked list [1,2,3].
ListNode head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
Solution solution = new Solution(head);

// getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
solution.getRandom();
 */

/**
 * Approach: Reservoir Sampling
 * 蓄水池抽样算法。经常在大数据中使用，应用场景如下：
 * 给出一个数据流，这个数据流的长度很大或者未知。并且对该数据流中数据只能访问一次。
 * 请写出一个随机选择算法，使得数据流中所有数据被选中的概率相等。
 * 与本题的 Fellow Up 相同。
 *
 * 如果我们想要从 N 个数据中，随机选出 K 个数，要求每个数据被选中的概率相同，均为 K/N.
 * 做法为：
 *  1. 首先我们维护一个 K 大小的蓄水池。对于前 K 个元素来说，其概率都是 1.
 *  2. 处理第 ith 个元素时，我们以 k/i 的概率决定是否将 ith 放入池内。
 *  如果不放入，直接舍弃；如果放入，则在蓄水池内 随机挑选一个 元素舍弃掉，然后放入 ith 元素。
 *  3. 处理 i+1, i+2 个元素时，重复 1， 2 步骤。
 *
 * 为什么以上做法可以保证每个元素被选取的概率都是 K/N 呢？
 * 分析如下：
 *  1. 在选 K+1 个元素之前，第 ith 元素留在池内的概率为 1.
 *  2. 然后，我们重点分析第 K+1 个元素。当 K+1 元素要进来的时候，
 *  我们按照 K/(K+1) 的概率决定是否将 (K+1)th 元素放入蓄水池，如果不放直接扔掉；
 *  如果放入，则在蓄水池中 随机挑选一个 元素扔掉，然后将 (K+1)th 元素加入蓄水池中。
 *  因此对于 ith 元素，要将其丢弃需要同时满足两个条件：
 *      1. 决定将 (K+1)th 元素放入池子 K/(K+1)
 *      2. 决定将 ith 元素扔出池子 1/K
 *      即被丢弃的概率为：K/(K+1) * 1/K = 1/(K+1)
 *  相应的 ith 元素留在池子内的概率为：
 *      1 - 1/(K+1) = K/(K+1)
 *  3. 同样我们继续分析第 K+2 个元素的情况。
 *  同第2步骤中的分析，我们可以分析出：
 *  只有当 (K+2)th 元素入池，且决定将 ith 元素丢弃的时候，ith 才会被丢弃。
 *      K/(K+2) * 1/K = 1/(K+2)
 *  因此相应的其留在池子内的概率为：
 *      1 - 1/(K+2) = (K+1)/(K+2)
 *  则对于后续的递推分析可得：在选第 Nth 元素时，从 1th 到 Nth 的全部过程中，
 *  ith 留在池内的概率为：
 *      K/(K+1) * (K+1)/(K+2) * (K+2)/(K+3) * ... * (N-1)/N = K/N
 *      
 * 以上便是 蓄水池抽样 的介绍与证明。
 * 而本题只是将 K 设置为 1 而已。理解了以上算法，本题自然迎刃而解。
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
    ListNode head;
    Random random;

    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
    public Solution(ListNode head) {
        this.head = head;
        this.random = new Random();
    }

    /** Returns a random node's value. */
    public int getRandom() {
        ListNode curr = head;
        ListNode rst = null;
        for (int i = 1; curr != null; i++) {
            // random.nextInt(n) between 0 (inclusive) and n (exclusive)
            // determine whether the ith is put into the pool or not.
            // For the nth target, i is n. 
            // Then the probability that rnd.nextInt(i)==0 is 1/n. 
            // Thus, the probability that return nth target is 1/n.
            if (random.nextInt(i) == 0) {
                rst = curr;
            }
            curr = curr.next;
        }
        return rst.val;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */