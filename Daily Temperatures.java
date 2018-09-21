/*
Given a list of daily temperatures, produce a list that, for each day in the input,
tells you how many days you would have to wait until a warmer temperature.
If there is no future day for which this is possible, put 0 instead.

For example, given the list temperatures = [73, 74, 75, 71, 69, 72, 76, 73],
your output should be [1, 1, 4, 2, 1, 1, 0, 0].

Note: The length of temperatures will be in the range [1, 30000].
Each temperature will be an integer in the range [30, 100].
 */

/**
 * Approach: Monotonic Stack
 * 题目的实质就是找到当前数右边第一个比它大的数。
 * 因此可以使用 单调栈 在 O(n) 的时间内解决问题。
 * 值得注意的是：
 *  这里使用了 ArrayDeque 而不是 Stack.
 *  原因是 ArrayDeque 的效率会更高一些，并且 Java Doc 中也明确指出应该优先选择 ArrayDeque.
 *  当然如果你直接使用 数组 进行进一步优化的话可以更快。
 * 一些关于 ArrayDeque 的补充：
 *  ArrayDeque 是 Deque 接口的一种具体实现，是依赖于可变数组来实现的。
 *  ArrayDeque 没有容量限制，可根据需求自动进行扩容。
 *  ArrayDeque 可以作为栈来使用，效率要高于 Stack；
 *  ArrayDeque 也可以作为队列来使用，效率相较于基于双向链表的 LinkedList 也要更好一些。
 *  但是注意，ArrayDeque 不支持为 null 的元素。
 *
 * 类似问题：
 *  https://github.com/cherryljr/LintCode/blob/master/Largest%20Rectangle%20in%20Histogram.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Online%20Stock%20Span.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Sum%20of%20Subarray%20Minimums.java
 */
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] rst = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                rst[index] = i - index;
            }
            stack.push(i);
        }
        return rst;
    }
}