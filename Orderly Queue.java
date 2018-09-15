/*
A string S of lowercase letters is given.  Then, we may make any number of moves.
In each move, we choose one of the first K letters (starting from the left), remove it, and place it at the end of the string.
Return the lexicographically smallest string we could have after any number of moves.

Example 1:
Input: S = "cba", K = 1
Output: "acb"
Explanation:
In the first move, we move the 1st character ("c") to the end, obtaining the string "bac".
In the second move, we move the 1st character ("b") to the end, obtaining the final result "acb".

Example 2:
Input: S = "baaca", K = 3
Output: "aaabc"
Explanation:
In the first move, we move the 1st character ("b") to the end, obtaining the string "aacab".
In the second move, we move the 3rd character ("c") to the end, obtaining the final result "aaabc".

Note:
1 <= K <= S.length <= 1000
S consists of lowercase letters only.
 */

/**
 * Approach: Similar to Bubble Sort
 * 本题的实质有点像是 冒泡排序。
 * 具体分析如下：
 * 首先，我们分析当 K=1 的情况，显然此时总共有 S.length 中排列方式。
 * 全部枚举的话时间复杂度也只有 O(n),所以是没有问题的。
 * 比如：12345 -> 23451 -> 34512 -> 45123 -> 51234
 * 然后，难点在于如何分析 K>1 的情况。
 * 实际上这个时候与我们的 冒泡排序 就非常类似了。
 * 因为当 K>=2 时，我们可以把两个这两个相邻位置的空间当作一个 buffer 来进行两个数之间的交换。
 * 因为次数是无限的，所以就意味着我们可以进行任意两个数之间的交换。这也就是冒泡排序的做法。
 * 比如：我们想要交换 S[i] 和 S[i+1] 这两个元素。
 * 那么我们需要：
 *  1. 首先对 S[i-1] 进行一次操作，将其放到末尾去
 *  2. 然后对 S[i+1] 进行一次操作，将其放到末尾去
 *  3. 再对 S[i] 进行一次操作，将其放到末尾去
 *  4. 最后依次对 S[i+2]~end 的数全部进行一次操作
 * 举个例子：K=2 S=bacdb 我们想要交换的是 S[1] = a 和 S[2] = c
 *  bacdb // 初始情况
 *  acdbb // 进行第 1 步操作
 *  adbbc // 进行第 2 步操作
 *  dbbca // 进行第 3 步操作
 *  bcadb // 进行第 4 步操作
 * 因此经过上述分析，我们可以得出：
 *  本题的 K 只需要讨论 K=1 和 K>=2 两种情况即可，
 *  且当 K>=2 时，我们的答案就是所有字符的最小排列情况。
 *
 * 时间复杂度：O(n)
 */
class Solution {
    public String orderlyQueue(String S, int K) {
        if (K > 1) {
            char[] chars = S.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        }

        String rst = S;
        for (int i = 1; i < S.length(); i++) {
            String temp = S.substring(i) + S.substring(0, i);
            if (rst.compareTo(temp) > 0) {
                rst = temp;
            }
        }
        return rst;
    }
}