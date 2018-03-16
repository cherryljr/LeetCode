/*
Implement the following operations of a stack using queues.
    push(x) -- Push element x onto stack.
    pop() -- Removes the element on top of the stack.
    top() -- Get the top element.
    empty() -- Return whether the stack is empty.
Notes:
    You must use only standard operations of a queue -- 
    which means only push to back, peek/pop from front, size, and is empty operations are valid.
    Depending on your language, queue may not be supported natively. 
    You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
    You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
 */

/**
 * Approach: Using Two Queues
 * 思想与 Implement Queue using Stacks 相同。
 * 因为 Stack 为 FILO,而 Queue 为 FIFO 所以我们可以通过两个队列来实现 Stack 的结构。
 *
 * 因为队列为 FIFO，所以当 Stack 弹出一个元素的时候，它弹出的是刚刚被插入的元素，而队列只能在最后才能弹出该元素value。
 * 因此我们需要将 value 之前的元素全部弹出，然后用另一个队列 q2 来存储这些被弹出弹出来的数据。
 * 这样 q1 中只剩下一个元素，而这个元素就是我们的 栈顶元素。
 * 然后 交换 q1 和 q2 的指针。（应当采用交换指针的方式，而非将 q2 拷贝回 q1）
 * 而对于 top(peek) 操作，我们在获得了 栈顶元素之后，还需要将 q1 中唯一剩下的元素添加到 q2 中。
 *
 * 另一种解法参见：https://leetcode.com/articles/implement-stack-using-queues/
 */
class MyStack {
    private Queue<Integer> q1;
    private Queue<Integer> q2;

    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        q1.add(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        while (q1.size() > 1) {
            q2.add(q1.poll());
        }
        int rst = q1.poll();
        swap();
        return rst;
    }

    /** Get the top element. */
    public int top() {
        while (q1.size() > 1) {
            q2.add(q1.poll());
        }
        int rst = q1.poll();
        q2.add(rst);
        swap();
        return rst;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.isEmpty();
    }

    /** Swap the two queues. */
    private void swap() {
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */