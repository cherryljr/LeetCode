/*
Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), 
find all unique combinations in candidates where the candidate numbers sums to target.
The same repeated number may be chosen from candidates unlimited number of times.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [2,3,6,7], target = 7,
A solution set is:
[
  [7],
  [2,2,3]
]

Example 2:
Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
 */

/**
 * Approach: Backtracking
 * 要求比 LintCode 上的要简单一些，可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Combination%20Sum.java
 *
 * 虽然这里不需要进行 去重 操作，但是我们仍然对其进行排序。
 * 为的是能够在 dfs 过程中，当 candidates[i] > remainTarget 可以直接结束递归。
 *（后面的数肯定都比 remainTraget 要大，所以不可能组成 target)
 * 这样可以对程序起到一定的加速作用。
 */
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> rst = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return rst;
        }

        Arrays.sort(candidates);
        dfs(rst, new ArrayList<>(), candidates, 0, target);
        return rst;
    }

    public void dfs(List<List<Integer>> rst, List<Integer> list, int[] candidates,
                    int startIndex, int remainTarget) {
        if (remainTarget == 0) {
            rst.add(new ArrayList<>(list));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if (candidates[i] > remainTarget) {
                break;
            }
            // 无需去重
            // if (i != startIndex && candidates[i] == candidates[i - 1]) {
            //     continue;
            // }
            list.add(candidates[i]);
            dfs(rst, list, candidates, i, remainTarget - candidates[i]);
            list.remove(list.size() - 1);
        }
    }
}