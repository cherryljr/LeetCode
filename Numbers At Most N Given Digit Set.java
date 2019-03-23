/*
We have a sorted set of digits D, a non-empty subset of {'1','2','3','4','5','6','7','8','9'}. (Note that '0' is not included.)
Now, we write numbers using these digits, using each digit as many times as we want.
For example, if D = {'1','3','5'}, we may write numbers such as '13', '551', '1351315'.

Return the number of positive integers that can be written (using the digits of D) that are less than or equal to N.

Example 1:
Input: D = ["1","3","5","7"], N = 100
Output: 20
Explanation:
The 20 numbers that can be written are:
1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.

Example 2:
Input: D = ["1","4","9"], N = 1000000000
Output: 29523
Explanation:
We can write 3 one digit numbers, 9 two digit numbers, 27 three digit numbers,
81 four digit numbers, 243 five digit numbers, 729 six digit numbers,
2187 seven digit numbers, 6561 eight digit numbers, and 19683 nine digit numbers.
In total, this is 29523 integers that can be written using the digits of D.

Note:
D is a subset of digits '1'-'9' in sorted order.
1 <= N <= 10^9
 */

/**
 * Approach 1: Mathematics (Count Numbers)
 * 根据数据规模可得，时间复杂度应该在 logn 级别。
 * 因此我们应该根据位数对可能性进行分析，而不是一个个数进行遍历的 Brute Force.
 * 该题属于 计数类 问题。根据数学分析，我们从高位开始看，
 * 首先我们可以加上出去最高位的所有可能性。因为这些数字必定是小于 N 的。
 * eg. N = 52525, 那么我们可以加上所有的：
 *  X, XX, XXX, XXXX 这些数，每个位上数的可能性均为 D.length. 因此 ans += pow(D.length, i)
 * 然后我们再从最高位开始分析：
 *  如果该位上的数 > D[i]，那么就意味着可以组成以 D[i] 为高位的，总共有 D.length^(n-i-1) 的个数比 N 要小。
 *  如果该位上的数 = D[i]，那么无法确定，我们需要进行向后判断，即判断较低位上的数。其实就是一个子问题罢了，继续求解下一位状态即可。
 *  如果该位上的数 < D[i]，那么说明没有继续组成 <=N 的元素了（因为 set 是排序好的，后面的 D[j] 只会比 D[i] 更大），
 *  此时直接 return ans 即可。
 *  即:核心就是，check当前位置上的数是否存在于Set D[] 之中，如果存在的话，就会导致当前结果无法确定，需要继续遍历下一位
 *  否则的话，就能确定当前结果 ans += sum(d[i] < s[i]) * pow(D.length, n - i - 1)
 *
 * 时间复杂度：O(log10(N))
 * 空间复杂度：O(1)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=d2O_jwPxroc
 * 类似的问题：
 * Numbers With Repeated Digits:
 *  https://github.com/cherryljr/LeetCode/blob/master/Numbers%20With%20Repeated%20Digits.java
 * Rotated Digits:
 *  https://github.com/cherryljr/LeetCode/blob/master/Rotated%20Digits.java
 */
class Solution {
    public int atMostNGivenDigitSet(String[] D, int N) {
        // 注意：按照上述解法分析，如果N中的每一位数均在 set D[] 中的话，那么循环将会一直遍历到 N 的最后一位。
        // 因为我们只对 <s[i] 的元素进行 add pow(D.length, n-i-1)操作，当最后一位仍然出现 D.contains(s[i]) 的情况。
        // 程序只能继续去遍历N的下一位，但是下一位已经没有了。
        // 因此，此时如果直接 return 的话，结果就会少一个数（就是N本身）
        // 即算法实际上计算的是 <N 的结果，故这里对 N 进行了一次 +1 操作。
        // 因为题目求的是 整数，所以 ans(<=N) == ans(<N+1)
        char[] s = String.valueOf(N + 1).toCharArray();
        int n = s.length, ans = 0;
        // 加上所有小于 ?XXX... 的数字个数。即不考虑最高位。
        for (int i = 1; i < n; i++) {
            ans += (int)Math.pow(D.length, i);
        }

        for (int i = 0; i < n; i++) {
            boolean prefixMatched = false;
            for (String num : D) {
                if (s[i] > num.charAt(0)) {
                    ans += (int)Math.pow(D.length, n - i - 1);
                } else if (s[i] == num.charAt(0)) {
                    // 如果当前位置上的数与num相等（存在于set之中），那么我们无法判断，只能去 check 下一位数的大小（子问题）
                    prefixMatched = true;
                    break;
                } else {
                    // 如果当前位置上的数小于num，那么直接break，因为set中后面的元素必定与当前元素要大
                    break;
                }
            }

            if (!prefixMatched) {
                // 能运行到这说明当前位置上的数不存在于 set 之中，此时已经可以确定所有 <N 的元素个数已经统计完毕
                break;
            }
        }

        return ans;
    }
}

/**
 * Approach 2: Mathematics (More Concise)
 * Approach 1 中对各个情况进行了详细的分析，但是对于 s[i] 和 D[j] 值大小的比较上（三种情况）处理并不够优雅。
 * 分析的时候，但是对于这三种情况，其实 s[i] < D[i] 和 s[i] > D[i] 在 for 循环的处理上是相同的，
 * 只有 s[i] == D[i] 时，才无法确定结果，需要继续遍历下一位上的值。
 * 因此根据这点，我们可以对代码进行一定的简化。
 *
 * 时间复杂度：O(lgN * D.length)
 * 空间复杂度：O(D.length)
 *
 * PS.我是真的不想吐槽...D里面的元素都是"1", "2", "3"...这种一个数的String...就不能给个 char[] 吗...
 */
class Solution {
    public int atMostNGivenDigitSet(String[] D, int N) {
        char[] s = String.valueOf(N + 1).toCharArray();
        int n = s.length, ans = 0;
        for (int i = 1; i < n; i++) {
            // D中所有的元素范围为 1~9，所以不需要担心最高位出现 0 的情况
            ans += (int)Math.pow(D.length, i);
        }

        Set<Character> set = new HashSet<>();
        for (String num : D) {
            set.add(num.charAt(0));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < D.length && D[j].charAt(0) < s[i]; j++) {
                ans += Math.pow(D.length, n - i - 1);
            }
            // 如果 s[i] 不在 set D[] 里面，那么状态已经可以确定，
            // 说明所有 <N 的元素个数已经统计完毕, 直接退出循环即可
            if (!set.contains(s[i])) {
                break;
            }
        }

        return ans;
    }
}