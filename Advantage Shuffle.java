/*
Given two arrays A and B of equal size, the advantage of A with respect to B is the number of indices i for which A[i] > B[i].
Return any permutation of A that maximizes its advantage with respect to B.

Example 1:
Input: A = [2,7,11,15], B = [1,10,4,11]
Output: [2,11,7,15]

Example 2:
Input: A = [12,24,8,32], B = [13,25,32,11]
Output: [24,32,8,12]

Note:
1 <= A.length = B.length <= 10000
0 <= A[i] <= 10^9
0 <= B[i] <= 10^9
 */

/**
 * Approach: Greedy (田忌赛马)
 * 根据数据规模可得，时间复杂度应该在 O(nlogn) 级别。
 * 题目要求一个排列使得 A[i] > B[i] 的对数尽量多，对应过来其实就是一个 田忌赛马 的问题。
 * 使得 A 尽量多地赢 B.
 * 因此我们可以将 A 进行一个排序（从小到大）
 * 然后对于 B 则放入一个 最大堆 中，我们必须首先检测能否找到一个数使得 A[i] > currentMaxB.
 * 因为它是最难满足的条件，如果能够找到，就使用这个数。（上等马 VS 上等马，只不过A的更强）
 * 否则就说明这场比赛必败，因此我们可以使用最小的元素跟当前B最大的元素进行PK。（下等马 VS 上等马）
 * 基于以上做法，我们就能够保证 A 赢的场数最多了。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  https://leetcode.com/problems/advantage-shuffle/discuss/149822/JAVA-Greedy-6-lines-with-Explanation
 */
class Solution {
    public int[] advantageCount(int[] A, int[] B) {
        Arrays.sort(A);
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return b[0] - a[0];
            }
        });
        for (int i = 0; i < B.length; i++) {
            maxHeap.offer(new int[]{B[i], i});
        }

        int[] rst = new int[A.length];
        int low = 0, high = A.length - 1;
        while (low <= high) {   // !maxHeap.isEmpty() 这两个判断条件均可
            int [] curr = maxHeap.poll();
            if (A[high] > curr[0]) {
                // 上等马 VS 上等马
                rst[curr[1]] = A[high--];
            } else {
                // 下等马 VS 上等马
                rst[curr[1]] = A[low++];
            }
        }
        return rst;
    }
}