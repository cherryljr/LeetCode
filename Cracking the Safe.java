/*
There is a box protected by a password. The password is n digits,
where each letter can be one of the first k digits 0, 1, ..., k-1.
You can keep inputting the password, the password will automatically be matched against the last n digits entered.

For example, assuming the password is "345", I can open it when I type "012345", but I enter a total of 6 digits.
Please return any string of minimum length that is guaranteed to open the box after the entire string is inputted.

Example 1:
Input: n = 1, k = 2
Output: "01"
Note: "10" will be accepted too.

Example 2:
Input: n = 2, k = 2
Output: "00110"
Note: "01100", "10011", "11001" will be accepted too.

Note:
n will be in the range [1, 4].
k will be in the range [1, 10].
k^n will be at most 4096.
 */

/**
 * Approach: Backtracking + Hamilton Path
 * 根据题目的数据量，我们可以推测出本题的解法应该是使用 DFS.
 * 总共有 k^n 个结果，而题目想要使得一个字符串包含所有可能性并且长度最短。
 * 因此我们的想法就是：
 *  后一个字符串的 前n-1个 字符(prefix) 与 前一个字符串的 后n-1个 字符(suffix) 应该相同。
 *  即能够共用。这样拼凑出来的字符串就是最短的答案。
 * 解题的时候，我并不知道能否保证这样的解是否存在，只是我写出来的例子都是符合这个做法的。
 *
 * 那么如果按照这个做法的话，把这个过程转换成有向图就是：
 * 每个节点有 k 条边， k-1 个邻居（有一条边指向自身）。
 * 而我们的目的就是找出一条路径，使其能够不重复地遍历图中的每一个节点（一笔画问题）
 * 这就是 Hamilton Path.这样的话得到的字符串就是最短的，也就是我们需要的答案。
 *
 * 时间复杂度： O(k!^(k^(n-1)) / k^n)
 * 虽然时间复杂度相当高，但是本题图的连结性质很强，并且经过剪枝，该方法跑起来效率还是非常高的。
 * 因为其 Backtracking 的次数非常低。运行时间 Beats 90.23%
 *
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/graph/leetcode-753-cracking-the-safe/
 * 
 * 不过本题最好的解法是使用 De Bruijin Sequence. 它是基于 欧拉图 的一个做法。
 * 但是如果不知道这东西存在的话，还是很难做出来的，有兴趣的可以参考：
 *  https://youtu.be/iPLQgXUiU14 （推荐观看该视频）
 *  https://leetcode.com/problems/cracking-the-safe/solution/
 */
class Solution {
    public String crackSafe(int n, int k) {
        // 所有的可能性总数，每个位置上都有 k 种可能，总共有 n 个位置
        int total = (int)Math.pow(k, n);
        // 初始化起始位置 "00...0"
        StringBuilder rst = new StringBuilder();
        for (int i = 0; i < n; i++) {
            rst.append('0');
        }
        Set<String> visited = new HashSet<>();
        visited.add(rst.toString());

        dfs(rst, total, visited, n, k);

        return rst.toString();
    }

    private boolean dfs(StringBuilder rst, int total, Set<String> visited, int n, int k) {
        if (visited.size() == total) {
            return true;
        }

        // 前一个字符串的 suffix, 当前字符串的 prefix
        String prefix = rst.substring(rst.length() - n + 1, rst.length());
        // 下一步有 k 种可能，分别走向 0,1,2...k-1
        for (int i = 0; i < k; i++) {
            // 这里使用了 String 来表示下一个字符串，Java中 String 是 final 的，所以没有必要回溯啥的
            // 如果使用 StringBuilder 就需要了，但是这里使用 StringBuilder 并没什么优势
            String next = prefix + i;
            if (visited.contains(next)) {
                continue;
            }
            rst.append(i);
            visited.add(next);
            // 如果当前路径能行得通，直接 return 相当于一个剪枝
            if (dfs(rst, total, visited, n, k)) {
                return true;
            }
            // Backtracking(只有该路径走不通的情况下，才需要回溯尝试其他路径)
            rst.deleteCharAt(rst.length() - 1);
            visited.remove(next);
        }

        return false;
    }
}
