/*
Given an array arr of positive integers, consider all binary trees such that:
Each node has either 0 or 2 children;
The values of arr correspond to the values of each leaf in an in-order traversal of the tree.
(Recall that a node is a leaf if and only if it has 0 children.)
The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtree respectively.
Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.
It is guaranteed this sum fits into a 32-bit integer.

Example 1:
Input: arr = [6,2,4]
Output: 32
Explanation:
There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
    24            24
   /  \          /  \
  12   4        6    8
 /  \               / \
6    2             2   4

Constraints:
    1. 2 <= arr.length <= 40
    2. 1 <= arr[i] <= 15
    3. It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
 */

/**
 * Approach 1: Interval DP
 * 该问题其实可以转换成将各个 SubArray 进行 merge，每次 merge 将耗费两个 SubArray 中最大值的乘积。
 * 因此这个问题就转换成了一个经典的区间DP问题。
 * 我们只需要做的就是枚举区间长度 l，根据题意分析，本题枚举的区间长度应该从 2 开始。
 * pivot的位置应该从 start 开始。（对于区间DP不清楚的可以参考下方给出的链接）
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Burst%20Balloons.java
 */
class Solution {
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        int[][] maxNum = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                maxNum[i][j] = i == j ? arr[i] : Math.max(maxNum[i][j - 1], arr[j]);
            }
        }

        for (int l = 2; l <= n; l++) {
            for (int start = 0; start + l <= n; start++) {
                int end = start + l - 1;
                // 因为要求的是最小值，因此在这里将 dp[start][end] 初始化成最大值，
                // 适用于 dp 数组需要初始化为 0，却需要求最小值的情况。
                dp[start][end] = Integer.MAX_VALUE;
                for (int pivot = start; pivot < end; pivot++) {
                    dp[start][end] = Math.min(dp[start][end], dp[start][pivot] + dp[pivot + 1][end] + maxNum[start][pivot] * maxNum[pivot + 1][end]);
                }
            }
        }
        return dp[0][n - 1];
    }
}

/**
 * Approach 2: Monotonic Stack
 * 该解法是评论区 lee215 大佬提出的...（太强了，给跪了）
 * 从数据规模看，这个问题只是很普通的 区间DP 问题（我堂堂区间DP竟然也沦落到了普通题级别了吗）
 * 但实际这道题目存在这 O(n) 级别的最优解。lee大佬的解释很到位，这边就直接复制过来了。出处见下方链接。
 * 类似 LintCode 上的 Max Tree.
 *
 * Intuition
 * When we build a node in the tree, we compared the two numbers a and b.
 * In this process, the smaller one is removed and we won't use it anymore, and the bigger one actually stays.
 *
 * The problem can translated as following:
 * Given an array A, choose two neighbors in the array a and b,
 * we can remove the smaller one min(a,b) and the cost is a * b.
 * What is the minimum cost to remove the whole array until only one left?
 *
 * To remove a number a, it needs a cost a * b, where b >= a.
 * So a has to be removed by a bigger number.
 * We want minimize this cost, so we need to minimize b.
 *
 * b has two candidates, the first bigger number on the left, the first bigger number on the right.
 *
 * The cost to remove a is a * min(left, right).
 *
 * Explanation
 * Now we know that, this is not a dp problem.
 * (Because dp solution test all ways to build up the tree, it's kinda of brute force)
 *
 * With the intuition above in mind,
 * we decompose a hard problem into reasonable easy one:
 * Just find the next greater element in the array, on the left and one right.
 * Refer to 1019. Next Greater Node In Linked List
 *
 * Time  Complexity: O(N) for one pass
 * Space Complexity: O(N) for stack in the worst cases
 *
 * References:
 *  https://github.com/cherryljr/LintCode/blob/master/Max%20Tree.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Next%20Greater%20Node%20In%20Linked%20List/Next%20Greater%20Node%20In%20Linked%20List.java
 */
class Solution {
    public int mctFromLeafValues(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i <= arr.length; i++) {
            // 维护一个单调递减的栈，最后通过 MAX 将栈中所有元素 pop 出来
            // 也可以参考 lee 的解法，一开始放入一个 MAX 在栈中，目的都是一样的
            int num = i == arr.length ? Integer.MAX_VALUE : arr[i];
            while (!stack.isEmpty() && num >= stack.peek()) {
                int x = stack.pop();
                // 注意在 pop 后对stack的判空操作，使用单调栈时务必注意这个情况
                ans += stack.isEmpty() ? (num == Integer.MAX_VALUE ? 0 : num * x) : x * Math.min(stack.peek(), num);
            }
            stack.push(num);
        }
        return ans;
    }
}