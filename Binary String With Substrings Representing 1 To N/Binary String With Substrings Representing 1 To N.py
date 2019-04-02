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

# 解法详解参考同名 java 文件
class Solution:
    def queryString(self, S: str, N: int) -> bool:
        return all(bin(i)[2:] in S for i in range(N, N >> 1, -1))