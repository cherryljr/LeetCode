Basic Questioin about HashMap.
For every i, we capture the number of points equidistant from i. 
Now for this i, we have to calculate all possible permutations of (n, k) from these equidistant points.
Total number of permutations of size 2 from n different points is P(n, 2) = n!/(n-2)! = n * (n-1).

Time complexity:  O(n^2)
Space complexity: O(n)

/*
Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) 
such that the distance between i and j equals the distance between i and k (the order of the tuple matters).
Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the range [-10000, 10000] (inclusive).

Example:
Input:
[[0,0],[1,0],[2,0]]
Output:
2

Explanation:
The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
*/

class Solution {
    public int numberOfBoomerangs(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        int rst = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j) {   // skip the same point
                    continue;
                }
                
                int dis = getDistance(points[i], points[j]);
                map.put(dis, map.getOrDefault(dis, 0) + 1); // getOrDefault(Object, V) is a new method in JDK8
            }
            for (int val : map.values()) {
                // Total number of permutations of size 2 from n different points is P(n, 2) = n*(n-1).
                rst += val * (val - 1);
            }
            // After traverse the points based on points[i], remember to clear the map.
            map.clear();
        }
        
        return rst;
    }
    
    private int getDistance(int[] point1, int[] point2) {
        int disx = point1[0] - point2[0];
        int disy = point1[1] - point2[1];
        return disx * disx + disy * disy;
    }
}