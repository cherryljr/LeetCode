Intuition
Stack is LIFO (last in - first out) data structure, in which elements are added and removed from the same end, called top. 
In general stack is implemented using array or linked list, 
but in the current article we will review a different approach for implementing stack using queues. 
In contrast queue is FIFO (first in - first out) data structure, 
in which elements are added only from the one side - rear and removed from the other - front. 
In order to implement stack using queues, we need to maintain two queues q1 and q2. 
Also we will keep top stack element in a constant memory.

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

Approach 1: (Two Queues, push - O(1), pop O(n) )
Algorithm
Push
    The new element is always added to the rear of queue q1 and it is kept as top stack element
Complexity Analysis
    Time  complexity : O(1). Queue is implemented as linked list and add operation has O(1) time complexity.
    Space complexity : O(1)

Pop
    We need to remove the element from the top of the stack. 
    This is the last inserted element in q1. 
    Because queue is FIFO (first in - first out) data structure, 
    the last inserted element could be removed only after all elements, except it, have been removed. 
    For this reason we need to maintain additional queue q2, 
    which will serve as a temporary storage to enqueue the removed elements from q1. 
    The last inserted element in q2 is kept as top. 
    Then the algorithm removes the last element in q1. 
    We swap q1 with q2 to avoid copying all elements from q2 to q1.
Complexity Analysis
    Time  complexity : O(n). The algorithm dequeues n elements from q1 and enqueues n - 1n−1 elements to q2, 
    where nn is the stack size. This gives 2n - 12n−1 operations.
    Space complexity : O(1).
    
Empty
    Queue q1 contains all stack elements, so the algorithm checks if q1 is empty.
    Time  complexity : O(1).
    Space complexity : O(1).

Top
    The top element is kept in constant memory and is modified each time when we push or pop an element.
    Time  complexity : O(1).
    Space complexity : O(1).
    
// Code Below
class MyStack {
    private Queue<Integer> q1;
    private Queue<Integer> q2;
    private int top;
    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        q1.add(x);
        top = x;
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        while (q1.size() > 1) {
            top = q1.remove();
            q2.add(top);
        }
        int rst = q1.remove();
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        return rst;
    }
    
    /** Get the top element. */
    public int top() {
        return top;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.isEmpty();
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
 

// Approach 2 (Two Queues, push - O(n), pop O(1) )
// You can get detials from here: https://leetcode.com/articles/implement-stack-using-queues/

Approach 3 (One Queue, push - O(n), pop O(1)O ) -- Best Method
The mentioned above two approaches have one weakness, they use two queues. 
This could be optimized as we use only one queue, instead of two.

Algorithm
Push
    When we push an element into a queue, it will be stored at back of the queue due to queue's properties. 
    But we need to implement a stack, where last inserted element should be in the front of the queue, not at the back. 
    To achieve this we can invert the order of queue elements when pushing a new element.
Complexity Analysis
Time  complexity : O(n). The algorithm removes n elements and inserts n+1 elements to q1 , where n is the stack size. 
                         This gives 2n+1 operations. The operations add and remove in linked lists has O(1) complexity.
Space complexity : O(1).

Pop
    The last inserted element is always stored at the front of q1 and we can pop it for constant time.
    Time  complexity : O(1).
    Space complexity : O(1).
    
Empty
    Queue q1 contains all stack elements, so the algorithm checks if q1 is empty.
    Time  complexity : O(1).
    Space complexity : O(1).
    
Top
    The top element is always positioned at the front of q1. Algorithm return it.
    Time  complexity : O(1).
    Space complexity : O(1).
    
// Code Below
class MyStack {
    private Queue<Integer> q;
    /** Initialize your data structure here. */
    public MyStack() {
        q = new LinkedList<>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        q.add(x);
        int size = q.size();
        // invert the order of queue elements
        while (size > 1) {
            q.add(q.remove());
            size--;
        }
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return q.remove();
    }
    
    /** Get the top element. */
    public int top() {
        return q.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q.isEmpty();
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