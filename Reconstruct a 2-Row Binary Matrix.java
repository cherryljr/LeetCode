/*
Given the following details of a matrix with n columns and 2 rows :
    The matrix is a binary matrix, which means each element in the matrix can be 0 or 1.
    The sum of elements of the 0-th(upper) row is given as upper.
    The sum of elements of the 1-st(lower) row is given as lower.
    The sum of elements in the i-th column(0-indexed) is colsum[i], 
    where colsum is given as an integer array with length n.
Your task is to reconstruct the matrix with upper, lower and colsum.

Return it as a 2-D integer array.
If there are more than one valid solution, any of them will be accepted.
If no valid solution exists, return an empty 2-D array.

Example 1:
Input: upper = 2, lower = 1, colsum = [1,1,1]
Output: [[1,1,0],[0,0,1]]
Explanation: [[1,0,1],[0,1,0]], and [[0,1,1],[1,0,0]] are also correct answers.

Example 2:
Input: upper = 2, lower = 3, colsum = [2,2,1,1]
Output: []

Example 3:
Input: upper = 5, lower = 5, colsum = [2,1,2,0,1,0,1,2,0,1]
Output: [[1,1,1,0,1,0,0,1,0,0],[1,0,1,0,0,0,1,1,0,1]]

Constraints:
    1. 1 <= colsum.length <= 10^5
    2. 0 <= upper, lower <= colsum.length
    3. 0 <= colsum[i] <= 2
*/

/**
 * Approach: Apparently...Easy...
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class Solution {
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        int n = colsum.length;
        int[] first = new int[n], second = new int[n];
        for (int i = 0; i < n; i++) {
            if (colsum[i] == 2) {
                first[i] = 1;
                second[i] = 1;
                upper--;
                lower--;
            } else if (colsum[i] == 1) {
                if (upper >= lower) {
                    first[i] = 1;
                    second[i] = 0;
                    upper--;
                } else {
                    second[i] = 1;
                    first[i] = 0;
                    lower--;
                }
            } else {
                first[i] = 0;
                second[i] = 0;
            }
        }
        return upper == 0 && lower == 0 ? new ArrayList(Arrays.asList(first, second)) : new ArrayList<>();
    }
}