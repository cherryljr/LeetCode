/*
Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.
Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2.
Elements that don't appear in arr2 should be placed at the end of arr1 in ascending order.

Example 1:
Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
Output: [2,2,2,1,4,3,3,9,6,7,19]

Constraints:
    1. arr1.length, arr2.length <= 1000
    2. 0 <= arr1[i], arr2[i] <= 1000
    3. Each arr2[i] is distinct.
    4. Each arr2[i] is in arr1.
 */

/**
 * Approach: Similar to BucketSort
 * 因为题目明确给出了数值的范围 0~1000 
 * 因此可以利用类似桶排序的方法进行解决。
 * 首先统计 arr1 中各个元素出现的次数，然后遍历 arr2
 * 先把 arr2 中出现的元素，按照顺序填到结果中，然后再遍历一次 count[]
 * 把剩余的元素按照从小到大填入结果中即可。
 * 
 * 时间复杂度：O(1001) ==> O(1)
 * 空间复杂度：O(1001) ==> O(1)
 * 
 * PS.如果题目没有给出具体的数值范围，则利用 TreeMap 进行统计即可。
 */
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] count = new int[1001];
        for (int num : arr1) {
            count[num] += 1;
        }

        int index = 0, ans[] = new int[arr1.length];
        for (int num : arr2) {
            while (count[num]-- > 0) {
                ans[index++] = num;
            }
        }
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                ans[index++] = i;
            }
        }
        return ans;
    }
}