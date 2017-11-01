Obverse the picture, we can solve the problem by mathematical method.
    1. loop over the matrix and count the number of islands;
    2. if the current cell is an island, count if it has any right neighbour or down neighbour;
    3. the result is islands * 4 - neighbours * 2

/*
You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. 
Grid cells are connected horizontally/vertically (not diagonally). 
The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells). 
The island doesn't have "lakes" (water inside that isn't connected to the water around the island). 
One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. 
Determine the perimeter of the island.

Example:
[[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

Answer: 16

Explanation: The perimeter is the 16 yellow stripes in the image below:
https://leetcode.com/static/images/problemset/island.png
*/
    
class Solution {
    public int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0
           || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        
        int count = 0;
        int neighbours = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // count islands
                if (grid[i][j] == 1) {
                    count++;
                    // count down neighbours
                    if (i < grid.length - 1 && grid[i + 1][j] == 1) {
                        neighbours++;
                    }
                    // count right neighbours
                    if (j < grid[i].length - 1 && grid[i][j + 1] == 1) {
                        neighbours++;
                    }
                }
            }
        }
        
        return count * 4 - neighbours * 2;
    }
}