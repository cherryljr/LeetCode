/*
Given an array A of 0s and 1s, we may change up to K values from 0 to 1.
Return the length of the longest (contiguous) subarray that contains only 1s.

Example 1:
Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
Output: 6
Explanation:
[1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.

Example 2:
Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
Output: 10
Explanation:
[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.

Note:
    1. 1 <= A.length <= 20000
    2. 0 <= K <= A.length
    3. A[i] is 0 or 1
 */

/**
 * Approach 1: Sliding Window
 * 这道题目属于 Max Consecutive Ones II 的一个 Follow Up.
 * 由原来的只能进行 一次flip 改成了 可以进行K次flip 的操作。
 * （其实有心的人在做 II 的时候应该就已经能够想到 K次flip 的这个扩展了）
 *
 * 题目难度不高，要求最多只能够进行 K 次flip.其实也可以把题目换一种问法：
 *  求一个最长 subarray 里面最多只能够含有K个0.
 * 这么问相信大家马上就能够反应过来这是一个考察 滑动窗口 的问题了。
 * 解题直接套模板就行...没啥好说的...
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * Sliding Window Template Explanation:
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
class Solution {
    public int longestOnes(int[] A, int K) {
        int ans = 0, count = 0;
        int begin = 0, end = 0;
        while (end < A.length) {
            if (A[end] == 0) {
                count++;
            }
            end++;

            while (count > K) {
                if (A[begin] == 0) {
                    count--;
                }
                begin++;
            }
            ans = Math.max(ans, end - begin);
        }
        return ans;
    }
}

/**
 * Approach 2: Sliding Window (Using Queue to record the left index)
 * 介于滑动窗口的特性，当左边界移动的时候，都要从左端开始移动。
 * 但是本题有个非常特殊的点在于：数组里面的元素要么是1，要么是0.
 * 因此当窗口内的0的个数超过了K的话，left要移动的下一个位置就是下一个0的位置。
 * 而0的位置，其实我们之前已经遍历过了，只需要把对应的位置存起来即可，然后left直接跳过去就行了。
 * 不再需要慢慢遍历过去。属于 空间换时间 的做法。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(k)
 * 
 * PS.由于引入了 Queue 这个数据结构，测试结果比 Approach 1 反而更慢了...
 * 但这是测试用例的问题...大家不用慌...
 */
class Solution {
    public int longestOnes(int[] A, int K) {
        int ans = 0, count = 0, left = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int right = 0; right < A.length; right++) {
            if (A[right] == 0) {
                queue.offer(right);
            }
            if (queue.size() > K) {
                left = queue.poll() + 1;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}