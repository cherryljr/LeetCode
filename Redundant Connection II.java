/*
In this problem, a rooted tree is a directed graph such that,
there is exactly one node (the root) for which all other nodes are descendants of this node,
plus every node has exactly one parent, except for the root node which has no parents.

The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N),
with one additional directed edge added. The added edge has two different vertices chosen from 1 to N,
and was not an edge that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents
a directed edge connecting nodes u and v, where u is a parent of child v.

Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes.
If there are multiple answers, return the answer that occurs last in the given 2D-array.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given directed graph will be like this:
  1
 / \
v   v
2-->3

Example 2:
Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
Output: [4,1]
Explanation: The given directed graph will be like this:
5 <- 1 -> 2
     ^    |
     |    v
     4 <- 3

Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */

/**
 * Approach: Union Find
 * 这道题目属于 Redundant Connection 和 Graph Valid Tree 的 Fellow Up.
 * 本题中，我们不仅仅需要使用到 Union Find 判断树的合法性，
 * 并且还需要找到 删除哪条边 可以使得该图变成一棵有效的树。
 *
 * 本题中使得树 invalid 的情况总共有 1+2=3 种。
 *  Case 1:
 *      每个节点都只有 一个 父亲节点，但是形成了环。
 *      如：[1, 2], [2, 3], [3, 1]
 *      这种情况下，题目退化成 Redundant Connection，只需要使用 Union Find 寻找
 *      是因为哪条边形成了环，然后返回即可。
 *  Case 2.1:
 *      有某个节点存在 两个 父亲节点，但并没有形成环。(注意这里指的是有向图的环)
 *      如：[1, 2], [1, 3], [2, 3]
 *      这种情况下，我们需要删除最后一次遇到的形成环的边，即 [2, 3]
 *  Case 2.2：
 *      有某个节点存在 两个 父亲节点，并且形成了环。
 *      如：[2, 1], [3, 1], [4, 2], [1, 4]
 *      这种情况下，如果按找 Redundant Connection 中的做法就会出现错误，
 *      因为形成环的边会被判断成 [1, 4], 而就算删除了这条边，1 仍然两个父亲节点，这是错误的。
 *  因此当我们遇到某个节点有 两个父亲节点 的情况时，我们需要删除的是：
 *  如果没有环，删除 最后一次遇到 的 存在两个父亲的节点 的边；
 *  如果有环，删除 环中 的 存在两个父亲的节点 的边。
 *  
 * 有了以上分析，将其转换成代码即可。
 * 代码的实现上使用了一个小 trick,具体详见注释。
 * 本题在实现上还是存在一定技巧性的
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * Redundant Connection：
 * 	https://github.com/cherryljr/LeetCode/blob/master/Redundant%20Connection.java
 * Graph Valid Tree：
 * 	https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 * 参考：
 *  https://www.youtube.com/watch?v=lnmJT5b4NlM&t=2s
 */
class Solution {
    private static final int N = 1000;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        // 因为可能有节点存在两个父亲节点，此时答案必定在这两条边之中
        int[] candidate1 = new int[]{-1, -1};
        int[] candidate2 = new int[]{-1, -1};
        // 记录各个节点的父亲节点
        int[] parent = new int[N + 1];
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            // 如果节点 v 的父亲不唯一，则记录下两个可能的答案
            if (parent[v] != 0) {
                candidate1 = new int[]{parent[v], v};
                candidate2 = new int[]{u, v};
                // 无效化第二条边 (trick)
                edge[0] = -1;
                edge[1] = -1;
            }
            parent[v] = u;
        }

        // Do Union Find
        UnionFind uf = new UnionFind(N);
        for (int[] edge : edges) {
            // 如果遇到被无效化的边直接跳过
            if (edge[0] == -1 && edge[1] == -1) {
                continue;
            }
            if (!uf.union(edge[0], edge[1])) {
                // 如果在无效化了第二条边之后（存在的话）
                // 仍然遇到了环，那么要么是无重复父亲节点，由该条边产生
                // 要么是 存在重复父亲节点，并且由 第一条边 产生
                return candidate1[0] == -1 ? edge : candidate1;
            }
        }
        
        // 对应 Case2.1 与 Case2.2
        return candidate2;
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