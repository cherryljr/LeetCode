/*
We have a set of items: the i-th item has value values[i] and label labels[i].
Then, we choose a subset S of these items, such that:
|S| <= num_wanted
For every label L, the number of items in S with label L is <= use_limit.
Return the largest possible sum of the subset S.

Example 1:
Input: values = [5,4,3,2,1], labels = [1,1,2,2,3], num_wanted = 3, use_limit = 1
Output: 9
Explanation: The subset chosen is the first, third, and fifth item.

Example 2:
Input: values = [5,4,3,2,1], labels = [1,3,3,3,2], num_wanted = 3, use_limit = 2
Output: 12
Explanation: The subset chosen is the first, second, and third item.

Example 3:
Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 1
Output: 16
Explanation: The subset chosen is the first and fourth item.

Example 4:
Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 2
Output: 24
Explanation: The subset chosen is the first, second, and fourth item.

Note:
    1. 1 <= values.length == labels.length <= 20000
    2. 0 <= values[i], labels[i] <= 20000
    3. 1 <= num_wanted, use_limit <= values.length
 */

/**
 * Approach 1: HashMap + PriorityQueue
 * 这道题目的题意描述不好，导致理解上有一定难度（很坑！！！）
 * 题意：给一个集合，每个元素有一个值 values[i] 与标签 labels[i]。
 * 这里要选择一个子集，使得子集的元素个数不超过 num_wanted，而且 相同标签的元素 个数不超过 use_limit，求子集value之和的最大值。
 * 关键点在于：For every label L, the number of items in S with label L is <= use_limit.。
 * 意思是子集里面，相同的标签有具有个数限制，即不超过 use_limit 个。
 * 读清题意之后，这就是一道考察 数据结构 的问题了（当然不用也可以，因为可以利用题目中明确的数据规模）
 * 具体解释可以参考代码注释。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        // key:label;  value:各个 label 被使用的次数
        Map<Integer, Integer> limit = new HashMap<>();
        // 依据 value 按照从大到小的顺序对各个元素进行排序（a[0]:value, a[1]:label）
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 0; i < values.length; i++) {
            limit.putIfAbsent(labels[i], 0);
            pq.offer(new int[]{values[i], labels[i]});
        }

        int ans = 0, count = 0;
        // 将 pq 中的元素依次弹出，如果其 label 被使用次数不超过 use_limit 则选中当前元素（count++），否则跳过
        // 直到 pq 为空，或者元素个数达到了 num_wanted
        while (!pq.isEmpty() && count < num_wanted) {
            int[] curr = pq.poll();
            if (limit.get(curr[1]) < use_limit) {
                ans += curr[0];
                limit.put(curr[1], limit.get(curr[1]) + 1);
                count++;
            }
        }
        return ans;
    }
}

/**
 * Approach 2: No Data Structure (Just Using Array and Sort)
 * 因为题目已经给出了明确的数据规模，所以我们可以单纯地利用 数组 和 排序 来达到目的。
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        // 替代 map 完成计数功能
        int[] limit = new int[20001];
        // node[i][0]:values[i];  node[i][1]:labels[i]
        int[][] nodes = new int[values.length][2];
        for (int i = 0; i < values.length; i++) {
            nodes[i][0] = values[i];
            nodes[i][1] = labels[i];
        }
        Arrays.sort(nodes, (a, b) -> (b[0] - a[0]));

        int ans = 0, count = 0;
        for (int i = 0; i < values.length && count < num_wanted; i++) {
            if (limit[nodes[i][1]]++ < use_limit) {
                ans += nodes[i][0];
                count++;
            }
        }
        return ans;
    }
}