'''
In a 1 million by 1 million grid, the coordinates of each grid square are (x, y) with 0 <= x, y < 10^6.
We start at the source square and want to reach the target square.
Each move, we can walk to a 4-directionally adjacent square in the grid that isn't in the given list of blocked squares.

Return true if and only if it is possible to reach the target square through a sequence of moves.

Example 1:
Input: blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
Output: false
Explanation:
The target square is inaccessible starting from the source square, because we can't walk outside the grid.

Example 2:
Input: blocked = [], source = [0,0], target = [999999,999999]
Output: true
Explanation:
Because there are no blocked cells, it's possible to reach the target square.

Note:
    1. 0 <= blocked.length <= 200
    2. blocked[i].length == 2
    3. 0 <= blocked[i][j] < 10^6
    4. source.length == target.length == 2
    5. 0 <= source[i][j], target[i][j] < 10^6
    6. source != target
'''

# Approach: Estimate the Upper Bound + BFS
# 时间复杂度：O(B^2) => O(19900 * 2) B为障碍物个数
# 空间复杂度：O(B)
# 解法详解参考同名 java 文件
class Solution:
    def isEscapePossible(self, blocked: List[List[int]], source: List[int], target: List[int]) -> bool:
        blocked = {tuple(p) for p in blocked}
        
        def bfs(start, end):
            queue, visited = [start], set([tuple(start)])
            for r, c in queue:
                for i, j in [[0, -1], [0, 1], [-1, 0], [1, 0]]:
                    nextR, nextC = r + i, c + j
                    if 0 <= nextR < 10**6 and 0 <= nextC < 10**6 and (nextR, nextC) not in visited and (nextR, nextC) not in blocked:
                        if [nextR, nextC] == end: 
                            return True
                        queue.append([nextR, nextC])
                        visited.add((nextR, nextC))
                if len(queue) > 19900:
                    return True
            return False
        
        return bfs(source, target) and bfs(target, source)