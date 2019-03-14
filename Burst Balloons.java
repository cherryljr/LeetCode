/*
Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. 
You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. 
Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
Find the maximum coins you can collect by bursting the balloons wisely.

Note: 
(1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
(2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100

Example:
Given [3, 1, 5, 8]
Return 167
    nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
   coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
*/

/**
 * Approach: Interval DP
 * 这道题目与 Strange Printer 有一定的类似，但是并不明显。
 * 下面我将会分析该题的解题思路并与 Strange Printer 进行对比，希望能帮助大家理解。
 *
 * 思路：
 * 题目想要求我们通过一种方式引爆气球使得最后的得分最高。
 * 本题的难点在于：每次引爆一个气球之后，气球的 左边部分 和 右边部分 将会连接起来，
 * 并且会对之后计算 maxCoins 产生影响。（没有独立子问题）
 * 这使得我们很难通过 分治 或者 动态规划 的方法去解决这个问题，因为我们无法很好地确认状态转移方程。
 * 那么为了解决该问题，我们需要注意到非常重要的一点：
 *  已经爆炸的气球不会再对 maxCoins 的计算产生任何影响！
 * 这点非常重要！它提示我们可以 逆过来 思考问题，即：
 *
 * 我们从最后一个爆炸的气球开始，逆序推导回去。
 * 这样我们就可以利用这个气球作为分割点来将序列分成左右两个部分，然后一步步逆推回去了。
 *  eg. 假设 i 为最后一个爆炸的气球，那么 nums[0...i] 和 nums[i...end] 必定在这之前就已经被引爆了。
 *  也就是说：这左右两个部分的 气球引爆情况 对彼此来说是没有任何影响的，因此也就可以被分割成两个部分了。
 * 然后我们只需要枚举 每个区间长度中 每一个气球 在最后被引爆的情况，就能够得出 maxCoins 了。
 * 对此我们用到了 序列的动态规划：
 * dp[i][j] 表示区间 [i...j] 所能得到的 maxCoins.
 *
 * 接下来的分析情况与 Strange Printer 有些许类似 （为了便于理解，我尽量保持二者代码的一致以便对比）总体可分为以下几个步骤：
 *  ① 我们需要枚举所有 合法的区间长度l, 并计算出它们对于的 maxCoins；
 *  ② 枚举所有可能的起始位置start，作为区间的左边界（起始位置start + 区间长度l <= 数组总长度len）
 *  ③ 在各个区间中，我们可以利用 pivot 作为分割点对区间进行分割以便我们计算出 maxCoins；
 *  注意，根据以上分析，这里的 pivot 代表的就是这段区间中 最后一个被引爆的气球。
 *  即在区间范围内枚举 pivot 的位置，求出最佳位置，从而得到 这段区间的maxCoins。
 * （同样，对应于 Strange Printer 中的 pivot 代表的是可能与 s[start] 相等的字符，
 *  如果相等，就意味着可以对 dp[start][end] 的大小进缩减，即减少打印次数，
 *  所以我们也要对它的位置进行 枚举 来求出该段区间的最少打印次数）。
 *  ④ 根据题目条件分析出 dp[start][end] 的递推方程
 *  coins 的计算由左右两个点的分乘以 pivot 点的分所得。因此可推断出如下公式:
 *  dp[start][end] = nums[start] * nums[pivot] * nums[end] + pivot左部分的最大值 + pivot右部分的最大值。
 *  因为在计算 左/右部分 的maxCoins时需要用到 pivot 的值，所以 左右部分maxCoins和 公式为 dp[start][pivot] + dp[pivot[end].
 *
 * 时间复杂度分析：
 *  因为我们需要对所有的区间长度进行一次分析，所以时间复杂度为：O(n^2)
 *  而对于各个区间，我们还需要枚举 pivot 的值来进行分割以求得该区间的 maxCoins。
 *  因此处理一个区间的时间复杂度为：O(n)
 *  故，整体时间复杂为：O(n^3)
 * 空间复杂度分析:
 *  动态规划需要一个二维数组，因此时间复杂度为：O(n^2)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LeetCode/blob/master/Strange%20Printer.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Cost%20to%20Merge%20Stones.java
 *  https://github.com/cherryljr/LintCode/blob/master/Segment%20Stones%20Merge.java
 */
class Solution {
    public int maxCoins(int[] iNums) {
        if (iNums == null || iNums.length == 0) {
            return 0;
        }

        // 因为当引爆最 左/右 边的气球时，它的一边值为 1.为了便于计算
        // 我们新建一个 nums[], 其大小为 iNums.length+2, 最左/右边的一个元素为 1
        int[] nums = new int[iNums.length + 2];
        int len = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[len++] = x;
            }
        }
        nums[0] = nums[len++] = 1;
        int[][] dp = new int[len][len];

        // 枚举所有合法的区间长度
        for (int l = 2; l <= len; l++) {
            // 枚举所有可能的起始位置
            for (int start = 0; start + l <= len; start++) {
                int end = start + l - 1;
                // 遍历当前区间，枚举 分段点pivot 并利用它将当前区间分段为 nums[start, pivot] 和 nums[pivot+1, end]
                // 以得到最佳位置使得 区间s[start, end]的得分最多 (dp[start][end]有最大值)
                for (int pivot = start + 1; pivot < end; pivot++) {
                    dp[start][end] = Math.max(dp[start][end],
                            nums[start] * nums[pivot] * nums[end] + dp[start][pivot] + dp[pivot][end]);
                }
            }
        }

        return dp[0][len - 1];
    }
}