/*
Given a n x n matrix where each of the rows and columns are sorted in ascending order,
find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:
matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.

Note:
You may assume k is always valid, 1 ≤ k ≤ n2.
 */

/**
 * Approach 1: PriorityQueue
 * 该题与 Merge K sorted Arrays 思路相同，并且要求时间复杂度为 O(klogn).
 * 故我们想到可以利用到 PriorityQueue 这个数据结构。
 * 矩阵为递增矩阵，要求的元素为 kth smallest number.顺序为 从左到右 从上到下 依次递增，
 * 故我们可以把范围锁定在 k * k 大小的左上角矩阵中。
 * 具体操作为：
 *  1. 利用矩阵第一行的元素创建一个 minHeap (若第一行元素长度大于k,则取前 k 个元素).
 *  2. 每次将堆顶元素 poll 出去，同时我们需要被 poll 出的元素的 行 和 列 的值.故我们堆中所保存的元素应该包含有这些信息.
 *  然后我们将与该元素同一列的下一行元素 offer 进去替代它。进行 k-1 次操作即可。
 *
 * Note：
 * 你也可以利用矩阵的第一列元素来构建 / 初始化 minHeap, 然后重复相似的操作：
 * （推出 peek element 后，利用与被 poll 元素同一行的下一列元素替代它）
 *
 * 该题中我们需要创建一个新的矩阵类，其包含了矩阵中各个元素的 Value, Row, Column这三个值。
 * 具体原因见 Solution1 操作第2点。
 * 并且该类需要能够互相比较大小。
 * 在这里有两个实现方法：
 * 1. 类本身实现了 Comparable 接口. 重写 compareTo 方法（本程序采用了该种写法）
 * 2. 创建一个新的比较器 Comparator 在这里面实现该类的比较方法. 重写 compare 方法.
 * （可利用 内部类 在创建 PriorityQueue 时直接创建,编写代码）
 */
class Solution {
    class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int value;

        Tuple(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public int compareTo(Tuple t) {
            return this.value - t.value;
        }
    }

    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix[0].length == 0) {
            return -1;
        }

        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        int bound = matrix[0].length > k ? k : matrix[0].length;
        for (int j = 0; j < bound; j++) {
            pq.offer(new Tuple(0, j, matrix[0][j]));
        }
        for (int i = 0; i < k - 1; i++) {
            Tuple t = pq.poll();
            if (t.x == matrix.length - 1) {
                continue;
            }
            pq.offer(new Tuple(t.x+1, t.y, matrix[t.x+1][t.y]));
        }

        return pq.peek().value;
    }
}

/**
 * Approach 2: Sorted Matrix + Binary Search
 * 由 Search a 2D Matrix 与 O(klogN) 的时间复杂度，我们想到该题或许也能够使用二分法进行计算。
 * 二分法计算的重点是：“搜索空间”(Search Space)。而搜索空间又可以被分为两种，也对应着二分法的两个解法：
 *  下标(index) 和 范围(range) (最小值与最大值之间的距离)。
 * 大多数情况下，当数组在一个方向上是排序好了的，我们可以使用 下标 作为搜索空间。
 * 而当数组是未被排序的，并且我们希望能够找到一个特定的数字，我们可以使用 范围 作为搜索空间。
 * 
 * 接下来我们来看两个例子：
 * 使用下标(index) -- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/ ( the array is sorted)
 * 使用范围(range) -- https://leetcode.com/problems/find-the-duplicate-number/ (Unsorted Array)
 * 
 * 那么在本题我们不使用 下标 作为搜索空间的原因便是，矩阵是在两个方向上有序的。
 * 我们无法使用下标在其中找到一个线性的关系。因此我们在这里使用了 范围(range) 作为搜索空间。
 * 当我们确定了一个二分值 mid 之后，我们就利用 Matrix 已经排序好的顺序信息，
 * 查找有几个元素 <= mid.每次查找总共需要的比较次数为 矩阵的列数 O(n)
 * 注意查找的时候，我们的移动顺序为：在行方向上，从大到小(右->左) 进行移动，而相应的 列方向 上 从小到大(上->下) 移动.
 * 我们这里是通过这个移动方式来利用个Sorted Matrix 的信息。（大家可以思考下为什么，当然也可以个人喜好使用其他的移动方式）
 * 因为 Sorted Matrix 根据题目的不同，其排序顺序很可能出现不同的情况，所以推荐大家写出一个例子，
 * 看清其排列规律后，再下手，根据需求使用合适的移动方式即可（时间一定是 O(n) 的，不然就失去意义了）。应用可以参考下面给出的链接。
 * 因此总体时间复杂度为：O(nlogn)
 * 
 * 题外话：只要具有排他性，便能够使用二分法 -- 二分法求局部最小值.java in NowCoder
 * https://github.com/cherryljr/NowCoder/blob/master/%E4%BA%8C%E5%88%86%E6%B3%95%E6%B1%82%E5%B1%80%E9%83%A8%E6%9C%80%E5%B0%8F%E5%80%BC.java
 *
 * 利用到本题知识点的相关问题以及 Fwllow Up:
 * Kth Smallest Number in Multiplication Table:
 *  https://github.com/cherryljr/LeetCode/blob/master/Kth%20Smallest%20Number%20in%20Multiplication%20Table.java
 * K-th Smallest Prime Fraction:
 *  https://github.com/cherryljr/LeetCode/edit/master/K-th%20Smallest%20Prime%20Fraction.java
 * Find K-th Smallest Pair Distance:
 *  https://github.com/cherryljr/LeetCode/blob/master/Find%20K-th%20Smallest%20Pair%20Distance.java
 */
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int start = matrix[0][0];
        int end = matrix[matrix.length - 1][matrix[0].length - 1];
        int count = 0;

        // Binary Search
        while (start < end) {
            int mid = start + ((end - start) >> 1);

            count = 0;
            for (int i = 0, j = matrix.length - 1; i < matrix.length; i++) {
                // 从右向左(大->小)按列比较，如果 matrix[i][j] > mid,则 列指针j 需要向左移动
                while (j >= 0 && matrix[i][j] > mid) {
                    j--;
                }
                // 加上当前行中 <=mid 的元素个数
                count += (j + 1);
                if (j < 0) {
                    break;
                }
            }

            if (k <= count) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }
}