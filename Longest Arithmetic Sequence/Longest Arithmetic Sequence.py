'''
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
'''

# Appraoch: DP
# 解法详细解释可以参见同名 java 文件
# 得益于 python 的自由性，我们可以利用 字典 来完成数据的存储。（类似 Java 中的 Map）
# 但是我们可以在 dict 中存储 [i, diff] 的元组，从而直观地完成数据的存储与解析。
# dp[i, diff] 对应的就是以 A[i] 作为结尾，差值为 diff 的最长等差数列的长度了
# 可能大家会抱怨这样的代码执行效率不高...但是我使用Python会更加偏向于代码的优美性和利用该语言本身的优势...
# 
# 时间复杂度：O(N^2)
# 空间复杂度：O(N*M)
class Solution:
    def longestArithSeqLength(self, A: List[int]) -> int:
        dp = {}
        for i in range(len(A) - 1):
            for j in range(i + 1, len(A)):
                dp[j, A[j] - A[i]] = dp.get((i, A[j] - A[i]), 1) + 1
        return max(dp.values())