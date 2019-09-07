/*
Given a 2D grid of 0s and 1s, return the number of elements in the largest square subgrid that has all 1s on its border,
or 0 if such a subgrid doesn't exist in the grid.

Example 1:
Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
Output: 9

Example 2:
Input: grid = [[1,1,0,0]]
Output: 1

Constraints:
    1. 1 <= grid.length <= 100
    2. 1 <= grid[0].length <= 100
    3. grid[i][j] is 0 or 1
 */

/**
 * Approach: Build Horizon and Vertical PreSum Array
 * This question is same as Given a matrix of ‘O’ and ‘X’, find the largest subsquare surrounded by 'X' in GeeksforGeeks.
 *
 * The idea is to create two auxiliary arrays hor[m][n] and ver[m][n].
 * The value stored in hor[i][j] is the number of horizontal continuous 1 till grid[i][j] in grid[][].
 * Similarly, the value stored in ver[i][j] is the number of vertical continuous 1 till grid[i][j] in grid[][].
 *
 * Once we have filled values in hor[][] and ver[][], we start from the bottommost-rightmost corner of matrix
 * and move toward the leftmost-topmost in row by row manner.
 * For every visited entry grid[i][j], we compare the values of hor[i][j] and ver[i][j], and pick the smaller of two as we need a square.
 * Let the smaller of two be 'small'. After picking smaller of two, we check if both ver[][] and hor[][] for left and up edges respectively.
 * If they have entries for the same, then we found a subsquare. Otherwise we try for small-1.
 *
 * Time Complexity: O(n^3)
 * Space Complexity: O(n^2)
 *
 * References:
 *  https://www.geeksforgeeks.org/given-matrix-o-x-find-largest-subsquare-surrounded-x/
 *  https://leetcode.com/problems/largest-1-bordered-square/discuss/345265/c%2B%2B-beats-100-(both-time-and-memory)-concise-with-algorithm-and-image
 */
class Solution {
    public int largest1BorderedSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] hor = new int[m][n], ver = new int[m][n];
        // Build the Horizon and Vertical PreSum Array from left-top
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    hor[i][j] = j == 0 ? 1 : hor[i][j - 1] + 1;
                    ver[i][j] = i == 0 ? 1 : ver[i - 1][j] + 1;
                }
            }
        }

        // Start from the rightmost-bottommost corner element and
        // find the largest subsquare with the help of hor[][] and ver[][]
        int max = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // Find smaller of values in hor[][] and ver[][] as A Square can only be made by taking smaller value
                int small = Math.min(hor[i][j], ver[i][j]);
                /*
                 At this point, we are sure that there is a right vertical line and bottom horizontal line of length at least 'small'.
                 We found a bigger square if following conditions are met:
                 1) If side of square is greater than max.
                 2) There is a left vertical line of length >= 'small'
                 3) There is a top horizontal line of length >= 'small'
                 */
                while (small > max) {
                    if (hor[i - small + 1][j] >= small && ver[i][j - small + 1] >= small) {
                        max = small;
                    }
                    small--;
                }
            }
        }
        return max * max;
    }
}