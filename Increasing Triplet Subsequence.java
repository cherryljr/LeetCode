This question has three solutions.

Solution 1: DP  O(n^2)
We can solve this question by calculating the LIS of the arrays.
Then if LIS >= 3, return true; else return false;
The method is the same as LIS.
you can find it here:
https://github.com/cherryljr/LintCode/blob/master/Longest%20Increasing%20Subsequence.java

Solution 2: Binary Search & minLast Array  O(nlogn)
this is also a method to calculate the LIS of arrays, but in O(nlogn) time.
you can see more detials here:
https://github.com/cherryljr/LintCode/blob/master/Longest%20Increasing%20Subsequence.java

Solution 3: 
From the question we can see clearly that:
we just need to find whether there exists an increasing subsequence (the length >= 3)
so we just need two values (small, big), then traverse the array.
if we can a number nums[i] bigger than both, it means that samll < big < num[i].
then return true.

/*
Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

Formally the function should:
Return true if there exists i, j, k 
such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
Your algorithm should run in O(n) time complexity and O(1) space complexity.

Examples:
Given [1, 2, 3, 4, 5],
return true.

Given [5, 4, 3, 2, 1],
return false.
*/

// Solution 1: DP O(n^2)
class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        // State & Initialize
        int len = nums.length;
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            dp[i] = 1;
        }
        // Function
        int max = 1;
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                if (dp[i] > max) {
                    max = dp[i];
                }
            }   
        }
        // Answer
        return max >= 3;
    }
}

// Solution 2: Binary Search & minLast Array  O(nlogn)
class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        // Initialize minLast Array
        int len = nums.length;
        int[] minLast = new int[len];
        for (int i = 0; i < len; i++) {
            minLast[i] = Integer.MAX_VALUE;
        }
        
        for (int i = 0; i < len; i++) {
            int index = findPosition(minLast, nums[i]);
            minLast[index] = nums[i];
        }
        int max = 1;
        for (int i = len - 1; i >= 0; i--) {
            if (minLast[i] != Integer.MAX_VALUE) {
                max = i + 1;
                break;
            }
        }
        
        return max >= 3;
    }
    // Binary Search minLast Array to find the first bigger num's position than nums[i]
    public int findPosition(int[] arr, int target) {
        int start = 0; 
        int end = arr.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (arr[start] >= target) {
            return start;
        }
        return end;
    }
}

// Solution 3: Traverse the Array Once  O(n)
class Solution {
   public boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
             // update small if n is smaller than both
            if (n <= small) { 
                small = n; 
            }
            // update big only if greater than small but smaller than big
            else if (n <= big) { 
                big = n; 
            } 
            // return true if you find a number bigger than both
            else {
                return true; 
            }
        }
        return false;
    }
}