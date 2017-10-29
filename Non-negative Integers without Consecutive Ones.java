Algorithm
We start scanning the given number numnum from its MSB. 
For every 1 encountered at the i​th bit position(counting from 0 from LSB), we add a factor of f[i] to the resultant count. 
For every 0 encountered, we don't add any factor. 
We also keep a track of the last bit checked. 
If we happen to find two consecutive 1's at any time, we add the factors for the positions of both the 1's and stop the traversal immediately. 
If we don't find any two consecutive 1's, we proceed till reaching the LSB and add an extra 1 to account for the given number numnum as well, 
since the procedure considers numbers upto num without including itself.

Complexity Analysis
Time complexity  : O(log2(num_binary) (max = 32). One loop to fill f array and one loop to check all bits of num.
Space complexity : O(log2(num_binary) (max = 32). f array of size is used.

A Good Explanation Here (Chinese):
https://mp.weixin.qq.com/s?__biz=MzA5MzE4MjgyMw==&mid=2649457939&idx=1&sn=5216b29a84a66a2efb15ed046357284a&chksm=887eeb1bbf09620d326d2c3d0352dc2be2273ca96c99ccc80c148349b9ec03c00af4ecdf5d4a&mpshare=1&scene=1&srcid=0730o7P5AvTmAhFOgNy6Uu3G&key=889821a349a208415d22c64ce184d54ccdf16047d078837e42feb48f7a132977311a1b77dd5589645a27cf0219cbfda340f36b5e35cdd61a5438949ffb57819db5e1eb7cc9d46e45ffa32ff53d0cf082&ascene=0&uin=Mjg3NzU0NTM4MA%3D%3D&devicetype=iMac+MacBook9%2C1+OSX+OSX+10.12.5+build(16F73)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=oCFcf2Lgg7hO6%2BEVkc%2BvxWL6lsULDFeeUNKCkU%2BBJUWHxFvJwwy3jhLN%2FtMVnMs1
English (with vividly demo):
https://leetcode.com/articles/non-negative-integers-without-consecutive-ones/

/*
Given a positive integer n, find the number of non-negative integers less than or equal to n, 
whose binary representations do NOT contain consecutive ones.

Example 1:
Input: 5
Output: 5

Explanation: 
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule. 

Note: 1 <= n <= 10^9
*/

class Solution {
    public int findIntegers(int num) {
        if (num < 2) {
            return num + 1;
        }
        
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(num)).reverse();
        int k = sb.length();
        
        // fill f array
        int[] f = new int[k];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < k; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        
        // check all bits of num
        int rst = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (sb.charAt(i) == '1') {
                rst += f[i];
                if (i < k - 1 && sb.charAt(i + 1) == '1') { // sb.charAt(i + 1) represents the prev_bits
                    return rst;
                }
            }
        }
        return rst + 1;
    }
}