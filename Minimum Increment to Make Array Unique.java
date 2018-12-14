/*
Given an array of integers A, a move consists of choosing any A[i], and incrementing it by 1.
Return the least number of moves to make every value in A unique.

Example 1:
Input: [1,2,2]
Output: 1
Explanation:  After 1 move, the array could be [1, 2, 3].

Example 2:
Input: [3,2,1,2,1,7]
Output: 6
Explanation:  After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
It can be shown with 5 or less moves that it is impossible for the array to have all unique values.

Note:
    1. 0 <= A.length <= 40000
    2. 0 <= A[i] < 40000
 */

/**
 * Approach: Sort The Array
 * Compared with previous number, the current number need to be at least prev + 1.
 *
 * Time Complexity: O(NlogN)
 * Space Complexity: O(1)
 */
class Solution {
    public int minIncrementForUnique(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        Arrays.sort(A);
        int rst = 0, prev = A[0];
        for (int i = 1; i < A.length; i++) {
            int expect = prev + 1;
            rst += A[i] > expect ? 0 : expect - A[i];
            prev = Math.max(expect, A[i]);
        }
        return rst;
    }
}