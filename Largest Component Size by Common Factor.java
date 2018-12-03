/*
Given a non-empty array of unique positive integers A, consider the following graph:
There are A.length nodes, labelled A[0] to A[A.length - 1];
There is an edge between A[i] and A[j] if and only if A[i] and A[j] share a common factor greater than 1.
Return the size of the largest connected component in the graph.

Example 1:
Input: [4,6,15,35]
Output: 4
4--6--15--35

Example 2:
Input: [20,50,9,63]
Output: 2
20--50  9--63

Example 3:
Input: [2,3,6,7,4,12,21,39]
Output: 8

Note:
1 <= A.length <= 20000
1 <= A[i] <= 100000
 */

/**
 * Approach: Union Find
 * 本题的难点在于数据规模，根据题目的信息，数据量在 20000 级别。
 * 因此时间复杂度只能在 O(nlogn) 或者以下。
 * 那么最初想的对 all pair 进行一次计算，然后建图使用 DFS 查询的做法就不可行了。
 *
 * 但是这又是图相关方面的问题，所以想到能够通过使用 UnionFind 来降低时间复杂度。
 * 毕竟都涉及到了联通块的问题。
 * 如果想到使用 UnionFind,那么本题基本就算做出来一半了。
 * 我们可以利用 UnionFind 来根据 公因子 这个信息，
 * 将所有具有相同 公因子 的数 union 在一起，并且注意：这个是可以传递的。
 * （非常符合 UnionFind 的性质，到这里就能更加确定本题考查的就是 并查集 了）
 *
 * 当我们建立完这个并查集之后，我们需要对 A 中的每一个元素进行一个 find，
 * 查找到它的 Boss/bigBrother 是谁，然后利用一张 Map 存储下每个 Boss/BigBrother 的出现次数。
 * 出现次数最多的那个就是最大的联通分块的 Boss,对应的出现次数就是 A 中有多少个数是这个联通块的成员。
 * PS.
 *  这里UnionFind中的 rank[] 只是为了保持并查集的平衡。
 *  而在本题结果查询的时候，不能使用 rank[] 中的值，因为它包含的是该 boss 下全部的元素。
 *  即可能包含了不在 A[] 中的元素。
 *
 * 时间复杂度：O(n * Σ(sqrt(A[i])))
 * 空间复杂度：O(max(A))
 */
class Solution {
    public int largestComponentSize(int[] A) {
        int max = Integer.MIN_VALUE;
        // 找出 A 中最大的元素，用来作为 UnionFind 中的最大值来建立并查集
        for (int num : A) {
            max = Math.max(max, num);
        }

        UnionFind uf = new UnionFind(max + 1);
        for (int num : A) {
            // 将 num 与其对应的因子 union 起来，注意范围只需要到 sqrt(num) 即可
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    uf.union(i, num);
                    uf.union(num / i, num);
                }
            }
        }

        // 利用 Map 存储各个 num 对应的 boss 的出现次数，取出现次数最大的就是需要的结果 
        HashMap<Integer, Integer> map = new HashMap<>();
        int rst = 0;
        for (int num : A) {
            int count = map.getOrDefault(uf.find(num), 0);
            rst = Math.max(rst, ++count);
            map.put(uf.find(num), count);
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