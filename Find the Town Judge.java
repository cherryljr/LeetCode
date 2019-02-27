/*
In a town, there are N people labelled from 1 to N.
There is a rumor that one of these people is secretly the town judge.
If the town judge exists, then:
    1. The town judge trusts nobody.
    2. Everybody (except for the town judge) trusts the town judge.
    3. There is exactly one person that satisfies properties 1 and 2.
You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.

If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.

Example 1:
Input: N = 2, trust = [[1,2]]
Output: 2

Example 2:
Input: N = 3, trust = [[1,3],[2,3]]
Output: 3

Example 3:
Input: N = 3, trust = [[1,3],[2,3],[3,1]]
Output: -1

Example 4:
Input: N = 3, trust = [[1,2],[2,3]]
Output: -1

Example 5:
Input: N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]
Output: 3

Note:
    1. 1 <= N <= 1000
    2. trust.length <= 10000
    3. trust[i] are all different
    4. trust[i][0] != trust[i][1]
    5. 1 <= trust[i][0], trust[i][1] <= N
 */

/**
 * Approach 1: Build Graph (Using HashMap)
 * 利用 Map 存储信任与被信任者的关系（类似建图）
 * key 为被信任的人， value 为信任者的集合。
 * 然后最后遍历一次Map，看是否存在一个 key 的 value 大小为 N-1.
 * 即存在一个人使得剩余的 N-1 个人都信任他。
 * 同时注意：
 *  Judge不能信任任何人。因此我们需要用一个 trustOther[] 来记录这个人是否信任了他人。
 *  来作为 Judge 的一个判断条件。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int findJudge(int N, int[][] trust) {
        // Deal with the exception
        // eg. N=1, trust={{}}
        if (trust == null || trust.length == 0) {
            return N == 1 ? 1 : -1;
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        boolean[] trustOther = new boolean[N + 1];
        for (int[] pair : trust) {
            map.computeIfAbsent(pair[1], x -> new ArrayList<>()).add(pair[0]);
            trustOther[pair[0]] = true;
        }
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() == N - 1 && !trustOther[entry.getKey()]) {
                return entry.getKey();
            }
        }
        return -1;
    }
}

/**
 * Approach 2: Build Degree Map (Using Array)
 * 题目中对于 N 和 每个人的标号已经给出了非常明确的范围。
 * 所以我们可以利用 Array 来实现，而不是去使用 Map。
 * 并且我们可以直接跳过第一步的 Build Graph 的过程。
 * 因为，我们只关心每一个node的 出度 和 入度。
 * 只要存在一个点使得其 出度==0 并且 入度==N-1，那就说明这个点就是我们的 judge。
 * 而对于出入度信息的统计，我们只需要使用 每条边 的信息即可，而正好题目给的就是这个信息。
 * （有点类似使用 BFS 进行拓扑排序时构建 indegreeMap）
 * 介于这个结果，我们可以规定：出度-1， 入度+1。
 * 最后去找寻是否存在一个 degree[i] == N-1 即可。
 * 若存在，这 i 就是我们需要的答案。
 * 即没有发生 score[i]-- 的操作，因为总共就 N 个人，一旦发生了 score[i]-- 操作，值就无法到达 N-1）
 *
 *  时间复杂度：O(n)
 *  空间复杂度：O(n)
 */
class Solution {
    public int findJudge(int N, int[][] trust) {
        int[] degree = new int[N];
        for (int[] pair : trust) {
            degree[pair[0] - 1]--;
            degree[pair[1] - 1]++;
        }

        for (int i = 0; i < N; i++) {
            if (degree[i] == N - 1) {
                return i + 1;
            }
        }
        return -1;
    }
}