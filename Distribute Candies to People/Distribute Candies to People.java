/*
We distribute some number of candies, to a row of n = num_people people in the following way:
We then give 1 candy to the first person, 2 candies to the second person, and so on until we give n candies to the last person.
Then, we go back to the start of the row, giving n + 1 candies to the first person, n + 2 candies to the second person,
and so on until we give 2 * n candies to the last person.

This process repeats (with us giving one more candy each time, and moving to the start of the row after we reach the end) until we run out of candies.
The last person will receive all of our remaining candies (not necessarily one more than the previous gift).

Return an array (of length num_people and sum candies) that represents the final distribution of candies.

Example 1:
Input: candies = 7, num_people = 4
Output: [1,2,3,1]
Explanation:
On the first turn, ans[0] += 1, and the array is [1,0,0,0].
On the second turn, ans[1] += 2, and the array is [1,2,0,0].
On the third turn, ans[2] += 3, and the array is [1,2,3,0].
On the fourth turn, ans[3] += 1 (because there is only one candy left), and the final array is [1,2,3,1].

Example 2:
Input: candies = 10, num_people = 3
Output: [5,2,3]
Explanation:
On the first turn, ans[0] += 1, and the array is [1,0,0].
On the second turn, ans[1] += 2, and the array is [1,2,0].
On the third turn, ans[2] += 3, and the array is [1,2,3].
On the fourth turn, ans[0] += 4, and the final array is [5,2,3].

Constraints:
    1. 1 <= candies <= 10^9
    2. 1 <= num_people <= 1000
 */

/**
 * Approach: Brute Force
 * 原本因为这会是一道考察数学的题目，结果发现它考察的是对数据规模的分析能力。
 * 按照分糖果的规则，后面一个人的个数 = 前一个人个数 + 1.
 * 每个人获得的糖果数目是一个等差数列（差值为1）。
 * 而对于 循环 处理，只需要利用到 取余 这个运算即可轻松解决。
 * 每次分配的糖果数为：1 + 2 + 3 + ... + n == candies ==> (1 + n) * n / 2 == candies
 * 所以我们只需要进行暴力计算出分配情况即可。
 * 注意点：当最后一次分配糖果数不足时，采取剩多少发多少的做法...
 *
 * 时间复杂度：O(sqrt(candies))
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] distributeCandies(int candies, int num_people) {
        int[] ans = new int[num_people];
        for (int i = 0; candies > 0; i++) {
            // 为了处理最后一次分配糖果数不足的情况，这里使用了 min 操作
            ans[i % num_people] += Math.min(candies, i + 1);
            candies -= i + 1;
        }
        return ans;
    }
}