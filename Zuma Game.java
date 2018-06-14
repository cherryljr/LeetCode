/*
Think about Zuma Game. You have a row of balls on the table, colored red(R), yellow(Y), blue(B), green(G), and white(W).
You also have several balls in your hand.

Each time, you may choose a ball in your hand, and insert it into the row
(including the leftmost place and rightmost place).
Then, if there is a group of 3 or more balls in the same color touching, remove these balls.
Keep doing this until no more balls can be removed.

Find the minimal balls you have to insert to remove all the balls on the table.
If you cannot remove all the balls, output -1.

Examples:
Input: "WRRBBW", "RB"
Output: -1
Explanation: WRRBBW -> WRR[R]BBW -> WBBW -> WBB[B]W -> WW

Input: "WWRRBBWW", "WRBRW"
Output: 2
Explanation: WWRRBBWW -> WWRR[R]BBWW -> WWBBWW -> WWBB[B]WW -> WWWW -> empty

Input:"G", "GGGGG"
Output: 2
Explanation: G -> G[G] -> GG[G] -> empty

Input: "RBYYBBRRB", "YRBGB"
Output: 3
Explanation: RBYYBBRRB -> RBYY[Y]BBRRB -> RBBBRRB -> RRRB -> B -> B[B] -> BB[B] -> empty

Note:
You may assume that the initial row of balls on the table won’t have any 3 or more consecutive balls with the same color.
The number of balls on the table won't exceed 20, and the string represents these balls is called "board" in the input.
The number of balls in your hand won't exceed 5, and the string represents these balls is called "hand" in the input.
Both input strings will be non-empty and only contain characters 'R','Y','B','G','W'.
 */

/**
 * Approach: Backtracking
 * 看到这道题目的背景，我还是相当具有兴趣的，觉得这还是一道很好玩的题目.
 * 然而做完之后，这道题目并没有给我留下太好的印象。原因我会在下面详细讲述。
 *
 * 解题思路：
 *  注意Note中的说明，需要重点关注的有两点：
 *      1. 游戏初始的珠子串(Board)中并不会有 >=3 个颜色相同的珠子连在一起，这跟我们玩的游戏还是不同的。
 *      2. 数据规模：珠子串长度最长不会超过 20，手里的珠子最多不会超过 5 个。
 *  根据数据规模，我们基本可以断定这道题目是使用 DFS(Backtracking) 来枚举所有插入位置进行暴力解的。
 *
 * 注意点：
 *  以下代码进行了两个部分的剪枝操作。
 *      剪枝操作1：因为如果有相同颜色珠子连在一起的话，插在它们中任意位置效果都是相同的，
 *      所以我们统一规定插在相同颜色珠子串的最后一个位置。
 *      剪枝操作2：只对插入后会被消去的地方进行插入操作。即：如果本轮操作后，无法消去一段
 *      那么该位置就不进行插入操作。
 *  而本题存在问题的也正是上述的这两个剪枝操作！很明显这是一个贪心的做法，但是这个贪心的策略是错误的。
 *  正确的做法应该是每个位置，都需要进行一遍试探性的插入才行。
 *  举个例子:
 *      board = "RRWWRRBBRR", hand = "WB"
 *  如果按照上述的做法，我们会在 [W] 或者 [B] 的后面插入手中的珠子，但是这样肯定会剩下 [RR] 没法消除
 *  因此会返回 -1. 当其实这个局面是有办法全部消去的，策略如下：
 *      RRWWRRBBRR -> RRWWRRBBR[W]R -> RRWWRRBB[B]RWR -> RRWWRRRWR -> RRWWWR -> RRR -> empty
 *  因此这里的剪枝（贪心）策略是错误的。
 *
 * 大家可能会有疑问，为什么错的还要这么做呢？
 * 因为...这题的标程都是错的啊！！！而且不贪心剪枝的话还过不去！！！坑爹啊！！！
 * 这也能解释为啥这么多人踩这道题目了...
 */
class Solution {
    public int findMinStep(String board, String hand) {
        if (board == null || board.length() == 0) {
            return 0;
        }

        // 为了方面查询，我们需要统计手中珠子的信息（本题中我们可以发射手中的任意一颗珠子）
        Map<Character, Integer> balls = new HashMap<>();
        for (char c : hand.toCharArray()) {
            balls.put(c, balls.getOrDefault(c, 0) + 1);
        }
        return dfs(board, balls);
    }

    // 返回全部清楚当前board所需要的最少珠子，如果无法全部清除返回-1
    private int dfs(String board, Map<Character, Integer> balls) {
        if (board == null || board.length() == 0) {
            return 0;
        }

        int rst = Integer.MAX_VALUE;
        int i = 0, j;
        while (i < board.length()) {
            j = i + 1;
            Character color = board.charAt(i);
            // j一直向后移动，直到 j 越界 或者 指向的珠子颜色与 i 的不同
            while (j < board.length() && color == board.charAt(j)) {
                j++;
            }
            // 需要插入多少颗珠子才能消掉 i~j-1 的珠子
            int costBalls = 3 - (j - i);
            // 手中剩余足够的珠子可供插入
            if (balls.containsKey(color) && balls.get(color) >= costBalls) {
                // 消去 i~j-1 段的珠子，同时因为消去该段会导致两边的两段的珠子碰到一起
                // 可能会引发连续的消除反应，因此实现了 shrink() 来实现这点
                String newBoard = shrink(board.substring(0, i) + board.substring(j));
                // 减去花费掉的珠子数
                balls.put(color, balls.get(color) - costBalls);
                // 进行 DFS 调用子过程，求解全部消去剩下珠子需要的最少珠子数
                int subRst = dfs(newBoard, balls);
                if (subRst >= 0) {
                    // 如果可以被顺利全部消除的话，取最小值即可
                    rst = Math.min(rst, costBalls + subRst);
                }
                // Backtracking 加上之前消耗掉的珠子，进行回溯操作
                balls.put(color, balls.get(color) + costBalls);
            }
            i = j;
        }

        return rst == Integer.MAX_VALUE ? -1 : rst;
    }

    // 消除当前 board 中所有可以消除的珠子串
    private String shrink(String board) {
        int i = 0, j;
        while (i < board.length()) {
            j = i + 1;
            Character color = board.charAt(i);
            while (j < board.length() && color == board.charAt(j)) {
                j++;
            }
            if (j - i >= 3) {
                board = board.substring(0, i) + board.substring(j);
                // 当进行成功消除后，记得将 i 重置回 0 位置，重新进行检查（因为可能发生连锁反应）
                i = 0;
            } else {
                i++;
            }
        }
        return board;
    }
}