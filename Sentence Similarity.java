/*
Given two sentences words1, words2 (each represented as an array of strings),
and a list of similar word pairs pairs, determine if two sentences are similar.

For example, "great acting skills" and "fine drama talent" are similar,
if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].

Note that the similarity relation is not transitive. For example,
if "great" and "fine" are similar, and "fine" and "good" are similar, "great" and "good" are not necessarily similar.

However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.
Also, a word is always similar with itself. For example,
the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar,
even though there are no specified similar word pairs.

Finally, sentences can only be similar if they have the same number of words.
So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

Note:
1.The length of words1 and words2 will not exceed 1000.
2.The length of pairs will not exceed 2000.
3.The length of each pairs[i] will be 2.
4.The length of each words[i] and pairs[i][j] will be in the range [1, 20].
 */

/**
 * Approach: HashMap
 * 题目大意：
 * 给你两个句子（由单词数组表示）和一些近义词对，问你这两个句子是否相似，即每组相对应的单词都要相似。
 * 注意相似性不可以传递，比如只给你”great”和”fine”相似、”fine”和”good”相似，不能推断”great”和”good”相似。
 *
 * 做法：
 * 使用 HashMap 根据 pairs 建图即可。（本题为无向图）
 * 然后遍历其中的一个句子，对于每个单词在建立好的关系图中查找即可。
 *
 * 本题还有一个 Fellow Up，唯一区别在于相似具有传递性。
 * 对此我们可以通过 并查集 来解决。太过典型，这里就不详细说明了。
 * 因为是字符串，所以使用 Map<String, String> 来实现 Union Find 即可。
 * 可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Find%20the%20Weak%20Connected%20Component%20in%20the%20Directed%20Graph.java
 */
public class Solution {
    /**
     * @param words1: a list of string
     * @param words2: a list of string
     * @param pairs: a list of string pairs
     * @return: return a boolean, denote whether two sentences are similar or not
     */
    public boolean isSentenceSimilarity(String[] words1, String[] words2, List<List<String>> pairs) {
        if ((words1 == null && words2 == null) && (words1.length == 0 && words2.length == 0)) {
            return true;
        }
        if (words1.length != words2.length) {
            return false;
        }

        Map<String, Set<String>> graph = new HashMap<>();
        for (List<String> pair : pairs) {
            graph.computeIfAbsent(pair.get(0), x -> new HashSet<>()).add(pair.get(1));
            graph.computeIfAbsent(pair.get(1), x -> new HashSet<>()).add(pair.get(0));
        }

        for (int i = 0; i < words1.length; i++) {
            if (words1[i].equals(words2[i])) {
                continue;
            }
            if (!graph.containsKey(words1[i]) || !graph.get(words1[i]).contains(words2[i])) {
                return false;
            }
        }

        return true;
    }
}