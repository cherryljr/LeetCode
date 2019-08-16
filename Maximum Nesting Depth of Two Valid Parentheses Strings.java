/*
A string is a valid parentheses string (denoted VPS) if and only if it consists of "(" and ")" characters only, and:
    ·It is the empty string, or
    ·It can be written as AB (A concatenated with B), where A and B are VPS's, or
    ·It can be written as (A), where A is a VPS.

We can similarly define the nesting depth depth(S) of any VPS S as follows:
    ·depth("") = 0
    ·depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's
    ·depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
For example,  "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.

Given a VPS seq, split it into two disjoint subsequences A and B, such that A and B are VPS's (and A.length + B.length = seq.length).

Now choose any such A and B such that max(depth(A), depth(B)) is the minimum possible value.
Return an answer array (of length seq.length) that encodes such a choice of A and B:  
answer[i] = 0 if seq[i] is part of A, else answer[i] = 1.  
Note that even though multiple answers may exist, you may return any of them.

Example 1:
Input: seq = "(()())"
Output: [0,1,1,1,1,0]

Example 2:
Input: seq = "()(())()"
Output: [0,0,0,1,1,0,1,1]

Constraints:
    1. 1 <= seq.size <= 10000
 */

/**
 * Approach 1: Spilt parentheses into odd level and even level
 * 大致题意：给定一个括号字符串（必定合法），要求将其拆成两个合法的 Parentheses SubSequence。
 * 使得括号的层数尽量小（即要求我们拆得尽量平均，使得两个 SubSequence 得层数相等，或者只差1）
 *
 * 因为目的是将括号序列尽量拆成两组层数相等得子括号序列，
 * 因此我们可以先遍历一遍括号的总层数 n，然后利用 half = n/2 作为分界线，
 * 将层数 <= half 的分在序列A，>half 的分在序列B，就能够得到结果了。
 * 但是这个做法缺点在于需要遍历两次数组。
 *
 * 那么有没有只遍历一遍数组就能够得到结果的方法呢？
 * 答案是有的，因为谈到根据数组进行平分，我们很自然能够想到根据数值的 奇偶性 进行拆分。
 * 即层数为 奇数 的分在 A 组，层数为 偶数 的分在 B 组，这样就能够实现一次遍历完成分组。
 * （对于将连续某一范围的整数进行平均拆分，利用 奇偶性 进行拆分是非常常用的技巧）
 * 这里要注意左右括号判断时机的不同（左括号的层数为加后再判断，右括号的层数为减之前就判断）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int count = 0, n = seq.length();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = seq.charAt(i) == '(' ? ++count & 1 : count-- & 1;
        }
        return ans;
    }
}

/**
 * Approach 2: Spilt parentheses by index (Odd and Even)
 * 这是一个在评论区看到的更加 Geek 的一个做法（优化了对 count 的计算）
 * 依据是：同一层的左括号的下标奇偶性必定是相同的，而与左括号匹配的右括号的下标奇偶性与对应的左括号相反。
 * 因此 1，3，5，7...层的括号会被分在同一组，而2，4，6，8...层的括号会被分在另外一组
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length(), ans[] = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = seq.charAt(i) == '(' ? i & 1 : (1 - i & 1);
        }
        return ans;
    }
}