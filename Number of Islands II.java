/*
A 2d grid map of m rows and n columns is initially filled with water.
We may perform an addLand operation which turns the water at position (row, col) into a land.
Given a list of positions to operate, count the number of islands after each addLand operation.
An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
You may assume all four edges of the grid are all surrounded by water.

Example:
Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).
0 0 0
0 0 0
0 0 0

Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.
1 0 0
0 0 0   Number of islands = 1
0 0 0

Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.
1 1 0
0 0 0   Number of islands = 1
0 0 0

Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.
1 1 0
0 0 1   Number of islands = 2
0 0 0

Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.
1 1 0
0 0 1   Number of islands = 3
0 1 0

We return the result as an array: [1, 1, 2, 3]

Challenge:
Can you do it in time complexity O(k log mn), where k is the length of the positions?
 */

/**
 * Approach: Union Find (Based on HashMap)
 * 该题是 Number of Islands 的 Follow Up.
 * 在分析这道题目之前，为了更好地将各个题目串起来，我们需要介绍一个小小的知识点：
 * 如何实现一个点在 二维平面 与 一维平面 之间的互相转换？
 * 答案其实很简单：设存在点 P(x, y), 矩阵的大小为 [m, n]
 * 2D -> 1D：p = x*n + y;
 * 1D -> 2D: x = p / n, y = p % n;
 * 因此题目中给定的各个岛屿的坐标实际上可以转换为 一维平面 上的一个个点。
 * 如果 二维平面 中两个点相邻，则 一维平面 上只需要在这两个点中间连一条线即可。
 * 那么求一块块岛屿的个数，其实就转换成了求 联通区 个数问题了。
 * 即，我们通过分析可以发现 Number of Islands 和 Find the Connected Component in the Undirected Graph 实质是完全相同的。
 *
 * 那么回到这个问题，我们发现如果我们仍然采用 BFS/DFS 方法来解决这个问题的话，需要 O(k*mn) 的时间复杂度。
 * 显然不符合要求，但是该方法的时间复杂度可以进一步降低，原因是：
 * 我们发现每次出现一个岛屿后，它影响的仅仅只是它周围上下左右这 4 个点的情况而已。
 * 如果它周围存在岛屿，直接进行 合并 即可；如果他周围不存在岛屿，则说明产生了一个新的岛屿。
 * 因此并不需要对整个图进行 BFS/DFS。
 * 那么涉及到了 合并 操作，我们自然会想到使用 并查集 来帮助我们解决该问题。
 *
 * 具体做法如下：
 *  1. 首先将二维数组转化为1维数组，初始化unionFind，并用一个数组来记录某一个位置是否已经被改变成了岛屿 isIsland
 *  （如何实现 二维 => 一维 的转换我们已经在前面提过了）
 *  2. 然后用一个 count 来记录每一次操作后联通分量的个数。
 *  每改变一个位置，就将count加1，并将该位置的 isIsland 置为 true。
 *  3. 查看该位置上下左右四个位置，若其相邻位置不越界且是一个岛屿 isIsland = true，并且不属于 同一个 联通分量，
 *  则将该位置和其相邻位置union，并将count减1。所有相邻位置检查完后剩下的 count 即为本次操作后的 联通分量数。
 *  4. 将每次操作后得到的 count 加入到 rst 中作为最后的结果返回即可。
 *  综上，本题只需要利用 并查集的 union 方法，在其上面稍作条件判断并进行相关操作即可。
 *
 * 时间复杂度：
 *  因为我们使用了 路径压缩算法 对 find 操作进行了优化，使得并查集的 union 和 find 操作的时间复杂度为: O(1)
 *  假设我们对岛屿进行了 k 次操作，程序的总体时间复杂度为: O(k)
 * 空间复杂度：
 *  为了便于理解，这边使用了 HashMap 来实现 Union Find；并且还开辟了一个 isIsland 数组用于标记岛屿状态。
 *  因此耗费了：O(2n) 的空间。但是，因为其坐标都是 Integer 类型的，
 *  那么我们完全可以通过 数组 来实现 Union Find，并且标记数据的空间其实也可以被优化掉。
 *  具体实现详见 Approach 2. (如果对该算法已经有所了解了，那么你可以直接看 解法2 了哦)
 *
 * Note:
 * 为什么要采用先假设产生了一个新的岛屿（count+1)，然后如果发生合并的话再 减1 的操作呢？
 * 原因举个例子就清楚了：
 * 假设当前岛屿情况如下图所示，岛屿个数为 3：
 * [0, 1, 0]
 * [1, 0, 1]
 * 发生改变后，变成了下图情况：
 * [0, 1, 0]
 * [1, 1, 1]
 * 此时岛屿个数只有1.
 * 用这个方法的话，运算过程为：假设产生岛屿,那么先执行 count +1 => 4；
 * 然后与左边岛屿发生 Union => 4 - 1 = 3
 * 同样的还会与 上/右 边的岛屿发生 Union => 3 - 2 = 1.
 * 如果不采用此方法，岛屿发生 Union 之后，岛屿个数就会变得很难计算。
 */
class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> rst = new ArrayList<>();
        if (m <= 0 || n <= 0 || positions == null || positions.length == 0) {
            return rst;
        }

        int[] pos = new int[m * n];
        boolean[] isIsland = new boolean[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                pos[index] = index;
            }
        }

        UnionFind uf = new UnionFind(pos);
        int count = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int[] position : positions){
            int index = position[0] * n + position[1];
            isIsland[index] = true;
            count++;

            // check neighbor cells
            for(int[] dir : dirs){
                int nextX = position[0] + dir[0];
                int nextY = position[1] + dir[1];
                // the indices of next point
                int nextP = nextX * n + nextY;
                // if the next point is beyond the bound or it's not an island, ignore it.
                if(nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || !isIsland[nextP]){
                    continue;
                }
                count = uf.union(index, nextP, count);
            }
            rst.add(count);
        }

        return rst;
    }

    // Union Find Template
    class UnionFind {
        HashMap<Integer, Integer> parent;

        UnionFind(int[] arr) {
            parent = new HashMap<>();
            for (int i : arr) {
                parent.put(i, i);
            }
        }

        public int compressedFind(int i) {
            while (i != parent.get(i)) {
                // compress path
                parent.put(i, parent.get(parent.get(i)));
                i = parent.get(i);
            }
            return i;
        }

        public int union(int a, int b, int count) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather != bFather) {
                parent.put(aFather, bFather);
                count--;
            }
            return count;
        }
    }
}
 
/**
 * Approach 2: Union Find (Based on Array)
 * We found that tha data in Union Find are all Integer type.
 * So we can use an Array instead of HashMap and save the extra space at the same time.
 * The code has detail comments, I think you can understand it easily.
 */
class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> rst = new ArrayList<>();
        if (m <= 0 || n <= 0 || positions == null || positions.length == 0) {
            return rst;
        }

        // use an array to hold root number(Big Brother) of each island
        int[] unionFind = new int[m * n];
        // initialize the unionFind with -1, so we know non negative number is a root number
        // and also mean that the island if true, so we can save the space of isIsland array
        Arrays.fill(unionFind, -1);

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        // count variable is used to count the island in current matrix.
        int count = 0;
        for(int[] position : positions){
            // for each input cell, its initial root number is itself
            int index = position[0] * n + position[1];
            unionFind[index] = index;
            count++;

            // check neighbor cells
            for(int[] dir : dirs){
                int nextX = position[0] + dir[0];
                int nextY = position[1] + dir[1];
                // the indices of next point
                int nextP = nextX * n + nextY;
                // if the next point is beyond the bound or it's not an island, ignore it.
                if(nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || unionFind[nextP] == -1){
                    continue;
                }

                // get the root number(Big Brother) of current island
                int roota = compressedFind(unionFind, index);
                //get the root number(Big Brother) of the next island
                int rootb = compressedFind(unionFind, nextP);
                //if roota and rootb are different, then we can union two isolated island together,
                // so the num of island - 1
                if (roota != rootb) {
                    unionFind[roota] = rootb;
                    count--;
                }
            }
            rst.add(count);
        }

        return rst;
    }

    private int compressedFind(int[] uf, int index) {
        while (index != uf[index]) {
            uf[index] = uf[uf[index]];
            index = uf[index];
        }
        return index;
    }
}
