/*
Given a positive integer N, count all possible distinct binary strings of length N
such that there are no consecutive 1’s. Output your answer mod 10^9 + 7.

Input:
The first line of input contains an integer T denoting the number of test cases. The description of T test cases follows.
Each test case contain an integer N representing length of the binary string.

Output:
Print the count number of binary strings without consecutive 1’s of length N.

Constraints:
1 ≤ T ≤ 100
1 ≤ N ≤ 100

Example:
Input:
2
3
2

Output:
5
3

Explanation:
Testcase 1: case 5 strings are (000, 001, 010, 100, 101)
Testcse 2:  case 3 strings are (00,01,10)

OJ: https://practice.geeksforgeeks.org/problems/consecutive-1s-not-allowed/0
 */

/**
 * Approach: Mathematics (Fibonacci)
 * 这是 geeksforgeeks 上面的一道题目，挺好玩的就搬过来了。（OJ链接在题目介绍里）
 * 主要就是使用到 Fibonacci 来求解。不过需要一开始列出前几个，这样才能发现这是个斐波那契数列。
 * n = 1, count = 2  = fib(3)
 * n = 2, count = 3  = fib(4)
 * n = 3, count = 5  = fib(5)
 * n = 4, count = 8  = fib(6)
 * n = 5, count = 13 = fib(7)
 * ................
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * Reference:
 *  https://youtu.be/a9-NtLIs1Kk
 */
import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
    private static final int MOD = 1000000007;

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            System.out.println(getCount(sc.nextInt() + 2));
        }
    }

    // Fibonacci Function
    private static int getCount(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        } else {
            int a = 1, b = 1, c = 0;
            for (int i = 3; i <= n; i++) {
                c = (a + b) % MOD;
                a = b;
                b = c;
            }
            return c;
        }
    }
}