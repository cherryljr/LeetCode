/*
An N x N board contains only 0s and 1s. In each move, you can swap any 2 rows with each other, or any 2 columns with each other.
What is the minimum number of moves to transform the board into a "chessboard"
- a board where no 0s and no 1s are 4-directionally adjacent?
If the task is impossible, return -1.

Examples:
Input: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
Output: 2
Explanation:
One potential sequence of moves is shown below, from left to right:
    0110     1010     1010
    0110 --> 1010 --> 0101
    1001     0101     1010
    1001     0101     0101
The first move swaps the first and second column.
The second move swaps the second and third row.

Input: board = [[0, 1], [1, 0]]
Output: 0
Explanation:
Also note that the board with 0 in the top left corner,
    01
    10
is also a valid chessboard.

Input: board = [[1, 0], [1, 0]]
Output: -1
Explanation:
No matter what sequence of moves you make, you cannot end with a valid chessboard.

Note:
board will have the same number of rows and columns, a number in the range [2, 30].
board[i][j] will be only 0s or 1s.
 */

/**
 * Approach: Mathematics
 * 这道题目需要对国际象棋的 棋盘布局性质 有一定的了解，做起来才会相对简单一些。
 * 符合要求的棋盘布局必须满足以下两点条件：（充要条件）
 *  1. 对于所有的 行，同一时间内，一个棋盘中 有且只有 2 种行布局，并且其中的一种布局是另一种的相反。(异或1)
 *  举个例子来说，假设我们一直棋盘的一个行布局为："01010011",那么棋盘中其他的行布局必定是："01010011" 或是 "10101100".
 *  以上性质对于 列 同样成立。
 *  那么介于以上特性的话，我们可以有两个重要结论：
 *      ① 棋盘中的任意一个矩形内，其 左上角，右上角，左下角，右下角的点值的情况只可能是：4个0; 4个1; 2个1和2个0.
 *      ② 当棋盘的第一 行/列 都变成 "010101" 或者 "101010" 的话，那么整个棋盘就转换成了 国际象棋棋盘。
 *  2. 假设棋盘大小为 N * N.
 *  当 N = 2*K 时，棋盘的每一 行/列 都将包含 K个0 和 K个1;
 *  当 N = 2*K+1 时，棋盘的每一 行/列 都将包含 K+1个0 和 K个1 或者 K+1个1 和 K个0.
 *
 * 因为 行/列 交换并不会改变以上两个性质，且 这两个条件 是一个棋盘为国际象棋棋盘的 充要条件。
 * 因此，我们需要做的就是：
 *  1. 判断 条件1 是否成立 （利用1中的结论①）
 *  这里我们选择使用 异或 运算来进行判断的原因是：异或运算具有 传递性，即：
 *  If A^B^C^D = 0 and A^B^E^F = 0,
 *  then we get (A^B^C^D) ^ (A^B^E^F) = C^D^E^F = 0 ^ 0 = 0
 *  根据这一点我们可以选定棋盘的 左上角位置 不动，然后枚举 右下角的位置 即可。
 *  时间复杂度为 O(n^2),相比于枚举所有 subMatrix 的时间复杂度 O(n^4) 低了不少。
 *  2. 判断 条件2 是否成立
 *  3. 利用 条件1 的结论②，进行解题。这里我们假设需要将其转换成 "010101..." 的格式（当然也可以选择"101010..."）
 *  然后遍历第一 行/列，找出所有需要 Swap 操作的位置，然后加起来。
 *  则最后需要 Swap 的次数就是 = (rowSwap + colSwap) / 2
 *  (因为我们记录的是 需要被交换的位置数 之和，而一次 Swap 操作是针对 两行/列 的)。
 *  4. 同样需要注意的是：
 *      当 N 为偶数时，取两种方案中更小的 Swap 值就好了(如果"010101..."需要X，那么 "101010..."就需要 N-X)
 *      当 N 为奇数时，检查 rowSwap/colSwap 是否为奇数，如果是的话，需要将其转为偶数，比如 rowSwap = N - rowSwap.
 *      （原因上面也已经提过了，因为一次 Swap 操作的操作对象是 两个 行/列，所以不能使用奇数）
 */
class Solution {
    public int movesToChessboard(int[][] board) {
        int N = board.length;
        // 通过 条件1 判断给定的 Board 是否合法
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Top Left: board[0][0];    Top Right: board[0][j]
                // Bottom Left: board[i][0]; Bottom Right: board[i][j]
                if ((board[0][0] ^ board[0][j] ^ board[i][0] ^ board[i][j]) == 1) {
                    return -1;
                }
            }
        }

        int rowSum = 0, colSum = 0;     // 代表一个 行/列 中 元素1 的个数
        int rowSwap = 0, colSwap = 0;   // 需要进行 Swap 操作的 位置数之和
        for (int i = 0; i < N; i++) {
            rowSum += board[0][i];
            colSum += board[i][0];
            if ((i & 1) != board[i][0]) {
                rowSwap += 1;
            }
            if ((i & 1) != board[0][i]) {
                colSwap += 1;
            }
        }
        // 通过 条件2 判断给定的 Chess Board 是否合法
        if ((N >> 1) > rowSum || rowSum > (N + 1) >> 1
                || (N >> 1) > colSum || colSum > (N + 1) >> 1) {
            return -1;
        }

        
        if ((N & 1) == 0) {
            // 取 数值更小 的Swap方案
            rowSwap = Math.min(rowSwap, N - rowSwap);
            colSwap = Math.min(colSwap, N - colSwap);
        } else {
            // 转为 偶数Swap值的 方案
            if ((rowSwap & 1) == 1) {
                rowSwap = N - rowSwap;
            }
            if ((colSwap & 1) == 1) {
                colSwap = N - colSwap;
            }
        }
        return (rowSwap + colSwap) >> 1;
    }
}