/*
Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. 
You can only see the k numbers in the window. Each time the sliding window moves right by one position.
For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Therefore, return the max sliding window as [3,3,5,5,6,7].

Note: 
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.

Follow up:
Could you solve it in linear time?
*/

/**
 * Approach: Deque
 * 该题与 Sliding Window Median 具有一定的相似性。
 * 都是求在一个滑动窗口中的一个参数值。
 * 毫无疑问，大家最直接的想法就是借鉴 Sliding Window Median 的方法：
 * 维护一个 maxHeap 即可，又因为需要进行 对某个特定的点 进行快速删除，
 * 所以使用了 HashHeap， 即使用 maxHashHeap 这个数据结构即可。
 * 时间复杂度为: O(nlogk)
 *
 * 但是根据题目要求，我们发现其要求的是 O(n) 的时间复杂度，即存在更好的做法。
 * 于是我们考虑有哪些数据结构是 O(1) 时间复杂度的呢？
 * 分别为: Stack; Queue; Deque.
 * 经过分析发现：我们需要能够在区间的两端 都能进行 pop 和 push 操作。即只有 Deque 能够满足要求。
 * 具体做法如下：
 *  1. 用 Deque 来存储 滑动窗口内可能是 max 元素的下标。
 *  当 Deque 中头节点的下标 小于 i-k+1 时，说明该元素已经不在 滑动窗口的范围内了，则将该元素从头部移除。
 *  2. 加入一个新的元素时，与之前的节点比较，如果发现 之前的节点 <= 当前节点的值，则将之前的节点从尾部移除。
 *  这是因为 如果 nums[last] <= nums[i] && last < i, 那么滑动窗口内的最大值不可能会是 nums[last].
 *  而如果 nums[last] == nums[i], 那么因为 last < i, 所以新来的元素 nums[i] 必定更晚被弹出窗口(过期)
 *  3. 根据上述的分析，我们可以发现 Deque 是一个 递减队列(没有等于，如果相等则取更晚进窗口的元素)。
 *  因此每次滑动窗口的最大值就是以 队列头 作为下标的元素，即 nums[queue.getFirst()].
 *  
 * 时间复杂度分析：每个节点只会进队列一次，出队列一次。因此时间复杂度为：O(n)
 */
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int len = nums.length;
        int[] rst = new int[len - k + 1];
        int index = 0;
        // Use a deque to store index
        Deque<Integer> qmax = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            //when index equals to (i-k) remove the head of deque, because it's out of range k
            if (!qmax.isEmpty() && qmax.getFirst() == i - k) {
                qmax.removeFirst();
            }
            // remove numbers that no bigger than nums[i] in k range as they are useless
            while (!qmax.isEmpty() && nums[qmax.getLast()] <= nums[i]) {
                qmax.removeLast();
            }
            // the deque - qmax contains index...
            // the array - rst contains content...
            qmax.addLast(i);
            if (i >= k - 1) {
                rst[index++] = nums[qmax.getFirst()];
            }
        }

        return rst;
    }
}
