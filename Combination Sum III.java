/*
Find all possible combinations of k numbers that add up to a number n,
given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

Example 1:
Input: k = 3, n = 7
Output:
[[1,2,4]]

Example 2:
Input: k = 3, n = 9
Output:
[[1,2,6], [1,3,5], [2,3,4]]
 */

/**
 * Approach: Backtracking
 * 依旧是 DFS 枚举所有可能性即可。
 * 因为只能从 1~9 中选择数字，并且每个数字只能使用一次。
 * 因此这就相当于：给定一个 有序 且 不包含重复元素 的数组。求所有 sum 为 n 的 subset.
 * 不了解 Backtracking 的可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Subset.java
 */
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        if (k <= 0) {
            return new LinkedList<>();
        }

        List<List<Integer>> rst = new LinkedList<>();
        dfs(1, n, k, new LinkedList<>(), rst);
        return rst;
    }

    private void dfs(int start, int remainTarget, int k, LinkedList<Integer> list, List<List<Integer>> rst) {
        // 找到一个符合条件的答案就将其 add 到 rst 中。
        if (list.size() == k && remainTarget == 0) {
            rst.add(new LinkedList<>(list));
            return;
        }

        for (int i = start; i <= 9; i++) {
            if (i > remainTarget) {
                break;
            }
            list.add(i);
            dfs(i + 1,remainTarget - i, k, list, rst);
            list.remove(list.size() - 1);
        }
    }
}