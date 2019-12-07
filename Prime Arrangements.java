/*
Return the number of permutations of 1 to n so that prime numbers are at prime indices (1-indexed.)
(Recall that an integer is prime if and only if it is greater than 1, 
and cannot be written as a product of two positive integers both smaller than it.)

Since the answer may be large, return the answer modulo 10^9 + 7.

Example 1:
Input: n = 5
Output: 12
Explanation: For example [1,2,5,4,3] is a valid permutation, 
but [5,2,3,4,1] is not because the prime number 5 is at index 1.

Example 2:
Input: n = 100
Output: 682289015

Constraints:
    1. 1 <= n <= 100
*/

/**
 * Approach: Sieve method
 */
class Solution {
    private static final int MOD = 1000000007;
    
    public int numPrimeArrangements(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, 2, n + 1, true);
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        int primeCount = 0;
        for (int i = 0; i < isPrime.length; i++) {
            primeCount += isPrime[i] ? 1 : 0;
        }
        return (int)(permutation(primeCount) * permutation(n - primeCount) % MOD);
    }
    
    private long permutation(int n) {
        long count = 1L;
        while (n > 0) {
            count = count * n-- % MOD;
        }
        return count;
    }
}