/*
Given an array A of integers, return the length of the longest arithmetic subsequence in A.
Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k <= A.length - 1,
and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 0 <= i < B.length - 1).

Example 1:
Input: [3,6,9,12]
Output: 4
Explanation:
The whole array is an arithmetic sequence with steps of length = 3.

Example 2:
Input: [9,4,7,2,10]
Output: 3
Explanation:
The longest arithmetic subsequence is [4,7,10].

Example 3:
Input: [20,1,15,3,10,5,8]
Output: 4
Explanation:
The longest arithmetic subsequence is [20,15,10,5].

Note:
    1. 2 <= A.length <= 2000
    2. 0 <= A[i] <= 10000
 */

/**
 * Approach 1: DP (Based on Array)
 * 这道问题与 LIS 具有一定的类似度。只不过这里要求的时等差数列。
 * 因此我们想到使用 DP 来解决这个问题。
 * 原因为：当结尾数字 A[i] 和 差值diff 一旦确定之后，答案变已经确定了下来，与如何到达当前状态无关。
 * 对此我们可以使用到 dp[][] 来表示对应的状态：
 *  dp[i][diff]: 表示以 A[i] 作为等差数列结尾，差值为 diff 的最长等差数列的长度。
 * 则，我们可以得到状态转移方程：
 *  dp[j][diff] = Math.max(dp[j][diff], dp[i][diff] + 1)
 * 因为 diff 可能为负数，所以这里需要将其 +offset 使其全都转换成 正整数 进行处理。（题目中已经说明所有A[i]为非负数）
 * 这个技巧在使用 arr[] 替代 Map 中非常常见。
 *
 * 初始状态时，各个值均为 1. （只有一个数本身，因此初始长度为1）
 * 但是值得注意的是，Java无法在开辟空间的时候，对数组值进行初始化，这点与 C++ 和 Python 不同。
 * 因此，为了省去初始化所需要 O(N*M) 的时间，这里使用了原本的初始值 0.
 * 而在最后对返回结果 ans 进行了 +1 操作，从而得到我们需要的结果。
 * （这里是否进行初始化大家可以根据自身的编码习惯进行取舍，注意对 ans 的处理即可）
 *
 * 时间复杂度：O(N^2)
 * 空间复杂度：O(N*M)
 */
class Solution {
    public int longestArithSeqLength(int[] A) {
        int offset = Integer.MIN_VALUE;
        for (int num : A) {
            offset = Math.max(offset, num);
        }

        /*int ans = 2;
        int[][] dp = new int[A.length][2 * offset + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, 1);
        }*/
        int ans = 1;
        int[][] dp = new int[A.length][2 * offset + 1];
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                int diff = A[j] - A[i] + offset;    // 保证 diff 为非负数
                dp[j][diff] = Math.max(dp[j][diff], dp[i][diff] + 1);
                ans = Math.max(ans, dp[j][diff]);
            }
        }
        // return ans;
        return ans + 1;
    }
}

/**
 * Approach 2: DP (Based on HashMap)
 * 解法思路说 Approach 1 一模一样，但是说实话，个人并不喜欢这个解法...
 * 虽然引入了 Map，但是并不利于对数据含义进行表达，反而没有使用数组来得直观，
 * 并且使用了 HashMap 和 lambda 表达式使得运行效率低了许多。
 * 好处就是看似省去了一些不必要的空间（diff）和易于存储 递减关系 的序列罢了。
 *
 * 时间复杂度：O(N^2)
 * 空间复杂度：O(N*M)
 */
class Solution {
    public int longestArithSeqLength(int[] A) {
        // dp的键值对代表含义：key -> 等差数列的数值之差； value -> map
        // map的键值对代表含义：key -> 以 A[i] 作为结尾的等差数列； value -> 该等差数列最长的长度
        Map<Integer, Map<Integer, Integer>> dp = new HashMap<>();
        int ans = 2;
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                Map<Integer, Integer> map = dp.computeIfAbsent(A[j] - A[i], diff -> new HashMap<>());
                map.put(j, map.getOrDefault(i, 1) + 1);
                ans = Math.max(ans, map.get(j));
            }
        }
        return ans;
    }
}