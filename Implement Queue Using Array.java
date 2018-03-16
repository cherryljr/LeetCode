/*
Description
Implement a Queue. You can use any data structure inside a queue except queue itself to implement it.

Example
    add(1)
    remove()
    add(2)
    peek()  // return 2
    remove()
    isEmpty() // return true
    add(3)
    isEmpty() // return false
Tags
    Array Queue
 */

/**
 * Approach: Using Array
 * 采用 数组 模拟队列。
 * 因为队列是对两个端口进行操作的，所以我们需要：
 *  用一个 first 指针来跟踪队列的头，队列移除元素的时候，在 first 端进行操作；
 *  用一个 last 指针来跟踪队列的尾，队列添加元素/求peek元素的时候，在 end 端进行操作。
 *  同时我们还需要用一个 size 来维护队列的大小，也方便于 first指针 和 end指针 移动到边界时的处理。
 *  （当然 size 这个空间可以被节省掉，但是为了代码有更好的可读性，我们选择了使用它）
 *  
 * 参考资料：
 * https://www.geeksforgeeks.org/queue-set-1introduction-and-array-implementation/
 */
public class ArrayQueue {
    private Integer[] arr;
    private Integer size;
    private Integer first;  // 队列头指针
    private Integer last;   // 队列尾指针

    public ArrayQueue(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException("The init size is less than 0");
        }
        arr = new Integer[initSize];
        size = 0;
        first = 0;
        last = 0;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return arr[first];
    }

    /**
     * 添加元素时，判断是否还有空间 (size < arr.length)
     * 若还有则加入元素，size++，last指针 向后移动一位，当last位置在数组末尾时，跳回到数组头部位置。
     */
    public void add(int value) {
        if (size == arr.length) {
            throw new ArrayIndexOutOfBoundsException("The queue is full");
        }
        size++;
        arr[last] = value;
        last = last == arr.length - 1 ? 0 : last + 1;
    }

    /**
     * 移除元素时，判断是否还有元素 (size > 0)
     * 若还有则弹出该元素，size--，first指针 向后移动一位，当first位置在数组末尾时，跳回到数组头部位置。
     */
    public Integer remove() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException("The queue is empty");
        }
        size--;
        int tmp = first;
        first = first == arr.length - 1 ? 0 : first + 1;
        return arr[tmp];
    }
}