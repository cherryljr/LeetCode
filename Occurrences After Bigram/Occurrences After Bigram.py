'''
Given a binary string S (a string consisting only of '0' and '1's) and a positive integer N, 
return true if and only if for every integer X from 1 to N, the binary representation of X is a substring of S.

Example 1:
Input: S = "0110", N = 3
Output: true

Example 2:
Input: S = "0110", N = 4
Output: false
 
Note:
    1. 1 <= S.length <= 1000
    2. 1 <= N <= 10^9
'''

# Approach: Split Text and Compare
# 时间复杂度：O(n)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def findOcurrences(self, text: str, first: str, second: str) -> List[str]:
        ans = []
        strs = text.split(" ")
        for i in range(2, len(strs)):
            if strs[i - 2] == first and strs[i - 1] == second:
                ans.append(strs[i]) 
        return ans