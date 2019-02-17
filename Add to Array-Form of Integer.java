/*
For a non-negative integer X, the array-form of X is an array of its digits in left to right order.
For example, if X = 1231, then the array form is [1,2,3,1].

Given the array-form A of a non-negative integer X, return the array-form of the integer X+K.

Example 1:
Input: A = [1,2,0,0], K = 34
Output: [1,2,3,4]
Explanation: 1200 + 34 = 1234

Example 2:
Input: A = [2,7,4], K = 181
Output: [4,5,5]
Explanation: 274 + 181 = 455

Example 3:
Input: A = [2,1,5], K = 806
Output: [1,0,2,1]
Explanation: 215 + 806 = 1021

Example 4:
Input: A = [9,9,9,9,9,9,9,9,9,9], K = 1
Output: [1,0,0,0,0,0,0,0,0,0,0]
Explanation: 9999999999 + 1 = 10000000000

Note：
    1. 1 <= A.length <= 10000
    2. 0 <= A[i] <= 9
    3. 0 <= K <= 10000
    4. If A.length > 1, then A[0] != 0
 */

/**
 * Approach 1: Mathematics Addition (Take K as A Carry)
 * 首先由数据规模可以看出 A 的位数有 10000 的级别，因此可以推测出这是一个 大数相加 的问题。
 * 当然，可以直接调用 Java 中的 BigInteger 来解决，但是并不漂亮，而且这也不是题目要考察的点。
 *
 * 我们可以发现，虽然所示大数相加，但是实际上只有 A 是大数，K的大小只有在 10000 以内。
 * 因此可以以此为突破口来解决这道问题。
 * 既然 K 的数值较小，那么就说明对K而言，如果只是一个位数上的加法操作，是不会出现溢出情况的。
 * 因此我们可以像小学做加法题时的 列式计算 一样，将 K 作为一个 加数，然后对应位上的数相加，
 * 进位的值会被自动累计到K的高位上，然后不断地把 K 向左推过去相加即可。
 * 直到遇到 A 的最高位 并且 K == 0 时，说明计算完成。
 * (K > 0 说明进位还没有完成，需要继续处理）
 *
 * eg. A=[1,2,3]  K=912
 * 这 123 + 912 的计算过程如下所示：
 *  首先将 A的最低位 与 K 相加 ==>  [1, 2, 3+912] 此时我们得到 [1, 2, 915]
 *  这里的 915 中的最低位就是此时 A+K 对应位置上的数的值，因此我们把 915 % 10 == 5 留下来（添加到结果中）
 *  此时剩下 910，然后向左（高位）移动一位，相应的此时加数K就变成了 910 / 10 == 91
 *  然后重复上述过程：
 *  [1, 2 + 91, 5] ==> [1, 93, 5] ==> [1, 3, 5] 然后此时 K = 9
 *  [1 + 9, 3, 5]  ==> [10, 3, 5] ==> [0, 3, 5] 然后此时 K = 1
 *  [1, 0, 3, 5] 就是我们最终要的结果
 *
 * 时间复杂度：O(max(N, logK))
 * 空间复杂度：O(max(N, logK))
 */
class Solution {
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> ans = new LinkedList<>();
        for (int i = A.length - 1; i >= 0; --i) {
            K += A[i];
            ans.add(0, K % 10);
            K /= 10;
        }
        while (K > 0) {
            ans.add(0, K % 10);
            K /= 10;
        }
        return ans;
    }
}

/**
 * Approach 2: More Concise (Only One For Loop)
 * 这里提供了一种更加简洁的写法，解题方法还是一样的。
 * 只不过代码更加优雅一些哈。
 */
class Solution {
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> ans = new LinkedList<>();
        for (int i = A.length - 1; i >= 0 || K > 0; --i) {
            K += (i >= 0 ? A[i] : 0);
            ans.add(0, K % 10);
            K /= 10;
        }
        return ans;
    }
}