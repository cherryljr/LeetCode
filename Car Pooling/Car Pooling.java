/*
You are driving a vehicle that has capacity empty seats initially available for passengers.
The vehicle only drives east (ie. it cannot turn around and drive west.)

Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the i-th trip:
the number of passengers that must be picked up, and the locations to pick them up and drop them off.
The locations are given as the number of kilometers due east from your vehicle's initial location.

Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.

Example 1:
Input: trips = [[2,1,5],[3,3,7]], capacity = 4
Output: false

Example 2:
Input: trips = [[2,1,5],[3,3,7]], capacity = 5
Output: true

Example 3:
Input: trips = [[2,1,5],[3,5,7]], capacity = 3
Output: true

Example 4:
Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
Output: true

Constraints:
    1. trips.length <= 1000
    2. trips[i].length == 3
    3. 1 <= trips[i][0] <= 100
    4. 0 <= trips[i][1] < trips[i][2] <= 1000
    5. 1 <= capacity <= 100000
 */

/**
 * Approach 1: Sweep Line (Similar to Meeting Rooms II)
 * 题目给了我们几段区间，然后分别告诉我们起点和终点，因此很容易看出是一个考察扫描线的题目。
 * 按照建点，排序，扫描分析的步骤即可解决。（当两个点位置重合时，按照先下后上的做法处理即可）
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Meeting%20Rooms%20II.java
 *  https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Airplanes%20in%20the%20Sky.java
 */
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        List<Point> list = new ArrayList<>();
        for (int[] trip : trips) {
            list.add(new Point(trip[1], trip[0]));
            list.add(new Point(trip[2], -trip[0]));
        }
        Collections.sort(list);

        int count = 0;
        for (Point point : list) {
            count += point.passengers;
            if (count > capacity) {
                return false;
            }
        }
        return true;
    }

    class Point implements Comparable<Point> {
        int location, passengers;

        public Point(int location, int num) {
            this.location = location;
            this.passengers = num;
        }

        @Override
        public int compareTo(Point other) {
            return this.location == other.location ? this.passengers - other.passengers : this.location - other.location;
        }
    }
}

/**
 * Approach 2: Traverse Array (One Pass)
 * 因为题目明确给出了 起点和终点 的范围(0 <= trips[i][1] < trips[i][2] <= 1000)。
 * 所以我们可以建立一个大小为 1001 站点数组stops，同于统计各个站点实际需要消耗掉的座位数（上车人数-下车人数）。
 * 最后，遍历一遍 stops 数组，用当前的空位减去每个站点所消耗的座位数。
 * 如果每一站都能够被容纳下（capacity >=0）则说明能够完成目标，否则不行。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(m) m is the maximum distance between two stops
 */
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        int[] stops = new int[1001];
        for (int[] trip : trips) {
            stops[trip[1]] += trip[0];
            stops[trip[2]] -= trip[0];
        }

        for (int i = 0; capacity >= 0 && i < stops.length; i++) {
            capacity -= stops[i];
        }
        return capacity >= 0;
    }
}