/*
Your car starts at position 0 and speed +1 on an infinite number line.  (Your car can go into negative positions.)
Your car drives automatically according to a sequence of instructions A (accelerate) and R (reverse).
When you get an instruction "A", your car does the following:
    position += speed, speed *= 2.
When you get an instruction "R", your car does the following:
    if your speed is positive then speed = -1 , otherwise speed = 1.  (Your position stays the same.)
For example, after commands "AAR", your car goes to positions 0->1->3->3, and your speed goes to 1->2->4->-1.
Now for some target position, say the length of the shortest sequence of instructions to get there.

Example 1:
Input:
target = 3
Output: 2
Explanation:
The shortest instruction sequence is "AA".
Your position goes from 0->1->3.

Example 2:
Input:
target = 6
Output: 5
Explanation:
The shortest instruction sequence is "AAARA".
Your position goes from 0->1->3->7->7->6.

Note:
1 <= target <= 10000.
 */

/**
 * Approach 1: BFS + Greedy
 * 刚刚看到这道问题的时候没什么思路，隐隐约约感觉到是DP，但是比赛时间又比较急没想出来。
 * 所以这里想着既然是找最短路径，不妨使用 BFS 暴力搜索看看。
 * 因为从数据上来看，这道题目的数据还是比较大的，所以感觉会超时，因此下面直接使用 贪心 进行了 剪枝 操作。
 *
 * BFS 基本过程就是按照模板来写，没什么特别的。值得注意的是：
 * 这里我们对时间要求比较高，所以最好在进队列的时候就检查 BFS 的结束条件，如果符合直接返回。
 * 不再是从队列中 poll 出来的时候检查结束条件。这样可以节省一些时间。
 * BFS 的时候，根据题意我们有两种操控方式：
 *  1. 'A' 加速，此时 pos1 = curr.pos + speed, speed1 = curr.speed * 2
 *  2. 'R' 反转，此时 pos2 = curr.pos, speed2 = curr.speed > 0 ? -1 : 1;
 * 依据上面这两个过程进行相应的计算，并使用 visited 记录下来即可。
 *
 * 加速的状态不记录，因为数据量太大了，记录的话会爆内存。
 * 并且通常情况下来说走到同一个位置，且速度相同的可能性应该不大。
 * 但是对于进行了 反转 操作的地方则进行了记录，因为记录它的话，我们可以不再对该位置进行一个扩展，还是能够节省挺多时间的。
 * 当然就算经过了这样处理，直接提交代码还是会超时的，因此我们需要进行 剪枝 操作。
 *
 * 贪心策略剪枝：
 *  优化1：visited一开始就加入 0_-1 反方向，即不要往反方向走
 *  优化2：位置超过 2*target 或者 小于0 时不再考虑
 *
 * 以上两点的贪心策略这里未能够给出有效证明，但是还是能 AC 的，运行时间 270ms
 */
class Solution {
    public int racecar(int target) {
        // 判断初始状态是否符合条件（使用进队列BFS时，需要额外注意这个问题）
        if (target == 0) {
            return 0;
        }
        // 数组中第一个数为 位置信息，第二个为 速度信息
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new int[]{0, 1});
        visited.add("0_1");
        // 剪枝优化
        visited.add("0_-1");
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();

                // Accelerate
                int pos1 = curr[0] + curr[1];
                int speed1 = curr[1] << 1;
                if (pos1 == target) {
                    return step + 1;
                }
                String next1 = pos1 + "_" + speed1;
                // 剪枝优化
                if (pos1 > 0 && pos1 < target << 1 && !visited.contains(next1)) {
                    queue.offer(new int[]{pos1, speed1});
                    visited.add(next1);
                }

                // Reverse
                int speed2 = curr[1] > 0 ? -1 : 1;
                String next2 = curr[0] + "_" + speed2;
                if (!visited.contains(next2)) {
                    visited.add(next2);
                    queue.offer(new int[]{curr[0], speed2});
                }
            }
            step++;
        }

        return -1;
    }
}

/**
 * Approach 2: BFS (Optimized)
 * 对于 1 中的方法，虽然能够但是多少心里还是不舒坦，既然都贪了，为什么不贪到底呢？
 * 因为我们为了记录状态而使用了 String 类型，但是我们知道在 Java 中对于 String 的操作是线性的，
 * 因此代价还是挺高的。所以我们想能不能将它转换成 Integer 类型来表示呢？
 * 但是 Accelerate 操作带来的 2*speed 影响我们不好记录，因此我们想要不干脆就不记录它了吧。
 * 我们只在 Reverse 操作的时候记录状态信息，即变成了我只在 方向翻转 的时候记录状态，
 * 从而避免在 状态翻转 点的扩展，这样还是能够省去很多时间的。
 * 并且因为 反转点 的速度只有 -1 和 1 两种，为了处理方便，我们对其进行了 +1 操作将其转成了 [0, 2]
 * 这样我们只需要 两位 就能够记录它们的状态了（00, 10）。
 * 因此我们以最低的两位来记录翻转点的速度信息，高位记录位置信息。根据题目的数据范围，32位的数据是完全足够的。
 * 所以这里就将 String 转换成了 Integer 来记录数据，省去了对字符串的操作以及 HashSet 对于字符串的查找，
 * 这会使得我们的速度提升非常多。最后验证运行时间为 90ms.
 *
 * 当然这种极端的做法比较 hack,正常情况下并不推荐这么做，也会给代码的阅读上带来困难
 */
class Solution {
    public int racecar(int target) {
        if (target == 0) {
            return 0;
        }

        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(new int[]{0, 1});
        // 利用 Integer 来替代 String 表示 位置 和 速度 信息
        // 将 speed + 1 转换成 [0, 2] 以便被低位的两位表示
        visited.add(0);
        visited.add(2);
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();

                // Accelerate
                int pos1 = curr[0] + curr[1];
                int speed1 = curr[1] << 1;
                if (pos1 == target) {
                    return step + 1;
                }
                // 剪枝优化
                if (pos1 > 0 && pos1 < target << 1) {
                    queue.offer(new int[]{pos1, speed1});
                }

                // Reverse
                int speed2 = curr[1] > 0 ? -1 : 1;
                // 位置信息左移两位腾出空间用来存储速度信息
                int key = curr[0] << 2 | (speed2 + 1);
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.offer(new int[]{curr[0], speed2});
                }
            }
            step++;
        }

        return -1;
    }
}

/**
 * Approach 3: DP
 * 本题最优的解法应该是使用 DP。时间复杂度为 O(nlogn)
 * 运行时间：14ms
 * 
 * 该解法参考：
 * https://leetcode.com/problems/race-car/solution/
 * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-818-race-car/
 */
class Solution {
    public int racecar(int target) {
        int[] dp = new int[target + 3];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; dp[1] = 1; dp[2] = 4;

        for (int t = 3; t <= target; ++t) {
            int k = 32 - Integer.numberOfLeadingZeros(t);
            // 如果 t = 2^k - 1,那么 k 步就是最短的步数(一直加速到达 t 位置即可 'AAA...')
            if (t == (1 << k) - 1) {
                dp[t] = k;
                continue;
            }

            for (int j = 0; j < k - 1; ++j) {
                dp[t] = Math.min(dp[t], dp[t - (1 << (k - 1)) + (1 << j)] + k - 1 + j + 2);
            }
            if ((1 << k) - 1 - t < t) {
                dp[t] = Math.min(dp[t], dp[(1 << k) - 1 - t] + k + 1);
            }
        }

        return dp[target];
    }
}