'''
There are 2N people a company is planning to interview. The cost of flying the i-th person to city A is costs[i][0],
and the cost of flying the i-th person to city B is costs[i][1].

Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.

Example 1:
Input: [[10,20],[30,200],[400,50],[30,20]]
Output: 110
Explanation:
The first person goes to city A for a cost of 10.
The second person goes to city A for a cost of 30.
The third person goes to city B for a cost of 50.
The fourth person goes to city B for a cost of 20.
The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.

Note:
    1. 1 <= costs.length <= 100
    2. It is guaranteed that costs.length is even.
    3. 1 <= costs[i][0], costs[i][1] <= 1000
'''

# Approach: Greedy (Sorting)
# 时间复杂度：O(nlogn)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def twoCitySchedCost(self, costs: List[List[int]]) -> int:
        # 利用 [0,N/2] 整除 N/2 的结果均为 0，[N/2+1, N] 整除 N/2 的结果均为 1 的特点，完成1行代码的压缩
        return sum([v[i // (len(costs)//2)] for i, v in enumerate(sorted(costs, key=lambda cost: cost[1] - cost[0], reverse=True))])