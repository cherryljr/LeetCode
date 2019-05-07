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
        ans, queue, visited = [], [[r0, c0]], set([(r0, c0)])
        for r, c in queue:
            ans.append([r, c])
            for i, j in [[0, -1], [0, 1], [-1, 0], [1, 0]]:
                nextR, nextC = r + i, c + j
                if nextR < 0 or nextR >= R or nextC < 0 or nextC >= C or (nextR, nextC) in visited:
                    continue
                queue.append([nextR, nextC])
                visited.add((nextR, nextC))
        return ans


# Approach 2: Sort Distance
# 可以直接计算出每个点到 (r0, c0) 的距离，然后依次进行排序即可
# 时间复杂度：O(R * C * log(R*C))
# 空间复杂度：O(R * C)
class Solution:
    def allCellsDistOrder(self, R: int, C: int, r0: int, c0: int) -> List[List[int]]:
        def dists(points):
            return abs(points[0] - r0) + abs(points[1] - c0)
        
        points = [(i, j) for i in range(R) for j in range(C)]
        return sorted(points, key = dists)