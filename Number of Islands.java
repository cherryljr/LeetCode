The algorithm works as follow:
    1. Scan each cell in the grid.
    2. If the cell value is '1', explored that island and start infection (DFS)
    3. Mark the explored island cells with 'x'.
    4. Once finished exploring that island, increment islands counter.

You can get detail explianations here: https://github.com/cherryljr/NowCoder/blob/master/%E5%B2%9B%E5%B1%BF%E4%B8%AA%E6%95%B0.java

/*
Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
You may assume all four edges of the grid are all surrounded by water.

Example 1:
11110
11010
11000
00000
Answer: 1

Example 2:
11000
11000
00100
00011
Answer: 3
*/
    
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || 
            grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        
        int rst = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    infect(grid, i, j, rows, cols);
                    rst++;
                }
            }
        }
        
        return rst;
    }
    
    // Using DFS Method to find the region
    private void infect(char[][] grid, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
            return;
        }
        // Mark the explored island cells with 'x'.
        grid[i][j] = 'x'; 
        // Use the directions array to make code more concise
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        for (int[] dir : dirs) {
            infect(grid, i + dir[0], j + dir[1], rows, cols);
        }
    }
}
