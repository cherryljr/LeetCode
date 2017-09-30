BinarySearch  O(logn)
This question is just like: Find Minimum in Rotated Sorted Array 
https://github.com/cherryljr/LintCode/blob/master/Find%20Minimum%20in%20Rotated%20Sorted%20Array.java

But this question alse has a Brilliant solution!
you can learn more details from here:
https://discuss.leetcode.com/topic/34467/pretty-short-c-java-ruby-python

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
You are given a target value to search. If found in the array return its index, otherwise return -1.
You may assume no duplicate exists in the array.
*/

class Solution {
    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end -start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[start] <= nums[mid]) {
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        
        return nums[start] == target ? start : -1;
    }
}

// A Smart Method (Also use BinarySearch)
class Solution {
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if ((nums[0] > target) ^ (nums[0] > nums[mid]) ^ (target > nums[mid])) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }   
        }
        return lo == hi && nums[lo] == target ? lo : -1;
    }
}

