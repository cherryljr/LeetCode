/*
We have an array A of integers, and an array queries of queries.
For the i-th query val = queries[i][0], index = queries[i][1], we add val to A[index].
Then, the answer to the i-th query is the sum of the even values of A.

(Here, the given index = queries[i][1] is a 0-based index, and each query permanently modifies the array A.)

Return the answer to all queries.  Your answer array should have answer[i] as the answer to the i-th query.

Example 1:
Input: A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
Output: [8,6,2,4]
Explanation:
At the beginning, the array is [1,2,3,4].
After adding 1 to A[0], the array is [2,2,3,4], and the sum of even values is 2 + 2 + 4 = 8.
After adding -3 to A[1], the array is [2,-1,3,4], and the sum of even values is 2 + 4 = 6.
After adding -4 to A[0], the array is [-2,-1,3,4], and the sum of even values is -2 + 4 = 2.
After adding 2 to A[3], the array is [-2,-1,3,6], and the sum of even values is -2 + 6 = 4.

Note:
    1. 1 <= A.length <= 10000
    2. -10000 <= A[i] <= 10000
    3. 1 <= queries.length <= 10000
    4. -10000 <= queries[i][0] <= 10000
    5. 0 <= queries[i][1] < A.length
 */

/**
 * Approach: Maintain Array Sum
 * 题目要求每次query操作之后，数组中所有偶数之和。
 * 根据题目所给的数据规模，如果采用暴力解法（每次query之后都遍历一遍数组）
 * 那么时间复杂度将达到 10^8 级别。这明显会出问题。
 *
 * 因此，我们可以先遍历一遍数组，然后求出所有偶数之和。
 * 然后每次 query 的时候，对当前要操作的数进行判断是否为偶数，
 * 如果为偶数，sum -= A[index]。
 * 然后再对 A[index] 的值进行操作， A[index] += value;
 * 最后在判断变更后的值是否为偶数，如果是 sum += A[index] 即可。
 * 这就是我们需要的答案。
 *
 * 时间复杂度：O(NQ) N为数组长度，Q为查询操作的次数
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int sum = 0;
        for (int num : A) {
            sum += (num & 1) == 0 ? num : 0;
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int value = queries[i][0], index = queries[i][1];
            // 值变更前
            sum -= (A[index] & 1) == 0 ? A[index] : 0;
            A[index] += value;
            // 值变更后
            sum += (A[index] & 1) == 0 ? A[index] : 0;
            ans[i] = sum;
        }
        return ans;
    }
}