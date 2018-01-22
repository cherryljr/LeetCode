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
 * Approach 1: Segment Tree
 * The same as "Interval Sum II" in LintCode.
 * You can get detail explanations from here:
 * https://github.com/cherryljr/LintCode/blob/master/Interval%20Sum%20II.java
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
 
 
 /**
 * Approach 2: BITree
 * 求区间和（并且支持单点修改）最好的数据结构无疑是树状数组了。
 * 对于树状数组的详细分析与代码注释可以参见：Binary Index Tree Template
 * https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 * 该题与模板唯一的区别就是在于模板中 update是在原来的基础上加上 val,
 * 而本题中是将 arr[index] 改成 val.
 * 实现方法很简单，多建立一个大小与输入数组相同的数组 nums[] 用于在第一次进行初始化即可。
 * 当然我们还能通过写一个新的 init() 方法来实现 BITree 的建立，这样就能节约 O(n) 的额外空间，
 * 但同样代码的复用性就降低了。大家自行取舍即可。
 */
class NumArray {
    private int size;
    int[] BITree;
    int[] nums;

    public NumArray(int[] nums) {
        size = nums.length;
        BITree = new int[size + 1];
        this.nums = new int[size];
        // this.nums = nums;  // 不分配额外空间，使用 init() 方法建立BITree
        for (int i = 0; i < size; i++) {
            update(i, nums[i]);
            // init(i, nums[i]);
        }
    }

    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        index += 1;
        while (index <= size) {
            BITree[index] += delta;
            index += index & -index;
        }
    }

//    private void init(int index, int val) {
//        index += 1;
//        while (index <= size) {
//            BITree[index] += val;
//            index += index & -index;
//        }
//    }

    public int sumRange(int i, int j) {
        return (i > j || i < 0 || j >= size) ? 0 :
                i == j ? nums[i] : getSum(j) - getSum(i - 1);
    }

    private int getSum(int index) {
        if (index < 0 || index >= size) {
            return 0;
        }
        int sum = 0;
        index += 1;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }

        return sum;
    }
}
