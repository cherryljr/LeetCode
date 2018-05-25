/*
We stack glasses in a pyramid, where the first row has 1 glass, the second row has 2 glasses, and so on until the 100th row.
Each glass holds one cup (250ml) of champagne.
Then, some champagne is poured in the first glass at the top.
When the top most glass is full, any excess liquid poured will fall equally to the glass immediately to the left and right of it.
When those glasses become full, any excess champagne will fall equally to the left and right of those glasses, and so on.
(A glass at the bottom row has it's excess champagne fall on the floor.)

For example, after one cup of champagne is poured, the top most glass is full.
After two cups of champagne are poured, the two glasses on the second row are half full.
After three cups of champagne are poured, those two cups become full - there are 3 full glasses total now.
After four cups of champagne are poured, the third row has the middle glass half full,
and the two outside glasses are a quarter full, as pictured below.

The picture: https://leetcode.com/problems/champagne-tower/description/

Now after pouring some non-negative integer cups of champagne,
return how full the j-th glass in the i-th row is (both i and j are 0 indexed.)

Example 1:
Input: poured = 1, query_glass = 1, query_row = 1
Output: 0.0
Explanation: We poured 1 cup of champange to the top glass of the tower
(which is indexed as (0, 0)). There will be no excess liquid so all the glasses under the top glass will remain empty.

Example 2:
Input: poured = 2, query_glass = 1, query_row = 1
Output: 0.5
Explanation: We poured 2 cups of champange to the top glass of the tower (which is indexed as (0, 0)).
There is one cup of excess liquid. The glass indexed as (1, 0) and the glass indexed as (1, 1) will share the excess liquid equally,
and each will get half cup of champange.

Note:
poured will be in the range of [0, 10 ^ 9].
query_glass and query_row will be in the range of [0, 99].
 */

/**
 * Approach: Simulation
 * 对于这种问题通常是通过模拟这个过程来进行解决，关键点就是在于找到信息的突破口。
 * 本题，我们关注的是香槟的量。我们可以实现在草图上写出前几个 poured 的值所对应香槟塔的情况。
 * 我们可以发现，对于每一个杯子而言，它溢出时必定会朝着两个方向溢出，并且量是相等的。
 * 那么剩下的香槟向下一层相邻两个杯子中的注入量各为：
 *      leftChampagne = (tower[row][col] - 1) / 2
 * 因此利用这一点，我们可以从 row = 0 开始逐层向下递推。
 * 比如 poured = 6, 那么 row = 0 可以消耗掉 1，还剩下 5，并且将这剩下的 5 平均向两个杯子中注入。
 * 即：5 -> 2.5 2.5  然后第二层的两个杯子注满之后仍然可以向第三层的杯子注入香槟。
 * 即：5 -> 2.5 2.5 -> 0.75 0.75+0.75 0.75 依次向下递推即可。
 *
 * 时间复杂度为：O(R^2) 因为题目中一定给出 R 的范围为 [0, 99]. 因此时间复杂度可看作为 O(1)
 * 空间复杂度为：O(R^2) 同样可以看做是 O(1) 的.
 */
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] tower = new double[101][101];
        tower[0][0] = poured;

        for (int row = 0; row <= query_row; row++) {
            for (int col = 0; col <= row; col++) {
                // 计算下一层两个杯子中的注入量
                double leftChampagne = (tower[row][col] - 1) / 2.0;
                if (leftChampagne > 0) {
                    tower[row + 1][col] += leftChampagne;
                    tower[row + 1][col + 1] += leftChampagne;
                }
            }
        }

        return Math.min(1, tower[query_row][query_glass]);
    }
}

/**
 * Approach 2: Simulation (Space Optimized)
 * 我们发现，当前层的状态仅仅依赖于上一层的状态，因此可以对空间复杂度进行优化。
 * 是不是非常像 01背包问题 呢。其实我觉得你把这道题目归入 DP 也没什么问题。
 * 优化方法同 01背包问题 这里就不细讲了。
 */
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[] tower = new double[101];
        tower[0] = poured;

        for (int row = 1; row <= query_row; row++) {
            for (int col = row; col >= 0; col--) {
                double leftChampagne = (tower[col] - 1) / 2.0;
                if (leftChampagne > 0) {
                    tower[col + 1] += leftChampagne;
                    tower[col] = leftChampagne;
                } else {
                    tower[col] = 0;
                }
            }
        }

        return Math.min(1, tower[query_glass]);
    }
}