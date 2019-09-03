/*
You are given a string, s, and a list of words, words, that are all of the same length.
Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

For example, given:
s: "barfoothefoobarman"
words: ["foo", "bar"]

You should return the indices: [0,9].
(order does not matter).
 */

/**
 * Approach 1: Brute Force
 * 时间复杂度：O(n*m*word.length)
 * 空间复杂度：O(m)
 */
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || words == null || words.length == 0 || s.length() < words.length * words[0].length()) {
            return ans;
        }

        int n = words.length, wordLength = words[0].length();
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (int i = 0; i + n * wordLength <= s.length(); i++) {
            String subString = s.substring(i, i + n * wordLength);
            if (isContain(subString, map, wordLength)) {
                ans.add(i);
            }
        }
        return ans;
    }

    private boolean isContain(String str, Map<String, Integer> map, int wordLength) {
        Map<String, Integer> count = new HashMap<>();
        for (int i = 0; i < str.length(); i += wordLength) {
            String subWord = str.substring(i, i + wordLength);
            count.put(subWord, count.getOrDefault(subWord, 0) + 1);
        }
        return count.equals(map);
    }
}

/**
 * Approach 2: Sliding Window.
 * 基本解法还是在 Sliding Window Template 的基础上面进行修改。
 * 但是相对改动较大，但是主要的思想与编码框架仍然是相同的，详见以下分析。
 *
 * 因为words字符串数组中的字符有可能重复。因此需要使用HashMap - map保存单词以及单词出现的次数。
 *  1. 将字符串数组中的字符串存放在HashMap中，key为单词，value为单词出现的次数
 *  2. 由于单词的长度都是相同的，所以外循环的次数为单词的长度，内循环的次数为字符串s中以单词长度划分之后的次数.
 *  循环条件中，r指向s中要判断的单词的第一个字符，r每次增加一个单词的长度，r最多只能到s中最后一个单词的第一个字符的位置。
 *  3. 在内循环时,借助了一个HashMap - curr来保存窗口中的已经匹配的单词及其出现的次数，
 *  如果当前的单词在 map 中，则每当给该curr中添加一个单词时，会判断其出现的次数是否超过了hm中该单词的出现次数，
 *  如果没超过，则表示匹配单词个数要增加count++, 如果超过则需要缩小窗口。
 *  同样，由于左窗口 left 的那个单词并不一定与当前单词相等，因此也需要从left开始一个循环找到那个次数超了的单词，
 *  同时还需要将循环中的单词的次数在 curr 中减少。直到 map 中该单词的次数与 curr 中该单词的次数相等。
 *  之后需要判断curr中所有的单词个数和count是否与hm中所有的单词的个数和是否相等。
 *  如果相等，表示从 left 到 right 的子串是words中字符串的一个连接，此时需要加入结果集，同时 curr 和 left 需要改变。
 *  如果在内循环中，突然有个单词不在 map 中，则表示从 left 到 right 的串都不符合条件，
 *  则 left = right + 单词长度，同时清空curr和count.
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(m)
 */
class Solution{
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || words == null || words.length == 0 || s.length() < words.length * words[0].length()) {
            return ans;
        }

        // 单词个数
        int n = words.length;
        // 单词长度
        int wordLength = words[0].length();
        Map<String, Integer> map = new HashMap<>();
        // 利用map记录字典中的各个单词以及对应的出现次数以便查询
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i < wordLength; i++) {
            // 记录当前窗口中已经匹配的单词与对应出现的次数
            Map<String, Integer> currMap = new HashMap<>();
            // 记录 currMap 中已经匹配 map/words 中单词的个数
            int count = 0;
            for (int left = i, right = i; right + wordLength <= s.length(); right += wordLength) {
                // 取一个单词
                String str = s.substring(right, right + wordLength);
                // 如果字典中包含该单词
                if (map.containsKey(str)) {
                    currMap.put(str, currMap.getOrDefault(str, 0) + 1);
                    if (currMap.get(str) <= map.get(str)) {
                        count++;
                    }
                    // 如果当前窗口中该单词的出现次数大于map中所记录的次数，则需要缩小窗口
                    while (currMap.get(str) > map.get(str)) {
                        String temp = s.substring(left, left + wordLength);
                        currMap.put(temp, currMap.get(temp) - 1);
                        left += wordLength;
                        if (currMap.get(temp) < map.get(temp)) {
                            count--;
                        }
                    }
                    if (count == n) {
                        ans.add(left);
                        String temp = s.substring(left, left + wordLength);
                        currMap.put(temp, currMap.get(temp) - 1);
                        left += wordLength;
                        count--;
                    }
                } else {
                    // 如果字典中不包含该单词，则直接跳到下一个单词
                    currMap.clear();
                    count = 0;
                    left = right + wordLength;
                }
            }
        }
        return ans;
    }
}
