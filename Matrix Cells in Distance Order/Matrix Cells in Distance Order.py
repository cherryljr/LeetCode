'''
We are given a matrix with R rows and C columns has cells with integer coordinates (r, c), where 0 <= r < R and 0 <= c < C.
Additionally, we are given a cell in that matrix with coordinates (r0, c0).
Return the coordinates of all cells in the matrix, sorted by their distance from (r0, c0) from smallest distance to largest distance.
Here, the distance between two cells (r1, c1) and (r2, c2) is the Manhattan distance, |r1 - r2| + |c1 - c2|.
(You may return the answer in any order that satisfies this condition.)

Example 1:
Input: R = 1, C = 2, r0 = 0, c0 = 0
Output: [[0,0],[0,1]]
Explanation: The distances from (r0, c0) to other cells are: [0,1]

Example 2:
Input: R = 2, C = 2, r0 = 0, c0 = 1
Output: [[0,1],[0,0],[1,1],[1,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2]
The answer [[0,1],[1,1],[0,0],[1,0]] would also be accepted as correct.

Example 3:
Input: R = 2, C = 3, r0 = 1, c0 = 2
Output: [[1,2],[0,2],[1,1],[0,1],[1,0],[0,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2,2,3]
There are other answers that would also be accepted as correct, such as [[1,2],[1,1],[0,2],[1,0],[0,1],[0,0]].

Note:
    1. 1 <= R <= 100
    2. 1 <= C <= 100
    3. 0 <= r0 < R
    4. 0 <= c0 < C
'''

# Approach 1: BFS
# 解法详解参考同名 java 文件
# 时间复杂度：O(R * C)
# 空间复杂度：O(R * C)
class Solution:
    def allCellsDistOrder(self, R: int, C: int, r0: int, c0: int) -> List[List[int]]:
        from collections import deque
        queue = deque()
        queue.append((r0, c0))
        visited, ans = {(r0, c0)}, []
        while queue:
            curr = queue.popleft()
            ans.append(curr)
            for d in [(0, -1), (0, 1), (-1, 0), (1, 0)]:
                nextX = curr[0] + d[0]
                nextY = curr[1] + d[1]
                if nextX < 0 or nextX >= R or nextY < 0 or nextY >= C or (nextX, nextY) in visited:
                    continue
                queue.append((nextX, nextY))
                visited.add((nextX, nextY))
        return ans

# Approach 2: Sort Distance (Brute Force)
# 可以直接计算出每个点到 (r0, c0) 的距离，然后依次进行排序即可
# 时间复杂度：O(R * C * log(R*C))
# 空间复杂度：O(R * C)
class Solution:
    def allCellsDistOrder(self, R: int, C: int, r0: int, c0: int) -> List[List[int]]:
        def dists(points):
            return abs(points[0] - r0) + abs(points[1] - c0)
        
        points = [(i, j) for i in range(R) for j in range(C)]
        return sorted(points, key = dists)