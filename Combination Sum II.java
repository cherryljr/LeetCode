/*
Given a collection of candidate numbers (candidates) and a target number (target), 
find all unique combinations in candidates where the candidate numbers sums to target.
Each number in candidates may only be used once in the combination.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [10,1,2,7,6,1,5], target = 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]

Example 2:
Input: candidates = [2,5,2,1,2], target = 5,
A solution set is:
[
  [1,2,2],
  [5]
]
 */

/**
 * Approach: Backtracking
 * 与 LintCode 上的 Combination Sun II 一样。
 * 具体解释可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Combination%20Sum%20II.java
 */
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new LinkedList<>();
        }

        List<List<Integer>> rst = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, 0, target, new LinkedList<>(), rst);
        return rst;
    }

    private void dfs(int[] candidates, int index, int remainTraget, LinkedList<Integer> list, List<List<Integer>> rst) {
        if (remainTraget == 0) {
            rst.add(new LinkedList<>(list));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            // 当 remainTarget < candidates[i] 时,可以直接结束递归
            // 以起到加速效果
            if (remainTraget < candidates[i]) {
                break;
            }
            if (i != index && candidates[i - 1] == candidates[i]) {
                continue;
            }

            list.add(candidates[i]);
            dfs(candidates, i + 1, remainTraget - candidates[i], list, rst);
            list.remove(list.size() - 1);
        }
    }
}