'''
You have a set of tiles, where each tile has one letter tiles[i] printed on it.
Return the number of possible non-empty sequences of letters you can make.

Example 1:
Input: "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".

Example 2:
Input: "AAABBC"
Output: 188

Note:
    1. 1 <= tiles.length <= 7
    2. tiles consists of uppercase English letters.
'''

# Approach: Backtracking (By Counting Values)
# 时间复杂度：O(1! + 2! + 3! + 4! + ... + n!)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def numTilePossibilities(self, tiles: str) -> int:
        counter = collections.Counter(tiles)
        
        def dfs(counter) -> int:
            ans = 0
            for k, v in counter.items():
                if v == 0: continue
                counter[k] -= 1
                ans += 1
                ans += dfs(counter)
                counter[k] += 1
            return ans

        return dfs(counter)

# Approach: Using itertools.permutations Method
# 时间复杂度：O(1! + 2! + 3! + 4! + ... + n!)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
# 
# itertools.permutations的用法可以参考：
#  https://docs.python.org/zh-cn/3/library/itertools.html#itertools.permutations
class Solution:
    def numTilePossibilities(self, tiles: str) -> int:
        ans = 0
        for i in range(1, len(tiles) + 1):
            p = itertools.permutations(tiles, i)
            ans += len(collections.Counter(p))
        return ans