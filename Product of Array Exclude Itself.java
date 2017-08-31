前后遍历的方法
Solution : O(N)
	首先对List进行一次 从前往后 的一次遍历。得到除去第 i 个数值的，左边 i-1 个数值的积。
	然后对List进行一次 从后往前 的一次遍历。得到出去第 i 个数值的，右边 i-1 个数字的积。
	最后将各个位置上对应的 左边数值积 乘以 右边数值积 便可以得到最终的结果。

/*
Given an array of n integers where n > 1, nums, 
return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Solve it without division and in O(n).

For example, given [1,2,3,4], return [24,12,8,6].

Follow up:
Could you solve it with constant space complexity?
 (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
*/

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] rst = new int[nums.length];
        if (nums == null || nums.length == 0) {
            return rst;
        }
        
        rst[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            rst[i] = rst[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            rst[i] *= right;
            right *= nums[i];
        }
        
        return rst;
    }
}