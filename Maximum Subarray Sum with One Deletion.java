/*
Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements) with at most one element deletion.
In other words, you want to choose a subarray and optionally delete one element from it so that there is still at least one element left
and the sum of the remaining elements is maximum possible.

Note that the subarray needs to be non-empty after deleting one element.

Example 1:
Input: arr = [1,-2,0,3]
Output: 4
Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes the maximum value.

Example 2:
Input: arr = [1,-2,-2,3]
Output: 3
Explanation: We just choose [3] and it's the maximum sum.

Example 3:
Input: arr = [-1,-1,-1,-1]
Output: -1
Explanation: The final subarray needs to be non-empty. You can't choose [-1] and delete -1 from it, then get an empty subarray to make the sum equals to 0.

Constraints:
    1. 1 <= arr.length <= 10^5
    2. -10^4 <= arr[i] <= 10^4
 */

/**
 * Approach 1: DP
 * 看到该问题第一反应是使用 DP 来解决（有点类似 House Robber），首先我们先定义状态。
 * 因为我们只能去除一个元素，因此我们可以将状态定义为：
 * keep[] 和 delete[]，其中 keep[] 表示不删除元素的情况下最大子数组和（区间为[0，i]），
 * delete[]代表删除元素的情况下的最大子数组和（区间为[0，i]）。
 *（如果没有次数限制，那么状态定义时，则通常针对 arr[i] 进行分析，比如在本题的环境下为：是否移除当前元素 arr[i]）
 *
 * 状态转移方程为：
 *  keep[i] = Math.max(keep[i - 1] + arr[i], arr[i]); //要么是当前元素累加之前的和，要么是重新从当前元素开始
 *  delete[i] = Math.max(delete[i - 1] + arr[i], keep[i - 1]);
 *  //要么是加上当前元素，也就是维持之前删除某个元素的情形，即 delete[i - 1] + arr[i]
 *  //要么是删除当前这个元素，那么区间[0, i-1]就是不删除元素的情况，即 keep[i - 1] + 0
 *
 * 初始化：
 * f(0)= arr[0] //因为必须要有元素，不能为 0 个元素
 * 那么问题在于 g(0) = 什么呢？
 * 举个例子，假设我们要计算g(1)：
 * g(1) = Math.max(g(0)+arr[1],f(0))//题目提到至少保留一个元素，所以必须要选f(0)，即g(0)要足够小（这里将其初始化为-0x3f3f3f3f）
 *
 * 最后遍历一遍 keep 数组和 delete 数组找出最大值即可。
 */
class Solution {
    public int maximumSum(int[] arr) {
        int n = arr.length;
        int[] keep = new int[n], delete = new int[n];
        keep[0] = arr[0];
        delete[0] = -0x3f3f3f3f;

        for (int i = 1; i < n; i++) {
            keep[i] = Math.max(keep[i - 1] + arr[i], arr[i]);
            delete[i] = Math.max(delete[i - 1] + arr[i], keep[i - 1]);
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, Math.max(keep[i], delete[i]));
        }
        return ans;
    }
}

/**
 * Approach 2: Forward-Backward Traversal
 * 1. 首先求以当前元素arr[i]作为结尾的最大子数组之和。
 * 2. 因为答案要求删除一个元素后的子数组的最大和，则答案可以表示为：ans = max(ans, left[i-1] + right[i+1])
 * left[i]表示从左到右以第i个元素结尾的最大子数组和，right[i]代表从右到左以第i个元素为结尾的最大子数组和.
 * 注意：本题要求元素个数不能为空，因此 left[] 和 right[] 里面至少包含一个元素值。
 * 因此在求解 ans 时，我们还需要考虑 left[] 或者 right[] 其中一个为空的情况（这也是合法情况）
 * 所以需要有 ans = Math.max(ans, left[i]) 这个步骤（left[] 和 right[] 是对称的，考虑一个即可）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 利用到类似解法的问题还有：Maximum Subarray II 和 Maximum Subarray Difference
 * References:
 *  https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/discuss/377397/Intuitive-Java-Solution-With-Explanation
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray%20II.java
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray%20Difference.java
 */
class Solution {
    public int maximumSum(int[] arr) {
        int n = arr.length, ans = arr[0];
        int[] left = new int[n], right = new int[n];
        left[0] = arr[0];
        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1] + arr[i], arr[i]);
            ans = Math.max(ans, left[i]);
        }
        right[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1] + arr[i], arr[i]);
        }
        for (int i = 1; i < n - 1; i++) {
            ans = Math.max(ans, left[i - 1] + right[i + 1]);
        }
        return ans;
    }
}