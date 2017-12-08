/*
Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

Example 1
Input: [3,0,1]
Output: 2

Example 2
Input: [9,6,4,2,3,5,7,0,1]
Output: 8

Note:
Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/

/**
 * Approach 1： HashSet 
 * Using a HashSet to get constant time containment queries and overall linear runtime.
 * Algorithm
 * We traverse the array nums and store the elements in the set.
 * Then we iterator the set, we can easily figure out which number is missing.
 * 
 * Complexity Analysis
 * Time complexity : O(n)
 * Because the set allows for O(1) containment queries, the main loop runs in O(n) time. 
 * Creating num_set costs O(n) time, as each set insertion runs in amortized O(1) time,
 * so the overall runtime is O(n+n) = O(n).
 * Space complexity : O(n)
 * nums contains n-1 distinct elements, so it costs O(n) space to store a set containing all of them.
 */
class Solution {
    public int missingNumber(int[] nums) {
        Set<Integer> numSet = new HashSet<Integer>();
        // store the elements in nums
        for (int num : nums) {
            numSet.add(num);
        }

        int expectedNumCount = nums.length + 1;
        // iterator the set to find the missing number
        for (int number = 0; number < expectedNumCount; number++) {
            if (!numSet.contains(number)) {
                return number;
            }
        }
        return -1;
    }
}

/**
 * Approach 2: Bit Manipulation 
 * Intuition
 * We can harness the fact that XOR is its own inverse to find the missing element in linear time.
 * Algorithm
 * Because we know that nums contains n numbers and that 
 * it is missing exactly one number on the range [0..n−1], 
 * we know that n definitely replaces the missing number in nums. 
 * Therefore, if we initialize an integer to n and XOR it with every index and value, 
 * we will be left with the missing number. 
 * 
 * Consider the following example 
 * (the values have been sorted for intuitive convenience, but need not be):
 *      Index   0   1   2   3
 *      Value   0   1   3   4
 *      missing = 4^(0^0)^(1^1)^(2^3)^(3^4)
 *              = (4^4)^(0^0)^(1^1)^(3^3)^2
 *              =0^0^0^0^2
 *              =2
 *              
 * Complexity Analysis
 * Time complexity : O(n)
 * Assuming that XOR is a constant-time operation, 
 * this algorithm does constant work on n iterations, so the runtime is overall linear.
 * Space complexity : O(1)
 * This algorithm allocates only constant additional space.
 */
class Solution {
    public int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }
}

/**
 * Approach 3: Gauss' Formula
 * Intuition
 * One of the most well-known stories in mathematics is of a young Gauss, 
 * forced to find the sum of the first 100 natural numbers. 
 * Rather than add the numbers by hand, 
 * he deduced a closed-form expression for the sum. You can see the formula below:
 * ∑​ni=​n(n+1)/2
 * https://brilliant.org/wiki/sum-of-n-n2-or-n3/
 * 
 * Algorithm
 * We can compute the sum of nums in linear time, 
 * and by Gauss' formula, we can compute the sum of the first n natural numbers in constant time. 
 * Therefore, the number that is missing is simply the result of Gauss' formula minus the sum of nums, 
 * as nums consists of the first n natural numbers minus some number.
 * 
 * Complexity Analysis
 * Time complexity : O(n)
 * Although Gauss' formula can be computed in O(1) time, summing nums costs us O(n) time, 
 * so the algorithm is overall linear. 
 * Because we have no information about which number is missing, 
 * an adversary could always design an input for which any algorithm 
 * that examines fewer than n numbers fails. 
 * Therefore, this solution is asymptotically optimal.
 * Space complexity : O(1)
 * This approach only pushes a few integers around, so it has constant memory usage.
 */
class Solution {
    public int missingNumber(int[] nums) {
        int expectedSum = nums.length * (nums.length + 1) / 2;
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        return expectedSum - actualSum;
    }
}