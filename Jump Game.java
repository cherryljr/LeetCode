Greedy
Idea is to work backwards from the last index. Keep track of the smallest index that can "jump" to the last index. 
Check whether the current index can jump to this smallest index.

/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.
Each element in the array represents your maximum jump length at that position.
Determine if you are able to reach the last index.

For example:
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.
*/

class Solution {
    public boolean canJump(int A[]) {
        int last = A.length - 1;
        for(int i = n - 2; i >= 0; i--) { 
            if(A[i] + i >= last) {
                last = i;
            }
        }
        return last <= 0;
    }
}