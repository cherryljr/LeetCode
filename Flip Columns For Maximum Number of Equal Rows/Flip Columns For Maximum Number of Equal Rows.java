/*
Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix and flip every cell in that column.
Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.

Return the maximum number of rows that have all values equal after some number of flips.

Example 1:
Input: [[0,1],[1,1]]
Output: 1
Explanation: After flipping no values, 1 row has all values equal.

Example 2:
Input: [[0,1],[1,0]]
Output: 2
Explanation: After flipping values in the first column, both rows have equal values.

Example 3:
Input: [[0,0,0],[0,0,1],[1,1,0]]
Output: 2
Explanation: After flipping values in the first two columns, the last two rows have equal values.

Note:
    1. 1 <= matrix.length <= 300
    2. 1 <= matrix[i].length <= 300
    3. All matrix[i].length's are equal
    4. matrix[i][j] is 0 or 1
 */

/**
 * Approach 1: Find the Most Frequent Pattern and Opposite Pattern
 * 这道题目的核心在于：我们需要找出一行序列，使得 与它相等 或者 与它完全相反 的行数出现次数最多。
 * 则 出现的次数 就是我们需要的答案。
 * 这是因为：
 * 如果两行序列上的元素值完全相同的话，那么经过对某些 column 上元素的 flip 操作，
 * 必定可以使得这两行的元素都变成所有值都相等的情况。
 * 如果两行序列上的元素值完全相反的话，那么经过对某些 column 上元素的 flip 操作，
 * 必定可以使得这两行元素都变成 同一行完全相等 但是 这两行完全相反 的一个结果。
 * 举个例子：
 *      [1,0,0,1,0]                                      [0,0,0,0,0]  // all-0s
 *      [1,0,0,1,0]  after flipping 0-th and 4-th rows   [0,0,0,0,0]  // all-0s
 *      [1,0,1,1,1] -----------------------------------> [0,0,1,0,1]
 *      [0,1,1,0,1]                                      [1,1,1,1,1]  // all-1s
 *      [1,0,0,1,1]                                      [0,0,0,0,1]
 * 可以看出，在 flip 操作之前，1st, 2nd 行的元素值完全相同，所以都得到了 all-0s 的结果；
 * 而 4th 的元素与 1st 的完全相反，所以在 flip 之后得到了相反的 all-1s 的结果。
 * 但是这两者均是符合题目 同一行元素完全相等 的要求的。
 *
 * 时间复杂度：O(N*M + N*N*M) N为矩阵的行，M为矩阵的列
 * 空间复杂度：O(M)
 */
class Solution {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        int max = 0;
        int rows = matrix.length, cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            // calculate the opposite pattern of current row
            int[] opposite = new int[cols];
            for (int j = 0; j < cols; j++) {
                opposite[j] = matrix[i][j] ^ 1;
            }

            int count = 0;
            // count how many row is equals to current row or opposite pattern
            for (int k = 0; k < rows; k++) {
                count += Arrays.equals(matrix[k], matrix[i]) || Arrays.equals(matrix[k], opposite) ? 1 : 0;
            }
            max = Math.max(max, count);
        }
        return max;
    }
}

/**
 * Approach 2: Using HashMap
 * 在 Approach 1 中，我们需要将矩阵中所有的行与当前行和对应的 opposite pattern 进行一次对比。
 * 然后将符合要求（相等）的个数计算出来。该计算方式虽然只需要 O(M) 的额外空间，但是花费了 O(N*N*M) 的时间。
 * 对此，我们不妨利用 HashMap 将所有计算出来的 当前行序列 和 对应的opposite pattern都存到 Map 里，
 * 遍历矩阵后，统计 Map 中出现次数最多的 pattern 就是我们需要的答案。
 * 即实现了 空间换时间 的一个做法。（此处可以对 String 进行编码，进一步加快时间）
 *
 * 时间复杂度：O(N*M)
 * 空间复杂度：O(N*M)
 */
class Solution {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        Map<String, Integer> map = new HashMap<>();
        int rows = matrix.length, cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            StringBuilder origin = new StringBuilder();
            StringBuilder opposite = new StringBuilder();
            for (int j = 0; j < cols; j++) {
                origin.append(matrix[i][j]);
                opposite.append(matrix[i][j] ^ 1);
            }
            String str1 = origin.toString(), str2 = opposite.toString();
            // 记录当前行和对应 opposite 出现的次数
            map.put(str1, map.getOrDefault(str1, 0) + 1);
            map.put(str2, map.getOrDefault(str2, 0) + 1);
        }

        int max = 0;
        for (int count : map.values()) {
            max = Math.max(max, count);
        }
        return max;
    }
}