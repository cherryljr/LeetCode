/*
We have some permutation A of [0, 1, ..., N - 1], where N is the length of A.
The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
Return true if and only if the number of global inversions is equal to the number of local inversions.

Example 1:
Input: A = [1,0,2]
Output: true
Explanation: There is 1 global inversion, and 1 local inversion.

Example 2:
Input: A = [1,2,0]
Output: false
Explanation: There are 2 global inversions, and 1 local inversion.

Note:
A will be a permutation of [0, 1, ..., A.length - 1].
A will have length in range [1, 5000].
The time limit for this problem has been reduced.
 */

/**
 * Approach 1: Binary Index Tree (Same as Reverse Pairs)
 * 根据题目的定义，我们可得 global inversions 实际上就是 逆序对 的个数。
 * 因此我们可以先利用 BIT / Merge Sort 求出 global inversions(逆序对) 的个数。
 * 然后再求出 local inversions 的个数，进行对比即可。
 * local inversions 的个数因为是相邻的，所以只需要遍历一遍数组即可。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 *
 * Reverse Pairs:
 *  https://github.com/cherryljr/LintCode/blob/master/Reverse%20Pairs.java
 */
class Solution {
    public boolean isIdealPermutation(int[] A) {
        if (A == null || A.length == 0) {
            return true;
        }

        int n = A.length;
        int[] BITree = new int[n + 1];
        int globalInv = 0;
        for (int i = n - 1; i >= 0; i--) {
            globalInv += query(BITree, A[i] - 1);
            System.out.println(i + " " + query(BITree, A[i] - 1));
            update(BITree, A[i]);
        }

        int localInv = 0;
        int pre = A[0];
        for (int i = 1; i < A.length; i++) {
            if (pre > A[i]) {
                localInv++;
            }
            pre = A[i];
        }
        return localInv == globalInv;
    }

    private int query(int[] BITree, int index) {
        int sum = 0;
        for (index += 1; index > 0; index -= index & -index) {
            sum += BITree[index];
        }
        return sum;
    }

    private void update(int[] BITree, int index) {
        for (index += 1; index < BITree.length; index += index & -index) {
            BITree[index] += 1;
        }
    }
}

/**
 * Approach 2: Merge Sort
 * 与 Reverse Pairs 相同，这道题目还可以通过 Merge Sort 的做法来解决，
 * 时间复杂度和空间复杂度与 Approach 1 是相同的。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public boolean isIdealPermutation(int[] A) {
        if (A == null || A.length == 0) {
            return true;
        }

        int localInv = 0;
        int pre = A[0];
        for (int i = 1; i < A.length; i++) {
            if (pre > A[i]) {
                localInv++;
            }
            pre = A[i];
        }
        int globalInv = mergeSort(A, 0, A.length - 1);
        return globalInv == localInv;
    }

    private int mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);
        // 结果为左半段数组中的逆序对数 + 右半段数组中的逆序对数 + 左边段数组中 与 右半段数组中 的元素组成的逆序对数
        return mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right) + merge(nums, left, mid, right);
    }

    private int merge(int[] nums, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int index = 0, count = 0;
        int p1 = left, p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            if (nums[p1] <= nums[p2]) {
                helper[index++] = nums[p1++];
            } else {
                count += mid - p1 + 1;
                helper[index++] = nums[p2++];
            }
        }
        while (p1 <= mid) {
            helper[index++] = nums[p1++];
        }
        while (p2 <= right) {
            helper[index++] = nums[p2++];
        }
        System.arraycopy(helper, 0, nums, left, helper.length);

        return count;
    }
}

/**
 * Approach 3: Mathematics
 * 由题意可轻松推测出： global inversions 的个数必定 >= local inversions 的个数。
 * 并且题目的输入数据具有一定的特殊性：全部介于 0~n-1.
 * 因此如果一旦有一个数的值与其下标差值超过 1，说明必定有一个 inversions 不是 local inversions。
 * 这就会导致 local inversions 的个数小于 global inversions，此时直接返回 false 即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=3QHSJSFm0W0
 */
class Solution {
    public boolean isIdealPermutation(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(A[i] - i) > 1) {
                return false;
            }
        }
        return true;
    }
}