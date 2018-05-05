/*
On a table are N cards, with a positive integer printed on the front and back of each card (possibly different).
We flip any number of cards, and after we choose one card.
If the number X on the back of the chosen card is not on the front of any card, then this number X is good.
What is the smallest number that is good?  If no number is good, output 0.
Here, fronts[i] and backs[i] represent the number on the front and back of card i.
A flip swaps the front and back numbers, so the value on the front is now on the back and vice versa.

Example:
Input: fronts = [1,2,4,4,7], backs = [1,3,4,1,3]
Output: 2
Explanation: If we flip the second card, the fronts are [1,3,4,4,7] and the backs are [1,2,4,1,3].
We choose the second card, which has number 2 on the back, and it isn't on the front of any card, so 2 is good.

Note:
1 <= fronts.length == backs.length <= 1000.
1 <= fronts[i] <= 2000.
1 <= backs[i] <= 2000.
 */

/**
 * Approach: HashSet
 * 这道题目首先要认真把题目看清楚...
 * Good Number 的定义是：将选定的一张牌翻过来（牌背面数值为X），如果当前所有牌的正面都不是该数值 X 的话，
 * 那么 X 就是一个 Good Number.
 * 因为题目并没有限制翻转的次数，所以我们可以任意翻牌。求最小的 Good Number.
 * 读懂题意之后，我们可以做如下分析：（注：场上的意思为当前人们可以看到的数值，即当前情况下的正面）
 * 当一张牌 X 的两面数值都一样时，这就意味着该数值将一定存在于场上（正面）。即怎么翻都是没用的。
 * 而对于那些正反面数值不同的牌呢，我们可以按照我们想要的将其 正面数值 / 反面数值 摆在场上给我们看。
 * 因此我们可以建立一个 Set，用来存储那些 正反面数值相同 牌的数值，
 * 然后遍历所有牌的正反面数值，如果其数值在 Set 中，那么必定不会是 Good Number.
 * 反之则有可能成为 Good Number.在这里面取最小值即可。
 * （不用在意Good Number指的是反面的数值这件事，因为翻转次数不限，所以我们可以把任意想要的数翻过去）
 */
class Solution {
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> same = new HashSet<>();
        for (int i = 0; i < fronts.length; i++) {
            if (fronts[i] == backs[i]) {
                same.add(fronts[i]);
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < fronts.length; i++) {
            if (!same.contains(fronts[i])) {
                min = Math.min(min, fronts[i]);
            }
            if (!same.contains(backs[i])) {
                min = Math.min(min, backs[i]);
            }
        }

        // 如果不存在 Good Number, 返回 0
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}