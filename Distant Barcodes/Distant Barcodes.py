'''
In a warehouse, there is a row of barcodes, where the i-th barcode is barcodes[i].
Rearrange the barcodes so that no two adjacent barcodes are equal.
You may return any answer, and it is guaranteed an answer exists.

Example 1:
Input: [1,1,1,2,2,2]
Output: [2,1,2,1,2,1]

Example 2:
Input: [1,1,1,1,2,2,3,3]
Output: [1,3,1,3,2,1,2,1]

Note:
    1. 1 <= barcodes.length <= 10000
    2. 1 <= barcodes[i] <= 10000
'''

# Approach: 01 Backpack
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def lastStoneWeightII(self, stones: List[int]) -> int:
        s = sum(stones)
        dp = [False] * (s // 2 + 1)
        dp[0] = True
        for i in range(len(stones)):
            for j in range(s >> 1, stones[i] - 1, -1):
                dp[j] |= dp[j - stones[i]]
        return min(s - i - i for i in range(s // 2 + 1) if dp[i])