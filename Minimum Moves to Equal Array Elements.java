将 n-1 个数 加1，相当于将所有数都 加1，再将其中一个数 减去1。
将所有数都加1这个操作，其实不会改变任何数的相对大小，也就是所有数两两之间的差都是不会变的，
这对于要使所有元素均相等的目标来说没有影响，所以可以忽略这一部分。

那么问题就变成每次选个数减1来达到目标的最小次数。
要使次数最小，而且每次只能将元素减1，故应当把所有数减到与最小值相等。

若n个元素为a(0),a(1),……,a(n-1)，其中最小值为min，则答案为 a(0)+a(1)+……+a(n-1) - min*n。
只需求出n个数中的最小值以及它们的和来计算即可，时间复杂度为O(n)。

/*
Given a non-empty integer array of size n, find the minimum number of moves required to make all array elements equal, 
where a move is incrementing n - 1 elements by 1.

Example:
Input:
[1,2,3]
Output:
3

Explanation:
Only three moves are needed (remember each move increments two elements):
[1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
*/

class Solution {
    public int minMoves(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int i : nums) {
            sum += i;
            min = Math.min(min, i);
        }
        
        return sum - min * nums.length;
    }
}