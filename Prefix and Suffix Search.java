/*
Given many words, words[i] has weight i.
Design a class WordFilter that supports one function, WordFilter.f(String prefix, String suffix).
It will return the word with given prefix and suffix with maximum weight. If no word exists, return -1.

Examples:
Input:
WordFilter(["apple"])
WordFilter.f("a", "e") // returns 0
WordFilter.f("b", "") // returns -1

Note:
words has length in range [1, 15000].
For each test case, up to words.length queries WordFilter.f may be made.
words[i] has length in range [1, 10].
prefix, suffix have lengths in range [0, 10].
words[i] and prefix, suffix queries consist of lowercase letters only.
 */

/**
 * Approach 1: HashMap
 * 根据题目给出的数据量：数组长度最长有 15000.而查询的次数最多也有这么多次。
 * 因此我们可以知道一次查询的时间必定要 < N,否则就会超时。
 * 对此可以想到有两种解决方案：
 *  1. 用 HashMap 保存下每个单词所有的前后缀的情况已经对应的权重 index.
 *  这样我们就能够在 O(1) 的时间内查询到结果了。
 *  2. 使用 Trie 建立字典树（先不说后缀，看到前缀，又需要查找第一反应就应该是字典树）。
 *  这个方法可以在 O(k) 的情况下查询到结果。k为单词长度。
 *  
 * 详细解析参考注释。
 *
 * 枚举一个单词的所有前后缀组合需要 O(k^2), 将其拼起来需要 O(k).
 * 因此总体时间复杂度为：O(n*k^3)
 */
class WordFilter {
    Map<String, Integer> map = new HashMap<>();

    public WordFilter(String[] words) {
        int index = 0;
        for (String word : words) {
            int len = word.length();
            String[] prefixes = new String[len + 1];
            String[] suffixes = new String[len + 1];
            // 计算该单词对应的所有前后缀
            prefixes[0] = "";
            suffixes[0] = "";
            for (int i = 1; i <= len; i++) {
                prefixes[i] = prefixes[i - 1] + word.charAt(i - 1);
                suffixes[i] = word.charAt(len - i) + suffixes[i - 1];
            }
            // 将前后缀所有可能的组合与对应的 index 记录到 map 以供查询
            for (String prefix : prefixes) {
                for (String suffix : suffixes) {
                    map.put(prefix + "_" + suffix, index);
                }
            }
            index++;
        }
    }

    public int f(String prefix, String suffix) {
        String key = prefix + "_" + suffix;
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return -1;
        }
    }
}

/**
 * Approach 2: Trie
 * Trie Tree是直接从模板拿过来的，稍微改改就能用了(*^_^*)
 * 说下主要改了哪里以及为什么。这也就是本题的做法了。
 *  1. 去除了 TrieNode 中的 end 参数，以及 Trie 中的 search() 方法
 *  因为在本题中，这两个东西并没有任何用处。
 *  2. 将原来的 path 修改为 rank.代表当前字符串的权值，对应插入时的 index.
 *  3. 因为 Trie 是用于表示前缀树的，对于后缀我们应该如何处理呢？
 *  我们可以使用拼接的方案，将一个单词所有的 suffix 拼接到该单词前面。
 *  然后插入到 Trie 中去，并存储相应的 index 信息。
 *  而对于分割符的选择，这里使用了 '{' 符号，因为其 ASCII 码正好是 98.
 *  即在 'z' 之后一位，所以我们只需要把 TrieNode 的数组大小改为 27 即可。
 *  不需要过多的代码修改。这也是本题最重要的地方。
 *  
 * 时间复杂度：O(n*k^2) k为单词长度
 *
 * Trie Tree Template:
 *  https://github.com/cherryljr/LintCode/blob/master/Trie%20Tree%20Template.java
 * 参考资料(C++)：
 *  http://zxi.mytechroad.com/blog/tree/leetcode-745-prefix-and-suffix-search/
 */
class WordFilter {
    Trie trie;

    public WordFilter(String[] words) {
        trie = new Trie();
        for (int i = 0; i < words.length; i++) {
            // 遍历所有的后缀可能，并将其与该单词拼接起来，插入到 Trie 中
            StringBuilder key = new StringBuilder("{").append(words[i]);
            trie.insert(key.toString(), i);
            for (int j = 0; j < words[i].length(); j++) {
                key.insert(0, words[i].charAt(words[i].length() - j - 1));
                trie.insert(key.toString(), i);
            }
        }
    }

    public int f(String prefix, String suffix) {
        String key = suffix + '{' + prefix;
        return trie.prefixNumber(key);
    }

    class TrieNode {
        public TrieNode[] child;
        public int rank;

        public TrieNode() {
            this.child = new TrieNode[27];
            this.rank = -1;
        }
    }

    class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word, int rank) {
            if (word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            TrieNode currNode = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    currNode.child[index] = new TrieNode();
                }
                currNode = currNode.child[index];
                currNode.rank = rank;
            }
        }

        public int prefixNumber(String prefix) {
            if (prefix == null) {
                return 0;
            }
            char[] chars = prefix.toCharArray();
            TrieNode currNode = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    return -1;
                }
                currNode = currNode.child[index];
            }
            return currNode.rank;
        }
    }
}

/**
 * Your WordFilter object will be instantiated and called as such:
 * WordFilter obj = new WordFilter(words);
 * int param_1 = obj.f(prefix,suffix);
 */