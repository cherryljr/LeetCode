/*
Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.

Example :
Input:
S = "abcde"
words = ["a", "bb", "acd", "ace"]
Output: 3
Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".

Note:
All words in words and S will only consists of lowercase letters.
The length of S will be in the range of [1, 50000].
The length of words will be in the range of [1, 5000].
The length of words[i] will be in the range of [1, 50].
 */

/**
 * Approach: Indexing HashMap + BinarySearch
 * 从题目给出的数据来看，答案的算法时间复杂度应该是 O(nlogn) 或者更低。
 * 因此我们如果直接一个个单词进行比对的话时间复杂度是 O(n * (W + S)). n为单词个数，W为单词长度，S为字符串长度。
 * 这无疑是会超时的。
 *
 * 那么首先我们不妨考虑是否存在 O(nlogn) 的解法呢？
 * 因为如果我们一个个字符进行匹配的话，每次Match最差情况下我们都需要遍历整个字符串，这将产生 O(S) 的时间复杂度。
 * 这个操作实在是太浪费了，因此我们不妨对其进行一次预处理：
 *  利用 Map 将每个字符出现的位置存储起来，即进行一次 Indexing HashMap. 这样当我们进行一个字符的匹配时，
 *  我们只需要通过 Map 来获取到其对应出现位置的 List，然后在 List 中进行查找即可。
 *  （如果 Map 中找不到对应的字符，说明该 word 包含 S 中没有的字符，即无法匹配）
 * 又因为当我们进行预处理时是按照顺序进行遍历的，所以每个字符出现位置的 List 是有序的（从小到大）。
 * 因此我们可以想到使用 二分查找 的方法来寻找有没有能够匹配的位置。
 * 而能够匹配的位置必定是在 上一个匹配位置之后 的，所以我们需要使用到 找下界 的方法
 * 查找第一个 大于等于 preIndex + 1 的位置。
 * 如果找到了就更新 preIndex,否则说明无法匹配直接 return false.
 *
 * 时间复杂度：O(nlogn)
 * 
 * PS.这道题目的 Test Case 中的大数据存在着大量重复单词，这就意味着我们只需要使用 Map 对 Words 的结果进行一个 cache.
 * 那么暴力法的时间复杂度为：O((W + S) * UN) UN代表 unique 的单词个数。
 * 结果执行时间比该方法反而更快...大家可以自行尝试。
 * 当然这道题目正确的解法并不是这样，而是使用这里提供的 Indexing HashMap + Binary Search 的方法。
 * 暴力法能过纯粹是 OJ 的 Test Case 不周全。
 */
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        Map<Character, List<Integer>> map = new HashMap<>();
        char[] str = S.toCharArray();
        // 预处理，记录每个字符的出现位置
        for (int i = 0; i < str.length; i++) {
            map.computeIfAbsent(str[i], x -> new ArrayList<>()).add(i);
        }

        int count = 0;
        for (String word : words) {
            if (canMatch(word, map)) {
                count++;
            }
        }
        return count;
    }

    // 判断 word 能否在 S 中进行匹配
    private boolean canMatch(String word, Map<Character, List<Integer>> map) {
        int preIndex = -1;
        for (int i = 0; i < word.length(); i++) {
            // S(map) 中不包含该字符则肯定无法匹配
            if (!map.containsKey(word.charAt(i))) {
                return false;
            }
             // 查找第一个能够匹配的位置并返回
             preIndex = binarySearch(map.get(word.charAt(i)), preIndex + 1);
             if (preIndex == Integer.MAX_VALUE) {
                 return false;
             }
        }
        return true;
    }

    // 查找第一个 >= target 的元素 （找下界方法）
    private int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (target <= list.get(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 注意这里应该返回的应该是匹配字符在字符串中的位置，即list.get(left)
        return left == list.size() ? Integer.MAX_VALUE : list.get(left);
    }
}