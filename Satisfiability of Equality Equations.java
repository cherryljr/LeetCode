/*
Given an array equations of strings that represent relationships between variables,
each string equations[i] has length 4 and takes one of two different forms: "a==b" or "a!=b".
Here, a and b are lowercase letters (not necessarily different) that represent one-letter variable names.

Return true if and only if it is possible to assign integers to variable names so as to satisfy all the given equations.

Example 1:
Input: ["a==b","b!=a"]
Output: false
Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.
There is no way to assign the variables to satisfy both equations.

Example 2:
Input: ["b==a","a==b"]
Output: true
Explanation: We could assign a = 1 and b = 1 to satisfy both equations.

Example 3:
Input: ["a==b","b==c","a==c"]
Output: true

Example 4:
Input: ["a==b","b!=c","c==a"]
Output: false

Example 5:
Input: ["c==c","b==d","x!=z"]
Output: true

Note:
    1. 1 <= equations.length <= 500
    2. equations[i].length == 4
    3. equations[i][0] and equations[i][3] are lowercase letters
    4. equations[i][1] is either '=' or '!'
    5. equations[i][2] is '='
 */

/**
 * Approach: Union Find
 * 并查集 的模板问题...没什么好说的了...趁着周赛敲遍模板热热手
 */
class Solution {
    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind();
        for (String equation : equations) {
            int a = equation.charAt(0) - 'a';
            int b = equation.charAt(3) - 'a';
            if (equation.charAt(1) == '=') {
                uf.union(a, b);
            }
        }

        for (String equation : equations) {
            int aFather = uf.compressedFind(equation.charAt(0) - 'a');
            int bFather = uf.compressedFind(equation.charAt(3) - 'a');
            if (equation.charAt(1) == '!' && aFather == bFather) {
                return false;
            }
        }
        return true;
    }

    class UnionFind {
        int[] parent;
        int[] rank; // Used to optimize the time complexity

        UnionFind() {
            parent = new int[26];
            rank = new int[26];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int compressedFind(int a) {
            if (parent[a] != a) {
                return compressedFind(parent[a]);
            }
            return parent[a];
        }

        public void union(int a, int b) {
            int aFather = compressedFind(a), bFather = compressedFind(b);
            if (aFather != bFather) {
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
}