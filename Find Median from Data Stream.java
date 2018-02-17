/*
Median is the middle value in an ordered integer list.
If the size of the list is even, there is no middle value.
So the median is the mean of the two middle value.

Examples:
[2,3,4] , the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Design a data structure that supports the following two operations:

void addNum(int num) - Add a integer number from the data stream to the data structure.
double findMedian() - Return the median of all elements so far.

For example:
addNum(1)
addNum(2)
findMedian() -> 1.5
addNum(3)
findMedian() -> 2
 */

/**
 * Approach: Two PriorityQueue (maxHeap + minHeap)
 * 与 LintCode 上面的 Data Stream Median 几乎一样。
 * 只是两题的 median 定义有稍微的不同，因此稍微修改一下 getMedian() 函数即可。
 * 详细解析可以参见：
 * https://github.com/cherryljr/LintCode/blob/master/Data%20Stream%20Median.java
 */
class MedianFinder {
    PriorityQueue<Integer> maxHeap, minHeap;

    /** initialize your data structure here. */
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (num > findMedian()) {
            minHeap.offer(num);
        } else {
            maxHeap.offer(num);
        }
        
        // Check the size of two heap is whether balanced or not
        // if not equal then maxHeap will be larger by one
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }

        if (maxHeap.size() == minHeap.size()) {
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */