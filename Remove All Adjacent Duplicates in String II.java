/*
Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s 
and removing them causing the left and the right side of the deleted substring to concatenate together.

We repeatedly make k duplicate removals on s until we no longer can.
Return the final string after all such duplicate removals have been made.

It is guaranteed that the answer is unique.

Example 1:
Input: s = "abcd", k = 2
Output: "abcd"
Explanation: There's nothing to delete.

Example 2:
Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation: 
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"

Example 3:
Input: s = "pbbcggttciiippooaais", k = 2
Output: "ps"

Constraints:
    1. 1 <= s.length <= 10^5
    2. 2 <= k <= 10^4
    3. s only contains lower case English letters.
*/

/**
 * Approach: Stack
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Reference:
 *  https://github.com/cherryljr/LeetCode/tree/master/Remove%20All%20Adjacent%20Duplicates%20In%20String
 */
class Solution {
    public String removeDuplicates(String s, int k) {
        Deque<Node> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek().ch == c) {
                if (++stack.peek().count == k) {
                    stack.pop();
                }
            } else {
                stack.push(new Node(c, 1));
            }
        }
        
        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            Node curr = stack.pollLast();
            for (int i = 0; i < curr.count; i++) {
                ans.append(curr.ch);
            }
        }
        return ans.toString();
    }
    
    class Node {
        char ch;
        int count;
        
        Node(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }
}