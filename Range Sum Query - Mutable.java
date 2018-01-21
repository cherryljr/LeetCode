/*
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

The update(i, val) function modifies nums by updating the element at index i to val.
Example:
Given nums = [1, 3, 5]

sumRange(0, 2) -> 9
update(1, 2)
sumRange(0, 2) -> 8

Note:
The array is only modifiable by the update function.
You may assume the number of calls to update and sumRange function is distributed evenly.
*/

/**
 * Approach: Segment Tree
 * The same as "Interval Sum II" in LintCode.
 * You can get detail explanations from here:
 * 
 */
class NumArray {
    class SegmentTreeNode {
        int start, end, sum;
        SegmentTreeNode left, right;
        SegmentTreeNode(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
            this.left = this.right = null;
        }
    }

    SegmentTreeNode root;
    public NumArray(int[] nums) {
        root = buildHelper(nums, 0, nums.length - 1);
    }
    
    private SegmentTreeNode buildHelper(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        
        SegmentTreeNode root = new SegmentTreeNode(start, end, nums[start]);
        if (start == end) {
            return root;
        }
        int mid = start + (end - start) / 2;
        root.left = buildHelper(nums, start, mid);
        root.right = buildHelper(nums, mid + 1, end);
        root.sum = 0;
        if (root.left != null) {
            root.sum += root.left.sum;
        }
        if (root.right != null) {
            root.sum += root.right.sum;
        }
        
        return root;
    }
    
    public void update(int i, int val) {
        modify(root, i, val);
    }
    
    private void modify(SegmentTreeNode root, int index, int val) {
        if (root.start ==index && root.end == index) {
            root.sum = val;
            return;
        }
        
        int mid = root.start + (root.end - root.start) / 2;
        if (index <= mid) {
            modify(root.left, index, val);
        } else {
            modify(root.right, index, val);
        }
        root.sum = root.left.sum + root.right.sum;
    }
    
    public int sumRange(int i, int j) {
        return query(root, i, j);
    }
    
    private int query(SegmentTreeNode root, int start, int end) {
        if (start > end) {
            return 0;
        }
        
        if (start <= root.start && end >= root.end) {
            return root.sum;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftSum = 0, rightSum = 0;
        if (start <= mid) {
            leftSum = query(root.left, start, end);
        }
        if (end > mid) {
            rightSum = query(root.right, start, end);
        }
        return leftSum + rightSum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
