/*
On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may have at most one stone.
Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
What is the largest possible number of moves we can make?

Example 1:
Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
Output: 5

Example 2:
Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
Output: 3

Example 3:
Input: stones = [[0,0]]
Output: 0

Note:
    1. 1 <= stones.length <= 1000
    2. 0 <= stones[i][j] < 10000
 */

/**
 * Approach: Union Find
 * 本题大意为：
 *  在二维坐标的整数坐标点上，有一些石头，如果一个石头和另外一个石头的横坐标或者纵坐标相等，那么认为他们是有链接的。
 *  我们每次取一个和其他石头有链接的石头，问最终能取得多少个石头。
 *
 * 如果两个石头在同行或者同列，两个石头就是连接的。连在一起的石头，可以组成一个连通图。
 * 按照题目中的 move 方法，每一个连通图至少会剩下1个石头。因此为了使得能够取走最多的石头，
 * 我们使用一种取法，使得最后每个连通图都只剩下1个石头。这样这题就转化成了数岛屿的问题。
 * 最后的答案就是：石头个数 减去 联通块个数
 *
 * 对于求 联通块个数 我们可以使用 DFS 或者 Union Find 去实现。
 * 对于 DFS 的实现，可以参考 Number of Islands.
 * 但是在实现上，我们发现在对于 行 和 列 的处理上，存在大量重复逻辑的代码。
 * 因此我们可以采用 0~N 表示 行坐标，N~2N 表示列坐标的方式，对位置进行处理。
 * （前 10000 表示行坐标，后10000表示列坐标）
 *
 * 然后就是本题最精彩的部分了：
 *  我们之前的思路是，以 石头 作为单位个体进行处理。
 *  但是，其实我们真正去处理的元素是 行列的index。
 *  我们以为是行列把石头连接在了一起。
 *  换个角度思考，也可以是一个石头把它所在行列坐标连在了一起。
 *  我们的输入是所有的石头，每个石头都将它所在的行和列连接在了一起。
 *  想到这里，不难得出本题真正想要考察的解法：Union Find 行列坐标
 *
 * 时间复杂度：O(N * log(N)) (If we used union-by-rank, this can be O(N∗α(N)), where α is the Inverse-Ackermann function)
 * 空间复杂度：O(N)
 *
 * References:
 *  https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Islands.java
 *  https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/197668/Count-the-Number-of-Islands-O(N)
 */
class Solution {
    public int removeStones(int[][] stones) {
        UnionFind uf = new UnionFind(stones);
        for (int[] stone : stones) {
            // uf.union(stone[0], 10000 + stone[1]);
            // 因为index不存在负数，所以我们可以利用 负数 来表示 列坐标（该操作可以通过取反实现，使得程序运行更快）
            uf.union(stone[0], ~stone[1]);
        }

        Set<Integer> islands = new HashSet<>();
        // 计算图中存在的联通块个数
        for (int[] stone : stones) {
            islands.add(uf.find(stone[0]));
        }
        return stones.length - islands.size();
    }

    class UnionFind {
        Map<Integer, Integer> parent;

        UnionFind(int[][] stones) {
            parent = new HashMap<>();
            // Initialize the Union Find
            for (int[] stone : stones) {
                parent.put(stone[0], stone[0]);
                // parent.put(10000 + stone[1], 10000 + stone[1]);
                parent.put(~stone[1], ~stone[1]);
            }
        }

        public int find(int index) {
            if (index != parent.get(index)) {
                parent.put(index, find(parent.get(index)));
            }
            return parent.get(index);
        }

        public void union(int a, int b) {
            int aFather = find(a);
            int bFather = find(b);
            if (aFather != bFather) {
                parent.put(bFather, aFather);
            }
        }
    }
}