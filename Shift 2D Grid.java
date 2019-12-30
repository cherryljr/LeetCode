/*
Given a 2D grid of size m x n and an integer k. You need to shift the grid k times.
In one shift operation:
    Element at grid[i][j] becomes at grid[i][j + 1].
    Element at grid[i][n - 1] becomes at grid[i + 1][0].
    Element at grid[n - 1][n - 1] becomes at grid[0][0].
Return the 2D grid after applying shift operation k times.

Example 1:
Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
Output: [[9,1,2],[3,4,5],[6,7,8]]

Example 2:
Input: grid = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]], k = 4
Output: [[12,0,21,13],[3,8,1,9],[19,7,2,5],[4,6,11,10]]

Example 3:
Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 9
Output: [[1,2,3],[4,5,6],[7,8,9]]

Constraints:
    1. m == grid.length
    2. n == grid[i].length
    3. 1 <= m <= 50
    4. 1 <= n <= 50
    5. -1000 <= grid[i][j] <= 1000
    6. 0 <= k <= 100
*/

/**
 * Approach: MOD
 * Time Complexity: O(m * n)
 * Space Complexity: O(m * n)
 * av76080725
 */
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length, mod = m * n;
        k = k % mod;
        Integer[][] newGrid = new Integer[m][n];
        for (int i = 0 ; i < mod; i++) {
            int pre = (i - k + mod) % mod;
            newGrid[i / n][i % n] = grid[pre / n][pre % n];
        }
        
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ans.add(Arrays.asList(newGrid[i]));
        }
        return ans;
    }
}