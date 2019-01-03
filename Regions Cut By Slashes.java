/*
In a N x N grid composed of 1 x 1 squares, each 1 x 1 square consists of a /, \, or blank space.
These characters divide the square into contiguous regions.
(Note that backslash characters are escaped, so a \ is represented as "\\".)

Return the number of regions.

Example 1:
Input:
[
  " /",
  "/ "
]
Output: 2
Explanation:
https://leetcode.com/problems/regions-cut-by-slashes/

Example 2:
Input:
[
  " /",
  "  "
]
Output: 1

Example 3:
Input:
[
  "\\/",
  "/\\"
]
Output: 4
Explanation: (Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.)

Example 4:
Input:
[
  "/\\",
  "\\/"
]
Output: 5
Explanation: (Recall that because \ characters are escaped, "/\\" refers to /\, and "\\/" refers to \/.)

The 2x2 grid is as follows:

Example 5:
Input:
[
  "//",
  "/ "
]
Output: 3

Note:
1 <= grid.length == grid[0].length <= 30
grid[i][j] is either '/', '\', or ' '.
 */

/**
 * Approach 1: Union Find
 * 这道题目是对 Graph 的一个考察。
 * 因为涉及到了联通块区域个数的计算，我们想到可以使用 DFS遍历，或者使用 Union Find 来解决。
 * 这里我们使用了 并查集 来解决这个问题。因为题目中只有 '\' 和 '/' 两种划分方法。
 * 所以可以利用两条对角线将一个 1x1 的小正方形划分成 4 个等腰三角形。
 * 然后利用 并查集 根据不同的划分情况来对这些小三角形的区域进行 union.
 * 这四块小三角的划分及标号如下所示：
 * ——————
 * |\0 /|
 * |3\/1|
 * | /\ |
 * |/2 \|
 * ——————
 * 这样我们只需要在最后检查并查集中总共有几个联通区域即可。
 *
 * 时间复杂度：O(n^2*α(n^2))
 * 空间复杂度：O(n^2)
 */
class Solution {
    public int regionsBySlashes(String[] grid) {
        int N = grid.length;
        UnionFind uf = new UnionFind(4 * N * N);
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                // get the index of triangle inner the 1x1 square
                int index = 4 * (row * N + col);
                switch (grid[row].charAt(col)) {
                    case '/':
                        // if it's the slash, we should union the 0 triangle with the 3rd triangle
                        // and the 1st triangle with 2nd triangle
                        uf.union(index, index + 3);
                        uf.union(index + 1, index + 2);
                        break;
                    case '\\':
                        // if it's the backslash, we should union the 0 triangle with the 1st triangle
                        // and the 2nd triangle with 3rd triangle
                        uf.union(index, index + 1);
                        uf.union(index + 2, index + 3);
                        break;
                    case ' ':
                        // if it's empty, we should union all the little triangle of the current 1x1 square
                        uf.union(index, index + 1);
                        uf.union(index + 1, index + 2);
                        uf.union(index + 2, index + 3);
                        break;
                    default: break;
                }
                // if the current row isn't the last row, we should union the current square's 2nd triangle
                // with the 0 triangle of square in the next row
                if (row < N - 1) {
                    uf.union(index + 2, index + 4 * N);
                }
                // if the current col isn't the last col, we should union the current square's 1st triangle
                // with the 3rd triangle of square in the next column : index + 4 + 3 = index + 7
                if (col < N - 1) {
                    uf.union(index + 1, index + 7);
                }
            }
        }

        int rst = 0;
        for (int i = 0; i < 4 * N * N; i++) {
            if (uf.find(i) == i) {
                rst++;
            }
        }
        return rst;
    }

    // UnionFind Template Based on Array
    class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        int find(int index) {
            if (index != parent[index]) {
                parent[index] = find(parent[index]);
            }
            return parent[index];
        }

        void union(int a, int b) {
            int aFather = find(a);
            int bFather = find(b);
            if (aFather == bFather) {
                return;
            }
            if (rank[aFather] > rank[bFather]) {
                parent[bFather] = aFather;
                rank[aFather] += rank[bFather];
            } else {
                parent[aFather] = bFather;
                rank[bFather] += rank[aFather];
            }
        }
    }
}

/**
 * Approach 2: Euler Characteristic + Union Find
 * 我们知道有欧拉示性数：V - E + F = X(P)
 * 对于 简单多面体 而言，X(P) = 2.
 * 因此也就有了我们平时所熟知的：V - E + F = 2 这个公式。
 * 同样，本题也可以使用这个知识来解决。
 * 由上式可以推得：F = E - V + 2.
 * 但是因为公式中的 F 指的是 内外两个部分 的面。而本题中我们要求的是内部面的个数。
 * 所以要求的 rst = F - 1 = E - V + 1.
 * 初始情况下（正方形没有被任何斜杠所分割，此时 faces 为 2 - 1 = 1.）
 * 观察可得：F 仅仅与 E(边的个数) 和 V(顶点个数) 相关。
 * 因此我们可以初始化 F 为 1.当出现了一条边将两个本来属于同一个集合的点连接起来时，
 * 就以为着 E 增加，而 V 保持不变，此时 F 就增加 1.
 * 而这个过程我们同样可以利用 Union Find 这个数据结构来处理。
 *
 * 时间复杂度：O((n+1) * (n+1)) = O(n^2)
 * 空间复杂度：O((n+1) * (n+1)) = O(n^2)
 *
 * 参考资料：
 *  https://en.wikipedia.org/wiki/Euler_characteristic
 *  https://zxi.mytechroad.com/blog/graph/leetcode-959-regions-cut-by-slashes/
 */
class Solution {
    public int regionsBySlashes(String[] grid) {
        int N = grid.length;
        UnionFind uf = new UnionFind(N);

        int faces = 1;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                switch (grid[row].charAt(col)) {
                    case '/':
                        faces += uf.union(uf.getIndex(N, row, col + 1), uf.getIndex(N, row + 1, col));
                        break;
                    case '\\':
                        faces += uf.union(uf.getIndex(N, row, col), uf.getIndex(N, row + 1, col + 1));
                        break;
                    default:break;
                }
            }
        }
        return faces;
    }

    // UnionFind Template Based on Array
    class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[(n + 1) * (n + 1)];
            rank = new int[(n + 1) * (n + 1)];
            for (int row = 0; row <= n; row++) {
                for (int col = 0; col <= n; col++) {
                    // 初始化时，将边缘上的点初始化成同一个区域的
                    parent[getIndex(n, row, col)] = (row == 0 || row == n || col == 0 ||col == n) ? 0 : getIndex(n, row, col);
                }
            }
        }

        int getIndex(int N, int row, int col) {
            return row * (N + 1) + col;
        }

        int find(int index) {
            if (index != parent[index]) {
                parent[index] = find(parent[index]);
            }
            return parent[index];
        }

        // 当我们 Union 两个属于同一个集合中的点时，就意味着 E+1, V保持不变，此时 F+1
        int union(int a, int b) {
            int aFather = find(a);
            int bFather = find(b);
            if (aFather == bFather) {
                return 1;
            }
            if (rank[aFather] > rank[bFather]) {
                parent[bFather] = aFather;
                rank[aFather] += rank[bFather];
            } else {
                parent[aFather] = bFather;
                rank[bFather] += rank[aFather];
            }
            return 0;
        }
    }
}

/**
 * Approach 3: Upscale 3 Times + DFS
 * 这个做法是网上看来的...觉得很有意思，就写了一个 Java 的版本分享给大家。
 * 做法为：直接将图形放大三倍，然后将被斜杆覆盖的区域涂成 黑色（标记为1）。
 * 然后对整个 graph 进行一个 DFS 的遍历操作。数有几个联通块即可。
 * （DFS 过程与 Number of Islands 相同）
 *
 * PS.这里放大 3 倍，是因为放大 3 倍正好效果相同。如果倍数过大，就会造成数据量过大；
 * 但是太小，会造成计算结果错误。
 * eg. [" /","/ "] 如果放大 2 倍，结果将会是 3，而正确答案是 2.
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 */
class Solution {
    public int regionsBySlashes(String[] grid) {
        int N = grid.length;
        byte[][] graph = new byte[3 * N][3 * N];
        // 将被斜杆覆盖的区域标记为 1
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (grid[row].charAt(col) == '/') {
                    graph[3 * row][3 * col + 2] = 1;
                    graph[3 * row + 1][3 * col + 1] = 1;
                    graph[3 * row + 2][3 * col] = 1;
                } else if (grid[row].charAt(col) == '\\') {
                    graph[3 * row][3 * col] = 1;
                    graph[3 * row + 1][3 * col + 1] = 1;
                    graph[3 * row + 2][3 * col + 2] = 1;
                }
            }
        }

        // DFS (Same as Number of Islands )
        int rst = 0;
        for (int row = 0; row < 3 * N; row++) {
            for (int col = 0; col < 3 * N; col++) {
                if (graph[row][col] == 0) {
                    dfs(graph, row, col);
                    rst++;
                }

            }
        }
        return rst;
    }

    private void dfs(byte[][] graph, int row, int col) {
        if (row < 0 || row >= graph.length || col < 0 || col >= graph.length || graph[row][col] == 1) {
            return;
        }
        // mark visited
        graph[row][col] = 1;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            dfs(graph, row + dir[0], col + dir[1]);
        }
    }
}