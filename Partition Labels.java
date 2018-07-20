/*
A string S of lowercase letters is given. We want to partition this string into as many parts as possible
so that each letter appears in at most one part, and return a list of integers representing the size of these parts.

Example 1:
Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.

Note:
S will have length in range [1, 500].
S will consist of lowercase letters ('a' to 'z') only.
 */

/**
 * Approach: Greedy + HashMap (Array)
 * 这道题目实际上有一定贪心的思想在里面。
 * 为了保证每个元素在当前 subString 中出现次数最多，
 * 那就说明了在整个 String 中，subString 中的字符只会出现在这里面。
 * 因此我们可以使用一个 Map 来记录下每个字符最后一次的出现位置。
 * （这里因为数据确定只有字母，所以可以开一个数组来替代）
 * 然后再遍历一遍字符串，同时维护两个元素 start, end。
 * 只有当当前位置 i 等于 lastIndex[str[i]] 时，说明正好可以被切分。
 * 否则就继续向后走，更新 end 的值。
 * （必须保证前面所有元素都被子串包含，因此end为当前子串所有出现过字符最后一次出现的位置）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/string/leetcode-763-partition-labels/
 */
class Solution {
    public List<Integer> partitionLabels(String S) {
        int[] lastIndex = new int[128];
        // 记录所有字符最后一次出现的位置
        for (int i = 0; i < S.length(); i++) {
            lastIndex[S.charAt(i)] = i;
        }

        List<Integer> rst = new LinkedList<>();
        int start = 0, end = 0;
        for (int i = 0; i < S.length(); i++) {
            // Update end（end为当前子串所有出现过字符最后一次出现的位置）
            end = Math.max(end, lastIndex[S.charAt(i)]);
            if (i == end) {
                rst.add(end - start + 1);
                start = end + 1;
            }
        }
        return rst;
    }
}