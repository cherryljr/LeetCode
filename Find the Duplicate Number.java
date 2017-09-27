There are two solutions for this question.

Solution 1: BinarySearch  O(nlogn)
At first the search space is numbers between 1 to n. 
Each time I select a number mid (which is the one in the middle) and count all the numbers equal to or less than mid. 
Then if the count is more than mid, the search space will be [1 mid] otherwise [mid+1 n]. 
I do this until search space is only one number.
Example:
Let's say n=10 and I select mid=5. 
Then I count all the numbers in the array which are less than equal mid. 
If the there are more than 5 numbers that are less than 5, then by Pigeonhole Principle 
(https://en.wikipedia.org/wiki/Pigeonhole_principle) one of them has occurred more than once. 
So I shrink the search space from [1 10] to [1 5]. 
Otherwise the duplicate number is in the second half so for the next step the search space would be [6 10].

Solution 2: Two Pointers  O(n)
The main idea is the same with problem Linked List Cycle II,https://leetcode.com/problems/linked-list-cycle-ii/. 
Use two pointers the fast and the slow. The fast one goes forward two steps each time, 
while the slow one goes only step each time. They must meet the same item when slow==fast. 
In fact, they meet in a circle, the duplicate number must be the entry point of the circle when visiting the array from nums[0]. 
Next we just need to find the entry point. We use a point(we can use the fast one before) to visit form begining with one step each time, 
do the same job to slow. When fast==slow, they meet at the entry point of the circle.

/*
Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
prove that at least one duplicate number must exist. Assume that there is only one duplicate number, 
find the duplicate one.

Note:
You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2).
There is only one duplicate number in the array, but it could be repeated more than once.
*/

// Solution 1: BinarySearch  O(nlogn)
class Solution {
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        
        int start = 1;
        int end = nums.length;
        while (start < end) {
            int count = 0;
            int mid = start + (end - start) / 2;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= mid) {
                    count++;
                }
            }
            
            if (count <= mid) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        
        return start;
    }
}

// Solution 2: Two Pointers  O(n)
class Solution {
    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int slow = n;
        int fast = n;
        do {
            slow = nums[slow-1];
            fast = nums[nums[fast-1]-1];
        } while (slow != fast);
        slow = n;
        while (slow != fast) {
            slow = nums[slow-1];
            fast = nums[fast-1];
        }
        return slow;
    }
}