/*
Given a non-empty integer array, find the minimum number of moves required to make all array elements equal, 
where a move is incrementing a selected element by 1 or decrementing a selected element by 1.
You may assume the array's length is at most 10,000.

Example:
Input:
[1,2,3]
Output:
2

Explanation:
Only two moves are needed (remember each move increments or decrements one element):
[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
*/

/**
 * 对于II，我们尝试继续用I的思路，但这一次我们增加了“-”操作。
 * 一个自然的想法是，我们尝试把所有数字都往“中间”靠拢，也就是把中位数作为其他数转换的目标，接下来我们来证明这个结论。
 * 
 * 假设数组中有n个元素，各个元素为A[1....N]，我们把目标值(所有值转换的目标，下同)初始值设为 X=MIN_VALUE (使这N个元素数轴上均在X右边)，
 * 操作数 sum=∑(A[i]-X) 这时我们把X右移一位，可得 sum=∑(A[i]-(X+1))=∑(A[i]-X)-N，
 * 我们发现，X每往右边移动一位(X始终小于min(A[i]))，答案都会减少N。
 * 这时，我们把X移到min(A[i])处继续往右移，当X位于[min(A[i]),max(A[i])]时，每向右移一位，
 * 对于X左边的数来说，它们需要操作的次数都会 +1，对于X右边的数来说，它们需要的操作次数都会 -1。
 * 
 * 设X左边有L个数，右边有R个数。
 * 每次右移后 sum=sum+L-R，很明显，随着X的右移，R越来越小，L越来越大，当R大于L时，sum逐渐减小，
 * 当R小于L时，sum逐渐增大，我们此时就要找到 L==R 的时刻，即sum取最小的时刻，
 * 很明显，目标值X就是这个数组的 中位数 (当N为偶数时，目标值X为[A[N/2], A[N/2+1]中的任意值)。
 * 
 * 此时我们的目标就是如何找到这个中位数？最简单的方法是排序这个数组，中位数即A
 *  1. 最简单的方法是排序这个数组，中位数即A[N/2]，时间复杂度为 O(nlogn) -- Method 1
 *  2. 或者可以用快速排序的思想 -- 快速选择算法来取得这个中位数，时间复杂度为O(n) -- Method 2
 *  3. 但是由于快速选择算法可能存在最坏的情况导致 O(n^2) 的时间复杂度，故我们可以选择使用更优的算法：
 *  BFPRT算法来实现（著名的TopK问题）
 */

/**
 * Approach 1: QuickSort
 * Time Complexity: O(nlogn) (Average); O(n^2) worst case runnig time
 */
class Solution {
    public int minMoves2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);
        int mid = nums[nums.length / 2];
        int sum = 0;
        for (int i : nums) {
            sum += Math.abs(i - mid);
        }
        return sum;
    }
}

/**
 * Approach 2: QuickSelect
 * This question is very similar to:
 * https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 *
 * Get more details about Partition function here:
 * https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 *
 * Time Complexity: O(n) best case, O(n^2) worst case runnig time
 * Space Complexity: O(1) extra memory
 */
class Solution {
    public int minMoves2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        int mid = quickselect(nums, 0, nums.length - 1, nums.length / 2);  // get the median
        for (int i : nums) {
            sum += Math.abs(i - mid);
        }
        return sum;
    }

    private int quickselect(int[] nums, int start, int end, int k) {
        if (start == end) {
            return nums[start];
        }

        int position = partition(nums, start, end);
        if (position == k) {
            return nums[position];
        } else if (position < k) {
            // Check the right part
            return quickselect(nums, position + 1, end, k);
        } else {
            // Check the left part
            return quickselect(nums, start, position - 1, k);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int less = left - 1;
        int more = right;
        // Take nums[right] as the pivot

        while (left < more) {
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
            } else if (nums[left] > nums[right]) {
                swap(nums, --more, left);
            } else {
                left++;
            }
        }
        // 最后,交换 nums[right] (pivot) 和 nums[more]
        swap(nums, more, right);

        // 返回此时pivot的 index
        return less + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


