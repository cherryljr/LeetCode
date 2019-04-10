/*
Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

The replacement must be in-place, do not allocate extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
    1,2,3 → 1,3,2
    3,2,1 → 1,2,3
    1,1,5 → 1,5,1
*/

/**
 * Approach 1: Brute Force
 * Algorithm
 * In this approach, we find out every possible permutation of list formed by the elements of the given array 
 * and find out the permutation which is just larger than the given one. 
 * But this one will be a very naive approach, 
 * since it requires us to find out every possible permutation which will take really long time and the implementation is complex. 
 * Thus, this approach is not acceptable at all. Hence, we move on directly to the correct approach.
 * 
 * Complexity Analysis
 * Time complexity : O(n!). Total possible permutations is n!.
 * Space complexity : O(n). Since an array will be used to store the permutations.
 */
 
 
/**
 * Approach 2: Swap and Reverse
 * Algorithm
 * We need to find the first pair of two successive numbers a[i] and a[i−1], from the right, which satisfy a[i] > a[i-1]. 
 * Now, no rearrangements to the right of a[i−1] can create a larger permutation since that subarray consists of numbers in descending order. 
 * Thus, we need to rearrange the numbers to the right of a[i−1] including itself.
 * 
 * Now, what kind of rearrangement will produce the next larger number? 
 * We want to create the permutation just larger than the current one. 
 * Therefore, we need to swap the number a[i−1] and the number a[larger] which is just larger than itself 
 * among the numbers lying to its right section, say a[larger].
 * 
 * We swap the numbers a[i−1] and a[larger]. 
 * We now have the correct number at index i−1. 
 * But still the current permutation isn't the permutation that we are looking for. 
 * We need the smallest permutation that can be formed by using the numbers only to the right of a[i−1]. 
 * Therefore, we need to place those numbers in ascending order to get their smallest permutation.
 * But, recall that while scanning the numbers from the right, 
 * we simply kept decrementing the index until we found the pair a[i] and a[i−1] where, a[i] > a[i-1]. 
 * Thus, all numbers to the right of a[i−1] were already sorted in descending order. 
 * Furthermore, swapping a[i−1] and a[larger] didn't change that order. 
 * Therefore, we simply need to reverse the numbers following a[i−1] to get the next smallest lexicographic permutation.
 * 
 * Complexity Analysis
 * Time complexity : O(n). In worst case, only two scans of the whole array are needed.
 * Space complexity : O(1). No extra space is used. In place replacements are done.
 */
class Solution {
    public void nextPermutation(int[] nums) {
        int index = nums.length - 2;
        // find the first pair of two successive numbers nums[index] and nums[index + 1]
        // from the right, which satisfy nums[index] < [index + 1]
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }
        
        // swap the number nums[index] with the number a[larger] which is just larger than itself 
        if (index >= 0) {
            int larger = nums.length - 1;
            while (larger >= 0 && nums[larger] <= nums[index]) {
                larger--;
            }
            swap(nums, index, larger);
        }
        
        // reverse the numbers following a[index] to get the next smallest lexicographic permutation
        reverse(nums, index + 1);
    }
    
    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
