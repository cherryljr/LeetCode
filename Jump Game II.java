/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.
Each element in the array represents your maximum jump length at that position.
Determine if you are able to reach the last index.

For example:
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.
*/

// Approach 1: DP O(n^2) (TLE)
Algorithm:
与Jump Game分析相同
Sequence Dynamic Programming
	算法时间复杂度为：O(N^2)
	最小值问题，并且数组不能够交换位置 =>  Sequence DP
State:
	f[i]表示从起点跳到第i个位置最少需要几步
Function:
	当第j个位置是可以被跳到的 && j < i && j能够跳到i, 即f[j] + j >= i 时
	f[i] = Math.min(f[i], f[j] + 1)
Initialize: 
	f[0] = 0
Answer:
	f[n - 1]

// Code Below
class Solution {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // State
        int[] dp = new int[nums.length];
        
        // Initialize
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        
        // Function
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] + j >= i && dp[j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[j] + 1, dp[i]);
                }  
            }
        }
        
        // Answer
        return dp[nums.length - 1];
    }
}


// Approach 2: BFS O(n)
Algorithm:
Change this problem to a BFS problem, where nodes in level i are all the nodes that can be reached in i-1th jump. 
For example. 2 3 1 1 4 , is
2||
3 1||
1 4 ||
clearly, the minimum jump of 4 is 2 since 4 is in level 3. 

// Code Below
class Solution {
    public int jump(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        
        int level = 0, currentMax = 0;
        int i = 0, nextMax = 0;
        // nodes count of current level > 0
        while (currentMax - i + 1 > 0) {		
            level++;
            // traverse current level , and update the max reach of next level
            for (; i <= currentMax; i++) {	
                nextMax = Math.max(nextMax, nums[i] + i);
                // if last element is in level + 1,  then the min jump = level 
                if (nextMax >= nums.length - 1) {
                    return level;   
                }
            }
            currentMax = nextMax;
        }
        
        return 0;
    }
}

// Approach 3: Greedy O(n)
Algorithm:
The main idea is based on greedy. 
Let's say the range of the current jump is [curBegin, curEnd], 
curFarthest is the farthest point that all points in [curBegin, curEnd] can reach. 
nce the current point reaches curEnd, then trigger another jump, and set the new curEnd with curFarthest, then keep the above steps.

// Codes Below
class Solution {
    public int jump(int[] nums) {
        int jumps = 0, curEnd = 0, curFarthest = 0;
        
        for (int i = 0; i < nums.length - 1; i++) {
            curFarthest = Math.max(curFarthest, i + nums[i]);
            if (i == curEnd) {
                jumps++;
                curEnd = curFarthest;
            }
        }
        
        return jumps;
    }
}