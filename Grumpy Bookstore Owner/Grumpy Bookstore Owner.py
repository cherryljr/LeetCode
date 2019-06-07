'''
Today, the bookstore owner has a store open for customers.length minutes.
Every minute, some number of customers (customers[i]) enter the store, and all those customers leave after the end of that minute.

On some minutes, the bookstore owner is grumpy.
If the bookstore owner is grumpy on the i-th minute, grumpy[i] = 1, otherwise grumpy[i] = 0.
When the bookstore owner is grumpy, the customers of that minute are not satisfied, otherwise they are satisfied.

The bookstore owner knows a secret technique to keep themselves not grumpy for X minutes straight, but can only use it once.
Return the maximum number of customers that can be satisfied throughout the day.

Example 1:
Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
Output: 16
Explanation: The bookstore owner keeps themselves not grumpy for the last 3 minutes.
The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.

Note:
    1. 1 <= X <= customers.length == grumpy.length <= 20000
    2. 0 <= customers[i] <= 1000
    3. 0 <= grumpy[i] <= 1
'''

# Approach: Sliding Window (Fixed Length)
# 时间复杂度：O(n)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def maxSatisfied(self, customers: List[int], grumpy: List[int], X: int) -> int:
        satisfied, useTechnique, maxi = 0, 0, 0
        for i in range(len(customers)):
            if grumpy[i] == 0:
                satisfied += customers[i]
            else:
                useTechnique += customers[i]
            if i >= X:
                useTechnique -= grumpy[i - X] * customers[i - X]
            maxi = max(maxi, useTechnique)
        return satisfied + maxi