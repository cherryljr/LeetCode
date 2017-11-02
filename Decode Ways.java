Using a dp array of size len + 1 to save subproblem solutions. 
dp[0] means an empty string will have one way to decode, dp[1] means the way to decode a string of size 1. 
Then check one digit and two digit combination and save the results along the way. 
In the end, dp[len] will be the answer.

/*
A message containing letters from A-Z is being encoded to numbers using the following mapping:
'A' -> 1
'B' -> 2
...
'Z' -> 26
Given an encoded message containing digits, determine the total number of ways to decode it.

For example,
Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).

The number of ways decoding "12" is 2.
*/

class Solution {
    /*
     * @param s: a string,  encoded message
     * @return: an integer, the number of ways decoding
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // State & Initialize
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        
        // Function
        for (int i = 2; i <= len; i++) {
            int firstChar = Integer.valueOf(s.substring(i - 1, i)); // The ith character of String s. 
            int secondChar = Integer.valueOf(s.substring(i - 2, i));
            if (firstChar >= 1 && firstChar <= 9) {
                dp[i] += dp[i - 1];
            }
            if (secondChar >= 10 && secondChar <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[len];
    }
}