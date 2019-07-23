'''
We are given hours, a list of the number of hours worked per day for a given employee.
A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
A well-performing interval is an interval of days for which the number of tiring days is strictly larger than the number of non-tiring days.

Return the length of the longest well-performing interval.

Example 1:
Input: hours = [9,9,6,0,6,6,9]
Output: 3
Explanation: The longest well-performing interval is [9,9,6].

Constraints:
    1. 1 <= hours.length <= 10000
    2. 0 <= hours[i] <= 16
'''

# Approach: PreSum + HashMap
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def longestWPI(self, hours: List[int]) -> int:
        ans = preSum = 0
        seen = {}
        for i, h in enumerate(hours):
            preSum = preSum + 1 if h > 8 else preSum - 1
            if preSum > 0:
                ans = i + 1
            seen.setdefault(preSum, i)
            if preSum - 1 in seen:
                ans = max(ans, i - seen[preSum - 1])
        return ans