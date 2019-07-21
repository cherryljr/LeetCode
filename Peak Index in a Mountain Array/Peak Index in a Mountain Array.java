/*
Let's call an array A a mountain if the following properties hold:
    ·A.length >= 3
    ·There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
Given an array that is definitely a mountain, return any i such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

Example 1:
Input: [0,1,0]
Output: 1

Example 2:
Input: [0,2,1,0]
Output: 1

Note:
    1. 3 <= A.length <= 10000
    2. 0 <= A[i] <= 10^6
    3. A is a mountain, as defined above.
 */

/**
 * Approach 1: Binary Search
 * 利用二分法在 单峰|谷数组 查找 山峰|山谷 的一个典型应用。
 *
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 */
class Solution {
    public int peakIndexInMountainArray(int[] A) {
        int left = 0, right = A.length;
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (A[mid] < A[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}

/**
 * Approach 2: Golden-section Search
 * 黄金分割算法，可以用于查询 单峰|谷 序列中的 山峰|山谷。
 * 首先该算法取 3个点 将序列分成了两个序列部分（自左向右为 x1,x2,x3）。然后再取第四个点 x4。
 * 比较 A[x3] 与 A[x4] 的大小，根据单峰的原理来缩小区间。（具体图解分析可以参见下面给出的链接）
 *  c/a == a/b == c/(b-c)  ==>  联立可得：a/b == 0.618
 * 因此该算法又被称为 黄金分割查找法。
 * 与之相关的还有：斐波那契搜索
 *
 * Reference:
 *  https://en.wikipedia.org/wiki/Golden-section_search
 *  https://baike.baidu.com/item/%E9%BB%84%E9%87%91%E5%88%86%E5%89%B2%E6%90%9C%E7%B4%A2
 */
class Solution {
    public int peakIndexInMountainArray(int[] A) {
        int left = 0, right = A.length - 1;
        int x1 = goldenIndex(left, right, 0.382), x2 = goldenIndex(left, right, 0.618);
        while (x1 < x2) {
            if (A[x1] < A[x2]) {
                left = x1;
                x1 = x2;
                x2 = goldenIndex(x1, right, 0.382);
            } else {
                right = x2;
                x2 = x1;
                x1 = goldenIndex(left, x2, 0.618);
            }
        }
        return x1;
    }

    private int goldenIndex(int left, int right, double param) {
        return left + (int)(Math.round((right - left) * param));
    }

}