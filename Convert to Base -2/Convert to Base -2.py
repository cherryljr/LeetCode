'''
Given a number N, return a string consisting of "0"s and "1"s that 
represents its value in base -2 (negative two).

The returned string must have no leading zeroes, unless the string is "0".

Example 1:
Input: 2
Output: "110"
Explantion: (-2) ^ 2 + (-2) ^ 1 = 2

Example 2:
Input: 3
Output: "111"
Explantion: (-2) ^ 2 + (-2) ^ 1 + (-2) ^ 0 = 3

Example 3:
Input: 4
Output: "100"
Explantion: (-2) ^ 2 = 4

Note:
    1. 0 <= N <= 10^9
'''

# 解法详解参考同名 java 文件
class Solution(object):
    def baseNeg2(self, N):
        """
        :type N: int
        :rtype: str
        """
        ans = []
        while N != 0:
            reminder = N % -2
            N //= -2
            if reminder < 0:
                reminder += 2
                N += 1
            ans.append(str(reminder))
        
        if len(ans) == 0:
            return "0"
        else:
            # Attetion here:
            # list.reverse() method acts in-place, and therefore returns None
            # "".join(ans.reverse()) will be wrong (can't be iterable)
            ans.reverse()
            return "".join(ans)
                