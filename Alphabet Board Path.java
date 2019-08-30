/*
On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
    https://leetcode.com/problems/alphabet-board-path/

We may make the following moves:
    ·'U' moves our position up one row, if the position exists on the board;
    ·'D' moves our position down one row, if the position exists on the board;
    ·'L' moves our position left one column, if the position exists on the board;
    ·'R' moves our position right one column, if the position exists on the board;
    ·'!' adds the character board[r][c] at our current position (r, c) to the answer.
    (Here, the only positions that exist on the board are positions with letters on them.)

Return a sequence of moves that makes our answer equal to target in the minimum number of moves.You may return any path that does so.

Example 1:
Input: target = "leet"
Output: "DDR!UURRR!!DDD!"

Example 2:
Input: target = "code"
Output: "RR!DDRR!UUL!R!"

Constraints:
    1. 1 <= target.length <= 100
    2. target consists only of English lowercase letters.
 */

/**
 * Approach: Build Path (Manhattan Distance)
 * 根据题意，我们可以清晰地知道各个字母所在的位置是确定的。
 * 并且因为只能 上下左右 进行移动，因此两点之间的最短距离实际上是确定的（曼哈顿距离）
 * 
 * 本题需要注意的点在于：'z'这个字符的特殊位置。
 * 当其他字符向 'z' 进行移动时，必须先向左移动；当'z'向其他字符移动时，必须先向上移动。
 * 否则就会出现路径不合法的情况。
 * 因此我们可以得知：'U'和'L' 的操作优先级必须要高于 'D'和'R' （后者两个移动方向是不会出现非法路径问题的）
 * 有了以上结论，我们只需要按照这个顺序把路径添加到结果中即可。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public String alphabetBoardPath(String target) {
        StringBuilder ans = new StringBuilder();
        int preRow = 0, preCol = 0;
        for (int i = 0; i < target.length(); i++) {
            int currRow = (target.charAt(i) - 'a') / 5;
            int currCol = (target.charAt(i) - 'a') % 5;
            addPath(currRow, preRow, currCol, preCol, ans);
            ans.append('!');
            preRow = currRow;
            preCol = currCol;
        }
        return ans.toString();
    }

    private void addPath(int currRow, int preRow, int currCol, int preCol, StringBuilder ans) {
        // Must be in order
        while (currRow < preRow) {
            ans.append('U');
            preRow--;
        }
        while (currCol < preCol) {
            ans.append('L');
            preCol--;
        }
        while (currCol > preCol) {
            ans.append('R');
            preCol++;
        }
        while (currRow > preRow) {
            ans.append('D');
            preRow++;
        }
    }
}