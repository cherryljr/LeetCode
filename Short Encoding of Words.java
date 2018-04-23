/*
Given a list of words, we may encode it by writing a reference string S and a list of indexes A.
For example, if the list of words is ["time", "me", "bell"], we can write it as S = "time#bell#" and indexes = [0, 2, 5].
Then for each index, we will recover the word by reading from the reference string from that index until we reach a "#" character.
What is the length of the shortest reference string S possible that encodes the given words?

Example:
Input: words = ["time", "me", "bell"]
Output: 10
Explanation: S = "time#bell#" and indexes = [0, 2, 5].

Note:
1 <= words.length <= 2000.
1 <= words[i].length <= 7.
Each word has only lowercase letters.
 */

/**
 * Approach 1: Sorting + SubString Compare
 * 我们发现当 一个单词words[i] 与另外一个单词的words[j] 结尾部分 完全匹配的时候，
 * 这个单词就可以被压缩。即题目中的 "me" 直接被"压缩编码"掉了。
 * 而关于子串的匹配搜索，最快的肯定就是 KMP 了。
 * 但是这里我们其实只是需要找一下是否存在以 words[i] 作为结尾的单词罢了...
 * 所以完全不需要自己去写一个 KMP，直接用现成的 lastIndexOf() 即可。（况且在比赛啊，脑子抽了会自己手撸）
 *
 * 本题注意点：
 * 1. 因为涉及到 字符子串匹配(后缀匹配) 问题。所以我们最好对其 长度 进行排序，这样可以节省不少时间。
 * 如：未排序的情况下，对于每一个 words[i] 我们都要在整个 words 中进行匹配查找。 O(n^2)
 * 如果进行了排序的话，对于每一个 words[i] 我们只需要对其前面的单词进行匹配即可，节省了一半的时间。
 * 因为后面的要么长度与 words[i] 相等，要么更小，没有比较的意义。 O(n^2 / 2)
 * 2. 对于 相同的单词，我们该如何处理？比如：{"time", "time", "time", "time"} 输出结果: 5
 * 因为我们已经按照长度排序好了，且对于相同的单词，他们一定会被放在一起（长度相等时，按照字符串排序）
 * 这里是否将他们放在一起其实并不会产生影响，这么做只是为了看上去容易理解罢了。
 * 当 重复单词 第一次被遍历到时，因为之前没有相同的（这里我们假设 words 中不存在以它作为结尾的单词）
 * 那么 len 就会加上 重复单词的长度+1，当第二次遍历到 重复单词 的时候，因为之前已经遇到过了，
 * 所以必定可以在前面的单词中找到 另一个已经出现过的重复单词。因此 len 保持不变。
 * 
 * 可见 排序 对于字符串的 子串匹配 问题有着非常大的用处
 * （除非题目要求不能排序，否则对于此类问题，我们最好进行一次 长度 排序）
 * 
 * 时间复杂度：O(n^2*k) k为单词长度
 * 空间复杂度：O(1)
 */
class Solution {
    public int minimumLengthEncoding(String[] words) {
        int len = 0;
        // 首先对 words 依据 长度 进行排序，以加快我们的查询速度
        Arrays.sort(words, (word1, word2) -> word2.length() - word1.length() == 0
                ? word1.compareTo(word2) : word2.length() - word1.length());
        for (int i = 0; i < words.length; i++) {
            boolean canMatch = false;
            // 因为 words 已经排序过了，所以我们只需要对比 i 之前的单词即可
            for (int j = 0; j < i; j++) {
                // 如果 word[j] 以 word[i] 作为结尾，那么说明 word[i] 可以被 encode.
                if (words[j].lastIndexOf(words[i]) == words[j].length() - words[i].length()) {
                    canMatch = true;
                    break;
                }
            }
            // 如果当前无法找到以 word[i] 作为结尾的单词，则 len 加上 word[i].length + 1
            if (!canMatch) {
                len += words[i].length() + 1;
            }
        }

        return len;
    }
}

/**
 * Approach 2: Remove Suffixes (Store Prefixes)
 * 由 Approach 1 中分析可得，当一个单词 X 是另外一个单词Y的 后缀 的话，那么 X 就可以被"压缩编码"掉。
 * 即 X 的长度对于编码长度 len 没有丝毫影响。
 * 而如果 X 并不是 words 中任意单词的 后缀 的话，那么编码长度 len 就需要加上 X的长度+1.
 * 因此我们的目标就是：
 *  移除 words 中所有的后缀单词，使得其中没有单词是另外一个单词的后缀。
 * 那么最终结果就是：移除后缀单词后的 words 中 所有单词长度+1 之和就是我们需要的答案。
 * 因为单词长度为 1~7，并不算长，所以我们可以遍历每一个单词的 所有后缀。
 * 然后将其从 words 中移除即可。
 * 而面对 Approach 1 中讨论到的 相同重复单词 这个问题，我们可以使用 Set 进行去重操作。
 *
 * 时间复杂度：O(n*k^2)
 * 空间复杂度：O(k^2) 每个单词的所有 suffixes (由 subString() 所产生)
 */
class Solution {
    public int minimumLengthEncoding(String[] words) {
    	// 不仅能够进行去重，而且还能够提高 remove() 的速度
        Set<String> wordSet = new HashSet<>(Arrays.asList(words));
        for (String word : words) {
            // 遍历 word 的所有后缀，并从 wordSet 中将其移除
            for (int k = 1; k < word.length(); k++) {
                wordSet.remove(word.substring(k));
            }
        }

        int len = 0;
        for (String word : wordSet) {
            len += word.length() + 1;
        }
        return len;
    }
}

/**
 * Approach 3: Trie
 * 我们这里的目标是找后缀，相应的，如果我们把单词 逆序 过来的话，
 * 就变成找 前缀 了。而对于前缀而言，我们非常熟悉的就是 前缀树（Trie Tree）了 。
 * 因此我们可以将一个单词，倒着插入到树中。
 * 如 "time", "me" 就变成 "emit", "em".
 * 这样利用 Trie Tree 帮我们去除了 后缀单词。
 * 因此其所有叶子节点的 单词长度+1 之和，就是我们需要的答案了。
 */
class Solution {
    public int minimumLengthEncoding(String[] words) {
        TrieNode trie = new TrieNode();
        Map<TrieNode, Integer> nodes = new HashMap<>();

        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            TrieNode cur = trie;
            for (int j = word.length() - 1; j >= 0; j++) {
                cur = cur.get(word.charAt(j));
            }
            nodes.put(cur, i);
        }

        int rst = 0;
        for (TrieNode node: nodes.keySet()) {
            if (node.count == 0)
                rst += words[nodes.get(node)].length() + 1;
        }
        return rst;
    }
}

class TrieNode {
    TrieNode[] children;
    int count;

    TrieNode() {
        children = new TrieNode[26];
        count = 0;
    }

    public TrieNode get(char c) {
        if (children[c-'a'] == null) {
            children[c-'a'] = new TrieNode();
            count++;
        }
        return children[c - 'a'];
    }
}
