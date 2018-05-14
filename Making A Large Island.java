/*
In a 2D grid of 0s and 1s, we change at most one 0 to a 1.
After, what is the size of the largest island? (An island is a 4-directionally connected group of 1s).

Example 1:
Input: [[1, 0], [0, 1]]
Output: 3
Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.

Example 2:
Input: [[1, 1], [1, 0]]
Output: 4
Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 1.

Example 3:
Input: [[1, 1], [1, 1]]
Output: 4
Explanation: Can't change any 0 to 1, only one island with area = 1.

Notes:
1 <= grid.length = grid[0].length <= 50.
0 <= grid[i][j] <= 1.
 */

/**
 * Approach: Union Find
 * For each 0 in the grid, we can change it to a 1 temporarily,
 * then do a DFS / BFS to find the size of that component. The answer is the maximum size component found.
 * But this method must be Time Limit Exceed.
 * So is there any better method?
 *
 * We can use Union Find to solve this problem just like in Number of Islands II.
 * Firstly, we traverse the map and union the 1s to form all single islands.
 * With the help of union find, we can get the current gird belongs to which island and the area easily in O(1)
 * (here we should use compressed find method)
 * Secondly, we change 0 to 1 temporarily at each grid.
 * Instead of connecting islands together by union method, we just add all four neighbor islands area
 * and update the maxArea if we find a bigger one.
 *
 * Time Complexity: O(MN)
 *
 * Number of Islands II:
 * https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Islands%20II.java
 *
 * It also could be solved with DFS, but with the same idea. (Just like implement a Union Find)
 * For details: https://leetcode.com/articles/making-a-large-island/
 */
class Solution {
    int maxArea = 1;

    public int largestIsland(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        UnionFind uf = new UnionFind(rows * cols);
        int[][] dirs = new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }

                // update the area of curr gird
                uf.area[i * cols + j] = 1;
                // we only need to union the left and up gird of current position here
                // the other two girds' area are all 0, cuz we haven't visited them.
                for (int k = 0; k < 2; k++) {
                    int preRow = i + dirs[k][0];
                    int preCol = j + dirs[k][1];
                    if (preRow < 0 || preCol < 0 || grid[preRow][preCol] == 0) {
                        continue;
                    }
                    uf.union(i * cols + j, preRow * cols + preCol);
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // skip the 1s gird
                if (grid[i][j] == 1) {
                    continue;
                }

                int curr = i * cols + j;
                uf.area[curr] = 1;
                // Use set to record neighbors' father to avoid adding area repeatedly
                Set<Integer> neighs = new HashSet<>();
                for (int[] dir : dirs) {
                    int nextRow = i + dir[0];
                    int nextCol = j + dir[1];
                    if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols || grid[nextRow][nextCol] == 0) {
                        continue;
                    }
                    int neighFather = uf.compressFind(nextRow * cols + nextCol);
                    if (!neighs.contains(neighFather)) {
                        neighs.add(neighFather);
                        uf.area[curr] += uf.area[neighFather];
                    }
                }
                maxArea = Math.max(maxArea, uf.area[curr]);
            }
        }

        return maxArea;
    }

    class UnionFind {
        int[] parent;
        int[] area;

        UnionFind(int n) {
            parent = new int[n];
            area = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int compressFind(int index) {
            if (index != parent[index]) {
                parent[index] = compressFind(parent[index]);
            }
            return parent[index];
        }

        // Union areaB to areaA
        // You can also use area value to make balance, but we ignore it for concise code
        public void union(int a, int b) {
            int aFather = compressFind(a);
            int bFather = compressFind(b);
            if (aFather != bFather) {
                parent[bFather] = aFather;
                area[aFather] += area[bFather];
                maxArea = Math.max(maxArea, area[aFather]);
            }
        }
    }
}