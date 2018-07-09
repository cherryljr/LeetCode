/*
According to the Wikipedia's article: "The Game of Life, also known simply as Life,
is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

Given a board with m by n cells, each cell has an initial state live (1) or dead (0).
Each cell interacts with its eight neighbors (horizontal, vertical, diagonal)
using the following four rules (taken from the above Wikipedia article):
    Any live cell with fewer than two live neighbors dies, as if caused by under-population.
    Any live cell with two or three live neighbors lives on to the next generation.
    Any live cell with more than three live neighbors dies, as if by over-population..
    Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
Write a function to compute the next state (after one update) of the board given its current state.
The next state is created by applying the above rules simultaneously to every cell in the current state,
where births and deaths occur simultaneously.

Example:
Input:
[
  [0,1,0],
  [0,0,1],
  [1,1,1],
  [0,0,0]
]
Output:
[
  [0,0,0],
  [1,0,1],
  [0,1,1],
  [0,1,0]
]

Follow up:
Could you solve it in-place? Remember that the board needs to be updated at the same time:
You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array.
In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array.
How would you address these problems?
 */

/**
 * Approach: Simulation + Bit Operation
 * 模拟题而已，但是如果想要实现 Fellow Up 中的 in-place 我们就需要使用 Bit Operation.
 * 用一个数来记录其状态。高位表示 next state，低位表示 current state.
 * 然后需要获得下一个状态只需要进行一次 右移 操作即可。
 * 用 0 表示死亡，1 表示存活即可。
 * 初始状态时，高位全部都为 0.因此我们只需要关心什么时候会发生 0 -> 1 即可。
 * 有题意可归纳出有：
 *  1.当有 2个 或 3个 存活的邻居时，该节点可以活到下一轮。
 *  2.当有 3 个存活的邻居时，该节点可以被复活。
 * 因此以上 4 种条件情况可以被状态压缩为：
 *  1. lives == 3 （包括2个存活，和复活情况）
 *  2. lives == 4 && lives - board[x][y] = 3  =>  lives - board[x][y] == 3（3个存活的邻居）
 *
 * 时间复杂度为：O(mn)
 * 空间复杂度为：O(1)
 *
 * 参考资料：
 *  https://leetcode.com/problems/game-of-life/discuss/73223/Easiest-JAVA-solution-with-explanation
 */
class Solution {
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int lives = 0;
                // Scan 3x3 region including (i, j)
                for (int x = Math.max(0, i - 1); x < Math.min(rows, i + 2); x++) {
                    for (int y = Math.max(0, j - 1); y < Math.min(cols, j + 2); y++) {
                        lives += board[x][y] & 1;
                    }
                }
                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (lives == 3 || lives - board[i][j] == 3) {
                    board[i][j] |= 0b10;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Get the next state
                board[i][j] >>= 1;
            }
        }
    }
}