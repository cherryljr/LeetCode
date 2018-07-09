/*
You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.
The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'.
Each move consists of turning one wheel one slot.

The lock initially starts at '0000', a string representing the state of the 4 wheels.
You are given a list of deadends dead ends, meaning if the lock displays any of these codes,
 the wheels of the lock will stop turning and you will be unable to open it.

Given a target representing the value of the wheels that will unlock the lock,
return the minimum total number of turns required to open the lock, or -1 if it is impossible.

Example 1:
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation:
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".

Example 2:
Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation:
We can turn the last wheel in reverse to move from "0000" -> "0009".

Example 3:
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
Output: -1
Explanation:
We can't reach the target without getting stuck.

Example 4:
Input: deadends = ["0000"], target = "8888"
Output: -1

Note:
The length of deadends will be in the range [1, 500].
target will not be in the list deadends.
Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities '0000' to '9999'.
 */

/**
 * Approach 1: BFS
 * 求转换过去的最少步数问题，第一反应为采用 BFS 来进行解决。
 * 以 "0000" 作为起始位置开始进行遍历，然后寻找到 target 的最短路径即可。
 * 每个节点的邻居为每个位置上的数 +1, -1.
 * 这里为了保证范围落在 0~9 以内使用了 (x + MOD) % MOD => (x + 10) % 10 的方法。
 * 算是非常常用的一个小技巧，希望大家能够掌握，其次就是涉及到了 字符 与 整数 之间的转换。
 * 接下来的就是使用 BFS 的通用模板解决即可。（这里使用的是出队列时判断的方法）
 */
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        if (deadSet.contains(target) || deadSet.contains("0000")) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer("0000");
        visited.add("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) {
                    return step;
                }
                for (int pos = 0; pos < 4; pos++) {
                    char[] chars = curr.toCharArray();
                    // (chars[pos] + 1 + 10 - '0') % 10 = (chars[pos] + 11 - '0') % 10
                    chars[pos] = (char)((chars[pos] + 11 - '0') % 10 + '0');
                    String nextString = String.valueOf(chars);
                    if (!visited.contains(nextString) && !deadSet.contains(nextString)) {
                        queue.offer(nextString);
                        visited.add(nextString);
                    }
                    // (chars[pos] - 2 + 10 - '0') % 10 = (chars[pos] + 8 - '0') % 10
                    chars[pos] = (char)((chars[pos] + 8 - '0') % 10 + '0');
                    nextString = String.valueOf(chars);
                    if (!visited.contains(nextString) && !deadSet.contains(nextString)) {
                        queue.offer(nextString);
                        visited.add(nextString);
                    }
                }
            }
            step++;
        }

        return -1;
    }
}

/**
 * Approach 2: BFS (Optimized)
 * 首先，使用了 进队列 时进行判断的 BFS 模板，从而使得运行效率得到了一定的提高
 * 其次，利用了 deadSet 的空间，同时发挥 visited 的作用，使得空间上得到了一定的优化。
 * 总体运行时间为：107ms, Beats 85.06% 还是可以的。
 */
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        if (deadSet.contains(target) || deadSet.contains("0000")) {
            return -1;
        }

        if (target.equals("0000")) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                for (int pos = 0; pos < 4; pos++) {
                    char[] chars = curr.toCharArray();
                    // (chars[pos] + 1 + 10 - '0') % 10 = (chars[pos] + 11 - '0') % 10
                    chars[pos] = (char)((chars[pos] + 11 - '0') % 10 + '0');
                    String nextString = String.valueOf(chars);
                    if (nextString.equals(target)) {
                        return step + 1;
                    }
                    if (!deadSet.contains(nextString)) {
                        queue.offer(nextString);
                        deadSet.add(nextString);
                    }
                    // (chars[pos] - 2 + 10 - '0') % 10 = (chars[pos] + 8 - '0') % 10
                    chars[pos] = (char)((chars[pos] + 8 - '0') % 10 + '0');
                    nextString = String.valueOf(chars);
                    if (nextString.equals(target)) {
                        return step + 1;
                    }
                    if (!deadSet.contains(nextString)) {
                        queue.offer(nextString);
                        deadSet.add(nextString);
                    }
                }
            }
            step++;
        }

        return -1;
    }
}

/**
 * Approach 3: Bidirectional BFS
 * 在 LeetCode 上发现还有更快的解法：
 * 双向 BFS，这个做法在 Word Search II 中也有使用到。
 * 具体可以参考：
 *  https://leetcode.com/problems/open-the-lock/discuss/110237/Regular-java-BFS-solution-and-2-end-BFS-solution-with-improvement
 */