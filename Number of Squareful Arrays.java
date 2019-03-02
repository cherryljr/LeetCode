/*
Given an array A of non-negative integers, the array is squareful if for every pair of adjacent elements, their sum is a perfect square.

Return the number of permutations of A that are squareful.
Two permutations A1 and A2 differ if and only if there is some index i such that A1[i] != A2[i].

Example 1:
Input: [1,17,8]
Output: 2
Explanation:
[1,8,17] and [17,8,1] are the valid permutations.

Example 2:
Input: [2,2,2]
Output: 1

Note:
    1. 1 <= A.length <= 12
    2. 0 <= A[i] <= 1e9
 */

/**
 * Approach: DFS (Permutations) + Pruning
 * 首先看题目的数据规模，我们发现 A.length 只有 12 的大小。（O(n!)的算法上限在10）
 * 因此可以推测出直接使用 DFS列出所有的Permutations 加上 简单的剪枝 应该就能过了。
 *
 * 题目这里说明结果需要的 unique 的，提醒我们要注意 permutations 的去重。
 * 这里可以参考 Permutations II 中的做法。
 * 对于剪枝，我们这里只需要在将 A[i] 添加到 list 中的时候，判断一下是否符合题目的 squareful 要求即可。
 * 即 list中的最后一个元素 与 A[i] 之和是否为一个完全平方数。
 * 如果不符合要求的话，说明该 permutation 不合法，没有继续做的必要，直接 continue 即可。
 *
 * 时间复杂度：O(n!)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Permutations%20II.java
 */
class Solution {
    int ans = 0;

    public int numSquarefulPerms(int[] A) {
        boolean[] visited = new boolean[A.length];
        Arrays.sort(A);
        dfs(A, visited, new ArrayList<Integer>());
        return ans;
    }

    private void dfs(int[] A, boolean[] visited, List<Integer> list) {
        if (list.size() == A.length) {
            ans++;
            return;
        }

        for (int i = 0; i < A.length; i++) {
            // Avoid duplications and used number
            if (visited[i] || (i != 0 && A[i] == A[i - 1]
                    && !visited[i - 1])) {
                continue;
            }
            // Pruning invalid permutations
            if (!list.isEmpty() && !squareful(list.get(list.size() - 1), A[i])) {
                continue;
            }

            list.add(A[i]);
            visited[i] = true;
            dfs(A, visited, list);
            // Backtracking
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }

    private boolean squareful(int x, int y) {
        int s = (int)Math.sqrt(x + y);
        return s * s == x + y;
    }
}

