/*
Given an array A of strings made only from lowercase letters,
return a list of all characters that show up in all strings within the list (including duplicates).
For example, if a character occurs 3 times in all strings but not 4 times,
you need to include that character three times in the final answer.

You may return the answer in any order.

Example 1:
Input: ["bella","label","roller"]
Output: ["e","l","l"]

Example 2:
Input: ["cool","lock","cook"]
Output: ["c","o"]

Note:
    1. 1 <= A.length <= 100
    2. 1 <= A[i].length <= 100
    3. A[i][j] is a lowercase letter
 */

/**
 * Approach: Convert String to an Integer Array (int[26])
 * 题目中明确说明，一个字符串中只可能出现26个字符（均为小写）
 * 因此我们可以定义一个长度为26的数组，用来表示字符串中字符出现的次数，
 * 从而避免使用 HashMap，也是一个很常见的技巧了。
 *
 * 计算得到这个数组之后，因为题目要求结果中的每个字符至少都需要在每个字符串中出现一次。
 * 因此，我们可以利用 A[0] 建立一个 baseCount[].
 * 然后对后续字符串遍历的时候，取各个字符出现的最小次数即可。
 *
 * 时间复杂度：O(N * M)
 * 空间复杂度：O(1)
 */

class Solution {
    public List<String> commonChars(String[] A) {
        // 第一个作为基准字符串，数组的值表示 a-z 每个字符出现的次数
        // int[0] 表示'a'出现的次数，int[1]表示'b'出现的次数
        int[] baseCount = new int[26];
        for (char c : A[0].toCharArray()) {
            baseCount[c - 'a']++;
        }

        // 遍历剩下的字符串
        for (int i = 1; i < A.length; i++) {
            int[] tempCount = new int[26];
            // 当前字符的出现次数++；
            for (char c : A[i].toCharArray()) {
                tempCount[c - 'a']++;
            }
            // 和基准字符串对应字符出现次数进行比较，取最小值
            for (int j = 0; j < baseCount.length; j++) {
                baseCount[j] = Math.min(baseCount[j], tempCount[j]);
            }
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < baseCount.length; i++) {
            for (int j = 0; j < baseCount[i]; j++) {
                ans.add(String.valueOf((char)('a' + i)));
            }
        }
        return ans;
    }
}