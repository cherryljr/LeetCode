我们需要判断的有
    1. 每行 1~9 元素出现且仅出现一次，即当前行各个元素（除去空白部分），出现的元素不能重复。
    2. 每列 1~9 元素出现且仅出现一次，即当前列各个元素（除去空白部分），出现的元素不能重复。
    3. 每一个 3*3 的小方格中 1~9 元素出现且仅出现一次，即各个小方格中不能出现重复的元素。
鉴于以上不重复的特性，我们想到了使用 HashSet 这个数据结构来解决该题。
    
/*
Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.

The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
imapge: https://leetcode.com/problems/valid-sudoku/description/
A partially filled sudoku which is valid.

Note:
A valid Sudoku board (partially filled) is not necessarily solvable. Only the filled cells need to be validated.
*/

// Approach 1
class Solution {
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> seen = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char number = board[i][j];
                /*
                 * 将number在 第i行,j列,i/3 - j/3 的小方格中对应的三个信息全部加入到 Set 中 
                 * 一旦其中任何一个无法正常添加（说明出现重复），则说明不是有效的数独，返回 false.
                 */
                if (number != '.') {
                    if (!seen.add(number + " in row " + i) ||
                        !seen.add(number + " in column " + j) ||
                        !seen.add(number + " in cube " + i/3 + "-" + j/3)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

// Approach 2
class Solution {
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9; j++) {
                if(board[i][j] != '.' && !rows.add(board[i][j]))
                    return false;
                if(board[j][i] != '.' && !columns.add(board[j][i]))
                    return false;
                int RowIndex = 3 * (i / 3);
                int ColIndex = 3 * (i % 3);
                if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
                    return false;
            }
        }
        return true;
    }
}