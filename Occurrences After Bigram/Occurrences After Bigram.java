/*
Given words first and second, consider occurrences in some text of the form "first second third",
where second comes immediately after first, and third comes immediately after second.

For each such occurrence, add "third" to the answer, and return the answer.

Example 1:
Input: text = "alice is a good girl she is a good student", first = "a", second = "good"
Output: ["girl","student"]

Example 2:
Input: text = "we will we will rock you", first = "we", second = "will"
Output: ["we","rock"]

Note:
    1. 1 <= text.length <= 1000
    2. text consists of space separated words, where each word consists of lowercase English letters.
    3. 1 <= first.length, second.length <= 10
    4. first and second consist of lowercase English letters.
 */

/**
 * Approach: Split Text and Compare
 * 把给的 text 按照空格进行分割得到一个字符串数组。
 * 然后按照顺序遍历一遍，对比该单词的前两个单词是否分别于 first 和 second 相等即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  对于 Collection.toArray(T[] a) 方法的解释与应用可以参考：
 *  https://blog.csdn.net/zhangyunfei_happy/article/details/51153754
 */
class Solution {
    public String[] findOcurrences(String text, String first, String second) {
        List<String> ans = new ArrayList<>();
        String[] strs = text.split(" ");
        for (int i = 2; i < strs.length; i++) {
            if (strs[i - 2].equals(first) && strs[i - 1].equals(second)) {
                ans.add(strs[i]);
            }
        }
        // return ans.toArray(new String[0]); 这个写法也可以
        return ans.toArray(new String[ans.size()]);
    }
}