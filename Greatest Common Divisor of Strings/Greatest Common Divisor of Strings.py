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

# Approach: Imitate GCD Algorithm (Recursion)
# 时间复杂度：O(n^2)
# 空间复杂度：O(n^2)
# 解法详解参考同名 java 文件
class Solution:
    def gcdOfStrings(self, str1: str, str2: str) -> str:
        if len(str1) < len(str2):
            return self.gcdOfStrings(str2, str1)
        elif str1[:len(str2)] != str2:
            return ""
        elif len(str2) == 0:
            return str1
        else:
            return self.gcdOfStrings(str1[len(str2):], str2)