'''
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
'''

# Approach: Sweep Line (Similar to Meeting Rooms II)
# 时间复杂度：O(nlogn)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件 Approach 1
class Solution:
    def carPooling(self, trips: List[List[int]], capacity: int) -> bool:
        points = []
        for num, start, end in trips:
            points.append((start, num))
            points.append((end, -num))
        count = 0
        for point in sorted(points):
            count += point[1]
            if count > capacity: return False
        return True

# Approach: Traverse Array
# 时间复杂度：O(n)
# 空间复杂度：O(m) m is the maximum distance between two stops
# 解法详解参考同名 java 文件 Approach 2
class Solution:
    def carPooling(self, trips: List[List[int]], capacity: int) -> bool:
        stops = [0] * (max(x for _,_,x in trips) + 1)
        for trip in trips:
            stops[trip[1]] += trip[0]
            stops[trip[2]] -= trip[0]
        for i in range(len(stops)):
            if capacity < 0: return False
            capacity -= stops[i]
        return capacity >= 0