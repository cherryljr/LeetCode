/*
Rotate an array of n elements to the right by k steps.
For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].

Note:
Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.

Related problem: Rotate String (LintCode)
*/

/**
 * Approach 1:  Using Extra Space
 * Algorithm
 * We use an extra array in which we place every element of the array at its correct position
 * i.e. the number at index i in the original array is placed at the index (i+k).
 * Then, we copy the new array to the original one.
 * 
 * Complexity Analysis
 * Time complexity  : O(n). One pass is used to put the numbers in the new array.
 * And another pass to copy the new array to the original one.
 * Space complexity : O(n). Another array of the same size is used.
 */
public class Solution {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int[] a = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            a[(i + k) % nums.length] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = a[i];
        }
    }
}

/**
 * Approach 2: Using Reverse tricks (三步翻转法)
 * Algorithm
 * This approach is based on the fact that when we rotate the array k times,
 * k elements from the back end of the array come to the front and the rest of the elements from the front shift backwards.
 * In this approach, we firstly reverse all the elements of the array.
 * Then, reversing the first k elements followed by reversing the rest n−k elements gives us the required result.
 * 
 * For example
 *  Let n=7 and k=3.
 *  Original List                   : 1 2 3 4 5 6 7
 *  After reversing all numbers     : 7 6 5 4 3 2 1
 *  After reversing first k numbers : 5 6 7 4 3 2 1
 *  After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
 *  
 * Complexity Analysis
 * Time complexity  : O(n). nn elements are reversed a total of three times.
 * Space complexity : O(1). No extra space is used.
 */
class Solution {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int offset = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, offset - 1);
        reverse(nums, offset, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}

/**
 * Approach 3: Using Cyclic Replacements
 * Algorithm
 * https://leetcode.com/articles/rotate-array/
 * 
 * Complexity Analysis
 * Time complexity  : O(n). Only one pass is used.
 * Space complexity : O(1). Constant extra space is used.
 */
public class Solution {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int offset = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + offset) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}