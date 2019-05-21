'''
You have N gardens, labelled 1 to N.  In each garden, you want to plant one of 4 types of flowers.
paths[i] = [x, y] describes the existence of a bidirectional path from garden x to garden y.
Also, there is no garden that has more than 3 paths coming into or leaving it.

Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have different types of flowers.
Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)-th garden.
The flower types are denoted 1, 2, 3, or 4.  It is guaranteed an answer exists.

Example 1:
Input: N = 3, paths = [[1,2],[2,3],[3,1]]
Output: [1,2,3]

Example 2:
Input: N = 4, paths = [[1,2],[3,4]]
Output: [1,2,1,2]

Example 3:
Input: N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
Output: [1,2,3,4]

Note:
    1. 1 <= N <= 10000
    2. 0 <= paths.size <= 20000
    3. No garden has 4 or more paths coming into or leaving it.
    4. It is guaranteed an answer exists.
'''

# Approach: Greedy + BFS
# 时间复杂度：O(paths) = O(1.5N) ==> O(N)
# 空间复杂度：O(N)
# 解法详解参考同名 java 文件
class Solution:
    def gardenNoAdj(self, N: int, paths: List[List[int]]) -> List[int]:
        graph = [[] for _ in range(N)]
        # Build the graph
        for path in paths:
            graph[path[0] - 1].append(path[1] - 1)
            graph[path[1] - 1].append(path[0] - 1)
        
        ans = [0] * N
        for i in range(N):
        	# 利用Python中 set 可以相减的操作，从可能的颜色中排除掉已被使用的颜色，
        	# 然后从剩下的集合中 pop() 出一个作为当前的颜色即可
            ans[i] = ({1, 2, 3, 4} - {ans[j] for j in graph[i]}).pop()
        return ans