/*
Given a non-empty list of words, return the k most frequent elements.
Your answer should be sorted by frequency from highest to lowest. 
If two words have the same frequency, then the word with the lower alphabetical order comes first.

Example 1:
Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
Output: ["i", "love"]
Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.
    
Example 2:
Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
Output: ["the", "is", "sunny", "day"]
Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
    with the number of occurrence being 4, 3, 2 and 1 respectively.
    
Note:
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Input words contain only lowercase letters.

Follow up:
Try to solve it in O(n log k) time and O(n) extra space.
*/

/**
 * Approach 1: Sorting
 * Count the frequency of each word, and sort the words with a custom ordering relation that uses these frequencies.
 * Then take the best k of them.
 * custom order: descend the frequency of each words.
 * If two words have the same frequency, then the word with the lower alphabetical order comes first.
 *
 * Complexity Analysis
 * Time Complexity: O(NlogN), where N is the length of words.
 * We count the frequency of each word in O(N) time, then we sort the given words in O(NlogN) time.
 *Space Complexity: O(N), the space used to store our candidates.
 */
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        if (words == null || words.length == 0) {
            return new ArrayList<String>();
        }

        Map<String, Integer> map = new HashMap();
        for (String s: words) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        List<String> candidates = new ArrayList(map.keySet());
        Collections.sort(candidates, (w1, w2) -> map.get(w1) != map.get(w2) ?
                map.get(w2) - map.get(w1) : w1.compareTo(w2));

        return candidates.subList(0, k);
    }
}

/**
 * Approach 2: HashMap and PriorityQueue (maxHeap)
 * This Approach is the same as -- Top K Frequent Elements' Approach 1.
 * Count the frequency of each word, then add it to heap that stores the best k candidates.
 * At last, we poll the results and add them to the rst list.
 *
 * Complexity Analysis
 * Time Complexity: O(Nlogk), where N is the length of words.
 * We count the frequency of each word in O(N) time,
 * then we add N words to the heap, each in O(logk) time.
 * Finally, we pop from the heap up to k times. As k≤N, this is O(Nlogk) in total.
 * Space Complexity: O(N), the space used to store our count.
 */
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> rst = new ArrayList<>();
        if (words == null || words.length == 0) {
            return rst;
        }

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s : words) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(k,
                (a, b) -> a.getValue() != b.getValue() ? b.getValue() - a.getValue()
                        : a.getKey().compareTo(b.getKey()));
        for (Map.Entry<String, Integer> set : map.entrySet()) {
            maxHeap.add(set);
        }

        while (k > 0) {
            rst.add(maxHeap.poll().getKey());
            k--;
        }
        return rst;
    }
}

/**
 * Approach 3: Bucket Sort
 * This problem is quite similar to the problem Top K Frequent Elements.
 * You can refer to the solution of the problem.
 *
 * We can solve this problem with the similar idea:
 * Firstly, we need to calculate the frequency of each word and store the result in a HashMap.
 * Secondly, we will use bucket sort to store words.
 * Why? Because the minimum frequency is greater than or equal to 1 and the maximum frequency
 * is less than or equal to the length of the input string array.
 * Thirdly, we should sort the Strings in the same bucket[i] to satisfied the demand:
 * If two words have the same frequency, then the word with the lower alphabetical order comes first.
 *
 * From the above analysis, we can see the time complexity is O(nlogn).
 * Because we need to sort the strings in the same bucket.
 *
 * But we can optimize this method by using data structure - trie.
 * With Trie, it ensures that the lower alphabetical word will be met first,
 * saving the trouble to sort the words within the bucket.
 * So the time complexity will be optimized to O(n) and Space Complexity is: O(n).
 * About how to optimize it, you can get from here:
 * https://discuss.leetcode.com/topic/107069/java-o-n-solution-using-hashmap-bucketsort-and-trie-22ms-beat-81/2
 */
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        if (words == null || words.length == 0) {
            return new ArrayList<String>();
        }

        Map<String, Integer> map = new HashMap();
        for (String s: words) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        List<String>[] bucket = new List[words.length + 1];
        for (Map.Entry<String, Integer> set : map.entrySet()) {
            int frequecny = set.getValue();
            if (bucket[frequecny] == null) {
                bucket[frequecny] = new ArrayList<>();
            }
            bucket[frequecny].add(set.getKey());
        }

        List<String> rst = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0; i--) {
            if (bucket[i] != null) {
                // Sort the string to meeting demand.
                Collections.sort(bucket[i]);
                // We can use addAll method to avoid adding too much elements (exceeding k).
                for (int j = 0; j < bucket[i].size() && rst.size() < k; j++) {
                    rst.add(bucket[i].get(j));
                }
            }
        }
        return rst;
    }
}