/*
Given n and m which are the dimensions of a matrix initialized by zeros and given an array indices where indices[i] = [ri, ci].
For each pair of [ri, ci] you have to increment all cells in row ri and column ci by 1.

Return the number of cells with odd values in the matrix after applying the increment to all indices.

Example 1:
Input: n = 2, m = 3, indices = [[0,1],[1,1]]
Output: 6
Explanation: Initial matrix = [[0,0,0],[0,0,0]].
After applying first increment it becomes [[1,2,1],[0,1,0]].
The final matrix will be [[1,3,1],[1,3,1]] which contains 6 odd numbers.

Example 2:
Input: n = 2, m = 2, indices = [[1,1],[0,0]]
Output: 0
Explanation: Final matrix = [[2,2],[2,2]]. There is no odd number in the final matrix.

Constraints:
1 <= n <= 50
1 <= m <= 50
1 <= indices.length <= 100
0 <= indices[i][0] < n
0 <= indices[i][1] < m
Discuss


*/

/**
 * Approach: Simulation
 * Count the rows and columns that appear odd times;
 * Traverse all cells to get the answer.
 *
 * Time Complexity: O(M*N + L), where L = indices.length.
 * Space Complexity: O(M + N)
 */

/**
 * Approach 2: Mathematics
 * We actually only care about the number of rows and columns with odd times of increments respectively.
 * 1. Count the rows and columns that appear odd times;
 * 2. Compute the number of cells in aforementioned rows and columns respectively, 
 * then both deduct the cells located on odd rows and odd columns 
 * (they become evens, because odd + odd results even).
 * Note: Easier alternative way for 2 is odd_cols * even_rows + even_cols * odd_rows
 *
 * Time Complexity: O(L + M + N), where L = indices.length.
 * Space Complexity: O(M + N)
 *
 * PS.根据数据规模的不同，如果矩阵特别大，即 n,m 值非常大。那么可以利用 Map 替代 Array。
 * 因为操作个数总共只有 L 次，所以空间复杂度为 O(L)
 * 
 * References:
 *  https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/discuss/425100/JavaPython-3-2-methods%3A-time-O(m-*-n-%2B-L)-and-O(L)-codes-w-comment-and-analysis
 *  av75189947
 */
class Solution {
    public int oddCells(int n, int m, int[][] indices) {
        boolean[] rows = new boolean[n], cols = new boolean[m];
        for (int[] indice : indices) {
            rows[indice[0]] ^= true;
            cols[indice[1]] ^= true;
        }
        
        int rowOdds = 0, colOdds = 0;
        for (int i = 0; i < n; i++) {
            rowOdds += rows[i] ? 1 : 0;
        }
        for (int i = 0; i < m; i++) {
            colOdds += cols[i] ? 1 : 0;
        }
        return rowOdds * (m - colOdds) + colOdds * (n - rowOdds);
        
    }
}