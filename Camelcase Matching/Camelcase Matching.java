/*
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
 */

/**
 * Approach 1: Traversal (String Matche)
 * 遍历 queries 中所有的字符串，与 pattern 按照规则对各个字符进行匹配即可。
 * 匹配规则如下（i, j分别代表 query 和 pattern 字符串的坐标）：
 *  1. 如果 query[i] == pattern[j] 则这两个 pointer 都向后移动一步；
 *  2. 如果 query[i] != pattern[j] 且 query[i] 为小写字母，i 向后移动一步；
 *  3. 如果 query[i] != pattern[j] 且 query[i] 为大写字母，说明无法匹配，直接返回 false
 * 最后检查 pointer j 是否到达了 pattern 末尾，如果到了说明 pattern 全部完成了匹配返回 true,否则返回 false.
 *
 * 时间复杂度：O(M * N)
 * 空间复杂度：O(1)
 */
class Solution {
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> ans = new ArrayList<>();
        for (String query : queries) {
            ans.add(isMatch(query, pattern));
        }
        return ans;
    }

    private boolean isMatch(String query, String pattern) {
        int j = 0;
        for (int i = 0; i < query.length(); i++) {
            if (j < pattern.length() && query.charAt(i) == pattern.charAt(j)) {
                j++;
            } else if (query.charAt(i) < 'a') {
                return false;
            }
        }
        return j == pattern.length();
    }
}

/**
 * Approach 2: Regular Expression
 * 涉及到匹配问题，因此可以使用到 正则表达式 来表示对应的 pattern
 * 然后利用创建好的 pattern 与 query 进行 match 即可。
 * 在此过程中可以利用到 stream() 操作把代码简化到一行。
 */
class Solution {
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        return Arrays.stream(queries).map(q -> q.matches("[a-z]*" + String.join("[a-z]*", pattern.split("")) + "[a-z]*")).collect(Collectors.toList());
    }
}