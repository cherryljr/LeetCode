'''
Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.
We repeatedly make duplicate removals on S until we no longer can.

Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.

Example 1:
Input: "abbaca"
Output: "ca"
Explanation:
For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".

Note:
    1. 1 <= S.length <= 20000
    2. S consists only of English lowercase letters.
'''

# Approach: Stack
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def removeDuplicates(self, S: str) -> str:
        ans = []
        for c in S:
            if ans and ans[-1] == c:
                ans.pop()
            else:
                ans.append(c)
        return "".join(ans)