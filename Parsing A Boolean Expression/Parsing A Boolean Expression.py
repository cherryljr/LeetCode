'''
Return the result of evaluating a given boolean expression, represented as a string.
An expression can either be:
    "t", evaluating to True;
    "f", evaluating to False;
    "!(expr)", evaluating to the logical NOT of the inner expression expr;
    "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
    "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...

Example 1:
Input: expression = "!(f)"
Output: true

Example 2:
Input: expression = "|(f,t)"
Output: true

Example 3:
Input: expression = "&(t,f)"
Output: false

Example 4:
Input: expression = "|(&(t,f,t),!(t))"
Output: false

Constraints:
    1. 1 <= expression.length <= 20000
    2. expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
    3. expression is a valid expression representing a boolean, as given in the description.
'''

# Approach: Using Stack + HashSet
# 时间复杂度：O(n)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件 Approach 2
class Solution:
    def parseBoolExpr(self, expression: str) -> bool:
        stack = []
        for c in expression:
            if c == ')':
                seen = set()
                while stack[-1] != '(':
                    seen.add(stack.pop())
                stack.pop()
                operator = stack.pop()
                stack.append(all(seen) if operator == '&' else any(seen) if operator == '|' else not seen.pop())
            elif c != ',':
                stack.append(True if c == 't' else False if c == 'f' else c)
        return stack.pop()