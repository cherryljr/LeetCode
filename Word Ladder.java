/*
Given two words (beginWord and endWord), and a dictionary's word list,
find the length of shortest transformation sequence from beginWord to endWord, such that:
    1. Only one letter can be changed at a time.
    2. Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

For example,
    Given:
    beginWord = "hit"
    endWord = "cog"
    wordList = ["hot","dot","dog","lot","log","cog"]
As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Note:
    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
    You may assume no duplicates in the word list.
    You may assume beginWord and endWord are non-empty and are not the same.

UPDATE (2017/1/20):
The wordList parameter had been changed to a list of strings (instead of a set of strings). 
Please reload the code definition to get the latest changes.
 */

/**
 * Approach 1: BFS
 * 对于这道题目，我们可以这么去解决。
 * 因为字符串每次只能改变 一个 字符，所以也就意味着：
 *  一个字符 一次变化 只能通向 wordList 中差距只有一个字符的单词。
 * 那么其实我们可以将 beginWord 与 wordList 中的单词建立一个连通图，每条边权值为 1.（就是一个简单图而已）
 * 建立好图后，我们就可以通过 BFS 从 beginWord 开始遍历并维持一个参数 level，看是否能找到 endWord.
 * 如果能找到返回 level 即可（level指的就是从 beginWord 到 endWord 我们需要经过几层BFS,或者说 走过几条边）
 * 如果不能找到则返回 0 即可。
 * 这里之所以选择 BFS 而不是 DFS，原因是我们只需要找到 最短路径，而不是遍历整张图。
 * DFS 会遍历整张图，时间复杂度更大。因此在 简单图 的遍历中，我们通常会使用 BFS 而不是 DFS。
 * 类似的应用还有 The Maze 系列（同理也是求最短路径）
 * https://github.com/cherryljr/LeetCode/blob/master/The%20Maze.java
 *
 * 时间复杂度分析：
 *  BFS 遍历图的时间为 O(n);
 *  寻找一个单词 word 所有改变 一个 字符能够得到的单词时间复杂度为 O(26 * len) （len 为单词长度）
 *  因此总体时间复杂度为：O(n * len * 26) => O(n)
 */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.size() == 0) {
            return 0;
        }
        // 将 List 转换为 Set 以满足在 O(1) 的时间内查找 List 中是否包含某个单词
        // 如果使用 List 时间复杂度为 O(n),更新测试用例后会 TLE.
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        // 记录已经遍历过的 word
        Set<String> visited = new HashSet<>();
        // 初始化 queue 和 visited Set.
        queue.offer(beginWord);
        visited.add(beginWord);
        int level = 1;  // level 是包括自身的，所以初始大小为 1

        // BFS
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                // 如果找到了 endWord,直接返回 level. 根据 BFS 的特性，此时 level 就是最小的
                if (word.equals(endWord)) {
                    return level;
                }
                // 将 wordList 中所有与 word 相差一个字符的单词放入队列中
                for (String neigh : getNeighbors(word, visited,  wordSet)) {
                    queue.offer(neigh);
                }
            }
            // 该层没有找到 endWord，继续找下一层，level+1
            level++;
        }

        return 0;
    }

    private List<String> getNeighbors(String word, Set<String> visited, Set<String> wordSet) {
        List<String> rst = new ArrayList<>();
        char[] chars = word.toCharArray();

        // 遍历该单词 word,将其每个字母都分别替换成 'a' ~ 'z'
        for (int i = 0; i < chars.length; i++) {
            char old = chars[i];    // 暂存该位置原来的字符
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == old) {
                    continue;
                }
                chars[i] = ch;
                String nextWord = String.valueOf(chars);
                // 查看修改后的单词是否在 wordSet 中存在，并还未被 visited.如果符合条件就加入到 rst 中
                if (!visited.contains(nextWord) && wordSet.contains(nextWord)) {
                    visited.add(nextWord);
                    rst.add(nextWord);
                }
            }
            chars[i] = old;     // 将 chars[i] 置回为原来字母
        }
        return rst;
    }
}

/**
 * Approach 2: BFS (Optimized)
 * 解题思路与 Approach 1 中相同，但是利用 wordSet 同时发挥了：
 * 解法1 中 visitedSet 和 wordSet 的功能。
 *
 * 开始时，利用 wordList 完成 wordSet 的初始化，并从其中移除 beginWord 代表其已经遍历过。
 * 然后每次判断 nextWord 是否在 wordSet 中时，我们直接对其进行 remove(nextWord) 操作，
 * 如果返回 true,代表其还未被遍历，同时我们又能将其移除，代表已经遍历过了。
 */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.size() == 0) {
            return 0;
        }
        // 将 List 转换为 Set 以满足在 O(1) 的时间内查找 List 中是否包含某个单词
        // 如果使用 List 时间复杂度为 O(n),更新测试用例后会 TLE.
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        // 从 wordSet 中移除 beginWord,代表已经遍历过了
        wordSet.remove(beginWord);
        int level = 1;

        // BFS
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) {
                    return level;
                }
                for (String neigh : getNeighbors(word, wordSet)) {
                    queue.offer(neigh);
                }
            }
            level++;
        }

        return 0;
    }

    private List<String> getNeighbors(String word, Set<String> wordSet) {
        List<String> rst = new ArrayList<>();
        char[] chars = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            char old = chars[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == old) {
                    continue;
                }
                chars[i] = ch;
                String nextWord = new String(chars);
                // 看是否能够成功从 wordSet 中移除 nextWord,
                // 该操作同时包含了两个信息：nextWord在wordSet中存在；移除 nextWord,表示已经遍历过了
                if (wordSet.remove(nextWord)) {
                    rst.add(nextWord);
                }
            }
            chars[i] = old;
        }
        return rst;
    }
}

/**
 * Approach 3: Two BFS (Forward and Backward Simultaneous)
 * The idea behind bidirectional search is to run two simultaneous searches—
 * one forward from the initial state and
 * the other backward from the goal—hoping that the two searches meet in the middle.
 * The motivation is that b^(d/2) + b^(d/2) is much less than b^d. b is branch factor, d is depth. "
 * ----- section 3.4.6 in Artificial Intelligence - A modern approach by Stuart Russel and Peter Norvig
 *
 * 因为是两端逼近的方法，所以不能像 Approach 2 中只使用一个 wordSet 同时来判断是否 contains 与是否 visited.
 * 具体做法为：
 *  分别从 beginWord 和 endWord 开始进行 BFS。
 *  因此初始化的时候我们需要分别将他们加入到各自的队列中，并将其加入到 visited 中，同时 level 初始化为 2.
 *  然后我们开始根据 改变单词各个位置上的字符 进行BFS，
 *  因为是 双BFS，所以我们选择 size 较小的 queue 进行 BFS，
 *  如果 修改之后的nextWord 不在 wordSet 中，说明其不是图中的节点，直接忽略即可。
 *  如果 nextWord 出现在了 另一个队列中，即两个 队列 相遇了。
 *  那么说明了 beginWord 可以通过 wordList 中单词的变换得到 endWord.变换次数为 level.
 *  如果没有发现，则将其放入队列，继续进行 BFS.
 *
 * 这个方法的操作步骤与 Approach 1,2 略有有些不同，大家不要混淆起来。
 * 1，2 中使用的是最基本的 BFS 模板直接进行遍历，终点也是明确的。
 * 但是这个解法中，我们使用的是 两个BFS相遇 的方法。
 * 不过算法的具体流程还是非常清晰明了的。
 */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.size() == 0) {
            return 0;
        }
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // Initialing the two queues and visited set
        Queue<String> beginQueue = new LinkedList<>();
        Queue<String> endQueue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        beginQueue.offer(beginWord);
        endQueue.offer(endWord);
        visited.add(beginWord);
        visited.add(endWord);
        int level = 2;

        while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {
            if (beginQueue.size() > endQueue.size()) {
                Queue<String> temp = beginQueue;
                beginQueue = endQueue;
                endQueue = temp;
            }

            Set<String> set = new HashSet<>(endQueue);
            int size = beginQueue.size();
            for (int i = 0; i < size; i++) {
                String begin = beginQueue.poll();
                char[] chars = begin.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char old = chars[j];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (old == ch) {
                            continue;
                        }
                        chars[j] = ch;
                        String nextWord = String.valueOf(chars);
                        // 判断修改后的单词是否在 wordList 中
                        if (!wordSet.contains(nextWord)) {
                            continue;
                        }
                        // 判断修改后的单词是否在 另一个队列 中（两个队列是否相遇）
                        if (set.contains(nextWord)) {
                            return level;
                        }
                        // 若 nextWord 还未被遍历过，则将其加入到 队列 和 visited set 中
                        if (visited.add(nextWord)) {
                            beginQueue.offer(nextWord);
                        }
                    }
                    chars[j] = old;
                }
            }
            level++;
        }

        return 0;
    }
}






