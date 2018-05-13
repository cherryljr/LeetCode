/*
A character is unique in string S if it occurs exactly once in it.
For example, in string S = "LETTER", the only unique characters are "L" and "R".
Let's define UNIQ(S) as the number of unique characters in string S.
For example, UNIQ("LETTER") =  2.
Given a string S, calculate the sum of UNIQ(substring) over all non-empty substrings of S.
If there are two or more equal substrings at different positions in S, we consider them different.
Since the answer can be very large, retrun the answer modulo 10 ^ 9 + 7.

Example 1:
Input: "ABC"
Output: 10
Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
Evey substring is composed with only unique letters.
Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10

Example 2:
Input: "ABA"
Output: 8
Explanation: The same as example 1, except uni("ABA") = 1.

Note: 0 <= S.length <= 10000.
 */

/**
 * Approach: Split By Character
 * 将目光转移到单个字符对各个 Unique Substring 的贡献上，
 * 而非去计算每个 Unique Substring 的长度...（非常巧妙的一个做法）
 *
 * 下面举个例子来帮助大家理解这个做法。假设我们有一个字符串 S，
 * 它只包含了 3 个'A': "XAXAXXAX"，那么我们现在来考虑包含 'A' 的 Unique Substring 有几个呢？
 * 首先我们可以利用 Map 来记录 'A' 的出现位置，在本例中为：S[1] = S[3] = S[6] = 'A'
 * 那么对于中间（第二个）'A'，它的左边界有 (2, 3) 总共 2 种可能性，("XA(XAX..." 和 "XAX(AX...")
 * 右边界有 (3,4,5) 总共 3 种可能性.("...XA)XXAX"; "...XAX)XAX" 和 "...XAXX)AX")
 * 因此包含第二个 'A' 的 Unique Substring 总共有：2*3=6 个。
 *  同时也意味着：第二个'A',在 6 个符合条件的Substring中贡献了 1 的长度。因此我们需要给结果长度 +6.
 *  而这也说明了为什么我们只需要计算 S 中各个字符被几个 Unique Substring 所包含即可。
 * 理解了上述思想，我们只需要统计各个字符在划分 Unique Substring 时可以被几个 Substring 包含，
 * 然后全部加起来即可。
 *
 * 注意：当该字符的位置是第一次出现时，其左边界就是 0，因此左边界可能性有：index.get(i) + 1 种。
 * 当该字符的位置是最后一次出现时，其右边界就是 S.length()-1，因此右边界可能性有：S.length() - index.get(i) 种
 *
 * 时间复杂度为：O(n)
 *
 * 参考资料：
 * https://leetcode.com/problems/unique-letter-string/discuss/128952/One-pass-O(N)-Straight-Forward
 * https://leetcode.com/problems/unique-letter-string/solution/
 */
class Solution {
    public int uniqueLetterString(String S) {
        final int MOD = 1000000007;
        Map<Character, List<Integer>> index = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            index.computeIfAbsent(S.charAt(i), x -> new ArrayList<>()).add(i);
        }

        int sum = 0;
        for (List<Integer> list : index.values()) {
            for (int i = 0; i < list.size(); i++) {
                // 需要经常对 list 的 i 位置附近的元素进行访问，因此需要使用 ArrayList（LinkedList会超时哦）
                int left = i == 0 ? -1 : list.get(i - 1);   // 左边界
                int right = i == list.size() - 1 ? S.length() : list.get(i + 1);    // 右边界
                sum = (sum + (list.get(i) - left) * (right - list.get(i))) % MOD;
            }
        }
        return sum;
    }
}

