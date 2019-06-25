'''
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
'''

# Approach: Similar to Add Spaces
# 时间复杂度：O(n)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def duplicateZeros(self, arr: List[int]) -> None:
        """
        Do not return anything, modify arr in-place instead.
        """
        shift, n = 0, len(arr)
        for x in arr:
            if x == 0:
                shift += 1
        
        for i in range(n - 1, -1, -1):
            if i + shift < n:
                arr[i + shift] = arr[i]
            if arr[i] == 0:
                shift -= 1
                if shift + i < n:
                    arr[shift + i] = arr[i]