/*
Given an array of integers A, find the number of triples of indices (i, j, k) such that:
    0 <= i < A.length
    0 <= j < A.length
    0 <= k < A.length
A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.

Example 1:
Input: [2,1,3]
Output: 12
Explanation: We could choose the following i, j, k triples:
(i=0, j=0, k=1) : 2 & 2 & 1
(i=0, j=1, k=0) : 2 & 1 & 2
(i=0, j=1, k=1) : 2 & 1 & 1
(i=0, j=1, k=2) : 2 & 1 & 3
(i=0, j=2, k=1) : 2 & 3 & 1
(i=1, j=0, k=0) : 1 & 2 & 2
(i=1, j=0, k=1) : 1 & 2 & 1
(i=1, j=0, k=2) : 1 & 2 & 3
(i=1, j=1, k=0) : 1 & 1 & 2
(i=1, j=2, k=0) : 1 & 3 & 2
(i=2, j=0, k=1) : 3 & 2 & 1
(i=2, j=1, k=0) : 3 & 1 & 2

Note:
    1. 1 <= A.length <= 1000
    2. 0 <= A[i] < 2^16
 */

/**
 * Approach 1: HashMap
 * 本题所给出的数据规模对于解题是相当重要的。
 * 首先，因为数组大小在 1000 级别，所以算法的时间复杂度应该在 O(n^2).
 * 因此如果采用暴力求解所有组合（O(n^3)）的方法是会超时的。
 * 其次，我们发现 A[i] 的大小不会超过 2^16 = 65536，
 * 这里给了我们一个非常重要的提示：应该利用 A[i] 的大小对状态进行压缩。
 * 因此我们可以很容易想出如下解法：
 *  1. 遍历所有的 两两相与 的数，然后记录在 Map 中
 *  key:两数相与的值; value:该数出现的次数
 *  2. 遍历 A[] 中所有的数 与 Map 中所有的 key 相与，
 *  如果结果为 0，则 res += map.get(a&b)
 * 
 * 时间复杂度：O(n^2 + n*max(A[i]))
 * 空间复杂度：O(n)
 */
class Solution {
    public int countTriplets(int[] A) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : A) {
            for (int b : A) {
                map.put(a & b, map.getOrDefault(a & b, 0) + 1);
            }
        }

        for (int a : A) {
            for (Map.Entry<Integer, Integer> set : map.entrySet()) {
                if ((a & set.getKey()) == 0) {
                    res += set.getValue();
                }
            }
        }
        return res;
    }
}

/**
 * Approach 2: Using Array instead of HashMap
 * 因为题目明确给出了数据范围，所以这里可以使用 数组 来替代 HashMap，从而实现对代码的加速。
 * 同时，这里在第二次遍历的时候，当 (a & num) != 0 时，利用了 num += (a & num) - 1 实现加速。
 * 原理在于，因为 (a & num) != 0，所以 [num, num+(a&num)-1] 之间的数都不可能和 a 与出 0 这个结果。
 * 因此可以直接跳过这段区间。
 *
 * 时间复杂度：O(n^2 + n*max(A[i]))
 * 空间复杂度：O(1 << 16) = O(1)
 */
class Solution {
    public int countTriplets(int[] A) {
        // max 表示所能与出来的最大值
        int res = 0, max = -1;
        // 根据数据范围初始化 map[]
        int[] map = new int[1 << 16];
        for (int a : A) {
            for (int b : A) {
                map[a & b]++;
                max = Math.max(max, a & b);
            }
        }

        for (int a : A) {
            for (int num = 0; num <= max; num++) {
                if ((a & num) == 0) {
                    res += map[num];
                } else {
                    num += (a & num) - 1;
                }
            }
        }
        return res;
    }
}