Greedy

We call a position in the array a "good index" if starting at that position, we can reach the last index. 
Iterating right-to-left, for each position we check if there is a potential jump that reaches a good index 
(currPosition + nums[currPosition] >= leftmostGoodIndex). 
If we can reach a GOOD index, then our position is itself GOOD. 
Also, this new GOOD position will be the new leftmost GOOD index. 
Iteration continues until the beginning of the array. 
If first position is a GOOD index then we can reach the last index from the first position.

/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.
Each element in the array represents your maximum jump length at that position.
Determine if you are able to reach the last index.

For example:
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.
*/

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