'''
A query word matches a given pattern if we can insert lowercase letters to the pattern word so that it equals the query.
(We may insert each character at any position, and may insert 0 characters.)

Given a list of queries, and a pattern, return an answer list of booleans,
where answer[i] is true if and only if queries[i] matches the pattern.

Example 1:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FB"
Output: [true,false,true,true,false]
Explanation:
"FooBar" can be generated like this "F" + "oo" + "B" + "ar".
"FootBall" can be generated like this "F" + "oot" + "B" + "all".
"FrameBuffer" can be generated like this "F" + "rame" + "B" + "uffer".

Example 2:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
Output: [true,false,true,false,false]
Explanation:
"FooBar" can be generated like this "Fo" + "o" + "Ba" + "r".
"FootBall" can be generated like this "Fo" + "ot" + "Ba" + "ll".

Example 3:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBaT"
Output: [false,true,false,false,false]
Explanation:
"FooBarTest" can be generated like this "Fo" + "o" + "Ba" + "r" + "T" + "est".

Note:
    1. 1 <= queries.length <= 100
    2. 1 <= queries[i].length <= 100
    3. 1 <= pattern.length <= 100
    4. All strings consists only of lower and upper case English letters.
'''

# Approach 1: Traversal (String Matche)
# 解法详解参考同名 java 文件 Approach 1
# 时间复杂度：O(M * N)
# 空间复杂度：O(1)
class Solution:
    def camelMatch(self, queries: List[str], pattern: str) -> List[bool]:
        ans = []
        for query in queries:
            ans.append(self.isMatch(query, pattern))
        return ans
    
    def isMatch(self, query: str, pattern: str) -> bool:
        index = 0
        for c in query:
            if index < len(pattern) and c == pattern[index]:
                index += 1
            elif c.isupper():
                return False
        return index == len(pattern)


# Approach 2: Regular Expression
# # 解法详解参考同名 java 文件 Approach 2
class Solution:
    def camelMatch(self, queries: List[str], pattern: str) -> List[bool]:
        return [re.match("^[a-z]*" + "[a-z]*".join(pattern) + "[a-z]*$", q) != None for q in queries]