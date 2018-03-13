/*
Given an array of integers with possible duplicates, randomly output the index of a given target number.
You can assume that the given target number must exist in the array.

Note:
The array size can be very large. Solution that uses too much extra space will not pass the judge.

Example:

int[] nums = new int[] {1,2,3,3,3};
Solution solution = new Solution(nums);

// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
solution.pick(3);

// pick(1) should return 0. Since in the array only nums[0] is equal to 1.
solution.pick(1);
 */

/**
 * Approach: Reservoir Sampling
 * 蓄水池抽样算法.
 * 与 Linked List Random Node 是同一类型的题目。
 * 关于该算法的讲解与证明可以参考：
 *
 */
class Solution {
    int[] nums;
    Random random;

    public Solution(int[] nums) {
        this.nums = nums;
        this.random = new Random();
    }

    public int pick(int target) {
        int rst = -1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果与该 target 相等，count++，此处的 count 相当于元素总个数 N
            // 蓄水池大小仍然只有 1.
            if (nums[i] == target) {
                // nums[i] 有 1/(count+1) 的概率进入池子
                if (random.nextInt(++count) == 0) {
                    rst = i;
                }
            }
        }
        return rst;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */