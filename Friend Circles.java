/*
There are N students in a class. Some of them are friends, while some are not.
Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C,
then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1,
then the ith and jth students are direct friends with each other, otherwise not.
And you have to output the total number of friend circles among all the students.

Example 1:
Input:
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
The 2nd student himself is in a friend circle. So return 2.

Example 2:
Input:
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.

Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.
 */

/**
 * Approach: Union Find
 * 初始情况下有 n 个人，他们互相都是不认识的，每当两个人互相成为朋友，就会形成一个 区域。
 * 那么，如果初始情况下有 2 个人，当他们成为朋友之后，就会发生一个 2-1 的操作，使得原本独立的两个人
 * 成为了 1 个群体，这个群体可以被认为是一个人。
 * 而这其实就是我们 并查集 中的 union 操作。
 * 每当两个 独立 群体相互认识的时候，我们只需要将 count-1 即可。
 * 并且认识这个动作是相互的，即如果 M[i][j]==1,必定有 M[j][i]==1，因此我们只需要遍历 上半/下半 个三角矩形即可。
 * 故这道题目，我们只需要使用 并查集 就能够轻松解决。
 */
class Solution {
    public int findCircleNum(int[][] M) {
        UnionFind uf = new UnionFind(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = i + 1; j < M.length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.count;
    }

    class UnionFind {
        int[] parent, rank;
        int count;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int compressedFind(int index) {
            while (index != parent[index]) {
                parent[index] = parent[parent[index]];
                index = parent[index];
            }
            return index;
        }

        public void union(int a, int b) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather != bFather) {
                if (rank[aFather] <= rank[bFather]) {
                    parent[aFather] = bFather;
                    rank[bFather] += rank[aFather];
                } else {
                    parent[bFather] = aFather;
                    rank[aFather] += rank[bFather];
                }
                count--;
            }
        }
    }
}