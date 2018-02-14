/*
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
write a function to check whether these edges make up a valid tree.

Notice
You can assume that no duplicate edges will appear in edges. 
Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

Example
Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
 */

/**
 * Approach: Union Find
 * 这道题给了我们一个无向图，让我们来判断其是否为一棵树.
 * 我们知道如果是树的话，所有的节点必须是连接的，也就是说必须是连通图，而且不能有环，
 * 所以问题的核心就变成了验证 是否是连通图 和 是否含有环。
 *  1. 是否是连通图？
 *  这个比较好解决，只需要判断 边的条数 是否等于 点的个数-1 即可。
 *  2. 是否含环？
 *  这里可以使用 DFS/BFS/Union Find 三种方法。
 *  前两种方法大家已经非常熟悉了，这边主要讲一下第三种方法 Union Find.
 *  这是也 并查集 在 判断连通图是否带环 上的应用。
 *  不管是理解起来还是代码的书写都非常的便利，值得我们学习！
 *  当我们利用 edges 将各个点连通起来的时候，
 *  如果发现 即将相连的edge对应的两个节点有着共同的 root (Big Brother)，
 *  则说明该图 带环。
 *  因此在本题中我们只需要对 Union Find 的 find() 方法进行稍微的修改，即可实现。
 *
 *  参考资料：
 *  https://www.geeksforgeeks.org/union-find/
 */
class Solution {
    /*
     * @param n: An integer
     * @param edges: a list of undirected edges
     * @return: true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        // initialize n isolated islands
        int[] unionFind = new int[n];
        for (int i = 0; i < n; i++) {
            unionFind[i] = i;
        }

        // perform union find
        for (int[] edge : edges) {
            int roota = compressedFind(unionFind, edge[0]);
            int rootb = compressedFind(unionFind, edge[1]);
            // if two vertices happen to be in the same set
            // then there's a cycle
            if (roota == rootb) {
                return false;
            } else {
                unionFind[roota] = rootb;
            }
        }

        // judge it's a Connectivity Graph or not
        return edges.length == (n - 1);
    }

    private int compressedFind(int[] unionFind, int index) {
        while (index != unionFind[index]) {
            // Compress Path
            unionFind[index] = unionFind[unionFind[index]];
            index = unionFind[index];
        }
        return index;
    }
}