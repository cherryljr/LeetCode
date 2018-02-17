/*
Median is the middle value in an ordered integer list.
If the size of the list is even, there is no middle value.
So the median is the mean of the two middle value.

Examples:
[2,3,4] , the median is 3
[2,3], the median is (2 + 3) / 2 = 2.5

Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
You can only see the k numbers in the window. Each time the sliding window moves right by one position.
Your job is to output the median array for each window in the original array.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

Window position                Median
---------------               -----
[1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
Therefore, return the median sliding window as [1,-1,-1,3,5,6].

Note:
You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
 */

/**
 * Approach 1: Two PriorityQueue (maxHeap + minHeap)
 * 这道题目与 Find Median from Data Stream 十分的类似
 * https://github.com/cherryljr/LeetCode/blob/master/Find%20Median%20from%20Data%20Stream.java
 * 只是这道题目加入了一个滑动窗口而已。相当于一个 Follow Up
 * 因此我们只需要在这基础之上，当窗口滑动时 加入一个 add操作 和 remove操作 即可。
 * 其他与 Find Median from Data Stream 相同。
 *
 * 时间复杂度分析：
 *  因为 PriorityQueue 每次移除一个特定元素需要 O(n) 的时间复杂度。
 *  总共有 n 个元素，因此总体时间复杂度为：O(n^2)
 */
class Solution {
    // 这里写 比较方法 的时候不能写成：b-a,不让会因为 int 类型越界导致排序发生错误（test case 的数非常大）
    // 这也告诉我们数值比较时，务必使用自带的 compareTo 方法！！！（要自己写的话，务必考虑越界问题）
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        double[] rst = new double[nums.length - k + 1];
        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) {
                rst[i - k] = getMedian();
                remove(nums[i - k]);
            }
            if (i < nums.length) {
                add(nums[i]);
            }
        }

        return rst;
    }

    private void add(int value) {
        if (value > getMedian()) {
            minHeap.offer(value);
        } else {
            maxHeap.offer(value);
        }

        // Check the size of two heap is whether balanced or not
        // if not equal then maxHeap will be larger by one
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    private double getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }

        // if the two heaps' size is the same, get the average.
        // remember to convert int into double
        if (maxHeap.size() == minHeap.size()) {
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        // if the size is not the same, return the top value of maxHeap
        // because the size of maxHeap is larger than minHeap by one.
        } else {
            return maxHeap.peek();
        }
    }

    private void remove(int value) {
        if (value <= getMedian()) {
            maxHeap.remove(value);
        } else {
            minHeap.remove(value);
        }

        // Remove a value may cause the data structure lost balance,
        // so remember to rebalance the two heap here.
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
}

/**
 * Approach 2: Two TreeSet (maxSet + minSet)
 * 在 Approach 1 中我们使用了 PriorityQueue 这个数据结构来实现，
 * 其 remove() 操作耗费了 O(n) 的时间使得整体的时间复杂度提升到了 O(n^2) 的级别。
 * 于是我们可以想到之前解决过的一道问题：Building Outline
 * https://github.com/cherryljr/LintCode/blob/master/Building%20Outline.java
 * 在那里我们使用了 TreeMap 来替代 PriorityQueue 来实现 O(logn) 的 remove() 操作。
 * 同理，在这里我们也可以使用同样的方法。
 * 但是这里有点小小的区别，我们不在再使用 TreeMap,而是使用更为简洁一些的 TreeSet 即可。
 * 使用 TreeMap 的问题点在于：本题需要维持 左右两个部分大小(size) 的平衡，并且输入数组中包含 重复元素。
 *  因此无法直接使用 size() 方法（可以通过维护 maxSize / minSize 这两个参数来进行比较），
 *  并且在使用 pollFirstEntry() 之前需要判断 firstEntry 是否只有一个。并分情况处理。
 *  以上就是使用 TreeMap 的注意点（Building Outline中其实也有所体现，但是因为比较简单，就没太在意）
 *
 * 当然使用 TreeSet 也是有一些需要注意的地方：
 *  1. 我们都知道 Set 是不能储存重复元素的，那么首先如何解决 储存重复元素 的问题呢？
 *  我们可以使用储存元素的 数组下标 而非直接储存数组元素的值，这样就能保证唯一性的同时也能够储存重复元素。
 *  2. 因为储存的是 数组下标，所以我们需要写一个 比较器 来帮助我们进行排序。
 *  值得注意的是：虽然数组下标不会相等，但是其对应的数组元素值可能相等，
 *  因此在写 比较器 时要注意 nums[a] == nums[b] 的情况，并进行处理，不然会导致无法存储重复的元素。
 *  3. 当 remove 操作没被正常执行时，我们需要去 另一半 中将它 remove 掉。这种情况会发生在 连续3个元素均相等的情况下。
 *  比如：nums[7] = nums[8] = nums[9]，8和9 在 maxSet 中，7在 minSet 中。当我们要 remove 7 时，
 *  会根据 nums[7] 的值，判断去 maxSet 中执行 remove,但是这个操作是无法成功的，因为 7 在 minSet 中。
 *  (这一点很难被发现，大家一定要注意！)
 *  
 * 时间复杂度分析：因为 TreeSet 的 remove 操作时间复杂度为 O(logn),因此总体时间复杂度为：O(nlogn)
 */
class Solution {
    TreeSet<Integer> maxSet;
    TreeSet<Integer> minSet;
    int[] nums;

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        this.nums = nums;
        // 注意处理 nums[a]==nums[b] 的情况，不然会导致重复元素的 indices 无法被加入 
        Comparator<Integer> compare = (a, b) -> nums[a] == nums[b] ? a - b : Integer.compare(nums[a], nums[b]);
        maxSet = new TreeSet<>(compare.reversed());
        minSet = new TreeSet<>(compare);
        double[] rst = new double[nums.length - k + 1];
        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) {
                rst[i - k] = getMedian();
                remove(i - k);
            }
            if (i < nums.length) {
                add(i);
            }
        }

        return rst;
    }

    // 使用 TreeSet 储存数组元素对应的下标来保证唯一性
    private void add(int index) {
        if (nums[index] > getMedian()) {
            minSet.add(index);
        } else {
            maxSet.add(index);
        }

        // Check the size of two Set is whether balanced or not
        // if not equal then maxSet will be larger by one
        if (maxSet.size() - minSet.size() > 1) {
            minSet.add(maxSet.pollFirst());
        } else if (maxSet.size() < minSet.size()) {
            maxSet.add(minSet.pollFirst());
        }
    }

    private double getMedian() {
        if (maxSet.isEmpty() && minSet.isEmpty()) {
            return 0;
        }

        // if the two sets' size is the same, get the average.
        // remember to convert int into double
        if (maxSet.size() == minSet.size()) {
            return ((double)nums[maxSet.first()] + (double)nums[minSet.first()]) / 2.0;
        // if the size is not the same, return the top value of maxSet
        // because the size of maxSet is larger than minSet by one.
        } else {
            return nums[maxSet.first()];
        }
    }
    
    private void remove(int index) {
        // remove时非常容易被忽略的点，参见分析 TreeSet 注意点3
        if (nums[index] <= getMedian()) {
            if (!maxSet.remove(index)) {
                minSet.remove(index);
            }
        } else {
            if (!minSet.remove(index)) {
                maxSet.remove(index);
            }
        }

        // Remove a value may cause the data structure lost balance,
        // so remember to rebalance the two heap here.
        if (maxSet.size() - minSet.size() > 1) {
            minSet.add(maxSet.pollFirst());
        } else if (maxSet.size() < minSet.size()) {
            maxSet.add(minSet.pollFirst());
        }
    }
}
