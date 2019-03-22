/*
X is a good number if after rotating each digit individually by 180 degrees, we get a valid number that is different from X.
Each digit must be rotated - we cannot choose to leave it alone.

A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate to each other;
6 and 9 rotate to each other, and the rest of the numbers do not rotate to any other number and become invalid.

Now given a positive number N, how many numbers X from 1 to N are good?

Example:
Input: 10
Output: 4
Explanation:
There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.

Note:
    1. N  will be in range [1, 10000].
 */

/**
 * Approach 1: Traverse (Brute Force)
 * 根据数据规模，可以直接暴力遍历所有元素，然后挨个判断即可。
 * 没啥好说的...看代码就行...
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 */
class Solution {
    public int rotatedDigits(int N) {
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            ans += isGood(i) ? 1 : 0;
        }
        return ans;
    }

    private boolean isGood (int n) {
        boolean isValid = false;
        while (n > 0) {
            int lastDigit = n % 10;
            if (lastDigit == 3 || lastDigit == 4 || lastDigit == 7) {
                return false;
            }
            if (lastDigit == 2 || lastDigit == 5 || lastDigit == 6|| lastDigit == 9) {
                isValid = true;
            }
            n = n/10;
        }
        return isValid;
    }
}

/**
 * Approach 2: Mathematics (Count Numbers)
 * 与 Numbers At Most N Given Digit Set 基本相同。
 * 不过分析上会稍微复杂一点，大家最好同时可以参考一下 Numbers With Repeated Digits。
 * 这三道问题都是属于 计数类 问题，比较相似。
 *
 * 虽然这道题目由于数据规模（只有10000），所以难度低了太多...但是实际上是存在 O(lgN) 解法的。
 * 数据规模增大之后，难度也相应提高了一些。
 * 具体做法与 Numbers At Most N Given Digit Set 基本相同。
 * 每个位上的数只可能为 可以旋转的数字(setA)，并且至少有一位上的数字必须是旋转后与自身不相等的数（setA ∩ !setB）。
 *
 * 有了上述两个 set 之后，题目就进一步向 Numbers At Most N Given Digit Set 靠近了（相当于得到了 D[]）
 * 后面的处理就大同小异了，详见代码注释。
 *
 * 时间复杂度：O(lg10 * set.size)
 * 空间复杂度：O(set.size)
 * 
 * References:
 * Numbers At Most N Given Digit Set:
 *  https://github.com/cherryljr/LeetCode/blob/master/Numbers%20At%20Most%20N%20Given%20Digit%20Set.java
 * Numbers With Repeated Digits:
 *  https://github.com/cherryljr/LeetCode/blob/master/Numbers%20With%20Repeated%20Digits.java
 */
class Solution {
    public int rotatedDigits(int N) {
        char[] s = String.valueOf(N + 1).toCharArray();
        int n = s.length, ans = 0;
        // 可以进行旋转的数字集合（increase order）
        Character[] A = {'0', '1', '2', '5', '6', '8', '9'};
        // 旋转后与本身相等的数字集合（increase order）
        Character[] B = {'0', '1', '8'};
        Set<Character> setA = new HashSet<>(Arrays.asList(A));
        Set<Character> setB = new HashSet<>(Arrays.asList(B));

        // 除去最高位(?XXX...)使得枚举的数必定小于 N，将所有情况相加
        for (int i = 1; i < n; i++) {
            // 注意最高位不能为0
            ans += 6 * Math.pow(7, i - 1) - 2 * Math.pow(3, i - 1);
        }

        // 标记高位上是否已经出现过旋转后不等于本身的数
        boolean unique = false;
        for (int i = 0; i < n; i++) {
            // 注意最高位不能为0
            for (int j = i == 0 ? 1 : 0; j < A.length && A[j] < s[i]; j++) {
                ans += Math.pow(7, n - i - 1);
                // 高位上未出现过旋转后不等于本身的数，且当前枚举的元素存在于 setB 中
                if (setB.contains(A[j]) && !unique) {
                    ans -= Math.pow(3, n - i - 1);
                }
            }
            // 如果当前位置上的元素不存在于 setA 中，说明所有可能的数已经全部累加完毕，后面不可能出现满足条件的数
            if (!setA.contains(s[i])) {
                break;
            }
            if (!setB.contains(s[i])) {
                unique = true;
            }
        }

        return ans;
    }
}

/**
 * Approach 3: Mathematics (More Concise)
 * 介于这道题目与 Numbers At Most N Given Digit Set 这么像，写好的代码不拿来重用不是可惜了吗。
 * 并且我相信反应灵敏的朋友们在看完 Numbers With Repeated Digits 中提到的“思路转换”后已经知道我要怎么写了...
 *
 * 我们发现求 setA ∩ !setB 在代码上还是有点点麻烦，不够优雅。
 * 因此我们可以这么想：
 *  1. 先把全集（所有使用setA中元素组成的 不大于N 的元素个数）求出来，记为 ansA；
 *  2. 然后把想要剔除的集合（所有使用setB中元素组成的 不大于N 的元素个数）求出来，记为 ansB；
 *  3. 最后结果就可以表示为 ansA - ansB 了
 * 而对于“求出所有使用集合set[]中元素组成的 不大于N 的元素个数” 这个做法可以完全重用 Numbers At Most N Given Digit Set 中的代码。
 *
 * 时间复杂度：O(lg10 * set.size)
 * 空间复杂度：O(set.size)
 */
class Solution {
    public int rotatedDigits(int N) {
        Character[] A = {'0', '1', '2', '5', '6', '8', '9'};
        Character[] B = {'0', '1', '8'};
        char[] s = String.valueOf(N + 1).toCharArray();
        return getCount(s, A) - getCount(s, B);
    }

    // reuse the code of Numbers At Most N Given Digit Set
    private int getCount(char[] s, Character[] arr) {
        int n = s.length, ans = 0;
        for (int i = 1; i < n; i++) {
            ans += (arr.length- 1) * Math.pow(arr.length, i - 1);
        }

        Set<Character> set = new HashSet<>(Arrays.asList(arr));
        for (int i = 0; i < n; i++) {
            for (int j = i == 0 ? 1 : 0; j < arr.length && arr[j] < s[i]; j++) {
                ans += Math.pow(arr.length, n - i - 1);
            }
            if (!set.contains(s[i])) {
                break;
            }
        }

        return ans;
    }
}

