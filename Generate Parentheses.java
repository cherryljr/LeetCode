/*
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given n = 3, a solution set is:

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
*/

/**
 * Approach 1: Brute Force 
 * Algorithm
 * To generate all sequences, we use DFS.
 * All sequences of length n is just '(' plus all sequences of length n-1, and then ')' plus all sequences of length n-1.
 * To check whether a sequence is valid, we use a stack method.
 * Of course, you can use other methods, like here:
 * https://github.com/cherryljr/LeetCode/blob/master/Longest%20Valid%20Parentheses.java
 *
 * Complexity Analysis
 * Time Complexity : O(2^(2n)).
 * Space Complexity : O(2n). we need a array to record the visited characters' indices.
 */
class Solution {
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("()");
        }
        String str = sb.toString();

        Set<String> set = new HashSet<>();
        boolean[] visited = new boolean[2 * n];
        dfs(set, new StringBuilder(), str, visited);

        return new ArrayList<>(set);
    }

    private void dfs(Set<String> set, StringBuilder sb, String str, boolean[] visited) {
        if (sb.length() == str.length() && closure(sb.toString()) == 0) {
            set.add(sb.toString());
        }

        for (int i = 0; i < str.length(); i++) {
            if (closure(sb.toString()) < 0) {
                break;
            }
            if (visited[i]) {
                continue;
            }
            sb.append(str.charAt(i));
            visited[i] = true;
            dfs(set, sb, str, visited);
            // backtracking
            sb.deleteCharAt(sb.length() - 1);
            visited[i] = false;
        }
    }

    private int closure(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else {
                return -1;
            }
        }
        return stack.size();
    }
}

/**
 * Approach 2: Backtracking
 * Algorithm
 * Instead of adding '(' or ')' every time as in Approach 1,
 * let's only add them when we know it will remain a valid sequence.
 * We can do this by keeping track of the number of opening and closing brackets we have placed so far.
 * We can start an opening bracket if we still have one (of n) left to place.
 * And we can start a closing bracket if it would not exceed the number of opening brackets.
 *
 * Complexity Analysis
 * Our complexity analysis rests on understanding how many elements there are in generateParenthesis(n).
 * And it turns out this is the n-th Catalan number: C(2n, n)/(n+1), which is bounded asymptotically by 4^n / (n*n^0.5)
 * Time Complexity: O(4^n / n^0.5). Each valid sequence has at most n steps during the backtracking procedure.
 * Space Complexity : O(4^n / n^0.5). using O(n) space to store the sequence.
 */
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        if (open < max)
            backtrack(ans, cur+"(", open+1, close, max);
        if (close < open)
            backtrack(ans, cur+")", open, close+1, max);
    }
}