/*
Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9
*/

// Approach 1: Dynamic Programming
/*
    dp[n] indicates that the perfect squares count of the given n, and we have:
    
    dp[0] = 0 
    dp[1] = dp[0]+1 = 1
    dp[2] = dp[1]+1 = 2
    dp[3] = dp[2]+1 = 3
    dp[4] = Min{ dp[4-1*1]+1, dp[4-2*2]+1 } 
          = Min{ dp[3]+1, dp[0]+1 } 
          = 1				
    dp[5] = Min{ dp[5-1*1]+1, dp[5-2*2]+1 } 
          = Min{ dp[4]+1, dp[1]+1 } 
          = 2
                            .
                            .
                            .
    dp[13] = Min{ dp[13-1*1]+1, dp[13-2*2]+1, dp[13-3*3]+1 } 
           = Min{ dp[12]+1, dp[9]+1, dp[4]+1 } 
           = 2
                            .
                            .
                            .
    dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1
   
    According to this conclusion above. The Code is like below.
*/
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            int j = 1; 
            while (i - j * j >= 0) {
                min = Math.min(min, dp[i - j * j] + 1);
                j++;
            }
            dp[i] = min;
        }
        
        return dp[n];
    }
}

// Approach 2: Mathematical Solution -- Legendre's three-square theorem
/* 
   You can get more detials from here: https://en.wikipedia.org/wiki/Legendre%27s_three-square_theorem
   About Bit Operation,  a % (2^n) equals to a & (2^n - 1)
   if you can't understand it, you can click here: http://blog.sina.com.cn/s/blog_417424300100ew8z.html
   More Applications about Bit Operation: http://blog.csdn.net/black_ox/article/details/46411997
*/
class Solution {
    public int numSquares(int n) {
        // Based on Lagrange's Four Square theorem,
        // there are only 4 possible results: 1, 2, 3, 4. 
        
        // If n is a perfect square, return 1.
        if (isSquare(n)) {
            return 1;
        }
        
        // Check whether 2 is the result.
        int sqrt = (int)Math.sqrt(n); 
        for(int i = 1; i <= sqrt; i++)
        {  
            if (isSquare(n - i * i)) 
            {
                return 2;  
            }
        }
        
        // The result is 4 if and only if n can be written in the 
        // form of 4^a*(8*b + 7). Please refer to 
        // Legendre's three-square theorem.
        while ((n & 3) == 0) {  // n%4 == 0  
            n >>= 2;
        }
        if ((n & 7) == 7) { // n%8 == 7  
            return 4;
        }
        
        return 3;
    }
    
    private boolean isSquare(int num) {
        int square = (int)Math.sqrt(num);
        return num == square * square;
    }
}
