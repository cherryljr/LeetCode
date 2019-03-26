/*
Given a list of non-negative numbers and a target integer k,
write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k,
that is, sums up to n*k where n is also an integer.

Example 1:
Input: [23, 2, 4, 6, 7],  k=6
Output: True
Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.

Example 2:
Input: [23, 2, 6, 4, 7],  k=6
Output: True
Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.

Note:
The length of the array won't exceed 10,000.
You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
 */

/**
 * Approach 1: PreSum + HashMap
 * 本题与 Maximum Size Subarray Sum Equals k 用到了相同的解法。
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 * 区别在于：
 * 我们原来需要考虑的仅仅只是 k，而本题需要考虑的是 0,k,2k,3k...preSum.
 * 但是本题难点不在在这里，难点在于各种 特殊 情况的考虑。
 * 具体有哪些情况呢？请参见代码注释...
 */
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        // Since the size of subarray is at least 2.
        if (nums == null || nums.length <= 1) {
            return false;
        }

        // Two continuous "0" will form a subarray which has sum = 0.
        // 0 * k == 0 will always be true. (n could be zero...Damn it!)
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == 0 && nums[i] == 0) {
                return true;
            }
        }
        // At this point, k can't be "0" any longer.
        if (k == 0) {
            return false;
        }
        // Let's only check positive k. Because if there is a n makes n * k = sum,
        // it is always true -n * -k = sum.
        else if (k < 0) {
            k = -k;
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            // Validate from the biggest possible n * k to k
            for (int j = (preSum / k) * k; j >= k; j -= k) {
                if (map.containsKey(preSum - j) && i - map.get(preSum - j) > 1) {
                    return true;
                }
            }
            // if we can't find preSum in the map, put it into it.
            if (!map.containsKey(preSum)) {
                map.put(preSum, i);
            }
        }

        return false;
    }
}

/**
 * Approach 2: Mathematics + PreSum + HashMap
 * Approach 1 中我们考虑了不少特殊情况，并且我们在 map 中还需要
 * 利用一个 for 循环依次查找 preSum-k, preSum-2k... 这增加了我们运算的时间。
 * 那么如何优化这个过程呢？
 * 
 * 实际上我们可以只遍历整个数组一次就够了。
 * map中存储的不再是 preSum 而是 preSum % k.
 * 其他过程与 Approach 1 相同，每次只需要在里面寻找 preSum % k 是否已经出现过，
 * 如果出现过并且二者的 index 相差大于1 直接返回 true;
 * 如果没出现过则将 preSum % k 记录到 map 中。
 * 
 * 根据的原理：
 * (a + (n*x))%x == (a%x + n*x%x) % x == (a%x + 0) % x == a%x
 * 举个例子：[23,2,6,4,7] => 其 preSum 数组为：[23,25,31,35,42]
 * 那么对 k 取余后的数组为：[5,1,1,5,0]
 * 我们发现在 index 0 和 index 3 都能获得 5，并且距离 大于1。
 * 这就意味着在这两个 index 之间，我们加入了一个 k 的倍数。
 * 
 * 在这个方法的帮助下，我们节省了不少的时间，Approach 1 中一些特殊情况我们也不需要考虑了。
 * 而这也应该是这道题目的考点所在了。
 */
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length <= 1) {
            return false;
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            int mod = k == 0 ? preSum : preSum % k;
            if (map.containsKey(mod) && i - map.get(mod) > 1) {
                return true;
            }
            if (!map.containsKey(mod)) {
                map.put(mod, i);
            }
        }

        return false;
    }
}
