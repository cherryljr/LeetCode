/*
Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it.
If no such solution, return -1.

For example, with A = "abcd" and B = "cdabcdab".

Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it;
and B is not a substring of A repeated two times ("abcdabcd").

Note:
The length of A and B will be between 1 and 10000.
 */

/**
 * Approach 1: Brute Force (StringBuilder)
 * 直接采用最直观的方法即可AC。
 *  维护一个计数器 counter,以 String A 创建一个 StringBuilder 用于扩展，
 *  首先在 StringBuilder 后面添加 A 的复制，同时 count++，直到 StringBuilder 的大小不小于 String B 为止。
 *  然后检查能否在 StringBuilder 中找到 B 作为 substring.
 *  如果能找到则直接返回count;
 *  否则继续添加一份拷贝，
 *  如果再次添加一个A后，仍然查找不到子串 B，则说明 B 无法由重复的A所组成，返回-1。
 */
class Solution {
    public int repeatedStringMatch(String A, String B) {
        if (A == null || A.length() == 0) {
            return -1;
        }

        StringBuilder sb = new StringBuilder(A);
        int count = 1;
        while (sb.length() < B.length()) {
            sb.append(A);
            count++;
        }

        // 在 StringBuilder 中查找看 B 是否为其子串
        if (sb.indexOf(B) != -1) {
            return count;
        } else {
            // 当未能成功查询到子串 B 时，有可能是我们还需要添加一份拷贝，
            // 如果再次添加一个A后，仍然查找不到子串 B，则说明 B 无法由重复的A所组成。
            return sb.append(A).indexOf(B) != -1 ? ++count : -1;
        }
    }
}

/**
 * Approach 2: KMP
 * 本道题目中涉及到了 在字符串A 中寻找子串 B 的操作。
 * 因此毫无疑问，我们可以使用 KMP 算法来对这个查找过程进行优化。
 * 时间复杂度为：O(n)
 *
 * 这道题目考察的是 KMP 算法的直接应用。
 * 与这道题类似的还有：Repeated Substring Pattern
 * 但是 Repeated Substring Pattern 的应用稍微更加灵活一些，大家可以看一看：
 * https://github.com/cherryljr/LeetCode/blob/master/Repeated%20Substring%20Pattern.java
 * 
 * KMP Template:
 * https://github.com/cherryljr/LintCode/blob/master/KMP%20Template.java
 */
class Solution {
    public int repeatedStringMatch(String A, String B) {
        if (A == null || A.length() == 0) {
            return -1;
        }

        int len = B.length();
        int count = 1;
        StringBuilder sb = new StringBuilder(A);
        while (sb.length() < len) {
            sb.append(A);
            count ++;
        }

        // 利用 KMP 算法在 StringBuilder 中查找看 B 是否为其子串
        if (kmpSearch(sb.toString(), B)) {
            return count;
        } else {
            return kmpSearch(sb.append(A).toString(), B) ? count + 1 : -1;
        }
    }

    private boolean kmpSearch(String s, String p) {
        char[] arr_s = s.toCharArray();
        char[] arr_p = p.toCharArray();
        int[] next = getNextArray(arr_p);
        int i = 0, j = 0;

        while (i < arr_s.length && j < arr_p.length) {
            if (arr_s[i] == arr_p[j]) {
                i++;
                j++;
            } else if (j > 0) {
                j = next[j];
            } else {
                i++;
            }
        }

        return j == arr_p.length ? true : false;
    }

    private int[] getNextArray(char[] arr) {
        int[] next = new int[arr.length];
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
        return next;
    }
}
