/*
A zero-indexed array A of length N contains all integers from 0 to N-1. 
Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.

Suppose the first element in S starts with the selection of element A[i] of index = i, 
the next element in S should be A[A[i]], and then A[A[A[i]]]… 
By that analogy, we stop adding right before a duplicate element occurs in S.

Example 1:
Input: A = [5,4,0,3,1,6,2]
Output: 6
Explanation: 
A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
One of the longest S[K]:
S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}

Note:
N is an integer within the range [1, 20,000].
The elements of A are all distinct.
Each element of A is an integer within the range [0, N-1].
 */

/**
 * Approach 1: Using Visited Array
 * Use visited array to keep a track of the elements of the array which have already been visited.
 * When we add an element nums[j] to a set corresponding to any of the indices,
 * we mark its position as visited in a visited array.
 * This is done so that whenever this index is chosen as the starting index in the future,
 * we do not go for redundant count calculations, since we've already considered the elements linked with this index,
 * which will be added to a new(duplicate) set.
 * We can also observe that no two elements at indices i and j will lead to a jump to the same index k,
 * since it would require nums[i] = nums[j] = k, which isn't possible since all the elements are distinct.
 * Also, because of the same reasoning, no element outside any cycle could lead to an element inside the cycle.
 * Because of this, the use of visited array goes correctly.
 *
 * You can get more detail explanations here:
 * https://leetcode.com/articles/array-nesting/
 */
class Solution {
    public int arrayNesting(int[] nums) {
        boolean[] visited = new boolean[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                int start = nums[i], count = 0;
                do {
                    start = nums[start];
                    count++;
                    visited[start] = true;
                }
                while (start != nums[i]);
                res = Math.max(res, count);
            }
        }
        return res;
    }
}

/**
 * Approach 2: Without Using Extra Space
 * Algorithm
 * In the last approach, the visited array is used just to keep a track of the elements of the array which have already been visited.
 * Instead of making use of a separate array to keep track of the same, we can mark the visited elements in the original array nums itself.
 * Since, the range of the elements can only be between 1 to 20,000,
 * we can put a very large integer value Integer.MAX_VALUE at the position which has been visited.
 * The rest process of traversals remains the same as in the last approach.
 * 
 * Complexity Analysis
 *  Time complexity : O(n). Every element of the nums array will be considered at most once.
 *  Space complexity : O(1). Constant Space is used.
 */
class Solution {
    public int arrayNesting(int[] nums) {
        int rst = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != Integer.MAX_VALUE) {
                int start = nums[i], count = 0;
                while (nums[start] != Integer.MAX_VALUE) {
                    int temp = start;
                    start = nums[start];
                    count++;
                    nums[temp] = Integer.MAX_VALUE;
                }

                rst = Math.max(rst, count);
            }
        }
        return rst;
    }
}