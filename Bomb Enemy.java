/*
Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero),
return the maximum enemies you can kill using one bomb.
The bomb kills all the enemies in the same row and column from the planted point
until it hits the wall since the wall is too strong to be destroyed.
Note that you can only put the bomb at an empty cell.

Example:
For the given grid

0 E 0 0
E 0 W E
0 E 0 0

return 3. (Placing a bomb at (1,1) kills 3 enemies)
 */
 
 /**
 * Approach 1: Brute Force
 * 最开始想出的方法十分直观暴力：
 * 遍历整个二维数组，计算出每个可以放炸弹的地方（字符'0'）可以炸死几个敌人，取最大值即可。
 * 具体算法如下：
 * 建立四个累加数组count_lr, count_rl, count_ud, count_du。
 * 其中 count_lr 是水平方向从左到右的累加数组；count_rl 是水平方向从右到左的累加数组；
 * count_ud 是竖直方向从上到下的累加数组；count_du 是竖直方向从下到上的累加数组。
 * 我们建立好这个累加数组后，对于任意位置(i, j)，其可以炸死的最多敌人数就是：
 *  count_lr[i][j] + count_rl[i][j] + count_ud[i][j] + count_du[i][j]
 * 最后我们通过比较每个位置的累加和，就可以得到结果。
 * 
 * 时间复杂度为：O(4mn).   需要对整个二维数组进行 4 个方向上的遍历
 * 空间复杂度为：O(4mn).   用于储存 4 个方向上累计的可以炸死敌人的数量
 */
class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int[][] count_lr = new int[rows][cols];
        int[][] count_rl = new int[rows][cols];
        int[][] count_ud = new int[rows][cols];
        int[][] count_du = new int[rows][cols];
        int rst = 0;

        for (int i = 0; i < rows; i++) {
            // 矩阵中[i, j]位置上，水平方向从左到右累加的敌人数量
            for (int j = 0; j < cols; j++) {
                int temp = (j == 0 || grid[i][j] == 'W') ? 0 : count_lr[i][j - 1];
                count_lr[i][j] = grid[i][j] == 'E' ? temp + 1 : temp;
            }
            // 矩阵中[i, j]位置上，水平方向从右到左累加的敌人数量
            for (int j = cols - 1; j >= 0; j--) {
                int temp = (j == cols - 1 || grid[i][j] == 'W') ? 0 : count_rl[i][j + 1];
                count_rl[i][j] = grid[i][j] == 'E' ? temp + 1 : temp;
            }
        }

        for (int j = 0; j < cols; j++) {
            // 矩阵中[i, j]位置上，竖直方向从上到下累加的敌人数量
            for (int i = 0; i < rows; i++) {
                int temp = (i == 0 || grid[i][j] == 'W') ? 0 : count_ud[i - 1][j];
                count_ud[i][j] = grid[i][j] == 'E' ? temp + 1 : temp;
            }
            // 矩阵中[i, j]位置上，竖直方向从下到上累加的敌人数量
            for (int i = rows - 1; i >= 0; i--) {
                int temp = (i == rows - 1 || grid[i][j] == 'W') ? 0 : count_du[i + 1][j];
                count_du[i][j] = grid[i][j] == 'E' ? temp + 1 : temp;
            }
        }

        // 计算每个可以放炸弹的位置上，可以炸死几个敌人
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '0') {
                    rst = Math.max(rst, count_lr[i][j] + count_rl[i][j] + count_ud[i][j] + count_du[i][j]);
                }
            }
        }
        return rst;
    }
}

/**
 * Approach 2: Optimized Method
 * 这是在 Discussion 里面看到的一个方法，比较巧妙。具体做法如下：
 * 首先，我们需要一个 rowCnt 变量，用来记录到下一个墙之前的敌人个数。
 * 还需要一个数组colCnt[n]，其中colCnt[j]表示第j列到下一个墙之前的敌人个数。
 * 算法思路是遍历整个数组grid，对于一个位置grid[i][j]:
 * 对于水平方向，如果当前位置是开头一个或者前面一个是墙壁，我们开始从当前位置往后遍历，遍历到末尾或者墙的位置停止，计算敌人个数;
 * 对于竖直方向也是同样，如果当前位置是开头一个或者上面一个是墙壁，我们开始从当前位置向下遍历，遍历到末尾或者墙的位置停止，计算敌人个数.
 *
 * 可能会有人有疑问，为什么 rowCnt 就可以用一个变量，而 colCnt 就需要用一个数组呢? 为什么 colCnt 不能也用一个变量呢？
 * 原因是由我们的遍历顺序决定的，我们是逐行遍历的，在每行的开头就统计了每次可被同时消灭的敌人总数，所以再该行遍历没必要用数组。
 * 但是每次移动时就会换到不同的列，我们总不能没换个列就重新统计一遍吧，所以就在第一行时一起统计了存到数组中供后来使用。
 * 有了水平方向和竖直方向敌人的个数，那么如果当前位置是0，表示可以放炸弹，我们更新结果 rst 即可。
 *
 * 时间复杂度：O(mn)  空间复杂度: O(n)
 */
class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int rowCnt = 0;
        int[] colCnt = new int[cols];
        int rst = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If left is wall / bound
                // 1. reset rowCnt
                // 2. scan rightwards to count enemies
                // 3. update rowCnt
                if (j == 0 || grid[i][j - 1] == 'W') {
                    rowCnt = 0;
                    for (int k = j; k < cols && grid[i][k] != 'W'; k++) {
                        rowCnt += grid[i][k] == 'E' ? 1 : 0;
                    }
                }
                // If above is wall
                // 1. reset colCnt[j]
                // 2. scan downwards to count enemies
                // 3. update colCnt[j]
                if (i == 0 || grid[i - 1][j] == 'W') {
                    colCnt[j] = 0;
                    for (int k = i; k < rows && grid[k][j] != 'W'; k++) {
                        colCnt[j] += grid[k][j] == 'E' ? 1 : 0;
                    }
                }

                // If this grid is empty, update result
                if (grid[i][j] == '0') {
                    rst = Math.max(rst, rowCnt + colCnt[j]);
                }
            }
        }

        return rst;
    }
}