/*
There is a strange printer with the following two special requirements:
The printer can only print a sequence of the same character each time.
At each turn, the printer can print new characters starting from and ending at any places,
and will cover the original existing characters.
Given a string consists of lower English letters only,
your job is to count the minimum number of turns the printer needed in order to print it.

Example 1:
Input: "aaabbb"
Output: 2
Explanation: Print "aaa" first and then print "bbb".

Example 2:
Input: "aba"
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.

Hint: Length of the given string will not exceed 100.
*/

/**
 * Approach: Interval DP
 * 我们来观察总结一下，打印出来的字符串的特点：
 * 假设某一次打印机打印了若干个'a'，像这样：”aaaaaa”，在这之后打印的字符，无非是三种情况：
 *  ● 在这段字符串的内部打印，但是不覆盖这段字符串的一端，例如”abbbaa”、”abbada”。
 *  ● 在这段字符串的外部打印，完全不覆盖这段字符串，例如”bbaaaaaa”、”bbaaaaaaccc”。
 *  ● 覆盖这段字符串的一端，例如”baaaaa”、”baaacccccc”。
 * 上面所说的第三种情况，看起来就像后来的字符都打印在”a”组成的字符串的外部。
 * 事实上，如果打印了一串字符串后，再打印一些字符覆盖这段字符串的端点会造成浪费，
 * 也就是说被覆盖的这字符串的部分一开始完全没有必要打印，也不会对最终打印次数造成影响。
 * 所以，我们可以假定，打印时不存在浪费，也就是说某次打印可以覆盖前面某一次打印的（不包含端点的）内部，也可以不覆盖，
 * 但是不能覆盖前面某一次打印的端点。
 *
 * 对于一个已知的目标字符串s，我们考虑打印出这个字符串最左边的字符s[0]的那次打印，我们总可以在打印方案中，把该次打印放到第一次打印。
 * 可以这样做的理由是，由于上述的假定，其它的打印要么在这次打印的内部（不包含端点），
 * 要么在这次打印的外部，并且由于包含目标字符串的最左端点，所以这次打印也不可能在别的打印的内部。
 * 这样一来我们就可以枚举包含s[0]的那次打印的长度L，然后把原目标字符串分为 s[0 ~ L-1] 和 s[L ~ N-1]（设原字符串长度为N），
 * 其中，s[0]==s[L-1] 必须成立（因为端点不被覆盖）。
 * s[0 ~ L-1] 的最少打印次数实际等于 s[1 ~ L-1] 的最少打印次数（也等于 s[0 ~ L-2] 的最少打印次数）
 * 这是因为打印出其中一个字符串的打印方案可以稍加变动变成另一个的打印方案而不改变打印次数。
 * s[L ~ N-1] 的打印与前面字符的打印没有关系，可以看成一个新的目标字符串，用同样的方法分析计算。
 * 有两个特殊情况：
 *  L = 1时，s[0]==s[L-1] 必然成立，这时的答案为 1 + s[1 ~ N-1] 的最小打印次数；
 *  L = N时，应满足 s[0]==s[L-1]==s[N-1]，即左右两端点相等，答案为 s[1 ~ N-1] 的最小打印次数。
 * 分别计算出 s[1 ~ L-1]、s[L ~ N-1] 的最小打印次数并相加得到特定 L 下的打印次数，枚举所有 区间长度L，对得到的答案取最小值即可得到最终答案。
 * 这样把一个区间分成两个小的连续区间求解的方法属于区间型动态规划。
 * 状态 dp[i][j] 表示从 i 到 j 这段子串最少打印次数。
 *
 * 算法复杂度分析：
 *  枚举所有区间的时间复杂度为O(n^2)，枚举分段点的时间复杂度为O(n)。
 *  故总的时间复杂度为O(n^3)，额外空间复杂度为O(n^2)。
 *
 * PS.对于区间动态规划问题，其难度主要在于很难想出这是一道区间DP的问题和找出递推方程上。
 * 一旦确定了这些，其解题步骤实际上是有章可循，非常清晰的。因此这里基本都把笔墨花费在了如何想出递推关系这点上。
 * 对于解题步骤可以参考代码注释，或者 Burst Balloons 中的详解：
 *  https://github.com/cherryljr/LeetCode/blob/master/Burst%20Balloons.java
 */
class Solution {
    public int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = s.length();
        int[][] dp = new int[len][len];
        // 对 dp[] 进行初始化
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }

        // 枚举所有的区间长度 l
        for (int l = 2; l <= len; l++) {
            // 枚举所有可能的起始位置
            for (int start = 0; start + l <= len; start++) {
                // end 为当前区间的最后一个数的 indices
                int end = start + l - 1;
                // 对 dp[start][end] 进行初始化，相当的关于 l=1 的情况
                dp[start][end] = dp[start + 1][end] + 1;
                // 遍历当前区间，枚举 分段点pivot 并利用它将当前区间分段为 s[start, pivot] 和 s[pivot+1, end]
                // 以得到最佳位置使得 区间s[start, end]的打印次数最少 (dp[start][end]有最小值)
                for (int pivot = start + 1; pivot < end; pivot++) {
                    // 当 s[start] == s[pivot] 时,说明该区间的打印次数可以被减少。此时，该区间的打印次数为:
                    // pivot左边序列的最少打印次数(包括pivot) + pivot右边序列的最少打印次数(不包括pivot)
                    if (s.charAt(start) == s.charAt(pivot)) {
                        dp[start][end] = Math.min(dp[start][end], dp[start + 1][pivot] + dp[pivot + 1][end]);
                    }
                }
                // 由于以上的 for 循环中 pivot 只遍历到了 end-1 的位置
                // 所以这里需要比较一次 s[start] 和 s[end] 的值
                // 若相等，则根据算法原理判断是否可以进一步缩小 dp[start][end] 的值
                if (s.charAt(start) == s.charAt(end)) {
                    dp[start][end] = Math.min(dp[start][end], dp[start + 1][end]);
                }
            }
        }

        return dp[0][len - 1];
    }
}

