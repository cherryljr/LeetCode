/*
Implement the following operations of a queue using stacks.
    push(x) -- Push element x to the back of queue.
    pop() -- Removes the element from in front of queue.
    peek() -- Get the front element.
    empty() -- Return whether the queue is empty.

Notes:
    You must use only standard operations of a stack --
    which means only push to top, peek/pop from top, size, and is empty operations are valid.
    Depending on your language, stack may not be supported natively.
    You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
    You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 */

/**
 * Approach: Using Two Stacks
 * 栈这种数据结构是 先进后出 的，而队列则是 先进先出 的.
 * 对于Stack来说，我们将数据存入一个栈之后，再取出来它的顺序是倒过来的。
 * 那么我们便可以想到，是否可以通过两个栈来实现 Queue 这个数据结构：
 *      将从第一个栈的数据 全部取出来 然后再次 push 到第二个栈里，
 *      实现再一次翻转顺序，这样我们取出来的数据便是顺序的了。也就是队列的 FIFO.
 *
 * 因此这里我们用到了两个栈: stackPush 和 stackPop.
 *  每次想要向队列中添加元素的时候，我们都往 stackPush 里面 push 入元素；
 *  当需要取出元素的时候：
 *  我们看 stackPop 是否为空，如果非空，直接 pop 出元素即可。
 *  如果为空，我们将 stackPush 中的全部元素 pop 出来，然后再 push 到 stackPop 中，然后从 stackPop 中取元素即可。
 *
 * 注意点有两个：
 *  1. 每次将 stackPush 中的元素倒出来的时候，必须全部倒出来才行；
 *  2. 每次将被 pop 出来的元素倒入 stackPop 中时，stackPop 必须为空。
 *  
 * 另一种方法可以参见：https://leetcode.com/articles/implement-queue-using-stacks/
 */
class MyQueue {
    Stack<Integer> stackPush;
    Stack<Integer> stackPop;

    /** Initialize your data structure here. */
    public MyQueue() {
        stackPush = new Stack<>();
        stackPop = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        stackPush.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        // 记得需要判断 stackPop 是否为空
        // 将 stackPush 中的 所有数据 pop出来，然后 push入stackPop 中，实现顺序的翻转
        if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stackPush.isEmpty() && stackPop.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */