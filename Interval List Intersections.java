/*
Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
Return the intersection of these two interval lists.

(Formally, a closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.
The intersection of two closed intervals is a set of real numbers that is either empty, or can be represented as a closed interval.
For example, the intersection of [1, 3] and [2, 4] is [2, 3].)

Example 1:
Input: A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
Reminder: The inputs and the desired output are lists of Interval objects, and not arrays or lists.

Note:
    1. 0 <= A.length < 1000
    2. 0 <= B.length < 1000
    3. 0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9
 */

/**
 * Approach 1: Scan Line (Sweep Line)
 * 与 Time Intersection 这道题目几乎一样，换了个问法而已。
 * 唯一小小的区别在于当 A 在某个点结束的时候，B正好在这个点开始，
 * 则该点也算一个 overlap.
 * 对此我们只需要在排序规则上面做个小小的规定即可。
 * 即：当 A.index == B.index 时，将开始的 point 放在前面即可。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Time%20Intersection.java
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
    private class Point implements Comparable<Point> {
        int flag, index;

        Point(int index, int flag) {
            this.index = index;
            this.flag = flag;
        }

        @Override
        public int compareTo(Point other) {    
            // 当两个 piont 的 index相等 时，将代表开始的 point 放在前面
            return this.index - other.index == 0 ? other.flag - this.flag : this.index - other.index;
        }
    }

    public Interval[] intervalIntersection(Interval[] A, Interval[] B) {
        List<Point> list = new ArrayList<>();
        for (Interval interval : A) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        for (Interval interval : B) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        Collections.sort(list);

        List<Interval> ans = new ArrayList<>();
        int preIndex = 0, preCount = 0;
        int count = 0;
        for (Point p : list) {
            count += p.flag;
            if (count == 1 && preCount == 2) {
                ans.add(new Interval(preIndex, p.index));
            }
            preCount = count;
            preIndex = p.index;
        }

        // 将一个 list 转换成 数组 返回
	return ans.toArray(new Interval[ans.size()]);
    }
}

/**
 * Approach 2: Two Pointers
 * 这个做法更加高效，在思路上也很顺。
 * （在 Contest 中用的其实就是这个做法，跟我们平常生活中做 overlap 一样）
 * 首先定义两个下边 i, j 用于跟踪 A[], B[]，然后向后推进，
 * 直到其中有一者移动到了末尾，则结束循环。
 *
 * 然后作为一个 overlap，其起点必定是 a,b 中的最大值，而终点必定是 a,b 中的最小值。
 * 并且 endMin <= startMax。
 * 通过上述两个条件，我们就能够找出一个 overlap.
 *
 * 最后我们需要找出该 overlap 以哪个 interval 的结尾作为end.
 * 并将对应的 interval index 向后推进一个位置。
 *
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(m + n)
 *
 * 这道问题与其变形题 Merge Intervals 非常类似。
 * 不同的是，一道是求并集，一道是求交集。但是主体思路都是相同的。
 * Merge Intervals:
 *  https://github.com/cherryljr/LintCode/blob/master/Merge%20Intervals.java
 */
class Solution {
    public Interval[] intervalIntersection(Interval[] A, Interval[] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new Interval[]{};
        }

        List<Interval> ans = new ArrayList<>();
        int n = A.length, m = B.length;
        int i = 0, j = 0;
        while (i < n && j < m) {
            Interval a = A[i], b = B[j];

            // find the overlap... if there is any...
            int startMax = Math.max(a.start, b.start);
            int endMin = Math.min(a.end, b.end);
            if (startMax <= endMin) {
                ans.add(new Interval(startMax, endMin));
            }

            // move the index of two array forward
            if (a.end == endMin) {
                i++;
            }
            if (b.end == endMin) {
                j++;
            }
        }

        return ans.toArray(new Interval[ans.size()]);
    }
}
