/*
We have jobs: difficulty[i] is the difficulty of the ith job, and profit[i] is the profit of the ith job. 
Now we have some workers. worker[i] is the ability of the ith worker, 
which means that this worker can only complete a job with difficulty at most worker[i]. 
Every worker can be assigned at most one job, but one job can be completed multiple times.
For example, if 3 people attempt the same job that pays $1, then the total profit will be $3.  
If a worker cannot complete any job, his profit is $0.
What is the most profit we can make?

Example 1:
Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
Output: 100 
Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get profit of [20,20,30,30] seperately.

Notes:
1 <= difficulty.length = profit.length <= 10000
1 <= worker.length <= 10000
difficulty[i], profit[i], worker[i]  are in range [1, 10^5]
 */

/**
 * Approach 1: Two PriorityQueue (maxHeap + minHeap)
 * 这道题目与 PIO 十分类似：
 * https://github.com/cherryljr/LeetCode/blob/master/PIO.java
 * 但是有点小小的不同：
 * PIO中，启动资金是升序的，即随着项目越做越多，启动资金也就也来越多。这样就会使得 minHeap 逐步减小
 * 而本题中，工人的能力值（对应PIO中的启动资金）不一定是升序来排列的。但是我们可以将其转换成 PIO 问题然后解决。
 * 对此我们需要利用到的就是 排序。
 */
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        // 总的任务池，当前还不能接的任务，按照 能力值 从低到高 排序
        PriorityQueue<Task> diffQueue = new PriorityQueue<>((a, b) -> a.difficulty - b.difficulty);
        // 当前能够接的任务，按照 报酬 从高到低 排序
        PriorityQueue<Task> profQueue = new PriorityQueue<>((a, b) -> b.profit - a.profit);
        for (int i = 0; i < difficulty.length; i++) {
            diffQueue.add(new Task(difficulty[i], profit[i]));
        }
        // 对工人的能力值按照 从低到高 排序（为了处理成和 PIO 相同的情况）
        Arrays.sort(worker);

        int maxProfit = 0;
        for (int i = 0; i < worker.length; i++) {
            // 随着能力的提高，查看 diffQueue(minHeap) 中是否还有新的任务可以接受
            while (!diffQueue.isEmpty() && worker[i] >= diffQueue.peek().difficulty) {
                profQueue.add(diffQueue.poll());
            }
            // 在 maxHeap.peek 就是当前所能接的 报酬最高 的任务（需要判断堆是否为空，即有无任务可以接）
            // 无需 poll 出去，因为一个任务可以被重复执行
            if (!profQueue.isEmpty()) {
                maxProfit += profQueue.peek().profit;
            }
        }
        return maxProfit;
    }

    class Task {
        int difficulty, profit;

        Task(int difficulty, int profit) {
            this.difficulty = difficulty;
            this.profit = profit;
        }
    }
}

/**
 * Approach 2: Sorting + BinarySearch
 * 因为在本题中，我们做完一个任务之后，任务是不会消失的，即一个任务可以重复被做（也可以被多人同时做）。
 * 那么我们不妨转换思路，对任务按照 难度值 进行排序。
 * 然后我们可以利用每个人的能力值在这里面进行 二分查找 其所能完成的任务。
 * 然后在区间内选取最高的收益即可。
 * 对于最高收益，我们只需要建立一个 preMaxProfit[] 来存储区间的 profit 最大值以供后续调用即可。
 * 非常典型的 空间 换 时间 的做法。
 *
 * 本题所应用的 BinarySearch 是其寻找 上界 的方法，即数组中最后一个 不大于 target 的元素。
 * 对于二分法不了解的可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        Task[] tasks = new Task[difficulty.length];
        for (int i = 0; i < difficulty.length; i++) {
            tasks[i] = new Task(difficulty[i], profit[i]);
        }
        Arrays.sort(tasks);

        // 计算 报酬 的区间最大值
        int[] preMaxProfit = new int[profit.length];
        preMaxProfit[0] = tasks[0].profit;
        for (int i = 1; i < preMaxProfit.length; i++) {
            preMaxProfit[i] = Math.max(preMaxProfit[i - 1], tasks[i].profit);
        }

        int maxProfit = 0;
        for (int i = 0; i < worker.length; i++) {
            int index = upperBound(tasks, worker[i]);
            // 如果找不到（即没有任务可以接）则返回 index = -1
            maxProfit += index == -1 ? 0 : preMaxProfit[index];
        }
        return maxProfit;
    }

    private int upperBound(Task[] tasks, int target) {
        int start = -1, end = tasks.length - 1;
        while (start < end) {
            int mid = start + ((end - start + 1) >> 1);
            if (target >= tasks[mid].difficulty) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return end;
    }

    class Task implements Comparable<Task> {
        int difficulty, profit;

        Task(int difficulty, int profit) {
            this.difficulty = difficulty;
            this.profit = profit;
        }

        @Override
        public int compareTo(Task other) {
            return this.difficulty - other.difficulty;
        }
    }
}