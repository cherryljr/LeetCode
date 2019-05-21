/*
You have N gardens, labelled 1 to N.  In each garden, you want to plant one of 4 types of flowers.
paths[i] = [x, y] describes the existence of a bidirectional path from garden x to garden y.
Also, there is no garden that has more than 3 paths coming into or leaving it.

Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have different types of flowers.
Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)-th garden.
The flower types are denoted 1, 2, 3, or 4.  It is guaranteed an answer exists.

Example 1:
Input: N = 3, paths = [[1,2],[2,3],[3,1]]
Output: [1,2,3]

Example 2:
Input: N = 4, paths = [[1,2],[3,4]]
Output: [1,2,1,2]

Example 3:
Input: N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
Output: [1,2,3,4]

Note:
    1. 1 <= N <= 10000
    2. 0 <= paths.size <= 20000
    3. No garden has 4 or more paths coming into or leaving it.
    4. It is guaranteed an answer exists.
 */

/**
 * Approach: Greedy + BFS
 * 题目为一个简单的染色问题，要求相邻的花园颜色不能相同。
 *
 * 对此我们可以使用到贪心的做法：从 1~4 依次判断当前花园可以染什么色，随便选一个即可。
 * 只要和邻居花园所染的颜色不冲突即可。
 *
 * 贪心正确性的证明：
 * 题目中已经明确说明有 4 种颜色，并且每个花园的度数最多为 3。
 * 假设某个花园周围都已经染色了，会不会当前花园没法选颜色呢？
 * 答案是不可能的。因为只有邻居把所有颜色都用完的，当前花园才无法进行染色，
 * 而已知最多只有 3 个邻居，因此颜色是永远也用不完的，即永远可以找到一个颜色来对当前花园进行染色。
 *
 * 时间复杂度：O(paths) = O(1.5N) ==> O(N)
 * 空间复杂度：O(N)
 */
class Solution {
    public int[] gardenNoAdj(int N, int[][] paths) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] path : paths) {
            graph.get(path[0] - 1).add(path[1] - 1);
            graph.get(path[1] - 1).add(path[0] - 1);
        }

        int[] ans = new int[N];
        for (int i = 0; i < graph.size(); i++) {
            boolean[] colors = new boolean[5];
            for (int neigh : graph.get(i)) {
                colors[ans[neigh]] = true;
            }
            // 选择一个未在邻居花园中出现过的颜色进行染色
            for (int color = 1; color <= 4; color++) {
                if (!colors[color]) {
                    ans[i] = color; break;
                }
            }
        }
        return ans;
    }
}