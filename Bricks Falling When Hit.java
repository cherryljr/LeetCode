/*
We have a grid of 1s and 0s; the 1s in a cell represent bricks.
A brick will not drop if and only if it is directly connected to the top of the grid,
or at least one of its (4-way) adjacent bricks will not drop.

We will do some erasures sequentially. Each time we want to do the erasure at the location (i, j),
the brick (if it exists) on that location will disappear, and then some other bricks may drop because of that erasure.

Return an array representing the number of bricks that will drop after each erasure in sequence.

Example 1:
Input:
grid = [[1,0,0,0],[1,1,1,0]]
hits = [[1,0]]
Output: [2]
Explanation:
If we erase the brick at (1, 0), the brick at (1, 1) and (1, 2) will drop. So we should return 2.

Example 2:
Input:
grid = [[1,0,0,0],[1,1,0,0]]
hits = [[1,1],[1,0]]
Output: [0,0]
Explanation:
When we erase the brick at (1, 0), the brick at (1, 1) has already disappeared due to the last move.
So each erasure will cause no bricks dropping.  Note that the erased brick (1, 0) will not be counted as a dropped brick.

Note:
The number of rows and columns in the grid will be in the range [1, 200].
The number of erasures will not exceed the area of the grid.
It is guaranteed that each erasure will be different from any other erasure, and located inside the grid.
An erasure may refer to a location with no brick - if it does, no bricks drop.
 */

/**
 * Approach: Reverse Time and Union Find
 * 这道题目是参考网上解法的，自己写的 DFS 虽然能过但是太搓就不拿出来了。
 * 下面对该解法进行一个分析说明。
 *
 * 首先这是一道求联通块的题（hit掉一块砖头，相当于 disUnion 一个区域）
 * 通常这类的题目可以通过 DFS/BFS, Union Find 来解决。
 * 考虑到时间复杂度，我们通过会选择 Union Find. （这道题目 DFS 也能过，但是需要优化）
 * 但是，我们知道 Union Find 只有将两个块连通起来的功能，并不具备 拆分 的功能。
 * 因此这条路看似走不通了...但是！其实只需要我们转换一下思路：
 * 将顺序倒过来，从后往前想即可，因为这样就变成了每次 加一块砖块 说产生的影响。
 * 而这不正是 Union Find 的常用场景吗（类似 Number of Islands II)
 *
 * 有了以上想法，我们可以分析出具体做法为：
 *  1. 首先将所有的 hits 操作全部进行完，这样剩下的局面就是最后布局情况。
 *  2. 利用结束的布局初始化并查集。并利用 parent[rows * cols] 来作为 root 节点，
 *  以此来统计当前局面下还留着的砖块个数 rank[rows * cols]。
 *  3. 按照逆序的方式，对 hits[] 进行遍历，还原 hit 操作。（时间倒流）
 *  4. 因为时间顺序是逆过来的，所以此时我们变成了在 hits[i] 的位置 添加一个砖块。
 *  然后利用 Union Find 进行 union 操作，查看此操作对当前布局的影响是什么。并记录到 rst[] 中。
 *
 * 以上就是本题的解法，不过在实现上还是考察一定的代码能力，下面帮大家列出了几个注意点：
 *  1. 我们应该如何保证 Union Find 的 root 必定是 parent[rows*cols].
 *  这样才能使得我们每次获取留下来的砖块个数时，直接访问 rank[rows*cols] 即可。
 *  对此我们需要控制好 union 的方向，即：
 *      当 aFather, bFather 其中有一个是 rows*cols 时，我们必须将另一个 union 向它。
 *      否则，我们需要将 较大的元素 union 向 较小的元素。
 *      这是因为只有贴在 row=0 的砖块才能支撑住下面的砖块。
 *  2. hit 掉的砖块个数是不计算在变换个数中的，因此需要对结果进行 -1 操作。
 *  3. 变换个数可能为 0，当进行 -1 操作后，会出现 -1 的情况，因此我们需要与 0 进行取最大值操作。
 */
class Solution {
    private static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int[] hitBricks(int[][] grid, int[][] hits) {
        int[] rst = new int[hits.length];
        int rows = grid.length, cols = grid[0].length;
        UnionFind uf = new UnionFind(rows * cols + 1);

        // Make all hits
        for (int i = 0; i < hits.length; i++) {
            // 1 turns to 0, 0 turns to -1.
            grid[hits[i][0]][hits[i][1]] -= 1;
        }

        // Build the board (After all hits)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // if the current cell is a brick, we can union it with its neighbors
                if (grid[i][j] == 1) {
                    int pos = i * cols + j;
                    // if it's in 0th row, it can stay in the board
                    // so we union it with the root (rows * cols)
                    if (i == 0) {
                        uf.union(pos, rows * cols);
                    }
                    // Here is similar to Making A Large Island
                    // We only need to union the left and up gird of current position
                    if (i > 0 && grid[i - 1][j] == 1) {
                        uf.union(pos, (i - 1) * cols + j);
                    }
                    if (j > 0 && grid[i][j - 1] == 1) {
                        uf.union(pos, i * cols + j - 1);
                    }
                }
            }
        }

        // Reverse the time
        for (int i = hits.length - 1; i >= 0; i--) {
            int hitRow = hits[i][0], hitCol = hits[i][1];
            // The block used to be empty (there is no bricks)
            if (grid[hitRow][hitCol] == -1) {
                grid[hitRow][hitCol] = 0;
                continue;
            }

            // Get how many bricks in the board currently
            int preRoof = uf.getRoof();
            for (int[] dir : DIRS) {
                int nextRow = hitRow + dir[0];
                int nextCol = hitCol + dir[1];
                if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols
                        || grid[nextRow][nextCol] != 1) {
                    continue;
                }
                uf.union(hitRow * cols + hitCol, nextRow * cols + nextCol);
            }

            // If the hit position is in 0th row, it should be union to root (rows*cols)
            if (hitRow == 0) {
                uf.union(hitRow * cols + hitCol, rows * cols);
            }
            // Add the hit brick
            grid[hitRow][hitCol] = 1;
            // Get the variation
            rst[i] = Math.max(0, uf.getRoof() - preRoof - 1);
        }

        return rst;
    }

    class UnionFind {
        int[] parent, rank;

        UnionFind(int N) {
            parent = new int[N];
            rank = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
            rank[N - 1] = 0;
        }

        public int compressedFind(int index) {
            if (index != parent[index]) {
                parent[index] = compressedFind(parent[index]);
            }
            return parent[index];
        }

        public void union(int a, int b) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather == bFather) {
                return;
            } else if (aFather > bFather) {
                // make bFather to be the bigger one
                int temp = aFather;
                aFather = bFather;
                bFather = temp;
            }

            // Union smaller to bigger one, unless bFather is the root
            if (bFather == rank.length - 1) {
                parent[aFather] = bFather;
                rank[bFather] += rank[aFather];
            } else {
                parent[bFather] = aFather;
                rank[aFather] += rank[bFather];
            }
        }

        // Return how many bricks can stay in the board
        public int getRoof() {
            return rank[rank.length - 1];
        }
    }
}