/*
Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:

The root is the maximum number in the array.
The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
Construct the maximum tree by the given array and output the root node of this tree.

Example 1:
Input: [3,2,1,6,0,5]
Output: return the tree root node representing the following tree:

      6
    /   \
   3     5
    \    / 
     2  0   
       \
        1      
Note:
The size of the given array will be in the range [1,1000].
*/

// Solution 1
Approach 1: Recursive Solution
The current solution is very simple. We make use of a function construct(nums, left, right), 
which returns the maximum binary tree consisting of numbers within the indices left and right in the given numsnums array

The algorithm consists of the following steps:
    1. Start with the function call construct(nums, 0, len). Here, n refers to the number of elements in the given numsnums array.
    Find the index, maxIndex, of the largest element in the current range of indices (l:r-1). 
    Make this largest element, nums[maxIndex] as the local root node.
    2. Determine the left child using construct(nums, left, maxIndex). 
    Doing this recursively finds the largest element in the subarray left to the current largest element.
    3. Similarly, determine the right child using construct(nums, maxIndex + 1, right).
    4. Return the root node to the calling function.
Complexity Analysis
    Time complexity : O(n^2). The function construct is called n times. 
    At each level of the recursive tree, we traverse over all the n elements to find the maximum element. 
    In the average case, there will be a log(n) levels leading to a complexity of O(nlogn). 
    In the worst case, the depth of the recursive tree can grow upto n, which happens in the case of a sorted numsnums array, 
    giving a complexity of O(n^2).
    Space complexity : O(n). 
    The size of the set can grow upto n in the worst case. 
    In the average case, the size will be log(n) for n elements in numsnums, giving an average case complexity of O(logn).
    
// Code Below
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        
        return construct(nums, 0, nums.length);
    }
    
    private TreeNode construct(int[] nums, int left, int right) {
        if (left == right) {
            return null;
        }
        
        // get the maximal element's index
        int maxIndex = maxIndex(nums, left, right);
        TreeNode root = new TreeNode(nums[maxIndex]);
        // call the construct to build left subtree recursively
        root.left = construct(nums, left, maxIndex);
        // call the construct to build right subtree recursively
        root.right = construct(nums, maxIndex + 1, right);
        return root;
    }
    
    private int maxIndex(int[] nums, int left, int right) {
        int maxIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}

// Solutoin 2
Approach 2: Using Stack to find the first bigger number in the left/right side.
This question is like constructing a MaxHeap. 
We all know that the time complexity of constructing a MaxHeap is O(n).
So is there a O(n) solution to solve this problem ? Of course, it does.
The key idea is:
    1. We scan numbers from left to right, build the tree one node by one step;
    2. We use a stack to keep some (not all) tree nodes and ensure a decreasing order;
    3. For each number, we keep pop the stack until empty or a bigger number; 
    The bigger number (if exist, it will be still in stack) its right child is current number, 
    and the last popped number (if exist) is current number's left child (temporarily, this relationship may change in the future); 
    Then we push current number into the stack.
Complexity Analysis
    Time  complexity : O(n). We only traverse the array once.
    Space complexity : O(n). The size of stack is n.
    
// Code Below
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            // if the peek element is smaller than current number,
            // then it would be the left child of current number then pop it.
            while (!stack.isEmpty() && nums[i] > stack.peek().val) {
                curr.left = stack.peek();
                stack.pop();
            }
            // the bigger number's (if exist) right chhild is current number.
            if (!stack.isEmpty()) {
                stack.peek().right = curr;
            }
            stack.push(curr);
        }
        
        // get the buttom element of stack (the largest one)
        TreeNode rst = null;
        while (!stack.isEmpty()) {
            rst = stack.pop();
        }
        return rst;
    }
}