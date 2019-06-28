'''
Return the lexicographically smallest subsequence of text
that contains all the distinct characters of text exactly once.

Example 1:
Input: "cdadabcc"
Output: "adbc"

Example 2:
Input: "abcd"
Output: "abcd"

Example 3:
Input: "ecbacba"
Output: "eacb"

Example 4:
Input: "leetcode"
Output: "letcod"

Note:
    1. 1 <= text.length <= 1000
    2. text consists of lowercase English letters.
'''

# Approach: Monotonic Stack
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def smallestSubsequence(self, text: str) -> str:
        lastIndex = {c : i for i, c in enumerate(text)}
        stack = []
        for i, c in enumerate(text):
            if c in stack: continue
            while stack and stack[-1] > c and lastIndex[stack[-1]] > i:
                stack.pop()
            stack.append(c)
        return "".join(stack)