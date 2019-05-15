/*
In a 1 million by 1 million grid, the coordinates of each grid square are (x, y) with 0 <= x, y < 10^6.
We start at the source square and want to reach the target square.
Each move, we can walk to a 4-directionally adjacent square in the grid that isn't in the given list of blocked squares.

Return true if and only if it is possible to reach the target square through a sequence of moves.

Example 1:
Input: blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
Output: false
Explanation:
The target square is inaccessible starting from the source square, because we can't walk outside the grid.

Example 2:
Input: blocked = [], source = [0,0], target = [999999,999999]
Output: true
Explanation:
Because there are no blocked cells, it's possible to reach the target square.

Note:
    1. 0 <= blocked.length <= 200
    2. blocked[i].length == 2
    3. 0 <= blocked[i][j] < 10^6
    4. source.length == target.length == 2
    5. 0 <= source[i][j], target[i][j] < 10^6
    6. source != target
 */

/**
 * Approach: Estimate the Upper Bound + BFS
 * 这道问题的难点在于网格图的大小有 1e6 * 1e6 这么大。
 * 为了求得是否存在一条路径连通起点和终点，我们需要 BFS/DFS 整个网格图，这将耗费 O(M*N) 的时间，
 * 因此在时间复杂度上是不容许我们对整张图进行遍历操作的。
 * 所以，我们需要根据题目给出的数据进行分析。
 * 可以发现，障碍物的个数不大于 200 个，这相比于整个网格图的小了非常之多，因此，我们考虑利用这个信息作为突破口。
 * 因为只有当 source 或者是 target 被障碍物包围的时候，才不存在对应的路径。
 * 所以我们可以考虑 200 个障碍物最大可以阻隔多大的面积（记为 MAX_AREA），
 * 如果从起点出发进行 BFS  的区域面积大于MAX_AREA，那么就说明这些障碍物无法包围住这个点了，
 * 因此只要 source 和 target 均无法被障碍物包围，则必定存在一条路径将这两个点连接起来。
 *
 * 那么对于这 200 个点，其所能包围的面积最大有多少呢？
 * 我们可以知道，如果将这些点全部按照 45°角的斜线方向进行排列的话，与网格的两条边界可以围成最大的面积（一个等腰直角三角形）
 * 如下图所示：
 * 0th      _________________________
 *         |O O O O O O O X
 *         |O O O O O O X
 *         |O O O O O X
 *         |O O O O X
 *         .O O O X
 *         .O O X
 *         .O X
 * 200th   |X
 * MAX_AREA = 1+2+3+4+5+...+198+199 = (1+199)*199/2 = 19900
 *
 * 时间复杂度：O(B^2) => O(19900 * 2) B为障碍物个数
 * 空间复杂度：O(B)
 */
class Solution {
    long MAX = 1000000;

    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        if (blocked == null || blocked.length <= 0) {
            return true;
        }

        Set<Long> blockedSet = new HashSet<>();
        for (int[] block : blocked) {
            blockedSet.add(block[0] * MAX + block[1]);
        }
        return canEscape(blockedSet, source, target) && canEscape(blockedSet, target, source);
    }

    private boolean canEscape(Set<Long> blockedSet, int[] start, int[] end) {
        Set<Long> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1]});
        visited.add(start[0] * MAX + start[1]);
        int area = 1;
        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            if (curr[0] == end[0] && curr[1] == end[1]) {
                return true;
            }
            for (int[] dir : DIRS) {
                int nextR = curr[0] + dir[0], nextC = curr[1] + dir[1];
                long position = nextR * MAX + nextC;
                if (nextR < 0 || nextR >= MAX || nextC < 0 || nextC >= MAX || blockedSet.contains(position) || visited.contains(position)) {
                    continue;
                }
                queue.offer(new int[]{nextR, nextC});
                visited.add(position);
                if (++area > 19900) {
                    return true;
                }
            }
        }
        return false;
    }
}