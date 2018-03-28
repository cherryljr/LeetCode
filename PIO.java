/*
Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
LeetCode would like to work on some projects to increase its capital before the IPO.
Since it has limited resources, it can only finish at most k distinct projects before the IPO.
Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given several projects.
For each project i, it has a pure profit Pi and a minimum capital of Ci is needed to start the corresponding project.
Initially, you have W capital. When you finish a project,
you will obtain its pure profit and the profit will be added to your total capital.

To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital,
and output your final maximized capital.

Example 1:
Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].

Output: 4

Explanation: Since your initial capital is 0, you can only start the project indexed 0.
             After finishing it you will obtain profit 1 and your capital becomes 1.
             With capital 1, you can either start the project indexed 1 or the project indexed 2.
             Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
             Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
Note:
    You may assume all numbers in the input are non-negative integers.
    The length of Profits array and Capital array will not exceed 50,000.
    The answer is guaranteed to fit in a 32-bit signed integer.
 */

/**
 * Approach: Greedy
 * The idea is each time we find a project with max profit and within current capital capability.
 *
 * Algorithm:
 *  1. Create (capital, profit) pairs and put them into PriorityQueue minHeap. This PriorityQueue sort by capital increasingly.
 *  2. Keep polling pairs from minHeap until the project out of current capital capability.
 *  Put them into PriorityQueue maxHeap which sort by profit decreasingly.
 *  3. Poll one from maxHeap, it’s guaranteed to be the project with max profit and within current capital capability.
 *  Add the profit to capital W.
 *  4. Repeat step 2 and 3 till finish k steps or no suitable project (pqPro.isEmpty()).
 * 
 * Time Complexity: 
 * For worst case, each project will be inserted and polled from both PriorityQueues once, 
 * so the overall runtime complexity should be O(NlgN), N is number of projects.
 */
class Solution {
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        // Profit Queue
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
        // Capital Queue
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[0] - b[0]));

        // Put all projects into the minHeap, which is sorted by Capital
        for (int i = 0; i < Capital.length; i++) {
            minHeap.offer(new int[]{Capital[i], Profits[i]});
        }
        // Do k times project
        for (int i = 0; i < k; i++) {
            // if W is no less than the capital in minHeap,
            // it means we can do this project, so poll it and push it into the maxHeap(Profit Queue)
            while (!minHeap.isEmpty() && W >= minHeap.peek()[0]) {
                maxHeap.offer(minHeap.poll());
            }
            // No project in the maxHeap, so every project that we could do is over
            if (maxHeap.isEmpty()) {
                return W;
            }
            // earn the pure profit
            W += maxHeap.poll()[1];
        }

        return W;
    }
}