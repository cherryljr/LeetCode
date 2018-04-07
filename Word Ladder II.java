/*
Given two words (beginWord and endWord), and a dictionary's word list, 
find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

For example,
Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
  ]
  
Note:
Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.

UPDATE (2017/1/20):
The wordList parameter had been changed to a list of strings (instead of a set of strings).
Please reload the code definition to get the latest changes.
 */

/**
 * Approach: BFS + DFS
 * 答案要求最短路径，因此我们想到了使用 BFS，
 * 但是又需要我们输出所有路径，因此我们还需要使用 DFS 进行遍历。
 *
 * 具体的做法为：
 * 先使用 BFS 搜索出 beginWord 到 endWord 之间最短的路径。
 * 并在搜索的过程中建立好 关系图，即每个结点对应的下一层节点（邻居）分别为哪些。
 * 同时为了在 DFS 时，能够知道遍历的是否是最短路径，我们还需要保存各个 单词节点 到 beginWord 的距离。
 * 有了以上信息之后，我们再使用 DFS 从 beginWord 开始
 * 遍历由 BFS 建立好的关系图，得到所有的最短路径即可。
 *
 * 不少人会问，为什么我们需要使用到 BFS 呢？直接使用 DFS 枚举不好吗？
 * 原因是，直接使用 DFS 暴力查找的话会耗费掉许多时间。
 * 比如 beginWord 和 endWord 之间距离只有 3，但是整张图非常大（两个点距离有1000的那种），
 * 这个时候如果使用 DFS，毫无疑问会超时。
 * 而如果实现使用 BFS 对图进行处理，构建出我们需要的那个部分，然后再 DFS 就可以节省大量的时间了。
 * 
 * 本题还有一种更加快速的解法：双向 BFS
 * https://leetcode.com/problems/word-ladder-ii/discuss/40477/Super-fast-Java-solution-(two-end-BFS)
 */
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }
        wordSet.add(beginWord);

        // 记录一个单词 BFS 的下一层节点
        Map<String, List<String>> neighbors = new HashMap<>();
        // 记录该单词与 beginWord 之间的距离，同时也充当了 visitedSet 的作用
        Map<String, Integer> distance = new HashMap<>();
        List<List<String>> rst = new ArrayList<>();

        bfs(beginWord, endWord, wordSet, neighbors, distance);
        dfs(beginWord, endWord, neighbors, distance, new LinkedList<>(), rst);

        return rst;
    }

    // 利用 bfs 找到最短路径，并构建出关系图
    private void bfs(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> neighbors, Map<String, Integer> distance) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        distance.put(beginWord, 0);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                // 遇到 endWord 就退出，其他的信息我们已经不需要了
                if (curr.equals(endWord)) {
                    break;
                }

                List<String> currNeig = getNeighbors(curr, wordSet);
                Iterator<String> iterator = currNeig.iterator();
                while (iterator.hasNext()) {
                    String word = iterator.next();
                    // 如果之前没有遍历过该单词，则将其加入到队列中，并记录 distance 信息
                    if (!distance.containsKey(word)) {
                        queue.offer(word);
                        distance.put(word, distance.get(curr) + 1);
                    }
                }
                // 记录当前单词节点对应的 下一级节点的信息（邻居信息）
                neighbors.put(curr, currNeig);
            }
        }
    }

    // 遍历获取邻居节点，与 Word Ladder 中的方法相同
    private List<String> getNeighbors(String curr, Set<String> wordSet) {
        List<String> neighbors = new LinkedList<>();
        char[] chars = curr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char old = chars[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == old) {
                    continue;
                }
                chars[i] = ch;
                String nextWord = String.valueOf(chars);
                if (wordSet.contains(nextWord)) {
                    neighbors.add(nextWord);
                }
            }
            chars[i] = old;
        }
        return neighbors;
    }

    // dfs 遍历求解所有路径
    private void dfs(String currWord, String endWord, Map<String, List<String>> neighbors, Map<String, Integer> distance,
                     LinkedList<String> list, List<List<String>> rst) {
        list.add(currWord);
        if (currWord.equals(endWord)) {
            rst.add(new LinkedList<>(list));
        } else {
            for (String neig : neighbors.get(currWord)) {
                if (neig == null) {
                    continue;
                }
                // 利用到了 distance 信息，只有当 neig 是 bfs 的下一级节点时才遍历
                // 因为不是所有的 neigh 都是到 endWord 的最短路径
                if (distance.get(neig) == distance.get(currWord) + 1) {
                    dfs(neig, endWord, neighbors, distance, list, rst);
                }
            }
        }
        list.remove(list.size() - 1);
    }

}