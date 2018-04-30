/*
On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K moves.
The rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right square is (N-1, N-1).

A chess knight has 8 possible moves it can make, as illustrated below.
Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
The picture: https://leetcode.com/static/images/problemset/knight.png

Each time the knight is to move, it chooses one of eight possible moves uniformly at random
(even if the piece would go off the chessboard) and moves there.
The knight continues moving until it has made exactly K moves or has moved off the chessboard.
Return the probability that the knight remains on the board after it has stopped moving.

Example:
Input: 3, 2, 0, 0
Output: 0.0625
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.

Note:
N will be between 1 and 25.
K will be between 0 and 100.
The knight always initially starts on the board.
 */

// 前言：这里展示了如何从 暴力求解 到 动态规划 一步步进行优化的过程。
// 当然，更详细的例子可以参考 换零钱(完全背包问题) 会相对详细，也更复杂一些：
// https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java

/**
 * Approach 1: BFS (Time Limit Exceeded)
 * 相当暴力的做法...利用 BFS 的模板写就行了。
 * 因为我们需要 位置信息 和 剩余次数信息 来进行判断，所以这里建立了一个 Mesg类。
 * 当然这个做法会超时...(这个解法直接跳过也无所谓...只是某人要看就写出来了...)
 */
class Solution {
    class Mesg {
        int row, col;
        int leftStep;

        public Mesg(int row, int col, int leftStep) {
            this.row = row;
            this.col = col;
            this.leftStep = leftStep;
        }
    }

    public double knightProbability(int N, int K, int r, int c) {
        Queue<Mesg> queue = new LinkedList<>();
        queue.offer(new Mesg(r, c, K));
        int[][] dirs = new int[][]{{-1, -2}, {-2,  -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

        double inBoard = 0;
        while (!queue.isEmpty()) {
            Mesg curr = queue.poll();
            if (curr.leftStep == 0) {
                inBoard += 1;
                continue;
            }

            for (int[] dir : dirs) {
                int nextRow = curr.row + dir[0];
                int nextCol = curr.col + dir[1];
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N || curr.leftStep <= 0) {
                    continue;
                }
                queue.offer(new Mesg(nextRow, nextCol, curr.leftStep - 1));
            }
        }

        return inBoard / Math.pow(8, K);
    }
}

/**
 * Approach 2: DFS (Time Limit Exceeded)
 * 简洁明了的做法...和 Approach 1 中的 BFS 方法一样，
 * 直接枚举各个可能性，暴力求解。(理解该做法对后续的优化有着非常大的帮助)
 */
class Solution {
    public double knightProbability(int N, int K, int r, int c) {
        return dfs(N, K, r, c) / Math.pow(8, K);
    }

    public static final int[][] DIRS = new int[][]{{-1, -2}, {-2,  -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

    private double dfs(int N, int K, int row, int col) {
        // 递归的结束条件
        if (K == 0) {
            return 1;
        }

        double inBoard = 0;
        // 递归求解的调用过程
        for (int[] dir : DIRS) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) {
                continue;
            }
            inBoard += dfs(N, K - 1, nextRow, nextCol);
        }
        return inBoard;
    }
}

/**
 * Approach 3: DFS with Memoization
 * 因为 Approach 2 的暴力方法挂了...分析发现各个位置存在大量重复计算。
 * 因此使用一个数组将其计算结果存储起来即可，当有需要时就可以直接调用，即 记忆化搜索。
 * 改起来还是十分简单的，代码几乎没变...
 *
 * 时间复杂度：O(N^2*K)
 * 空间复杂度：O(N^2*K)
 */
class Solution {
    double[][][] mem;
    public static final int[][] DIRS = new int[][]{{-1, -2}, {-2,  -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

    public double knightProbability(int N, int K, int r, int c) {
        mem = new double[N][N][K + 1];
        return dfs(N, K, r, c) / Math.pow(8, K);
    }

    private double dfs(int N, int K, int row, int col) {
        // 递归的结束条件
        if (K == 0) {
            return 1;
        }

        double inBoard = 0;
        // 递归求解的调用过程
        for (int[] dir : DIRS) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) {
                continue;
            }
            // 若需要位置的值还未被计算过，则递归调用dfs进行计算
            if (mem[nextRow][nextCol][K - 1] == 0) {
                mem[nextRow][nextCol][K - 1] = dfs(N, K - 1, nextRow, nextCol);
            }
            // 使用事先记录好值计算当前值即可
            inBoard += mem[nextRow][nextCol][K - 1];
        }
        // In fact, this line could be ignored (Think about why?)
        // mem[row][col][K] = inBoard; 
        return inBoard;
    }
}

/**
 * Approach 4: Matrix DP
 * 至此我们已经写出了 记忆化搜索 的方法了...其实已经写出 DP 的方法了。
 * 不过在最后，还是带着大家分析一遍直接从 DP 入手的话该怎么做吧。
 * 我们发现这是一个 无后效性 问题，当 位置 和 剩余步数 这两个信息确定之后，
 * 走完所有步数并且还留在棋盘上的方案数（结果）就已经确定了，与如何到达该位置并无关系。
 * 因此我们可以将原来的 DFS 方法改为 DP 方法。
 * dp[i][j][step] 代表骑士当前在 [i, j] 的位置，走Step步留在棋盘上的方案数。
 * 经过 以上分析（好吧，其实也没分析什么...）我们可以知道当前位置的信息 dp[i][j] 依赖于：
 * dp[i + dir[0]][j + dir[1]][step - 1] 这 8 个位置的信息。（如果有效的话）
 * 最终结果为：dp[r][c][step]
 * 对此我们需要对 dp[][] 进行一个初始化，当 step == 0 时，则 dp[i][j] = 1.
 * 因为这里的位置信息只依赖于 上一步 的信息。这就意味着原本的 三维矩阵 中的 Step 这一维的空间其实可以被优化掉。
 * 即我们使用 两个 二维矩阵的空间就够了(实际上就是一个滚动数组)。
 *
 * 时间复杂度：O(N^2*K)
 * 空间复杂度：O(N^2)
 */
class Solution {
    public double knightProbability(int N, int K, int r, int c) {
        double[][] dp = new double[N][N];
        // Initialize
        for (double[] row : dp) {
            Arrays.fill(row, 1);
        }
        int[][] dirs = new int[][]{{-1, -2}, {-2,  -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

        for (int k = 0; k < K; k++) {
            // Create the temp dp[][] to store message.
            double[][] dp2 = new double[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int[] dir : dirs) {
                        int nextRow = i + dir[0];
                        int nextCol = j + dir[1];
                        if (isOnBoard(nextRow, nextCol, N)) {
                            dp2[i][j] += dp[nextRow][nextCol];
                        }
                    }
                }
            }
            // Update the dp[][] message.
            dp = dp2;
        }

        return dp[r][c] / Math.pow(8, K);
    }

    private boolean isOnBoard(int row, int col, int N) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return false;
        }
        return true;
    }
}