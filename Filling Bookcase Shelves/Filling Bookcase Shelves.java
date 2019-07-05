/*
We have a sequence of books: the i-th book has thickness books[i][0] and height books[i][1].
We want to place these books in order onto bookcase shelves that have total width shelf_width.
We choose some of the books to place on this shelf (such that the sum of their thickness is <= shelf_width),
then build another level of shelf of the bookcase
so that the total height of the bookcase has increased by the maximum height of the books we just put down.
We repeat this process until there are no more books to place.

Note again that at each step of the above process, the order of the books we place is the same order as the given sequence of books.
For example, if we have an ordered list of 5 books, we might place the first and second book onto the first shelf,
the third book on the second shelf, and the fourth and fifth book on the last shelf.

Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.

Example 1:
Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4
Output: 6
Explanation:
The sum of the heights of the 3 shelves are 1 + 3 + 2 = 6.
Notice that book number 2 does not have to be on the first shelf.

Constraints:
    1. 1 <= books.length <= 1000
    2. 1 <= books[i][0] <= shelf_width <= 1000
    3. 1 <= books[i][1] <= 1000
 */

/**
 * Approach 1: DFS + Memory Search
 * 题意：给定几本书，这些书分别具备 宽度 和 高度 信息。
 * 要求对这些书按层进行排列，使得同一层书的宽度之和不得超过给定的限制 shelf_width，
 * 书架每层的高度为当前层所有书的最大高度，问书架的总高度最小时多少。
 *
 * 根据上述题意，我们可以将问题转换成：
 * 给定一个二维数组，将其分割成不同的 SubArray，使得各个 SubArray 宽度之和不超过 shelf_width，
 * 求各个 SubArray 最高高度值之和的最小值。
 * 考虑到是不能改变各个元素的位置，因此无法使用 贪心+排序 的做法。
 * 因此，想到使用 DFS + Memory Search 或者是 DP 的做法来解决。
 * 这里先给出 DFS + Memory Search 的做法。
 * 对于一本书 books[i] 其放法只有两种：
 *  1. 跟之前的其他书籍放在同一层（但是需要满足宽度之和不超过 shelf_width 的要求）
 *  2. 将当前书籍放在新的一层上。
 * 即在 DFS 的时候，有以上两种不同的选择，对此我们选择使得 书架高度更低 的方案即可。
 *
 * 在 DFS 过程中，我们需要维护的信息有：当前书的序号位置，书架当前层书的最高高度，书架当前层的剩余宽度。
 * 当递归过程中 书的位置 和 剩余宽度 确定时，答案就是确定的（是一个无后效性问题）
 * 终止条件为：遍历完全部书籍（index == books.length）
 *
 * 时间复杂度：O(M * N)
 * 空间复杂度：O(M * N)
 */
class Solution {
    int[][] mem;
    public int minHeightShelves(int[][] books, int shelf_width) {
        mem = new int[books.length][shelf_width + 1];
        return dfs(books, 0, 0, shelf_width, shelf_width);
    }

    private int dfs(int[][] books, int index, int currHeight, int leftWidth, int shelf_width) {
        if (index == books.length) {
            return currHeight;
        }
        if (mem[index][leftWidth] != 0) {
            return mem[index][leftWidth];
        }

        // 将当前书本放到下面新的一层上
        mem[index][leftWidth] = currHeight + dfs(books, index + 1, books[index][1], shelf_width - books[index][0], shelf_width);
        // 如果当前层剩余宽度能够放得下当前书本，则可以选择将其跟前面书籍放到同一层上
        if (leftWidth >= books[index][0]) {
            mem[index][leftWidth] = Math.min(mem[index][leftWidth], dfs(books, index + 1, Math.max(currHeight, books[index][1]), leftWidth - books[index][0], shelf_width));
        }
        return mem[index][leftWidth];
    }
}

/**
 * Approach 2: DP
 * 使用 DP 的方式来实现，原理与 DFS + Memory Search 是相同的。
 * 时间复杂度：O(M * N)
 * 空间复杂度：O(N)
 */
class Solution {
    public int minHeightShelves(int[][] books, int shelf_width) {
        int[] dp = new int[books.length + 1];
        for (int i = 1; i <= books.length; i++) {
            int width = books[i - 1][0];
            int height = books[i - 1][1];
            dp[i] = dp[i - 1] + height;
            for (int j = i - 1; j > 0 && width + books[j - 1][0] <= shelf_width; j--) {
                height = Math.max(height, books[j - 1][1]);
                width += books[j - 1][0];
                dp[i] = Math.min(dp[i], dp[j - 1] + height);
            }
        }
        return dp[books.length];
    }
}
