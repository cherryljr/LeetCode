/*
Given a string s, we make queries on substrings of s.
For each query queries[i] = [left, right, k], we may rearrange the substring s[left], ..., s[right], 
and then choose up to k of them to replace with any lowercase English letter. 

If the substring is possible to be a palindrome string after the operations above, 
the result of the query is true. Otherwise, the result is false.

Return an array answer[], where answer[i] is the result of the i-th query queries[i].

Note that: Each letter is counted individually for replacement so if 
for example s[left..right] = "aaa", and k = 2, we can only replace two of the letters.
(Also, note that the initial string s is never modified by any query.)

Example :
Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
Output: [true,false,false,true,true]
Explanation:
queries[0] : substring = "d", is palidrome.
queries[1] : substring = "bc", is not palidrome.
queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
queries[3] : substring = "abcd", could be changed to "abba" which is palidrome. 
             Also this can be changed to "baab" first rearrange it "bacd" then replace "cd" with "ab".
queries[4] : substring = "abcda", could be changed to "abcba" which is palidrome.

Constraints:
    1. 1 <= s.length, queries.length <= 10^5
    2. 0 <= queries[i][0] <= queries[i][1] < s.length
    3. 0 <= queries[i][2] <= s.length
    4. s only contains lowercase English letters.
*/

/**
 * Approach 1: Count the Odd's Occurency of character
 * 题目要求：对于字符串 substring[left, right]，字符串中字符的位置可以进行任意移动，
 * 最多进行 K 的更改，求能否将该字符串转化成以回文串。
 * 因为没有了顺序的要求，所以限制能否组成回文串的因素就成了各个字符的出现次数。
 * 如果字符串长度为偶数，则不能出现 出现次数为奇数的字符；
 * 如果字符串长度为技术，则需要出现一个 出现次数为奇数的字符。
 *
 * 因为每次 query 会指定子串的左右位置，因此我们可以使用一个 count[][] 来统计：
 * 每个位置当前各个字符的出现次数。（空间换时间）
 *  count[i][j]:表示当前位置为 i 时，字符 j+'a' 的出现次数
 * 如果区间 [left, right] 出现奇数次的字符个数/2 不超过 更改次数K，则说明可以修改为回文串。
 *
 * 时间复杂度：O(26*q) q表示查询的次数
 * 空间复杂度：O(n)
 */
class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        int[][] count = new int[n + 1][26];
        for (int i = 0; i < n; i++) {
            count[i + 1] = count[i].clone();
            count[i + 1][s.charAt(i) - 'a']++;
        }
        
        List<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int odd = 0;
            for (int i = 0; i < 26; i++) {
                odd += (count[q[1] + 1][i] - count[q[0]][i]) % 2;
            }
            // odd -= (right - left + 1) % 2; 分析后该行可以被移除
            ans.add(odd / 2 <= q[2]);
        }
        return ans;
    }
}

/**
 * Approach 2: Bit + XOR Operation
 * 通过 Approach 1 中的分析，进一步观察我们发现，其实我们只关心当前位置各个字符出现次数的 奇偶性，并不需要确切的出现次数。
 * 而本题只有 26 个字符，因此自然想到可以进行 状态压缩。（一个int就够了）
 *  odds[i]：表示表示当前位置为 i 时，odds[i]>>j 为 字符(j+'a') 出现次数的 奇偶性，1代表出现奇数次，0代表出现偶数次。
 * 我们知道：只有偶数和奇数之间的差才会产生 奇数。对应到本题就是：
 * 如果 odds[left] 和 odds[right] 对应位上的数不同，就说明该位置上的字符在区间 [left, right]出现了奇数次。
 * 因为采用的是二进制表示，所以可以利用 XOR 操作来计算。
 * 最后利用 Count 1 in Binary 的方法计算异或值中有几个1（代表有几个字符出现了奇数次）
 *
 * 时间复杂度：O(q*32)
 * 空间复杂度：O(n)
 */
class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        // odds[i]: within range [0...i) of s, the jth bit of odd[i] indicates even/odd of the count of (char)(j + 'a'). 
        int[] odds = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            odds[i + 1] = odds[i] ^ 1 << s.charAt(i) - 'a';
        }
        
        List<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            // odds[q[1] + 1] ^ odds[q[0]] indicates the count of (char)(i + 'a') in substring(q[0], q[1] + 1) is even/odd.
            ans.add(Integer.bitCount(odds[q[1] + 1] ^ odds[q[0]]) / 2 <= q[2]);
        }
        return ans;
    }
}