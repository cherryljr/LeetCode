首先我们考虑一个简单的情况：
    所有信封的长和宽并不重复。
    这样我们可以：先对长进行排序，然后对 宽 用二分法求 最长递增子序列（LIS）即可。
    时间复杂度为：O(nlogn) 用 DP 解法的话就要 O(N^2)了。

/*
You have a number of envelopes with widths and heights given as a pair of integers (w, h).
One envelope can fit into another 
if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

What is the maximum number of envelopes can you Russian doll? (put one inside other)

Example:
Given envelopes = [[5,4],[6,4],[6,7],[2,3]], 
the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
*/

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes  == null || envelopes.length == 0) {
            return 0;
        }
    }
}