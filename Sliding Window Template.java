/**
 * There are so many substring search problems which could be solved by the sliding window algorithm.
 * So I sum up the algorithm template here. wish it will help you!
 *
 * The similar questions are:
 * Problem : https://leetcode.com/problems/find-all-anagrams-in-a-string/
 * Solution:
 * Problem : https://leetcode.com/problems/permutation-in-string/description/
 * Solution:
 * Problem : https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * Solution:
 * Problem : https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
 * Solution:
 * Problem : https://leetcode.com/problems/minimum-window-substring/
 * Solution:
 * Problem : https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 * Solution:
 */

public class Solution {
    public List<Integer> slidingWindowTemplate(String s, String t) {
        // Init a collection or int value to save the result according the question.
        List<Integer> result = new LinkedList<>();
        if (t.length() > s.length()) {
            return result;
        }

        // Create a hashmap to save the Characters of the target substring.
        // (K, V) = (Character, Frequency of the Characters)
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // Maintain a counter to check whether match the target string.
        // Must be the map size, NOT the string size because the char may be duplicate.
        int counter = map.size();

        // Two Pointers: begin - left pointer of the window; end - right pointer of the window
        int begin = 0, end = 0;

        // The length of the substring which match the target string.
        int len = Integer.MAX_VALUE;

        // Loop at the beginning of the source string
        while (end < s.length()) {
            // Get a character
            char c = s.charAt(end);

            if (map.containsKey(c)) {
                // Plus or minus one
                map.put(c, map.get(c) - 1);
                // Modify the counter according the requirement (different condition).
                if (map.get(c) == 0) {
                    counter--;
                }
            }
            end++;

            // Increase begin pointer to make it invalid/valid again
            while (counter == 0 /* counter condition. different question may have different condition */) {
                // Be careful here: choose the char at begin pointer, NOT the end pointer
                char tempc = s.charAt(begin);
                if (map.containsKey(tempc)) {
                    // Plus or minus one
                    map.put(tempc, map.get(tempc) + 1);
                    if (map.get(tempc) > 0) {
                        // Modify the counter according the requirement (different condition).
                        counter++;
                    }
                }

                // save / update(min/max) the result if find a target
                // result collections or result int value

                begin++;
            }
        }
        return result;
    }
}

