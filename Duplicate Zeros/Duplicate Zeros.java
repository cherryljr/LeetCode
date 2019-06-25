/*
Given a fixed length array arr of integers, duplicate each occurrence of zero, shifting the remaining elements to the right.
Note that elements beyond the length of the original array are not written.
Do the above modifications to the input array in place, do not return anything from your function.

Example 1:
Input: [1,0,2,3,0,4,5,0]
Output: null
Explanation: After calling your function, the input array is modified to: [1,0,0,2,3,0,0,4]

Example 2:
Input: [1,2,3]
Output: null
Explanation: After calling your function, the input array is modified to: [1,2,3]

Note:
    1. 1 <= arr.length <= 10000
    2. 0 <= arr[i] <= 9
 */

/**
 * Approach: Similar to Add Spaces
 * 题意：将数组中所有出现的 0 进行一次重复，即原本的 '0' 变成 "00"，原本的 "00" 则会变成 "0000"，
 * 而对于其他元素，就顺着往后移动即可。要求不能够使用额外空间，在原本的 arr 上进行操作。
 *
 * 对此，我们可以通过遍历两次该数据来解决这个问题。
 * 第一次遍历的时候，计算数组中有几个 0（记作 shift），这就意味着我们数组上的元素将会进行大小为 shift 的向右偏移。
 * 第二次遍历时，我们 从右向左 进行遍历，对于 非零的元素 将其进行一定的偏移放入合适的位置即可，
 * 对于 0，我们同样将其放入对应的位置后，需要对偏移量 shift 进行 -1 操作。
 *  eg. [1,0,2,3,0,4,5,0] 进行重复0的扩展后将会得到 [1,0,0,2,3,0,0,4],5,0,0 而我们需要的答案就是前 8 个元素。
 *  将其放入对应的位置上即可。
 * 这里我们第二次遍历的方向时 从右向左，是因为我们无法使用额外空间，而 从左向右 遍历会使得填充时丢失信息。
 * 和 “不使用额外空间添加空格问题” 的所使用的技巧是一样的。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public void duplicateZeros(int[] arr) {
        int shift = 0;
        // Count the number of 0s
        for (int i = 0; i < arr.length; i++) {
            shift += arr[i] == 0 ? 1 : 0;
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            // if the current value can be fit
            if (shift + i < arr.length) {
                arr[shift + i] = arr[i];
            }
            // if the current value is 0, shift--
            // and arr[shift + i] should also be 0 (duplicate)
            if (arr[i] == 0 && --shift + i < arr.length) {
                arr[shift + i] = 0;
            }
        }
    }
}