/*
Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
You may assume that the intervals were initially sorted according to their start times.

Example 1:
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

Example 2:
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 */

/**
 * Approach 1: Scan Line (Sweep Line)
 * 这道题目属于 Merge Intervals 的一个变种。
 * 做法基本相同，只是多加了一个 newInterval.
 * 采用扫描线做法的话，代码几乎没变，只是多了两次 add 操作罢了。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * Merge Intervals:
 *  https://github.com/cherryljr/LintCode/blob/master/Merge%20Intervals.java
 */

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Point> list = new ArrayList<>();
        for (Interval interval : intervals) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        list.add(new Point(newInterval.start, 1));
        list.add(new Point(newInterval.end, -1));
        Collections.sort(list);

        List<Interval> rst = new LinkedList<>();
        int preCount = 0, count = 0;
        int start = 0;
        for (Point point : list) {
            count += point.flag;
            if (preCount == 0 && count > 0) {
                start = point.index;
            }
            if (preCount > 0 && count == 0) {
                rst.add(new Interval(start, point.index));
                start = point.index;
            }
            preCount = count;
        }
        return rst;
    }

    class Point implements Comparable<Point> {
        int index, flag;

        Point(int index, int flag) {
            this.index = index;
            this.flag = flag;
        }

        @Override
        public int compareTo(Point other) {
            return this.index - other.index == 0 ? other.flag - this.flag
                    : this.index - other.index;
        }
    }
}

/**
 * Approach 2: Insert Sort + Greedy
 * 与 Merge Intervals 相同，本题还可以通过相同的贪心策略来解决。
 * 但是该题相比于 Merge Intervals 有两点最大的不同：
 *  1. 给定的 intervals 事先已经按照 start time 排序好了
 *  2. intervals 之间没有重复部分。
 * 依据上面两个条件，本题可以做出如下优化：
 *  1. 将 newInterval 按照 插入排序 的方式插入到 list 中。（排序依据为 start time）
 *  这样可以将原本 O(nlogn) 的排序时间缩短到 O(n)
 *  2. 因为原本的 intervals 之间没有重复部分，那么就说明重复部分只可能在 newInterval
 *  的范围内产生，那么我们只需要处理 newInterval 的范围即可。
 *  处理方法与 Merge Intervals 相同。（依据前一个的结束点与当前点的起点大小进行判断，来相应调整范围大小）
 * 因此最终结果由 3 部分组成：
 *  左侧的无相交部分 + 中部的相交部分(merge) + 右侧的无相交部分。
 *  总体为一个插入排序。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> rst = new LinkedList<>();
        int index = 0;
        // Left Part
        // add all the intervals ending before newInterval starts
        while (index < intervals.size() && intervals.get(index).end < newInterval.start) {
            rst.add(intervals.get(index++));
        }
        // Middle Part (Overlapping Part)
        // merge all overlapping intervals to one considering newInterval
        while (index < intervals.size() && intervals.get(index).start <= newInterval.end) {
            // mutate newInterval range here
            newInterval = new Interval(
                    Math.min(newInterval.start, intervals.get(index).start),
                    Math.max(newInterval.end, intervals.get(index).end));
            index++;
        }
        // add the union of intervals we got
        rst.add(newInterval);
        // Right Part
        // add all the rest
        while (index < intervals.size()) {
            rst.add(intervals.get(index++));
        }
        return rst;
    }
}