First Method, we can deal with it by QuickSort.
Then scan the array, find the first misssing positive.
This method will cost O(nlogn) Time Complexity, O(1) Space Complexity.
Can't meet the demand.

Second Method, we can use HashMap to restore the number and its occurence.
Then scan the array, find the first missing positive.
As we all know, the operation of HashMap will cost O(1) time once.
But this method will cost O(n) extra space.
So, it can't meet the demand also.

Third Method, 
The first missing positive must be within [1, index + 1].
The reason is like you put index balls into index+1 bins, there must be a bin empty, the empty bin can be viewed as the missing number.

The main idea is: nums[index] restore the number: index+1.
each time we encounter an valid intger, 
find the correct position and swap, otherwise continue.  
Time Complexity: O(n)
Space Complxity: O(1)

/*
Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.

Your algorithm should run in O(n) time and uses constant space.
*/

class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        // Traverse the array
        for (int i = 0; i < nums.length; i++) {
		
            while (nums[i] > 0 && nums[i] < i + 1 && nums[i] != nums[nums[i] - 1]) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }
}