The algorithm is quite simple: 
    Use DFS starting from 'O's on the boundary and alter them to '*', 
    then iterate over the whole board and alter 'O' to 'X' and '*' to 'O'.
The DFS method is the same as Question: Number of Islands

/*
Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
A region is captured by flipping all 'O's into 'X's in that surrounded region.

For example,
X X X X
X O O X
X X O X
X O X X

After running your function, the board should be:
X X X X
X X X X
X X X X
X O X X
*/
    
class Solution {
    /*
     * @param board: board a 2D board containing 'X' and 'O'
     * @return: nothing
     */
    public void solve(char[][] board) {
        if (board == null || board.length <= 2 || 
            board[0] == null || board[0].length <= 2) {
            return;
        }
        
        int rows = board.length;
        int cols = board[0].length;
        // Any 'O' connected to a boundary can't be turned to 'X', so ...
	    // Start from first and last column, turn 'O' to '*' and start infection
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') {
                infect(board, i, 0, rows, cols);
            }
            if (board[i][cols - 1] == 'O') {
                infect(board, i, cols - 1, rows, cols);
            }
        }
        // Start from first and last row, turn '0' to '*' and start infection
        for (int j = 0; j < cols; j++) {
            if (board[0][j] == 'O') {
                infect(board, 0, j, rows, cols);
            }
            if (board[rows - 1][j] == 'O') {
                infect(board, rows - 1, j, rows, cols);
            }
        }
        
        // post-prcessing, turn 'O' to 'X', '*' back to 'O', keep 'X' intact.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }
    
    // Use DFS to turn internal however boundary-connected 'O' to '*';
    private void infect(char[][] board, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || board[i][j] != 'O') {
            return;
        }
        board[i][j] = '*'; // Mark the explored island cells with '*'.
        infect(board, i + 1, j, rows, cols);
        infect(board, i - 1, j, rows, cols);
        infect(board, i, j + 1, rows, cols);
        infect(board, i, j - 1, rows, cols);
    }
}
