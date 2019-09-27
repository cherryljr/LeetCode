/*
Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) needed to make arr1 strictly increasing.
In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do the assignment arr1[i] = arr2[j].

If there is no way to make arr1 strictly increasing, return -1.

Example 1:
Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
Output: 1
Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].

Example 2:
Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
Output: 2
Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].

Example 3:
Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
Output: -1
Explanation: You can't make arr1 strictly increasing.

Constraints:
    1. 1 <= arr1.length, arr2.length <= 2000
    2. 0 <= arr1[i], arr2[i] <= 10^9
 */

/**
 * Approach: DP (Similar to Minimum Swaps to Make Sequences Increasing)
 * 这道题目和 Minimum Swaps to Make Sequences Increasing 解题思路基本相同，但是情况会更加复杂一些。
 * 主体思路依旧是在DP遍历的过程中，维护当前状态是否需要进行 replace 和 前一个状态是否是经过 replace 获得的。
 * 并且本题中，可以用于 replace 的元素为 arr2 中所有的元素，因此我们需要记录被替换成的值是多少，以便于状态比较。
 *  keep[i]：表示当前字符 arr1[i] 保留的情况下，使得前 i 个元素为严格单调递增的最小 cost
 *  replace[i][j]：表示将当前字符 arr1[i] 替换成 arr2[j]，使得前 i 个元素为严格单调递增的最小 cost
 * 则结果就是二者的最小值。
 * 至于状态转移过程，因为前一个元素和当前元素的状态均有 保留 和 替换 两种。
 * 因此进行组合后总共有 4 种情况需要进行分析。（当然替换时还需要记录具体替换成成哪一个元素）
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 *
 * References:
 *  https://youtu.be/8ttxdMCU2GE
 *  https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Swaps%20To%20Make%20Sequences%20Increasing.java
 */
class Solution {
    private static final int INF = 0x3f3f3f3f;

    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        // 因为我们只关心大小，不关心一个数出现了几次，因此可以对 arr2 进行 排序去重 的预处理。
        Arrays.sort(arr2);
        List<Integer> replaceList = new ArrayList<>();
        replaceList.add(arr2[0]);
        for (int i = 1; i < arr2.length; i++) {
            if (arr2[i] != arr2[i - 1]) {
                replaceList.add(arr2[i]);
            }
        }

        // Initialize
        int m = arr1.length, n = replaceList.size();
        int[] keep = new int[m];
        int[][] replace = new int[m][n];
        Arrays.fill(keep, INF);
        keep[0] = 0;
        for (int[] arr : replace) {
            Arrays.fill(arr, INF);
        }
        Arrays.fill(replace[0], 1);

        for (int i = 1; i < m; i++) {
            // 这里使用到了空间换时间的做法，维护 minKeep 和 minReplace，避免了每次重新开始进行一次 O(n) 的遍历
            int minKeep = INF, minReplace = INF;
            for (int j = 0; j < n; j++) {
                // 前一个字符被替换成 arr2[j-1] 且当前字符需要被替换成 arr2[j]
                if (j > 0) minReplace = Math.min(minReplace, replace[i - 1][j - 1] + 1);
                // 前一个字符被替换成 arr2[j] 且当前字符进行保留
                if (arr1[i] > replaceList.get(j)) minKeep = Math.min(minKeep, replace[i - 1][j]);
                // 前一个字符与当前字符都进行保留
                if (arr1[i] > arr1[i - 1]) keep[i] = keep[i - 1];
                // 前一个字符进行保留，当前字符替换成 arr2[j]
                if (replaceList.get(j) > arr1[i - 1]) replace[i][j] = keep[i - 1] + 1;
                keep[i] = Math.min(keep[i], minKeep);
                replace[i][j] = Math.min(replace[i][j], minReplace);
            }
        }

        int replaceMin = INF;
        for (int num : replace[m - 1]) {
            replaceMin = Math.min(replaceMin, num);
        }
        int ans = Math.min(keep[m - 1], replaceMin);
        return ans >= INF ? -1 : ans;
    }
}