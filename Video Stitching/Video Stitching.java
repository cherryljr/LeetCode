/*
You are given a series of video clips from a sporting event that lasted T seconds.
These video clips can be overlapping with each other and have varied lengths.

Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1].
We can cut these clips into segments freely: for example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].

Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire sporting event ([0, T]).
If the task is impossible, return -1.

Example 1:
Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
Output: 3
Explanation:
We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
Then, we can reconstruct the sporting event as follows:
We cut [1,9] into segments [1,2] + [2,8] + [8,9].
Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].

Example 2:
Input: clips = [[0,1],[1,2]], T = 5
Output: -1
Explanation:
We can't cover [0,5] with only [0,1] and [0,2].

Example 3:
Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
Output: 3
Explanation:
We can take clips [0,4], [4,7], and [6,9].

Example 4:
Input: clips = [[0,4],[2,8]], T = 5
Output: 2
Explanation:
Notice you can have extra video after the event ends.

Note:
    1. 1 <= clips.length <= 100
    2. 0 <= clips[i][0], clips[i][1] <= 100
    3. 0 <= T <= 100
 */

/**
 * Approach: Greedy
 * 区间类问题...解法比较明显，排序之后使用 Greedy 的解法即可。
 * 排序的规则为按照左端点进行排序，然后比较右端点能够覆盖的最大范围。
 * 这也是本题的贪心思想：在当前能够 reach 到的范围内，尽量选择右端点大的 segment。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 *
 * 类似的问题：
 * Non-overlapping Intervals:
 *  https://github.com/cherryljr/LintCode/blob/master/Non-overlapping%20Intervals.java
 */
class Solution {
    public int videoStitching(int[][] clips, int T) {
        // Arrays.sort(clips, (a, b) -> a[0] - b[0]);
        // 我也想用 Lambda表达式 来写...奈何每次跑出来的成绩实在太难看...
        Arrays.sort(clips, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int currEnd = 0, ans = 0;
        for (int i = 0; i < clips.length; ) {
            // 如果当前 clip 的左端点 > 当前覆盖区域的右端点，则说明无法完成一个连续区间的覆盖，返回-1
            if (clips[i][0] > currEnd) {
                return -1;
            }
            int newEnd = currEnd;
            // 只要当前 clips 的左端点能够被 reach 到，当前区间的右端点便可以被不断地更新。
            while (i < clips.length && clips[i][0] <= currEnd) {
                newEnd = Math.max(newEnd, clips[i++][1]);
            }

            // 从 while 循环中跳出后，说明必须引入一个新的 clip 才能使得区间得以连续
            ans++;
            currEnd = newEnd;
            if (currEnd >= T) {
                return ans;
            }
        }
        return -1;
    }
}