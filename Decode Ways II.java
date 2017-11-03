We can observe that the number of decodings possible upto any index i, is dependent only on the characters upto the index i and not on any of the characters following it. 
This leads us to the idea that this problem can be solved by making use of Dynamic Programming.

We can also easily observe that, the number of decodings possible upto the index i can be determined easily 
if we know the number of decodings possible upto the index i−1 and i−2. 
Thus, we fill in the dp array in a forward manner.
dp[i] is used to store the number of decodings possible by considering the characters in the given string s upto the (i−1)​th index only(including it).

The equations for filling this dp at any step again depend on the current character and the just preceding character. 
We can deduce these equations. All situations below:
No '*'
	The same as Decodes Ways.
‘*’ later:
	?*
		1. ? == '1'; dp[i] = (dp[i] + 9 * dp[i - 2]) % MOD;
		2. ? == '2'; dp[i] = (dp[i] + 6 * dp[i - 2]) % MOD;
		3. ? == '*'; dp[i] = (dp[i] + 15 * dp[i - 2]) % MOD;
		4. else, it make 0 contributions to the result.
'*' start:
	*?
		1. ? <= '6'; (dp[i] + 2 * dp[i - 2]) % MOD  
		2. ? > '6'; (dp[i] + 1 * dp[i - 2]) % MOD 

Details expliantions here (Chinese):
	https://mp.weixin.qq.com/s?__biz=MzA5MzE4MjgyMw==&mid=2649458097&idx=1&sn=362cf1535732571b6da635d6d98d954e&chksm=887eebb9bf0962afe98742ef0582c848b4e9af0bfbec4c66534d5e8c87b395142ea544a95d5b&mpshare=1&scene=1&srcid=0809vLlXjKyrC1ET50aiyASK&key=c95b8826c6a30de13093d83ff19b4428aa02e6b2ea730740d955e6bf4da1ddcb98236b971a753c69ac0e38f9f4aa37ef937b8731da9bfdbe12385e67976d1ea070117c195f53b0d6e99aeabd92995a3a&ascene=0&uin=Mjg3NzU0NTM4MA%3D%3D&devicetype=iMac+MacBook9%2C1+OSX+OSX+10.12.5+build(16F73)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=8n%2Bv%2Fri%2FMRk6LZfNLx26M4k7Pe%2BnteBhx6SgUzCgFiihw6L%2Fq9bpOntVyZTRBSKg

/*
A message containing letters from A-Z is being encoded to numbers using the following mapping way:
'A' -> 1
'B' -> 2
...
'Z' -> 26
Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.
Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
Also, since the answer may be very large, you should return the output mod 109 + 7.

Example 1:
Input: "*"
Output: 9
Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".

Example 2:
Input: "1*"
Output: 9 + 9 = 18

Note:
	The length of the input string will fit in range [1, 105].
	The input string will only contain the character '*' and digits '0' - '9'.
*/

class Solution {
    final int MOD = 1000000007;
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // State & Initialize
        int len = s.length();
        long[] dp = new long[len + 1];	// we should use long array here to avoid overfolw Integer
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : s.charAt(0) != '0' ? 1 : 0;
        
        // Function
        for (int i = 2; i <= len; i++) {
            if (s.charAt(i - 1) == '*') {
                dp[i] = 9 * dp[i - 1];
                if (s.charAt(i - 2) == '1') {
                    dp[i] = (dp[i] + 9 * dp[i - 2]) % MOD;
                } else if (s.charAt(i - 2) == '2') {
                    dp[i] = (dp[i] + 6 * dp[i - 2]) % MOD;
                } else if (s.charAt(i- 2) == '*') {
                    dp[i] = (dp[i] + 15 * dp[i - 2]) % MOD;
                }
            } else {
				// if s.charAt(i-1) != '*' && s.charAt(i-1) != '0', => s[i-1] could be decode as a character.
				// then dp[i] = dp[i-1]
				// if s.charAt(i-1) == '0', => s[i-1] couldn't be decoded as a character
				// it means it can't make any contributions to the result, so dp[i] = 0
                dp[i] = s.charAt(i - 1) != '0' ? dp[i - 1] : 0;  
                if (s.charAt(i - 2) == '1') {
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                } else if (s.charAt(i - 2) == '2' && s.charAt(i - 1) <= '6') {
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                } else if (s.charAt(i - 2) == '*') {
                    dp[i] = (dp[i] + (s.charAt(i - 1) <= '6' ? 2 : 1) * dp[i - 2]) % MOD;
                }
            }
        }
        
        return (int)dp[len];
    }
}