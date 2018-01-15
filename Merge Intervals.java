/*
Given a collection of intervals, merge all overlapping intervals.

For example,
Given [1,3],[2,6],[8,10],[15,18],
return [1,6],[8,10],[15,18].
 */

/**
 * Approach: Use Sorting
 * This Problem is similar to "Minimum Number of Arrows to Burst Balloons" to some extent.
 * We can get some inspirations from here:
 * https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Number%20of%20Arrows%20to%20Burst%20Balloons.java
 *
 * Algorithm:
 * The idea is to sort the intervals by their starting points.
 * Then, we take the first interval and compare its end with the next intervals starts.
 * As long as they overlap, we update the end to be the max end of the overlapping intervals.
 * Once we find a non overlapping interval, we can add the previous “extended” interval and start over.
 *
 * Complexity Analysis
 *  Time complexity : O(nlgn)
 *  The runtime is dominated by the O(nlgn) complexity of sorting.
 *  Space complexity : O(1) (or O(n))
 *  If we can sort intervals in place, we do not need more than constant additional space. 
 *  Otherwise, we must allocate linear space to store a copy of intervals and sort that.
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
    public List<Interval> merge(List<Interval> intervals) {
        LinkedList<Interval> merged = new LinkedList<>();
        if (intervals == null || intervals.size() == 0) {
            return merged;
        }

        // Sort by ascending starting point using Collections.sort()
        Collections.sort(intervals, (a, b) -> a.start - b.start);
        for (Interval interval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (merged.isEmpty() || merged.getLast().end < interval.start) {
                merged.add(interval);
            // otherwise, there is overlap, so we merge the current and previous intervals.
            } else {
                merged.getLast().end = Math.max(merged.getLast().end, interval.end);
            }
        }

        return merged;
    }
}