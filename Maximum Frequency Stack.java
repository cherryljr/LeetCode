/*
Implement FreqStack, a class which simulates the operation of a stack-like data structure.

FreqStack has two functions:
    push(int x), which pushes an integer x onto the stack.
    pop(), which removes and returns the most frequent element in the stack.
If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.

Example 1:
Input:
["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
Output: [null,null,null,null,null,null,null,5,7,5,4]

Explanation:
After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
pop() -> returns 5, as 5 is the most frequent.
The stack becomes [5,7,5,7,4].
pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
The stack becomes [5,7,5,4].
pop() -> returns 5.
The stack becomes [5,7,4].
pop() -> returns 4.
The stack becomes [5,7].

Note:
Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
The total number of FreqStack.push calls will not exceed 10000 in a single test case.
The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
 */

/**
 * Approach: HashMap + List<Stack<T>> (Bucket Sort)
 * 这道题目属于数据结构的设计题。与 LRU, LFU 考察点是相同的。
 * 在细节等方面的处理上很大程度都参考了 LFU 的做法。
 *
 * 本题的核心突破点在于：使用多（N）个栈去完成。
 * 利用元素的出现次数不同将其分类在不同的栈里面，这一点非常类似 桶排序 的做法。
 * 以题目为例，压栈的顺序为：[5],[7],[5],[7],[4],[5]
 * 那么相应的有：
 *  freq    stack
 *   1      5,7,4
 *   2      5,7
 *   3      5
 * 通过这个做法，我们每次中 freq 最大的栈中 pop 出栈顶元素即可。
 * 相应的对此我们需要一个 Map 来记录各个元素的出现次数。
 * 同时这样还解决了当两个元素出现次数相同，pop 出离栈顶最近的元素这个要求。
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=IkrGghj6_fk
 */
class FreqStack {
    Map<Integer, Integer> freqMap;
    List<Stack<Integer>> stacks;

    public FreqStack() {
        freqMap = new HashMap<>();
        stacks = new ArrayList<>();
    }

    public void push(int x) {
        int freq = 1;   // 获取元素 x 的出现次数，默认情况下为1
        if (freqMap.containsKey(x)) {
            freq = freqMap.get(x) + 1;
        }
        // 将 x 的出现次数记录在 freqMap 中
        freqMap.put(x, freq);

        // 如果 x 的出现次数 freq 已经超过了 stacksList 所能表示的，
        // 那么我们需要再加一个 stack 来表示出现次数为 freq 的元素。
        if (stacks.size() < freq) {
            stacks.add(new Stack<>());
        }
        stacks.get(--freq).push(x);
    }

    public int pop() {
        // 当前出现次数最多的元素的 stack 集合
        Stack<Integer> lastStack = stacks.get(stacks.size() - 1);
        int x = lastStack.pop();
        // 如果移除当前元素后，stack为空，则移除当前stack
        if (lastStack.isEmpty()) {
            stacks.remove(stacks.size() - 1);
        }
        // 同样的，需要对记录出现次数的 freqMap 进行相应的处理
        freqMap.put(x, freqMap.get(x) - 1);
        if (freqMap.get(x) <= 0) {
            freqMap.remove(x);
        }
        return x;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 */