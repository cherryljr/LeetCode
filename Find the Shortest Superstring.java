/*
Given an array A of strings, find any smallest string that contains each string in A as a substring.
We may assume that no string in A is substring of another string in A.

Example 1:
Input: ["alex","loves","leetcode"]
Output: "alexlovesleetcode"
Explanation: All permutations of "alex","loves","leetcode" would also be accepted.

Example 2:
Input: ["catg","ctaagt","gcta","ttca","atgcatc"]
Output: "gctaagttcatgcatc"

Note:
1 <= A.length <= 12
1 <= A[i].length <= 20
 */

/**
 * Approach 1: DFS (Backtracking)
 * 根据数据规模，我们可以推测出本题的时间复杂度可以是 O(n!) 级别的
 * 因此我们想到使用类似 Permutation 中的做法，即使用 DFS(Backtracking) 遍历所有的排列组合，而最优解必定在这里面。
 * 因为题目给的数组大小为 12，并且还涉及到了字符串的合并等操作。
 * 因此需要进行必要的剪枝操作，否则必定超时。
 * 代码在这里主要进行了以下 4 点的优化：
 * 1. 剪枝操作：
 *  如果 当前拼接成的字符串 >= 当前已知的最优解 则直接 return （放弃当前方案）
 * 对此我们需要记录一个 currLen 以便我们代码可能更加高效地运行。
 * 2. 事先计算 nonOverlap 数组：
 *  因为我们在对字符串进行拼接时需要计算两个字符串前后缀的公共部分，
 *  因此我们可以事先对此进行一次计算，然后用数组保存起来，以避免递归过程中的重复计算。
 * 3. 利用 KMP 进行优化：
 *  在计算 nonOverlap 数组的时候，因为涉及到 最长公共前后缀 的计算，因此自然而然地想到使用 KMP 来对此进行优化。
 *  思想做法与 Shortest Palindrome 相同。（但是本题因为数据量太小了，所以做不做这一步相差其实不大）
 * 4. 在递归过程中不进行字符串的拼接操作
 *  因为字符串的操作在 Java 中是非常费时的一个操作，所以这里仅仅利用 List 来记录下字符串对应的数组下标。
 *
 * 这个做法比较简单直白，只要代码能力过关还是能够很快AC的。但是这并不是最优解，过得也比较勉强...
 *
 * 时间复杂度：O(n!)
 * 空间复杂度：O(n)
 *
 * References:
 *  Permutations: https://github.com/cherryljr/LintCode/blob/master/Permutations.java
 *  Shortest Palindrome: https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Palindrome.java
 */
class Solution {
    private int[][] nonOverlap = null;
    private int bestLen = Integer.MAX_VALUE;
    private List<Integer> bestPath = null;

    public String shortestSuperstring(String[] A) {
        int n = A.length;
        nonOverlap = new int[n][n];
        // 计算将 A[j] 放在 A[i] 后面，最少需要添加多少个字符（不重复部分的字符个数）
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nonOverlap[i][j] = getNonOverlap(A[i], A[j]);
            }
        }

        boolean[] visited = new boolean[n];
        dfs(A, 0, 0, visited, new ArrayList<>());
        // 对结果进行拼接操作
        StringBuilder rst = new StringBuilder();
        rst.append(A[bestPath.get(0)]);
        for (int i = 1; i < n; i++) {
            int pre = bestPath.get(i - 1);
            int curr = bestPath.get(i);
            rst.append(A[curr].substring(A[curr].length() - nonOverlap[pre][curr]));
        }
        return rst.toString();
    }

    // 利用 KMP 计算 字符串a的后缀 和 字符串b的前缀 的最长公共部分
    private int getNonOverlap(String a, String b) {
        StringBuilder sb = new StringBuilder();
        sb.append(b).append('#').append(a);
        return b.length() - endNextLength(sb.toString().toCharArray());
    }

    // KMP的 getNextArray() 方法
    private int endNextLength(char[] arr) {
        int[] next = new int[arr.length + 1];
        next[0] = -1;
        int pos = 2, cn = 0;
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next[next.length - 1];
    }

    // 与 Permutation 类似的做法(Backtracking)
    private void dfs(String[] A, int index, int currLen, boolean[] visited, ArrayList<Integer> path) {
        if (currLen >= bestLen) {
            return;
        }
        if (path.size() == A.length) {
            bestLen = currLen;
            // 与 Permutation 相同，这里需要对结果进行一次数值拷贝将其存储起来
            // 因为在之后的递归过程中 path 中的元素是会发生变化的
            bestPath = new ArrayList<>(path);
            return;
        }

        for (int i = 0; i < A.length; i++) {
            if (visited[i]) {
                continue;
            }
            path.add(i);
            visited[i] = true;
            // 注意这里如果当 i 是第一个被使用的字符串的话，那么 currLen = A[i].length
            dfs(A, index + 1, index == 0 ? A[i].length() : currLen + nonOverlap[path.get(index - 1)][i], visited, path);
            // Backtracking
            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}
