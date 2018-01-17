/*
Implement a magic directory with buildDict, and search methods.
For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary.
For the method search, you'll be given a word, and judge whether if you modify exactly one character into another character in this word,
the modified word is in the dictionary you just built.

Example 1:
Input: buildDict(["hello", "leetcode"]), Output: Null
Input: search("hello"), Output: False
Input: search("hhllo"), Output: True
Input: search("hell"), Output: False
Input: search("leetcoded"), Output: False

Note:
You may assume that all the inputs are consist of lowercase letters a-z.
For contest purpose, the test data is rather small by now. You could think about highly efficient algorithm after the contest.
Please remember to RESET your class variables declared in class MagicDictionary,
as static/class variables are persisted across multiple test cases. Please see here for more details.
 */

/**
 * Approach 1: Brute Force with Bucket By Length
 * Algorithm
 * Call two strings neighbors if exactly one character can be changed in one to make the strings equal
 * (ie. their hamming distance is 1.)
 * Strings can only be neighbors if their lengths are equal.
 * When searching a new word, let's check only the words that are the same length.
 * 
 * Complexity Analysis
 *  Time Complexity: O(S) to build and O(NK) to search, 
 *  where N is the number of words in our magic dictionary, S is the total number of letters in it, 
 *  and K is the length of the search word.
 *  Space Complexity: O(S), the space used by buckets.
 */
class MagicDictionary {
    Map<Integer, List<String>> bucket;

    /** Initialize your data structure here. */
    public MagicDictionary() {
        bucket = new HashMap<>();
    }

    /** Build a dictionary through a list of words */
    public void buildDict(String[] dict) {
        for (String word : dict) {
            bucket.computeIfAbsent(word.length(), x -> new ArrayList<>()).add(word);
        }
    }

    /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
    public boolean search(String word) {
        if (!bucket.containsKey(word.length())) {
            return false;
        }

        for (String s : bucket.get(word.length())) {
            int misMatch = 0;
            for (int i = 0; i < word.length(); i++) {
                if (s.charAt(i) != word.charAt(i)) {
                    if (++misMatch > 1) {
                        break;
                    }
                }
            }
            if (misMatch == 1) {
                return true;
            }
        }

        return false;
    }
}

/**
 * Your MagicDictionary object will be instantiated and called as such:
 * MagicDictionary obj = new MagicDictionary();
 * obj.buildDict(dict);
 * boolean param_2 = obj.search(word);
 */
 
 /**
 * Approach 2: Generalized Neighbors
 * Intuition
 * Recall in Approach 1 that two words are neighbors if exactly one character can be changed in one word to make the strings equal.
 * Let's say a word 'apple' has generalized neighbors '*pple', 'a*ple', 'ap*le', 'app*e', and 'appl*'.
 * When searching for whether a word like 'apply' has a neighbor like 'apple',
 * we only need to know whether they have a common generalized neighbor.
 *
 * Algorithm
 * Continuing the above thinking, one issue is that 'apply' is not a neighbor with itself,
 * yet it has the same generalized neighbor '*pply'. To remedy this, we'll count how many sources generated '*pply'.
 * If there are 2 or more, then one of them won't be 'apply'.
 * If there is exactly one, we should check that it wasn't 'apply'.
 * In either case, we can be sure that there was some magic word generating '*pply' that wasn't 'apply'.
 * 
 * Complexity Analysis
 *  Time Complexity: O(∑w​_i^2) to build and O(K^2) to search, 
 *  where w_i is the length of words[i], and K is the length of our search word.
 *  Space Complexity: O(∑w​_i^2) the space used by count. We also use O(K^2) space when generating neighbors to search.
 */
class MagicDictionary {
    Set<String> words;
    Map<String, Integer> count;

    /** Initialize your data structure here. */
    public MagicDictionary() {
        words = new HashSet();
        count = new HashMap();
    }

    /** Generte the neighbors of the word **/
    private ArrayList<String> generalizedNeighbors(String word) {
        ArrayList<String> ans = new ArrayList();
        char[] ca = word.toCharArray();
        for (int i = 0; i < word.length(); ++i) {
            char letter = ca[i];
            ca[i] = '*';
            String magic = new String(ca);
            ans.add(magic);
            ca[i] = letter;
        }
        return ans;
    }
    
    /** Build a dictionary through a list of words */
    public void buildDict(String[] dict) {
        for (String word: dict) {
            this.words.add(word);
            for (String nei: generalizedNeighbors(word)) {
                count.put(nei, count.getOrDefault(nei, 0) + 1);
            }
        }
    }
    
    /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
    public boolean search(String word) {
        for (String nei: generalizedNeighbors(word)) {
            int c = count.getOrDefault(nei, 0);
            if (c > 1 || c == 1 && !words.contains(word)) {
                return true;
            }
        }
        return false;
    }
}