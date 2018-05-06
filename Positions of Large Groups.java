/*
In a string S of lowercase letters, these letters form consecutive groups of the same character.
For example, a string like S = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z" and "yy".
Call a group large if it has 3 or more characters.
We would like the starting and ending positions of every large group.
The final answer should be in lexicographic order.

Example 1:
Input: "abbxxxxzzy"
Output: [[3,6]]
Explanation: "xxxx" is the single large group with starting  3 and ending positions 6.

Example 2:
Input: "abc"
Output: []
Explanation: We have "a","b" and "c" but no large group.

Example 3:
Input: "abcdddeeeeaabbbcd"
Output: [[3,5],[6,9],[12,14]]

Note:  1 <= S.length <= 1000
 */

/**
 * Approach 1: Traverse
 * 热身题...直接遍历，如果 str[i] == str[i+1]，记录开始位置。
 * 然后指针一直往后跑，直到不想等位置，计算 start~i 之间的距离是否 >= 3.
 * 若成立就是一个 large group，加入到 rst 中即可。
 * 指针 i 从左向右遍历不回退，因此时间复杂度为 O(n)
 */
class Solution {
    public List<List<Integer>> largeGroupPositions(String S) {
        if (S == null || S.length() == 0) {
            return new LinkedList<>();
        }

        List<List<Integer>> rst = new LinkedList<>();
        char[] str = S.toCharArray();
        for (int i = 0; i < str.length - 1; i++) {
            if (i + 1 < str.length && str[i] == str[i + 1]) {
                int start = i;
                while (i + 1 < str.length && str[i] == str[i + 1]) {
                    i++;
                }
                if (i - start >= 2) {
                    rst.add(Arrays.asList(new Integer[]{start, i}));
                }
            }
        }

        return rst;
    }
}

/**
 * Approach 2: Two Pointers
 * Algorithm
 *  Maintain pointers i, j with i <= j.
 *  The i pointer will represent the start of the current group,
 *  and we will increment j forward until it reaches the end of the group.
 *  We know that we have reached the end of the group when j is at the end of the string, or S[j] != S[j+1].
 *  At this point, we have some group [i, j]; and after, we will update i = j+1, the start of the next group.
 *
 * Complexity Analysis
 *  Time Complexity : O(N), where NN is the length of S.
 *  Space Complexity: O(N), the space used by the answer.
 */
class Solution {
    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> rst = new LinkedList<>();
        // i is the start of each group
        int i = 0, N = S.length();
        for (int j = 0; j < N; ++j) {
            if (j == N-1 || S.charAt(j) != S.charAt(j + 1)) {
                // Here, [i, j] represents a group.
                if (j - i + 1 >= 3) {
                    rst.add(Arrays.asList(new Integer[]{i, j}));
                }
                i = j + 1;
            }
        }

        return rst;
    }
}