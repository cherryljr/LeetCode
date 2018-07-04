/*
Given several boxes with different colors represented by different positive numbers.
You may experience several rounds to remove boxes until there is no box left.
Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1),
remove them and get k*k points.Find the maximum points you can get.

Example 1:
Input:
[1, 3, 2, 2, 2, 3, 4, 3, 1]
Output:
23
Explanation:
[1, 3, 2, 2, 2, 3, 4, 3, 1]
----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
----> [1, 3, 3, 3, 1] (1*1=1 points)
----> [1, 1] (3*3=9 points)
----> [] (2*2=4 points)

Note: The number of boxes n would not exceed 100.
 */

/**
 * Approach 1: DFS + Memory Search
 * Reference:
 *  https://leetcode.com/problems/remove-boxes/discuss/101312/Memoization-DFS-C++
 *  https://www.youtube.com/watch?v=U8Ru-ZpfHfA
 */
class Solution {
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] mem = new int[n][n][n];
        return dfs(boxes, mem, 0, n - 1, 0);
    }

    private int dfs(int[] boxes, int[][][] mem, int left, int right, int k) {
        if (left > right) {
            return 0;
        }
        if (mem[left][right][k] != 0) {
            return mem[left][right][k];
        }

        // First Case
        while (right > left && boxes[right] == boxes[right - 1]) {
            right--;
            k++;
        }
        mem[left][right][k] = (k + 1) * (k + 1) + dfs(boxes, mem, left, right - 1, 0);

        // Second Case
        for (int i = left ; i < right; i++) {
            if (boxes[i] == boxes[right]) {
                mem[left][right][k] = Math.max(mem[left][right][k],
                        dfs(boxes, mem, i + 1, right - 1, 0) + dfs(boxes, mem, left, i, k + 1));
            }
        }
        return mem[left][right][k];
    }
}

/**
 * Approach 2: DP
 * The nature way to divide the problem is burst one balloon and
 * separate the balloons into 2 sub sections one on the left and one one the right.
 * However, in this problem the left and right become adjacent and have effects on the maxCoins in the future.
 *
 * Then another interesting idea come up. Which is quite often seen in dp problem analysis.
 * That is reverse thinking. Like I said the coins you get for a balloon does not depend on the balloons already burst.
 * Therefore instead of divide the problem by the first balloon to burst, we divide the problem by the last balloon to burst.
 *
 * Why is that? Because only the first and last balloons we are sure of their adjacent balloons before hand!
 * For the first we have nums[i-1]*nums[i]*nums[i+1] for the last we have nums[-1]*nums[i]*nums[n].
 * OK. Think about n balloons if i is the last one to burst, what now?
 * We can see that the balloons is again separated into 2 sections.
 * But this time since the balloon i is the last balloon of all to burst,
 * the left and right section now has well defined boundary and do not affect each other!
 * Therefore we can do either recursive method with memoization or dp.
 *
 * Note that we put 2 balloons with 1 as boundaries and also burst all the zero balloons in the first round
 * since they won't give any coins.
 * The algorithm runs in O(n^3) which can be easily seen from the 3 loops in dp solution.
 */
class Solution {
    public int removeBoxes(int[] boxes) {
        int len = boxes.length;
        int[][][] dp = new int[len][len][len];

        // Initialize the dp array
        for (int j = 0; j < len; j++) {
            for (int k = 0; k <= j; k++) {
                dp[j][j][k] = (k + 1) * (k + 1);
            }
        }

        for (int l = 1; l < len; l++) {
            for (int j = l; j < len; j++) {
                int i = j - l;

                for (int k = 0; k <= i; k++) {
                    int res = (k + 1) * (k + 1) + dp[i + 1][j][0];

                    for (int m = i + 1; m <= j; m++) {
                        if (boxes[m] == boxes[i]) {
                            res = Math.max(res, dp[i + 1][m - 1][0] + dp[m][j][k + 1]);
                        }
                    }

                    dp[i][j][k] = res;
                }
            }
        }

        return (len == 0 ? 0 : dp[0][len - 1][0]);
    }
}