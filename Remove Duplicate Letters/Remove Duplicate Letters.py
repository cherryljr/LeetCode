'''
Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
You must make sure your result is the smallest in lexicographical order among all possible results.

Example 1:
Input: "bcabc"
Output: "abc"

Example 2:
Input: "cbacdcbc"
Output: "acdb"
'''

# Approach: Monotonic Stack (Same as Smallest Subsequence of Distinct Characters)
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def removeDuplicateLetters(self, s: str) -> str:
        lastIndex = {c : i for i, c in enumerate(s)}
        stack = []
        for i, c in enumerate(s):
            if c in stack: continue
            while stack and stack[-1] > c and lastIndex[stack[-1]] > i:
                stack.pop()
            stack.append(c)
        return "".join(stack)