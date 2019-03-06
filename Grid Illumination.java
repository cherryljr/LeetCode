/*
On a N x N grid of cells, each cell (x, y) with 0 <= x < N and 0 <= y < N has a lamp.
Initially, some number of lamps are on.  lamps[i] tells us the location of the i-th lamp that is on.
Each lamp that is on illuminates every square on its x-axis, y-axis, and both diagonals (similar to a Queen in chess).

For the i-th query queries[i] = (x, y), the answer to the query is 1 if the cell (x, y) is illuminated, else 0.
After each query (x, y) [in the order given by queries],
we turn off any lamps that are at cell (x, y) or are adjacent 8-directionally (ie., share a corner or edge with cell (x, y).)

Return an array of answers.  Each value answer[i] should be equal to the answer of the i-th query queries[i].

Example 1:
Input: N = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
Output: [1,0]
Explanation:
Before performing the first query we have both lamps [0,0] and [4,4] on.
The grid representing which cells are lit looks like this, where [0,0] is the top left corner, and [4,4] is the bottom right corner:
1 1 1 1 1
1 1 0 0 1
1 0 1 0 1
1 0 0 1 1
1 1 1 1 1
Then the query at [1, 1] returns 1 because the cell is lit.  After this query, the lamp at [0, 0] turns off,
and the grid now looks like this:
1 0 0 0 1
0 1 0 0 1
0 0 1 0 1
0 0 0 1 1
1 1 1 1 1
Before performing the second query we have only the lamp [4,4] on.
Now the query at [1,0] returns 0, because the cell is no longer lit.

Note:
    1. 1 <= N <= 10^9
    2. 0 <= lamps.length <= 20000
    3. 0 <= queries.length <= 20000
    4. lamps[i].length == queries[i].length == 2
 */

/**
 * Approach: HashMap
 * 这道题目有点类似 N 皇后问题的布局。
 * 每一盏灯都能够照亮 横竖，对角，反对角 总共四个方向上的所有格子。
 * 因此我们可以建立 4 个 Map 来存储信息：
 *  key为 行，列，对角线 对应的坐标；value为对应坐标上有多少盏灯。
 * 只有当该对应坐标上一盏灯都没有的时候，该位置才不会被照亮。
 * 这里在 对角线 和 反对角线 的坐标表示上，我们可以利用 x+y 和 x-y 来表示。
 * eg.假设为一个 3X3 的图，则各个点的坐标如下所示
 *      (0,0) (0,1) (0,2)
 *      (1,0) (1,1) (1,2)
 *      (2,0) (2,1) (2,2)
 * 我们可以发现正对角线上的 (0,2) (1,1) (2,0) 中 x+y 的值是相等的；
 * 反对角线上的 (0,0) (1,1) (2,2) 中 x-y 的值是相等的。
 * 因此我们可以分别用这两个值来表示这两条对角线的坐标。
 *
 * 然后我们遍历所有的灯，统计出四个 Map 的值。
 * 在查询的时候，我们只需要检测该位置上，不管是 横，竖，对角，反对角 其中有任意一个被照亮。
 * 则说明这个位置是被照亮的。
 * 
 * 然后该位置为中心的 3X3 的范围内，熄灭所有亮着的灯。
 * 如果该位置存在着一盏灯，对此我们只需要在 Map 中对应的位置进行 -1 操作即可。
 * 如果进行 -1 操作后发现其值为 0，说明这个位置无法被照亮了，那么就应该将这个坐标从 map 中移除。
 * 当然，对此我们还需要知道所有灯的分布位置。因此我们需要一个 lampSet。
 * 因为坐标范围有 10^9 之大，所以我们利用了 long 来存储坐标。
 * （低32位存储行坐标，高32位存储列坐标）
 * 这里注意：
 *  Set 里面存的是 Long，但是Java在对数计算的时候默认是 int 的。
 *  所以一定要记得进行类型转换，否则在 对row左移32 操作的时候，会因为数值溢出而出现问题。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 */
class Solution {
    public int[] gridIllumination(int N, int[][] lamps, int[][] queries) {
        Map<Integer, Integer> rowMap = new HashMap<>();
        Map<Integer, Integer> colMap = new HashMap<>();
        Map<Integer, Integer> diagonalMap = new HashMap<>();
        Map<Integer, Integer> backDiagonalMap = new HashMap<>();
        Set<Long> lampSet = new HashSet<>();

        for (int[] lamp : lamps) {
            int x = lamp[0], y = lamp[1];
            rowMap.put(x, rowMap.getOrDefault(x, 0) + 1);
            colMap.put(y, colMap.getOrDefault(y, 0) + 1);
            diagonalMap.put(x + y, diagonalMap.getOrDefault(x + y, 0) + 1);
            backDiagonalMap.put(x - y, backDiagonalMap.getOrDefault(x - y, 0) + 1);
            lampSet.add((long)y << 32 | x);
        }

        int[] ans = new int[queries.length];
        int[][] DIRS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {0, 0}};
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0], y = queries[i][1];
            if (rowMap.containsKey(x) || colMap.containsKey(y) || diagonalMap.containsKey(x + y) || backDiagonalMap.containsKey(x - y)) {
                ans[i] = 1;
            } else {
                ans[i] = 0;
            }

            for (int[] dir : DIRS) {
                int nextX = x + dir[0], nextY = y + dir[1];
                if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= N || !lampSet.contains((long)nextY << 32 | nextX)) {
                    continue;
                }
                rowMap.put(nextX, rowMap.get(nextX) - 1);
                if (rowMap.get(nextX) == 0) {
                    rowMap.remove(nextX);
                }
                colMap.put(nextY, colMap.get(nextY) - 1);
                if (colMap.get(nextY) == 0) {
                    colMap.remove(nextY);
                }
                diagonalMap.put(nextX + nextY, diagonalMap.get(nextX + nextY) - 1);
                if (diagonalMap.get(nextX + nextY) == 0) {
                    diagonalMap.remove(nextX + nextY);
                }
                backDiagonalMap.put(nextX - nextY, backDiagonalMap.get(nextX - nextY) - 1);
                if (backDiagonalMap.get(nextX - nextY) == 0) {
                    backDiagonalMap.remove(nextX - nextY);
                }
                lampSet.remove((long)nextY << 32 | nextX);
            }
        }

        return ans;
    }
}