/*
A sorted list A contains 1, plus some number of primes.
Then, for every p < q in the list, we consider the fraction p/q.

What is the K-th smallest fraction considered?
Return your answer as an array of ints, where answer[0] = p and answer[1] = q.

Examples:
Input: A = [1, 2, 3, 5], K = 3
Output: [2, 5]
Explanation:
The fractions to be considered in sorted order are:
1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
The third fraction is 2/5.

Input: A = [1, 7], K = 1
Output: [1, 7]

Note:
A will have length between 2 and 2000.
Each A[i] will be between 1 and 30000.
K will be between 1 and A.length * (A.length - 1) / 2.
 */

/**
 * Approach: Sorted Matrix + Binary Search
 * Brute Force 的做法为枚举所有的分数结果，然后排序，再数第 K 个即可。
 * 时间复杂度为 O(n^2 * 2logn)，而题目给的数据是 2000 肯定是会超时的。
 * （取 Kth Largest Number 虽然可以用 QuickSelect 的方法进行优化，但是仍然需要 O(n^2) 的时间）
 * 有兴趣的朋友可以试一下，如果分析没错的话是会超时的。
 *
 * 这道题目属于 Kth Smallest Element in a Sorted Matrix 的一个 Fellow Up.
 * 突破点在与我们能否想到可以将各个分数结果转换成一个 Sorted Matrix.
 * 举个例子来说：A[] = [1, 2, 3, 5]，因为 A[] 是有序的，所以对于其结果可以按照 A[i] / A[j] 被排列成一个有序的矩阵。
 *      matrix = {1/2, 1/3, 1/5}
 *               {     2/3, 2/5}
 *               {          3/5}
 * matrix大小为 (n-1)^2，只有右上三角部分有值; 顺序为 从左到右，以及从下到上依次递减。即右上角是最小值。
 *
 * 有了上述矩阵之后，我们参考 Kth Smallest Element in a Sorted Matrix 中的做法，
 * 可以利用二分查找的方法来求解结果 target. 因为值的范围在 0~1，所以这也就确定我们所需要进行二分的范围。
 * 当确定一个二分值 mid 之后，我们就可以在 sorted matrix 中寻找有几个数 <= mid.
 * 然后将个数与 K 进行比较，如果相等则说明找到了，直接返回该位置，否则判断大小继续二分下去。
 *
 * 这里有几个注意点说明：
 *  1. 我们是针对 具体数字 来进行二分的，即二分的是 数值range 而不是平时的 index.
 *  对此因为对象是 小数，所以不需要进行 +1 / -1 的操作，即不存在 Boundary 问题。
 *  我们只需要关心 abs(start - end) 的精度是否满足需求即可。
 *  这里 A[i] 最大值为 30000，我们使用 1e-6 的精度完全是满足要求的。
 *  2. 我们不需要去生成 Sorted Matrix 中全部的值，只需要在查找时利用它的顺序即可。
 *  否则时间复杂度会上升到 O(n^2) 级别。即实际操作过程中，我们只计算需要用到的值，
 *  因为 Matrix 的排序关系，我们每次查找时实际上的比较次数为 n-1 次，即矩阵的 列数。 
 *  列的移动方向为 从大到小(从左往右) 进行移动，列指针j(col) 只向右移动，不回退。
 *  因此总体时间复杂度为：O(nlogn)
 *
 * 类似的问题：
 *
 */
class Solution {
    // Keep Track of the kth smallest element's index
    private int row, col;

    public int[] kthSmallestPrimeFraction(int[] A, int K) {
        double start = 0, end = 1;
        while (start < end) {
            double mid = (start + end) / 2;
            int sum = getSum(A, mid);
            if (sum == K) {
                return new int[]{A[row], A[col]};
            } else if (sum < K) {
                // 如果 <= mid 的分数个数小于 K,说明我们需要增大 mid
                start = mid;
            } else {
                // 如果 <= mid 的分数个数大于 K,说明我们需要减小 mid
                end = mid;
            }
        }

        return new int[]{};
    }

    private int getSum(int[] A, double target) {
        int sum = 0, n = A.length;
        double max = 0.0;
        for (int i = 0, j = 1; i < n - 1; i++) {
            // 从左向右(大->小)按列比较，如果 A[i]/A[j] > target,则 列指针j 需要向右移动
            while (j < n && A[i] > A[j] * target) {
                j++;
            }
            // 加上当前行中 <=target 的元素个数
            sum += (n - j);
            // 如果 j 已经到达列的末尾，则直接结束（根据排列顺序，下面的元素都比 target 大）
            if (j == n) {
                break;
            }
            double temp = (double) A[i] / A[j];
            // Update the result
            if (temp > max) {
                max = temp;
                row = i;
                col = j;
            }
        }
        return sum;
    }
}