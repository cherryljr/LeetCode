/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.
Each element in the array represents your maximum jump length at that position.
Determine if you are able to reach the last index.

For example:
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.
*/

Details: https://leetcode.com/articles/jump-game/

// DP O(n^2)   (TLE)
Algorithm:
Yes / No 问题，并且数组不能够交换位置 =>  Sequence DP
State:
	f[i]表示能否从起点跳到第i个位置 true or false
Function:
	f[i] = ( f[j]为true,即第j个位置是肯定可以被跳到的 && j < i && j能够跳到i, 即f[j] + j >= i )
Initialize: 
	f[0] = true
Answer:
	f[n - 1]是否为true

// Code Below
public class Solution {
    public boolean canJump(int[] A) {
        //	State
        boolean[] can = new boolean[A.length];
        
        //  Initialize
        can[0] = true;
        
        //  Function
        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                if (can[j] && A[j] + j >= i) {
                    can[i] = true;
                    break;
                }
            }
        }
        
        //  Answer
        return can[A.length - 1];
    }
}

//  Greedy O(n)
Algorithm:
We call a position in the array a "good index" if starting at that position, we can reach the last index. 
Iterating right-to-left, for each position we check if there is a potential jump that reaches a good index 
(currPosition + nums[currPosition] >= leftmostGoodIndex). 
If we can reach a GOOD index, then our position is itself GOOD. 
Also, this new GOOD position will be the new leftmost GOOD index. 
Iteration continues until the beginning of the array. 
If first position is a GOOD index then we can reach the last index from the first position.

// Code Below
public class Solution {
    public boolean canJump(int[] nums) {
        int lastPos = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        return lastPos == 0;
    }
}