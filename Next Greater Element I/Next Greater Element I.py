'''
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. 
Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. 
If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.

Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.

Note:
    1. All elements in nums1 and nums2 are unique.
    2. The length of both nums1 and nums2 would not exceed 1000.
'''

# 解法详解参考同名 java 文件
# Approach 1:   Traverse
# 因为题目数据量只有 1000 所以写得比较 geek，数据量上去的话这个做法是过不去的
class Solution:
    def nextGreaterElement(self, nums1: List[int], nums2: List[int]) -> List[int]:
        return [next((y for y in nums2[nums2.index(x):] if y > x), -1) for x in nums1]


# Approach 2: Monotonic Stack
class Solution:
    def nextGreaterElement(self, nums1: List[int], nums2: List[int]) -> List[int]:
        allRes = {}
        stack = []
        for num in nums2:
            while stack and num > stack[-1]:
                allRes[stack.pop()] = num
            stack.append(num)
        
        return [allRes.get(num, -1) for num in nums1]