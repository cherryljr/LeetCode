/*
Description
Implement a stack. You can use any data structure inside a stack except stack itself to implement it.

Example
    push(1)
    pop()
    push(2)
    top()  // return 2
    pop()
    isEmpty() // return true
    push(3)
    isEmpty() // return false
Tags
    Array Stack
 */

/**
 * Approach: Using Array
 * 用 数组 模拟一个栈。
 * 我们只需要用一个 size 指针始终跟踪栈顶元素即可。
 * 因为所有的操作都发生在 数组的尾部(栈顶)。
 * 因此 size 还能代表数组的大小。
 *
 * 参考资料：
 * https://www.geeksforgeeks.org/stack-data-structure-introduction-program/
 */
public class ArrayStack {
    private Integer[] arr;
    private Integer size;

    // 开辟一个固定大小的栈
    public ArrayStack(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException("The init size is less than 0");
        }
        arr = new Integer[initSize];
        size = 0;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return arr[size - 1];
    }

    public void push(int value) {
        if (size == arr.length) {
            throw new ArrayIndexOutOfBoundsException("The stack is full");
        }
        arr[size++] = value;
    }

    public Integer pop() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException("The stack is empty");
        }
        return arr[--size];
    }
}