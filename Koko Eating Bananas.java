/*
Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.
The guards have gone and will come back in H hours.

Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and eats K bananas from that pile.
If the pile has less than K bananas, she eats all of them instead, and won't eat any more bananas during this hour.

Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
Return the minimum integer K such that she can eat all the bananas within H hours.

Example 1:
Input: piles = [3,6,7,11], H = 8
Output: 4

Example 2:
Input: piles = [30,11,23,4,20], H = 5
Output: 30

Example 3:
Input: piles = [30,11,23,4,20], H = 6
Output: 23

Note:
    1. 1 <= piles.length <= 10^4
    2. piles.length <= H <= 10^9
    3. 1 <= piles[i] <= 10^9
 */

/**
 * Approach: Greedy + Binary Search
 * 题目数据规模给的提示还是非常明显的...时间复杂度必定是在 O(NlogM) （毕竟 10^9 的级别，只能用 logN 级别的算法）
 * 要求：猩猩最低以每小时吃多少个香蕉的速度，才能在管理员回来之前吃完所有香蕉。
 * （注意：猩猩每小时只会吃某一堆香蕉，如果这堆香蕉个数不足，则剩余的时间内，猩猩并不会去其他堆吃香蕉）
 * 这个限制使得题目难度低了非常多，我们不需要去考虑最优组合什么的，只要吃完就行。
 *
 * 因此，我们可以直接对速度 K 进行二分（下界为1，上界为 max(piles[i])）。
 * 然后花费 O(n) 的时间对 K 进行验证看是否其偏大还是偏小即可。
 *
 * 时间复杂度：O(nlogm)
 * 空间复杂度：O(1)
 *
 * 类似的问题：
 * Capacity To Ship Packages Within D Days:
 *  https://github.com/cherryljr/LeetCode/blob/master/Capacity%20To%20Ship%20Packages%20Within%20D%20Days.java
 * Split Array Largest Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Split%20Array%20Largest%20Sum.java
 *
 * PS.我记得国内某知名企业2018年的笔试题好像就是抄的这个...
 */
class Solution {
    public int minEatingSpeed(int[] piles, int H) {
        int left = 1, right = 0;
        for (int pile : piles) {
            right = Math.max(right, pile);
        }

        while (left < right) {
            int mid = left + (right - left >> 1);
            int hour = getHour(piles, mid);
            if (hour <= H) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int getHour(int[] piles, int limit) {
        int hour = 0;
        for (int pile : piles) {
            hour += (pile + limit - 1) / limit;
            // hour += Math.ceil((double)pile / limit);
        }
        return hour;
    }
}