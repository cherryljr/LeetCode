/*
Return all non-negative integers of length N such that the absolute difference between every two consecutive digits is K.

Note that every number in the answer must not have leading zeros except for the number 0 itself.
For example, 01 has one leading zero and is invalid, but 0 is valid.

You may return the answer in any order.

Example 1:
Input: N = 3, K = 7
Output: [181,292,707,818,929]
Explanation: Note that 070 is not a valid number, because it has leading zeroes.

Example 2:
Input: N = 2, K = 1
Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]

Note:
1 <= N <= 9
0 <= K <= 9
 */

/**
 * Approach 1: BFS
 * 根据题目的数据规模，很明显这是一道暴力求解的问题。
 * 因此我们可以利用 BFS / DFS 来解决这道问题。
 * 根据题意，每个数的下一个位置上的数与当前位置上的数的绝对值之差为 K.
 * 这就意味这我们的 expand 有两种走法。
 *  1. curr * 10 + remainder + K
 *  2. curr * 10 + remainder - K
 * 当然上述的值必须合法（在 0~9 之间），并且不能重复，即需要考虑 K==0 的特殊情况。
 * 然后使用 BFS 或者 DFS 模板即可解决问题。
 *
 * 时间复杂度：O(9 * 2^n)
 * 空间复杂度：O(9 * 2^n)
 *
 * PS.这里使用到了 stream 来方面我们代码的书写（Stream为 Java 8 中对集合（Collection）对象功能的增强）
 * 有兴趣的朋友可以参考：
 *  https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/index.html
 */
class Solution {
    public int[] numsSameConsecDiff(int N, int K) {
        // 使用了 ArrayDeque，效率比 LinkedList 更高
        Deque<Integer> deque = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        // 考虑到 0 这个特殊情况
        if (N == 1) {
            deque.addFirst(0);
        }

        // BFS. 因为只有答案只有 N 位数，所以 BFS 进行 N-1 次后即可以退出了。
        // (第一层已经在初始化的时候，人为添加进去了)
        while (--N > 0) {
            // Level by level
            // 依旧是先获得并保存当前队列的 size()，然后一层层遍历下去
            for (int size = deque.size(); size > 0; size--) {
                int curr = deque.poll();
                int remainder = curr % 10;
                // 判断两个 expand 的值是否合法
                if (remainder + K <= 9) {
                    deque.offer(curr * 10 + remainder + K);
                }
                if (remainder - K >= 0 && K != 0) {
                    deque.offer(curr * 10 + remainder - K);
                }
            }
        }

        // 利用 stream 对集合中的元素进行处理，并在最后将答案转为题目需要的 数组 形式
        return deque.stream().mapToInt(x -> x).toArray();
    }
}

/**
 * Approach 2: DFS
 * 使用 DFS 的解法，思路与 BFS 相同，写法不同罢了。
 * 这里就不再赘述了。有什么不清楚的可以看代码注释。
 * 相比与 BFS 会节省点空间，但是速度会慢一点。
 *
 * 时间复杂度：O(9 * 2^n)
 * 空间复杂度：O(2^n)
 */
class Solution {
    public int[] numsSameConsecDiff(int N, int K) {
        ArrayList<Integer> rst = new ArrayList<>();
        if (N == 1) {
            rst.add(0);
        }
        for (int i = 1; i <= 9; i++) {
            dfs(i, N - 1, K, rst);
        }
        return rst.stream().mapToInt(x -> x).toArray();
    }

    // num：当前的值； N：还剩下几个位置上的数还没处理
    private void dfs(int num, int N, int K, List<Integer> rst) {
        // N==0 说明已经求得一个数，那么将其加入到结果当中，结束递归
        if (N == 0) {
            rst.add(num);
            return;
        }
        int remainder = num % 10;
        if (remainder + K <= 9) {
            dfs(num * 10 + remainder + K, N - 1, K, rst);
        }
        if (remainder - K >= 0 && K != 0) {
            dfs(num * 10 + remainder - K, N - 1, K, rst);
        }
    }
}