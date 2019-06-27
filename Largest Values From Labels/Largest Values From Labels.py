'''
We have a set of items: the i-th item has value values[i] and label labels[i].
Then, we choose a subset S of these items, such that:
|S| <= num_wanted
For every label L, the number of items in S with label L is <= use_limit.
Return the largest possible sum of the subset S.

Example 1:
Input: values = [5,4,3,2,1], labels = [1,1,2,2,3], num_wanted = 3, use_limit = 1
Output: 9
Explanation: The subset chosen is the first, third, and fifth item.

Example 2:
Input: values = [5,4,3,2,1], labels = [1,3,3,3,2], num_wanted = 3, use_limit = 2
Output: 12
Explanation: The subset chosen is the first, second, and third item.

Example 3:
Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 1
Output: 16
Explanation: The subset chosen is the first and fourth item.

Example 4:
Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 2
Output: 24
Explanation: The subset chosen is the first, second, and fourth item.

Note:
    1. 1 <= values.length == labels.length <= 20000
    2. 0 <= values[i], labels[i] <= 20000
    3. 1 <= num_wanted, use_limit <= values.length
'''

# Approach: Map and Sorting with ZIP Function
# 时间复杂度：O(nlogn)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
# 
# 关于 zip 和 zip(*) 函数的使用可以参考：
# https://blog.csdn.net/weixin_41599977/article/details/89386629
class Solution:
    def largestValsFromLabels(self, values: List[int], labels: List[int], num_wanted: int, use_limit: int) -> int:
        limit = collections.defaultdict(int)
        nodes = sorted(zip(values, labels))
        ans = 0
        while nodes and num_wanted:
            value, label = nodes.pop()
            if limit[label] < use_limit:
                ans += value
                limit[label] += 1
                num_wanted -= 1
        return ans