Using Bakctracking

A Better Approach:
https://discuss.leetcode.com/topic/13314/singapore-prime-minister-lee-hsien-loong-s-sudoku-solver-code-runs-in-1ms
  
/*
Write a program to solve a Sudoku puzzle by filling the empty cells.

Empty cells are indicated by the character '.'.

You may assume that there will be only one unique solution.
image:https://leetcode.com/problems/sudoku-solver/description/

A sudoku puzzle...
image:https://leetcode.com/problems/sudoku-solver/description/

...and its solution numbers marked in red.
*/

class Solution {
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        solve(board);
    }
    
    private boolean solve(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == '.') {
                    // trial. Try 1 through 9
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; // Put c for this cell
                            
                            if (solve(board)) {
                                // If it's the solution return true
                                return true;
                            } else {
                                // Otherwise go back
                                board[i][j] = '.';
                            }
                        }
                    }
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // check rows
            if (board[i][col] != '.' && board[i][col] == c) {
                return false;
            }
            // check columns
            if (board[row][i] != '.' && board[row][i] == c) {
                return false;
            }
            // check 3*3 blocks
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.' && 
                board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                return false; 
            }
        }
        return true;
    }
}