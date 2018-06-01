/*
In this problem, a tree is an undirected graph that is connected and has no cycles.
The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N),
with one additional edge added. The added edge has two different vertices chosen from 1 to N,
and was not an edge that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v,
that represents an undirected edge connecting nodes u and v.

Return an edge that can be removed so that the resulting graph is a tree of N nodes.
If there are multiple answers, return the answer that occurs last in the given 2D-array.
The answer edge [u, v] should be in the same format, with u < v.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given undirected graph will be like this:
  1
 / \
2 - 3

Example 2:
Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
Output: [1,4]
Explanation: The given undirected graph will be like this:
5 - 1 - 2
    |   |
    4 - 3

Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */

/**
 * Approach: Union Find
 * 题意还是很简单的，给定一个图，求环中最后遇到的那条边，即形成环的那条边。
 * 属于无向图中的判环问题。想到可以直接使用 Union Find 进行解决。
 * 直接套用模板就好了，因为这里是 1~N 的整数，所以直接使用 数组实现 即可，就不动用 HashMap 了。
 *
 * 时间复杂度：O(nlg*N) 因为 lg*N 太小了，所以可以被忽略不计,因此为 O(n)
 * 空间复杂度：O(n)
 *
 * 与 Graph Valid Tree 非常类似，详细分析可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 */
class Solution {
    private static final int N = 1000;

    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(N);
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) {
                return new int[]{edge[0], edge[1]};
            }
        }
        // Can't reach here
        return new int[]{};
    }

    class UnionFind {
        int[] parent, rank;

        UnionFind(int N) {
            parent = new int[N + 1];
            rank = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int compressedFind(int index) {
            if (index != parent[index]) {
                parent[index] = compressedFind(parent[index]);
            }
            return parent[index];
        }

        public boolean union(int a, int b) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather == bFather) {
                return false;
            }
            if (rank[aFather] < rank[bFather]) {
                parent[aFather] = bFather;
                rank[bFather] += rank[aFather];
            } else {
                parent[bFather] = aFather;
                rank[aFather] += rank[bFather];
            }
            return true;
        }
    }
}