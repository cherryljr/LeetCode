/*
You have an infinite number of stacks arranged in a row and numbered (left to right) from 0, each of the stacks has the same maximum capacity.
Implement the DinnerPlates class:
    ·DinnerPlates(int capacity) Initializes the object with the maximum capacity of the stacks.
    ·void push(int val) pushes the given positive integer val into the leftmost stack with size less than capacity.
    ·int pop() returns the value at the top of the rightmost non-empty stack and removes it from that stack, and returns -1 if all stacks are empty.
    ·int popAtStack(int index) returns the value at the top of the stack with the given index and removes it from that stack, and returns -1 if the stack with that given index is empty.

Example:
Input:
["DinnerPlates","push","push","push","push","push","popAtStack","push","push","popAtStack","popAtStack","pop","pop","pop","pop","pop"]
[[2],[1],[2],[3],[4],[5],[0],[20],[21],[0],[2],[],[],[],[],[]]
Output:
[null,null,null,null,null,null,2,null,null,20,21,5,4,3,1,-1]

Explanation:
DinnerPlates D = DinnerPlates(2);  // Initialize with capacity = 2
D.push(1);
D.push(2);
D.push(3);
D.push(4);
D.push(5);         // The stacks are now:  2  4
                                           1  3  5
                                           ﹈ ﹈ ﹈
D.popAtStack(0);   // Returns 2.  The stacks are now:     4
                                                       1  3  5
                                                       ﹈ ﹈ ﹈
D.push(20);        // The stacks are now: 20  4
                                           1  3  5
                                           ﹈ ﹈ ﹈
D.push(21);        // The stacks are now: 20  4 21
                                           1  3  5
                                           ﹈ ﹈ ﹈
D.popAtStack(0);   // Returns 20.  The stacks are now:     4 21
                                                        1  3  5
                                                        ﹈ ﹈ ﹈
D.popAtStack(2);   // Returns 21.  The stacks are now:     4
                                                        1  3  5
                                                        ﹈ ﹈ ﹈
D.pop()            // Returns 5.  The stacks are now:      4
                                                        1  3
                                                        ﹈ ﹈
D.pop()            // Returns 4.  The stacks are now:   1  3
                                                        ﹈ ﹈
D.pop()            // Returns 3.  The stacks are now:   1
                                                        ﹈
D.pop()            // Returns 1.  There are no stacks.
D.pop()            // Returns -1.  There are still no stacks.

Constraints:
    1. 1 <= capacity <= 20000
    2. 1 <= val <= 20000
    3. 0 <= index <= 100000
    4. At most 200000 calls will be made to push, pop, and popAtStack.
 */

/**
 * Approach: TreeSet (BST)
 * 从本题的数据规模可以轻易推测出时间复杂度应该在 O(ops * logn) 级别。
 * 为了实现各个操作在 O(1) 或者 O(logn) 级别，我们自然想到可以使用 二分 或者 Tree 之类 logn 级别的做法。
 * 因为需要在 O(logn) 的时间内找到第一个可用的 Stack 进行插入，这就要求了这是一个有序的集合，且每次操作时间复杂度在 O(logn) 级别。
 * 那么对应的数据结构有 TreeMap | TreeSet | PriorityQueue。而本题会有 移除最大值 的操作，因此排除 PriorityQueue。
 * 而且我们只需要存 index 即可，所以选择 TreeSet。
 * 其他的就是代码实现上的注意点了。（如果 pop 之后，最后一个 stack 为空要进行 shrink 操作才行）
 *
 * Time Complexity: O(nlogn)
 * Space Complexity: O(n)
 *
 * Reference: https://youtu.be/DUsOp0HMQQg
 */
class DinnerPlates {

    List<ArrayDeque<Integer>> list;
    int capacity;
    TreeSet<Integer> availableSet;

    public DinnerPlates(int capacity) {
        this.list = new ArrayList<>();
        this.capacity = capacity;
        this.availableSet = new TreeSet<>();
    }

    public void push(int val) {
        if (availableSet.isEmpty()) {
            list.add(new ArrayDeque<>());
            availableSet.add(list.size() - 1);
        }
        ArrayDeque<Integer> stack = list.get(availableSet.first());
        stack.push(val);
        if (stack.size() >= capacity) {
            availableSet.pollFirst();
        }
    }

    public int pop() {
        if (list.isEmpty()) {
            return -1;
        }
        int ans = list.get(list.size() - 1).pop();
        availableSet.add(list.size() - 1);
        // Amortized O(1)
        while (!list.isEmpty() && list.get(list.size() - 1).isEmpty()) {
            list.remove(list.size() - 1);
            availableSet.pollLast();
        }
        return ans;
    }

    public int popAtStack(int index) {
        if (index >= list.size()) return -1;
        if (index == list.size() - 1) {
            return pop();
        }
        ArrayDeque<Integer> stack = list.get(index);
        int ans = stack.isEmpty() ? -1 : stack.pop();
        availableSet.add(index);
        return ans;
    }

}

/**
 * Your DinnerPlates object will be instantiated and called as such:
 * DinnerPlates obj = new DinnerPlates(capacity);
 * obj.push(val);
 * int param_2 = obj.pop();
 * int param_3 = obj.popAtStack(index);
 */