/*
Given an array A of positive integers (not necessarily distinct), 
return the lexicographically largest permutation that is smaller than A, 
that can be made with one swap (A swap exchanges the positions of two numbers A[i] and A[j]).  
If it cannot be done, then return the same array.

Example 1:
Input: [3,2,1]
Output: [3,1,2]
Explanation: Swapping 2 and 1.

Example 2:
Input: [1,1,5]
Output: [1,1,5]
Explanation: This is already the smallest permutation.

Example 3:
Input: [1,9,4,6,7]
Output: [1,7,4,6,9]
Explanation: Swapping 9 and 7.

Example 4:
Input: [3,1,1,3]
Output: [1,3,1,3]
Explanation: Swapping 1 and 3.

Note:
    1. 1 <= A.length <= 10000
    2. 1 <= A[i] <= 10000
 */

/**
 * Approach: Traverse and Swap (Similar to Next Permutation)
 * 这道问题与 Next Permutation 属于同一类问题，只不过这里换成了问 Previous Permutation。
 * 并且加上了 只能交换一次 的限制条件。（反而还简单了一些...）
 *
 * 思路与 Next Permutation 是相同的，只不过需要反过来：
 *   1. 从后到前，找到第一个非递减序列的位置。如 A[i] > A[i+1] <= A[i+2] <= ... <= A[n] 则 i 就是我们要找的位置；
 *   2. 如果找不到，说明整个序列就是一个递增序列，本身序列就是最小值，直接返回即可，无需进行后续操作；
 *   3. 利用值 smaller 来记录序列 从后往前 最后一个 小于 A[i] 的值的位置；
 *   4. 交互 A[i] 和 A[smaller] 的位置，即可的到最终的答案。
 * PS.这里要注意，在 Next Permutation 中，我们应该找的是 第一个大于A[i] 的元素，
 * 而在本题中，我们应该找的是 最后一个小于A[i] 的元素。
 * 原因就是因为 Next Permutation 求的是：大于当前数的 最小值；
 * Previous Permutation 求的是：小于当前数的 最大值。
 * 这样大家稍加考虑即可明白原因。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LeetCode/blob/master/Next%20Permutation.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Next%20Greater%20Element%20III/Next%20Greater%20Element%20III.java
 */
class Solution {
    public int[] prevPermOpt1(int[] A) {
        if (A == null || A.length <= 1) {
            return A;
        }

        int n = A.length, index = n - 2;
        while (index >= 0 && A[index] <= A[index + 1]) {
            index--;
        }
        if (index >= 0) {
            int smaller = n - 1;
            while (smaller >= 0 && A[smaller] >= A[index]) {
                smaller--;
                // find the last one which is smaller than A[i] (Forward)
                while (smaller >= 1 && A[smaller] == A[smaller - 1]) smaller--;
            }
            swap(A, index, smaller);
        }
        return A;
    }

    private void swap(int[] A, int a, int b) {
        int temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }
}