/*
Given a string text, we are allowed to swap two of the characters in the string.
Find the length of the longest substring with repeated characters.

Example 1:
Input: text = "ababa"
Output: 3
Explanation: We can swap the first 'b' with the last 'a', or the last 'b' with the first 'a'.
Then, the longest repeated character substring is "aaa", which its length is 3.

Example 2:
Input: text = "aaabaaa"
Output: 6
Explanation: Swap 'b' with the last 'a' (or the first 'a'), and we get longest repeated character substring "aaaaaa", which its length is 6.

Example 3:
Input: text = "aaabbaaa"
Output: 4

Example 4:
Input: text = "aaaaa"
Output: 5
Explanation: No need to swap, longest repeated character substring is "aaaaa", length is 5.

Example 5:
Input: text = "abcdef"
Output: 1

Constraints:
    1. 1 <= text.length <= 20000
    2. text consist of lowercase English characters only.
 */

/**
 * Approach 1: Get The Index of Each Segment
 * The idea is to keep track of all repeated character segments and see if the neighbor segments (segments separated by one other character) can be merged:
 *  1. There exist a third segment with same character, swap a same character from a third segment. Merged length = len(segment1) + 1 + len(segment2)
 *  2. There does not exist a third segment with same character, swap a character from the first character of the first segment,
 *  or swapping the last character from the second segment, to the separating index. Merged length = len(segment1) + len(segment2)
 *  Otherwise, if there are multiple segments of a character but they are not neighbor, we can only swap a character from one to the other.
 *  New length = len(segment) + 1
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class Solution {
    public int maxRepOpt1(String text) {
        Map<Character, List<int[]>> map = new HashMap<>();
        int left = 0, right = 0, ans = 0;
        while (right < text.length()) {
            while (right < text.length() && text.charAt(left) == text.charAt(right)) {
                right++;
            }
            ans = Math.max(ans, right - left);
            map.computeIfAbsent(text.charAt(left), x -> new ArrayList<>()).add(new int[]{left, right - 1});
            left = right;
        }

        for (Map.Entry<Character, List<int[]>> entry : map.entrySet()) {
            List<int[]> indexes = entry.getValue();
            for (int i = 0; i < indexes.size() - 1; i++) {
                int[] index1 = indexes.get(i), index2 = indexes.get(i + 1);
                int len1 = index1[1] - index1[0] + 1, len2 = index2[1] - index2[0] + 1;
                if (index1[1] + 1 == index2[0] - 1) {
                    ans = Math.max(ans, (indexes.size() > 2 ? 1 : 0) + len1 + len2);
                } else {
                    ans = Math.max(ans, Math.max(len1, len2) + 1);
                }
            }
        }
        return ans;
    }
}

/**
 * Approach 2: Sliding Window
 * 本题是一道比较明显的滑动窗口问题。关键点在于判断窗口什么时候需要进行滑动。
 * 在这里使得窗口不满足条件（无法通过 swap 两个字符形成一个只拥有同一个字符的substring）有两种情况：
 *  1. substring中包含了1个以上的不同字符； eg. "aaabbaaa"
 *  2. substring中字符的个数超过了最大相同字符的个数（此时即使只包含一个不同字符，但是已经没有多余的相同字符可供swap了）
 *  eg. "aaabaaaacc"
 * 其他情况，根据 Approach 1 中的分析，我们中能够获得一个满足条件的 substring.
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int maxRepOpt1(String text) {
        int[] map = new int[128], counts = new int[128];
        char[] chars = text.toCharArray();
        for(char c : chars) {
            counts[c]++;
        }

        // most代表当前串口中出现次数最多的相同字符的出现次数
        int most = 0, ans = 0;
        for(int left = 0, right = 0; right < chars.length;right++){
            most = Math.max(most, ++map[chars[right]]);
            // 如果窗口中包含 1 个以上的不同字符：right-left+1 > most + 1
            // 或者窗口中包含字符的个数已经超过相同字符的全部出现次数：right-left+1 > counts[c]
            // 则窗口不合理，left需要右移
            while(right - left  > most || right - left + 1 > counts[chars[left]]){
                map[chars[left++]]--;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}