/*
Given a list of strings words representing an English Dictionary,
find the longest word in words that can be built one character at a time by other words in words.
If there is more than one possible answer, return the longest word with the smallest lexicographical order.
If there is no answer, return the empty string.

Example 1:
    Input:
    words = ["w","wo","wor","worl", "world"]
    Output: "world"
Explanation:
    The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".

Example 2:
    Input:
    words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
    Output: "apple"
Explanation:
    Both "apply" and "apple" can be built from other words in the dictionary.
    However, "apple" is lexicographically smaller than "apply".

Note:
All the strings in the input will only contain lowercase letters.
The length of words will be in the range [1, 1000].
The length of words[i] will be in the range [1, 30].
*/

/**
 * Approach 1: HashSet + Pruning (Brute Force)
 * Algorithm
 * For each word, check if all prefixes word[:k] are present. We can use a Set structure to check this quickly.
 * Whenever our found word would be superior, we check if all it's prefixes are present, then replace our answer.
 * Of course, if the current word.length is smaller than best answer(rst) or the length is the same,
 * but the current word has bigger lexicographical order, we will skip it. (Pruning)
 *
 * Complexity Analysis
 * Time complexity : O(∑w_i^2), where w_i is the length of words[i].
 * Checking whether all prefixes of words[i] are in the set is O(∑w​i​^2).
 * Space complexity : O(∑w_i^2) to create the substrings.
 */
class Solution {
    public String longestWord(String[] words) {
        Set<String> wordset = new HashSet<>();
        for (String word : words) {
            wordset.add(word);
        }

        String rst = "";
        for (String word : words) {
            // Pruning
            if (word.length() < rst.length()
                    || (word.length() == rst.length() && word.compareTo(rst) > 0)) {
                continue;
            }

            // Check all prefixes of word in wordSet or not
            StringBuilder prefix = new StringBuilder();
            boolean isValid = true;
            for (int i = 0; i < word.length() - 1; i++) {
                prefix.append(word.charAt(i));
                if (!wordset.contains(prefix.toString())) {
                    isValid = false;
                    break;
                }
            }
            // Update the best answer if the word is valid
            if (isValid) {
                rst = word;
            }
        }

        return rst;
    }
}

/**
 * Approach 2: Trie + DFS
 * Algorithm
 * As prefixes of strings are involved, this is usually a natural fit for a trie (a prefix tree.)
 * Put every word in a trie, then we can check the word's prefixes in the trie or not quickly,
 * Every node found (except the root, which is a special case) then represents a word with all it's prefixes present.
 * We take the best such word.
 * Of course, we should also do pruning in this method.
 *
 * Complexity Analysis
 *  Time Complexity: O(∑w_i), where w_i is the length of words[i].
 *  This is the complexity to build the trie and to search it.
 * Space Complexity: O(26*∑w_i), the space used by our trie.
 */
class Solution {
    public String longestWord(String[] words) {
        Trie trie = new Trie();
        // Build the trie
        for (String word : words) {
            trie.insert(word);
        }

        String rst = "";
        for (String word : words) {
            // Pruning
            if (word.length() < rst.length()
                    || (word.length() == rst.length() && word.compareTo(rst) > 0)) {
                continue;
            }
            // Check the prefixes
            if (trie.hasAllPrefixes(word)) {
                rst = word;
            }
        }
        return rst;
    }

    class TrieNode {
        TrieNode[] child;
        boolean isWord;

        TrieNode() {
            this.child = new TrieNode[26];
            this.isWord = false;
        }
    }

    class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            // if (word == null || word.length() == 0) {
            //     return;
            // }
            TrieNode currNode = root;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    currNode.child[index] = new TrieNode();
                }
                currNode = currNode.child[index];
            }
            currNode.isWord = true;
        }

        public boolean hasAllPrefixes(String word) {
            // if (word == null || word.length() == 0) {
            //     return true;
            // }
            TrieNode currNode = root;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length - 1; i++) {
                int index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    return false;
                }
                currNode = currNode.child[index];
                if (!currNode.isWord) {
                    return false;
                }
            }
            return true;
        }
    }
}