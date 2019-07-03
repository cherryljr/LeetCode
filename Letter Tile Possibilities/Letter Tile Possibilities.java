/*
You have a set of tiles, where each tile has one letter tiles[i] printed on it.
Return the number of possible non-empty sequences of letters you can make.

Example 1:
Input: "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".

Example 2:
Input: "AAABBC"
Output: 188

Note:
    1. 1 <= tiles.length <= 7
    2. tiles consists of uppercase English letters.
 */

/**
 * Approach 1: Backtracking (Similar to Permutations II)
 * 题目要求从原本字符串中任意取出 1~len(str) 个字符，进行全排列，求不重复的结果个数总共有多少个。
 * 可以看出，这是一道非常经典的 Permutation 问题。相比于 Permutations II 去除了元素个数的一个限制。
 *
 * 考虑到数据规模只有 7，因此我们可以仿照 Permutations II 的做法，进行暴力求解。
 * 因为答案只需要个数，这里就不浪费额外的空间去维护结果集了，只需要一个 count 进行计数即可。
 *
 * 时间复杂度：O(1! + 2! + 3! + 4! + ... + n!)
 * 空间复杂度：O(n)
 *
 * https://github.com/cherryljr/LintCode/blob/master/Permutations%20II.java
 */
class Solution {
    int count = 0;

    public int numTilePossibilities(String tiles) {
        char[] chars = tiles.toCharArray();
        Arrays.sort(chars); // 为了之后的去重，需要先进行一次排序
        boolean[] visited = new boolean[chars.length];
        dfs(chars, 0, visited);
        return count;
    }

    private void dfs(char[] chars, int len, boolean[] visited) {
        // 递归的终止条件
        if (len == chars.length) {
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            // 去重操作
            if (visited[i] || (i > 0 && chars[i] == chars[i - 1] && !visited[i - 1])) {
                continue;
            }
            // 因为已经去重过了，所以能够到这里的（求出来的任意一个字符串）都是满足要求的。
            count++;
            visited[i] = true;
            dfs(chars, len + 1, visited);
            // Backtracking
            visited[i] = false;
        }
    }
}

/**
 * Approach 2: Backtracking (By Counting Values)
 * 同样是利用了 Backtracking，但是不同于 Approach 1.
 * 因为是 Permutations，所以顺序并不重要，所以这里计算了每个元素出现的次数并存储起来。
 * 然后利用了 DFS 将这些元素进行组合，计算总共的组合方案。
 *
 * 时间复杂度；O(1! + 2! + 3! + 4! + ... + n!)
 * 空间复杂度：O(26) ==> O(1)
 */
class Solution {
    public int numTilePossibilities(String tiles) {
        int[] count = new int[26];
        for (char tile : tiles.toCharArray()) {
            count[tile - 'A']++;
        }
        return dfs(count);
    }

    private int dfs(int[] count) {
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                continue;
            }
            // means we are using the i-th tile ('A'+i) as the current tile because there are still remaining ones.
            count[i]--;
            // means with these current tiles (not necessarily all the tiles given) we already have a valid combination.
            sum++;
            // means if we still want to add more tiles to the existing combination, we simply do recursion with the tiles left;
            sum += dfs(count);
            // backtracking, because we have finished exploring the possibilities of using the i-th tile and need to restore it 
            // and continue to explore other possibilities.
            count[i]++;
        }
        return sum;
    }
}