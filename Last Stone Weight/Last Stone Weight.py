'''
We have a collection of rocks, each rock has a positive integer weight.
Each turn, we choose the two heaviest rocks and smash them together.
Suppose the stones have weights x and y with x <= y.  The result of this smash is:
    If x == y, both stones are totally destroyed;
    If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)

Example 1:
Input: [2,7,4,1,8,1]
Output: 1
Explanation:
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.

Note:
    1. 1 <= stones.length <= 30
    2. 1 <= stones[i] <= 1000
'''

# Approach: Heapify
# Python中需要使用 heapq 来进行堆的操作。
# 这里为了方便起见，将数值转换成对应的负数，然后构建 最小堆。
# 最后返回的结果为负数，加个负号即可
# 
# 时间复杂度：O(nlogn)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def lastStoneWeight(self, stones: List[int]) -> int:
        pq = [-x for x in stones]
        heapq.heapify(pq)
        for i in range(len(stones) - 1):
            heapq.heappush(pq, heapq.heappop(pq) - heapq.heappop(pq))
        return -pq[0]