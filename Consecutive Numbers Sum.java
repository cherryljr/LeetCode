/*
Given a positive integer N, how many ways can we write it as a sum of consecutive positive integers?

Example 1:
Input: 5
Output: 2
Explanation: 5 = 5 = 2 + 3

Example 2:
Input: 9
Output: 3
Explanation: 9 = 9 = 4 + 5 = 2 + 3 + 4

Example 3:
Input: 15
Output: 4
Explanation: 15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5

Note: 1 <= N <= 10 ^ 9.
*/

/**
 * Approach: Mathematics
 * To be honest, this is a really good question, I love it!
 * If you are clear with Gauss formula, you can solve it quickly.
 *
 * let sum(m,n) = N; According to Gauss formula:
 *  (m + n) * (n - m + 1) / 2 = N;
 *  (m + n) * (n - m + 1) = 2 * N
 *
 * let's say:
 *  a * b = 2 * N 
 * Then we have:
 *  1. m + n = a
 *  2. n - m + 1 = b
 * plus 1 and 2
 *  2n + 1 = a + b
 *
 * so if a * b = 2* N and a + b is an odd number, there is a solution
 *
 * Time Complexity: O(sqrt(n))
 */
class Solution {
    public int consecutiveNumbersSum(int N) {
        int rst = 0;
        // Make N = 2 * N
        N <<= 1;   
        // Traverse the value of a
        for (int i = 1; i * i < N; i++) {
            // a * b = 2N and a + b is odd
            if (N % i == 0 && ((i + N / i) & 1) == 1) {
                rst++;
            }
        }
        return rst;
    }
}