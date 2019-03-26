/*
In a list of songs, the i-th song has a duration of time[i] seconds.
Return the number of pairs of songs for which their total duration in seconds is divisible by 60.
Formally, we want the number of indices i < j with (time[i] + time[j]) % 60 == 0.

Example 1:
Input: [30,20,150,100,40]
Output: 3
Explanation: Three pairs have a total duration divisible by 60:
(time[0] = 30, time[2] = 150): total duration 180
(time[1] = 20, time[3] = 100): total duration 120
(time[1] = 20, time[4] = 40): total duration 60

Example 2:
Input: [60,60,60]
Output: 3
Explanation: All three pairs have a total duration of 120, which is divisible by 60.

Note:
    1. 1 <= time.length <= 60000
    2. 1 <= time[i] <= 500
 */

/**
 * Approach: HashMap PreRecord (Similar to PreSum)
 * 这道题目与 Continuous Subarray Sum 非常类似，属于一个简单的变型题。
 * 因为题目已经明确告知数据范围，总数据量在 60000 级别。因此只考虑 复杂度小于等于O(nlogn) 的算法。
 * 同时因为要求 i < j, 因此排除了排序，这样可以考虑的 O(nlogn) 算法并不明显，直觉应该是 O(n) 的算法。
 * 注意到：数据大小不会超过 500， 并且题目只求 pair，即只求两个数之和。
 * 因此想到使用 Map 来进行类似 PreSum 中用到的记录方法。这边就称之为 PreHashMap 吧~
 * 我们可以实现把每个数的值和对应的出现次数记录下来，然后看是否有 map.containsKey(j - time[i]) 即可~
 * 总体原理和 Continuous Subarray Sum 基本相同，但是要简单一些。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * References:
 * Continuous Subarray Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Continuous%20Subarray%20Sum.java
 * Maximum Size Subarray Sum Equals k:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 */
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            for (int j = 60; j < 1000; j += 60) {
                if (map.containsKey(j - time[i])) {
                    ans += map.get(j - time[i]);
                }
            }
            map.put(time[i], map.getOrDefault(time[i], 0) + 1);
        }
        return ans;
    }
}

/**
 * Approach 2: Mathematics + MOD
 * 与 Continuous Subarray Sum 的 Approach 2 一样，利用一点数学可以节省一些时间（省去第二个 for 循环）
 * 在 Continuous Subarray Sum 中我们利用到了 (a + (n*k))%k == a%k 这个特性。
 * 而这里我们要利用的则是:
 *  a%k + b%k == k  ==> (a%k + b%k) % k == k%k == 0  ==>  (a+b) % k == 0
 * 道理应该还是比较浅显易懂的...
 *
 * 根据这个知识，我们可以对代码进行如下优化。
 *  1. 省去 HashMap 数据结构，直接使用 Array 替代（大小固定为60，比较对60取取余不会超过60...）
 *  2. 省去第二重 for 循环，直接判断 count[60 - time[i]%60] 即可（注意对 整除 的判断就行）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * References:
 * Continuous Subarray Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Continuous%20Subarray%20Sum.java
 */
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int ans = 0;
        int[] count = new int[60];
        for (int t : time) {
            int reminder = t % 60;
            ans += reminder == 0 ? count[0] : count[60 - reminder];
            count[reminder]++;
        }
        return ans;
    }
}
