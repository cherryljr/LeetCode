/*
We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
(Here, the distance between two points on a plane is the Euclidean distance.)
You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)

Example 1:
Input: points = [[1,3],[-2,2]], K = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].

Example 2:
Input: points = [[3,3],[5,-1],[-2,4]], K = 2
Output: [[3,3],[-2,4]]
(The answer [[-2,4],[3,3]] would also be accepted.)

Note:
1 <= K <= points.length <= 10000
-10000 < points[i][0] < 10000
-10000 < points[i][1] < 10000
 */

/**
 * Approach 1: PriorityQueue
 * 根据数据规模，可以推测出时间复杂度在 O(nlogn) 级别。
 * 因此直接利用 优先级队列 即可实现。
 * 或者直接排序，然后取前 K 个即可。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(K)
 */
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        // 事先已经知道了只需要前 K 个数，因此优先级队列的大小初始化为 K 即可。
        PriorityQueue<int[]> pq = new PriorityQueue<>(K, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] * a[0] + a[1] * a[1] - (b[0] * b[0] + b[1] * b[1]);
            }
        });
        for (int[] point : points) {
            pq.offer(point);
        }

        int[][] rst = new int[K][2];
        for (int i = 0; i < K; i++) {
            int[] point = pq.poll();
            rst[i][0] = point[0];
            rst[i][1] = point[1];
        }
        return rst;
    }
}

/**
 * Approach 2: QuickSelect
 * 这个问题实际上是一个 TopK 问题，并且表明答案可以按任意顺序返回，
 * 因此可以利用 QuickSelect 来实现（非稳定排序）。
 * 通过 partition 这个做法，可见将时间复杂度降低到 O(n) 级别。
 * 因为在比较的时候，会多次计算 distance 这个值，故实现建立了 distances[][] 来保存计算好的值。
 * 以减少运算量（空间换时间）
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 对 QuickSelect 不了解的可以参见：
 *  https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 *  https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 */
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        // distance[i][0] 表示计算好的 距离；
        // distance[i][1]表示对应的点的位置，以便排序后能够找到对应的点
        int[][] distances = new int[points.length][2];
        for (int i = 0; i < points.length; i++) {
            distances[i][0] = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            distances[i][1] = i;
        }

        helper(distances, 0, distances.length - 1, K - 1);
        int[][] rst = new int[K][2];
        for (int i = 0; i < K; i++) {
            rst[i][0] = points[distances[i][1]][0];
            rst[i][1] = points[distances[i][1]][1];
        }
        return rst;
    }

    // QuickSelect
    // 对这部分有疑问的，可以参考上文给出的那两个链接
    private void helper(int[][] distance, int left, int right, int K) {
        if (left >= right) {
            return;
        }

        int pos = partition(distance, left, right);
        if (pos == K) {
            return;
        } else if (pos < K) {
            helper(distance, pos + 1, right, K);
        } else {
            helper(distance, left, pos - 1, K);
        }
    }

    private int partition(int[][] distance, int left, int right) {
        int less = left - 1, more = right;
        while (left < more) {
            if (distance[left][0] < distance[right][0]) {
                swap(distance, ++less, left++);
            } else if (distance[left][0] > distance[right][0]) {
                swap(distance, left, --more);
            } else {
                left++;
            }
        }
        swap(distance, more, right);
        return less + 1;
    }

    private void swap(int[][] distance, int a, int b) {
        int t1 = distance[a][0], t2 = distance[a][1];
        distance[a][0] = distance[b][0];
        distance[a][1] = distance[b][1];
        distance[b][0] = t1;
        distance[b][1] = t2;
    }
}