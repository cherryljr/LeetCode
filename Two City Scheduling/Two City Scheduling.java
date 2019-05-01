/*
There are 2N people a company is planning to interview. The cost of flying the i-th person to city A is costs[i][0],
and the cost of flying the i-th person to city B is costs[i][1].

Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.

Example 1:
Input: [[10,20],[30,200],[400,50],[30,20]]
Output: 110
Explanation:
The first person goes to city A for a cost of 10.
The second person goes to city A for a cost of 30.
The third person goes to city B for a cost of 50.
The fourth person goes to city B for a cost of 20.
The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.

Note:
    1. 1 <= costs.length <= 100
    2. It is guaranteed that costs.length is even.
    3. 1 <= costs[i][0], costs[i][1] <= 1000
 */

/**
 * Approach: Greedy (Sorting)
 * 对于这道问题，很显然会涉及到 排序 这个操作，而问题就是在于 排序的标准 是什么？
 * 我们可以这样去考虑：
 *  为了使得总花费最少，我们需要最小化去 A 和 B 城的总花费。
 *  而对于每一个人来说，他必定去 A 或者 B，如果 costs[i][B] - costs[i][A] > 0 就说明这个人去 A 城花费更少，可以节省花费。
 *  因此本题的 排序标准 就是：一个人去 A 和 B 所使用花费的差值 (costs[i][1] - costs[i][0])。
 *  即：采访者i如果去 A 而不去 B 将可以节省多少费用。
 *  按照这个标准 从大到小 排序之后，我们就能知道：
 *  前 N 个人应该去 A 城，而后 N 个人应该去 B 城。这样就可以使得我们节省最多的花费。
 *
 * 这里需要使用排序的原因是因为我们不能简单地比较每个人去 A，B 的花费，然后取较小值。
 * 因为可能存在两个人都是去 A 城花费更小的情况，但是题目需要保证 A，B 城的人数均为 N 个。
 * 举个例子：[10, 20], [10, 300]
 * 对每组数计算 a[1] - a[0] 之后为：10, 290.
 * 这里虽然两个人都是去 A 城更加便宜，但是根据差值大小的比较我们可以知道
 * 第二个人 去 A 城的话将为我们节省更多的花费，因此我们选择 第二个人去 A 城，第一个个人去 B 城。
 * 故题目的实质上的排序依据为：能够节省的费用大小。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 *
 * PS.这里可以发现，其实我们只需要结果中 前N大 的元素即可。因此可以使用 QuickSelect 来解决，而不必对全部元素进行排序。
 * 从而将时间复杂度降低到 O(n) in average.
 * 对于 QuickSelect 可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 */
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (b[1] - b[0]) - (a[1] - a[0]);
            }
        });
        // Lambda表达式跑得慢，但是代码简洁...大家根据喜好选择即可
        // Arrays.sort(costs, (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));
        int sum = 0;
        for (int i = 0; i < costs.length / 2; i++) {
            sum += costs[i][0] + costs[costs.length - i - 1][1];
        }
        return sum;
    }
}
