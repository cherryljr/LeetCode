/*
In a project, you have a list of required skills req_skills, and a list of people.
The i-th person people[i] contains a list of skills that person has.

Consider a sufficient team: a set of people such that for every required skill in req_skills,
there is at least one person in the team who has that skill.
We can represent these teams by the index of each person:
for example, team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].

Return any sufficient team of the smallest possible size, represented by the index of each person.
You may return the answer in any order.  It is guaranteed an answer exists.

Example 1:
Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
Output: [0,2]

Example 2:
Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"], people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],["csharp","math"],["aws","java"]]
Output: [1,2]

Constraints:
    1. 1 <= req_skills.length <= 16
    2. 1 <= people.length <= 60
    3. 1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
    4. Elements of req_skills and people[i] are (respectively) distinct.
    5. req_skills[i][j], people[i][j][k] are lowercase English letters.
    6. Every skill in people[i] is a skill in req_skills.
    7. It is guaranteed a sufficient team exists.
 */

/**
 * Approach: DP + Path Record + State Compression
 * 题意：我们要从 P个人里面挑选若干人组建一个有 N 个技能组成的全能团队。
 * 问最少选择多少人可以组建这个全能团队。
 *
 * 第一眼看上去这道问题与背包问题非常类似，每个人有选和不选两种情况。
 * 但是在这道题目中我们面临着一个问题：每个人都具备 X 个技能，那么应该如何表示呢？
 * 这时，我们观察题目的数据规模可以发现：需求的技能总个数不会超过16，2^16=65536 因此想到可以利用 二进制 进行状态压缩。
 * 即将一个人的技能项转换成一个 int 来表示。
 *
 * 然后就是按照 01背包 的思路来解决这个问题。
 * dp[i][j] 代表前 i 个人组成一个拥有技能 j 的团队，最少需要多少人。
 * 状态转移方程为：
 *  选择第i个人：dp[i][j|k] = dp[i-1][j] + 1
 *  不选择第i个人：dp[i][j|k] = dp[i-1][j|k]
 * 即 dp[i][j|k] = min(dp[i-1][j|k], dp[i-1][j] + 1)
 * 最后所需要最少的人数就是：dp[people.size()][target] （target就是所有技能都具备的情况，是一个由状态压缩获得的 int 值）
 * 与 01背包 同理，dp[i][j] 仅仅与 上一行dp[i-1][j] 的状态有关，因此可以将其进行空间优化成一维数组。
 *
 * 因为这道题目要求我们给出具体的人员组成，不再是像传统 DP 那样给出最后的个数即可。
 * 因此，在 DP 的过程中，我们需要记录其路径，从而最后才能将结果路径还原出来。
 * 这里使用了 Map 来记录路径，key:当前的技能状态， value: arr[0]代表上一个状态，arr[1]代表选取了哪个人
 * 然后通过对 target 进行回溯，即可得到最终的结果路径。
 *
 * 时间复杂度：O(p * 2^n) p为人员个数，n为需求的技能个数
 * 空间复杂度：O(2^n)
 *
 * Reference:
 *  https://youtu.be/5Kr1PWAgEx8
 */
class Solution {
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int n = req_skills.length, m = people.size(), target = (1 << n) - 1;
        Map<String, Integer> skillMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            skillMap.put(req_skills[i], i);
        }

        int[] dp = new int[target + 1];
        // Initialize the dp array using 0x3f3f3f3f to avoid overflow
        Arrays.fill(dp, 0x3f3f3f3f);
        dp[0] = 0;
        Map<Integer, int[]> path = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int skill = 0;
            for (String s : people.get(i)) {
                skill |= 1 << skillMap.get(s);
            }
            for (int j = target; j >= 0; j--) {
                if (dp[j] + 1 < dp[j | skill]) {
                    dp[j | skill] = dp[j] + 1;
                    // Record the path
                    path.put(j | skill, new int[]{j, i});
                }
            }
        }

        int index = dp[target];
        int[] ans = new int[index];
        while (target > 0) {
            ans[--index] = path.get(target)[1];
            target = path.get(target)[0];
        }
        return ans;
    }
}