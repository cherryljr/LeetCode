/*
Implement the StreamChecker class as follows:
StreamChecker(words): Constructor, init the data structure with the given words.
query(letter): returns true if and only if for some k >= 1,
the last k characters queried (in order from oldest to newest,
including this letter just queried) spell one of the words in the given list.

Example:
StreamChecker streamChecker = new StreamChecker(["cd","f","kl"]); // init the dictionary.
streamChecker.query('a');          // return false
streamChecker.query('b');          // return false
streamChecker.query('c');          // return false
streamChecker.query('d');          // return true, because 'cd' is in the wordlist
streamChecker.query('e');          // return false
streamChecker.query('f');          // return true, because 'f' is in the wordlist
streamChecker.query('g');          // return false
streamChecker.query('h');          // return false
streamChecker.query('i');          // return false
streamChecker.query('j');          // return false
streamChecker.query('k');          // return false
streamChecker.query('l');          // return true, because 'kl' is in the wordlist

Note:
    1. 1 <= words.length <= 2000
    2. 1 <= words[i].length <= 2000
    3. Words will only consist of lowercase English letters.
    4. Queries will only consist of lowercase English letters.
    5. The number of queries is at most 40000.
 */

/**
 * Approach: Reverse Word and Search + Trie
 * 这个问题在大数据流处理的实际生产环境中十分常见。
 * 因为 数据量多 且 请求查询量巨大 ，所以需要使用到 字典树（Trie）这个数据结构来保存和查询数据。
 * 题目的要求为：查询是否存在以当前字符作为结尾的 substring.
 * 对此，我们在构造 字典树 的时候，需要将单词进行 逆序 存储。
 * 然后在数据流查询的时候，同样进行逆序查询即可达到目的。
 *
 * 时间复杂度：O(QW) Q代表查询的次数，W代表当前数据流的长度
 * 空间复杂度：O(W) W代表数据流长度，因为使用了 Trie, 所以空间耗费不会大于 O(W)
 */
class StreamChecker {

    TrieNode root = new TrieNode();
    // 利用 StringBuilder 来存储数据流
    StringBuilder str = new StringBuilder();

    class TrieNode {
        TrieNode[] child;
        boolean isWord;

        TrieNode() {
            this.child = new TrieNode[26];
            this.isWord = false;
        }
    }

    public StreamChecker(String[] words) {
        for (String word : words) {
            insert(word);
        }
    }

    // 因为单词是逆序保存的，所以对数据流从最后一个字符开始进行逆序查询即可
    // 判断是否存在以当前字符作为结尾的 substring
    public boolean query(char letter) {
        str.append(letter);
        return search(str);
    }

    // 将单词进行逆序保存，如 "abc" 将被保存为 "cba"
    private void insert(String word) {
        TrieNode currNode = root;
        for (int i = word.length() - 1; i >= 0; i--) {
            int index = word.charAt(i) - 'a';
            if (currNode.child[index] == null) {
                currNode.child[index] = new TrieNode();
            }
            currNode = currNode.child[index];
        }
        currNode.isWord = true;
    }

    private boolean search(StringBuilder word) {
        TrieNode currNode = root;
        for (int i = word.length() - 1; i >= 0; i--) {
            int index = word.charAt(i) - 'a';
            if (currNode.child[index] == null) {
                return false;
            } else if (currNode.child[index].isWord) {
                return true;
            }
            currNode = currNode.child[index];
        }
        return currNode.isWord;
    }
}

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker obj = new StreamChecker(words);
 * boolean param_1 = obj.query(letter);
 */