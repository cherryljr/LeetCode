/*
You are given an integer array nums and you have to return a new counts array. 
The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

Example:

Given nums = [5, 2, 6, 1]

To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element.
Return the array [2, 1, 1, 0].
*/

/**
 * Approach: Binary Index Tree
 * It's almost the same as the question "Count of Smaller Number before itself" in LintCode except the order.
 * You can get detail explanations here:
 * https://github.com/cherryljr/LintCode/blob/master/Count%20of%20Smaller%20Number%20before%20itself.java
 * 
 * This problem can be convert to a question about query a sum in rang [1...nums[i]'s diff].
 * So we can also use BIT to solve this problem,
 * yet BIT is the best solution of this question.
 * You can get the code with detail comments below.
 *
 * If you want to get more information about Binary Index Tree.
 * You can get it from here:
 * https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 */
class Solution {
    int[] BITree;

    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<Integer>();
        }

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        // Get the minValue of array nums[]
        for (int i : nums) {
            minValue = Math.min(minValue, i);
        }

        int[] diff = new int[nums.length];
        // Get the difference of minValue and nums[i]
        // and store the max difference value
        for (int i = 0; i < nums.length; i++) {
            // the difference will be the index of BITree's indices.
            // cuz the BITree's size is one bigger than maxValue, we should add 1 on the diff
            diff[i] = nums[i] - minValue + 1;
            maxValue = Math.max(maxValue, diff[i]);
        }

        this.BITree = new int[maxValue + 1];
        Integer[] rst = new Integer[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            // query the number of smaller element than nums[i]
            // it means that we should sum all the number in range [1...diff[i]-1]
            // so we can use BIT to do this
            rst[i] = query(diff[i] - 1);
            // update the BITree[diff[i]]
            update(diff[i]);
        }

        return Arrays.asList(rst);
    }

    private void update(int index) {
        // the index shouldn't be bigger than maxValue
        while (index < BITree.length) {
            BITree[index]++;
            index += index & -index;
        }
    }

    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }
        return sum;
    }
}

/**
 * Approach 2: Binary Index Tree (with Discretization)
 * We can save more extra space with the help of Discretization.
 * But it will also cost more time.
 * Using it or not depending on the source of data.
 *
 * You can get more detail explanations here:
 * https://github.com/cherryljr/LintCode/blob/master/Count%20of%20Smaller%20Number%20before%20itself.java
 */
class Solution {
    int[] BITree;

    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<Integer>();
        }

        int len = nums.length;
        // Discretization
        int[] sorted_arr = Arrays.copyOf(nums, len);
        Arrays.sort(sorted_arr);
        int[] discre = new int[len];
        for (int i = 0; i < len; i++) {
            discre[i] = Arrays.binarySearch(sorted_arr, nums[i]) + 1;
        }

        this.BITree = new int[len + 1];
        Integer[] rst = new Integer[len];
        for (int i = nums.length - 1; i >= 0; i--) {
            // query the number of smaller element than nums[i]
            // it means that we should sum all the number in range [1...discre[i]-1]
            // so we can use BIT to do this
            rst[i] = query(discre[i] - 1);
            // update the BITree[discre[i]]
            update(discre[i]);
        }

        return Arrays.asList(rst);
    }

    private void update(int index) {
        // the index shouldn't be bigger than BITree.length (nums.length)
        while (index < BITree.length) {
            BITree[index]++;
            index += index & -index;
        }
    }

    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }
        return sum;
    }
}

