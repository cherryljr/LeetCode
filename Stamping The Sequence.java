/*
You want to form a target string of lowercase letters.
At the beginning, your sequence is target.length '?' marks.  You also have a stamp of lowercase letters.
On each turn, you may place the stamp over the sequence,
and replace every letter in the sequence with the corresponding letter from the stamp.
You can make up to 10 * target.length turns.

For example, if the initial sequence is "?????", and your stamp is "abc",
then you may make "abc??", "?abc?", "??abc" in the first turn.
(Note that the stamp must be fully contained in the boundaries of the sequence in order to stamp.)

If the sequence is possible to stamp, then return an array of the index of the left-most letter being stamped at each turn.
If the sequence is not possible to stamp, return an empty array.

For example, if the sequence is "ababc", and the stamp is "abc",
then we could return the answer [0, 2], corresponding to the moves "?????" -> "abc??" -> "ababc".

Also, if the sequence is possible to stamp, it is guaranteed it is possible to stamp within 10 * target.length moves.
Any answers specifying more than this number of moves will not be accepted.

Example 1:
Input: stamp = "abc", target = "ababc"
Output: [0,2]
([1,0,2] would also be accepted as an answer, as well as some other answers.)

Example 2:
Input: stamp = "abca", target = "aabcaca"
Output: [3,0,1]

Note:
    1. 1 <= stamp.length <= target.length <= 1000
    2. stamp and target only contain lowercase letters.
 */

/**
 * Approach: Reverse Simulation
 * 这道题目与 Strange Printer 有点类似，但是本题并不要求最优解。
 * 因此我们可以使用 贪心 的想法去做。
 * 但是这道题目如果按照题目要求的做法 正向（正序） 思考的话，并不能相处什么比较好的做法。
 * 因此我们可以考虑使用逆过来处理的方法来做这道题目。
 * 使用到类似处理方法的还有 Bricks Falling When Hit.
 * 做法为：
 *  因为最后一次的 stamp 必定是会将字符串中的一部分全部改成 stamp.
 *  也就是所谓的 full match.然后将替换的部分全部标记为 '*'.
 *  然后继续进行 match, 但是此时就是 partial match.
 *  并且 '*' 可以与任意字符进行匹配。因为这些字符之后必定会被 stamp 所重新覆写一遍，
 *  所以他们具体是什么并不会造成任何影响。
 *  最后我们只需要将每次得到的结果进行一次 reverse 即可。
 *
 * 时间复杂度：O((T-S)*S)
 * 空间复杂度：O(T)
 *
 * References:
 *  https://zxi.mytechroad.com/blog/greedy/leetcode-936-stamping-the-sequence/
 * Bricks Falling When Hit:
 *  https://github.com/cherryljr/LeetCode/blob/master/Bricks%20Falling%20When%20Hit.java
 */
class Solution {
    // 因为需要在 doStamp 中对 target 进行修改，所以需要一个成员变量以便访问（Java中字符串类型的限制）
    String myTarget = null;

    public int[] movesToStamp(String stamp, String target) {
        int M = stamp.length(), N = target.length();
        myTarget = target;
        int count = 0;  // 替换的字符个数
        boolean[] visited = new boolean[N];         // 用于记录哪些位置开始的字符被修改过
        List<Integer> index = new ArrayList<>();    // 用于记录每次修改的起始位置
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            sb.append('*');
        }
        // 事先构造出用于替换的字符串
        String replace = sb.toString();

        while (count < N) {
            boolean found = false;
            for (int i = 0; i <= N - M; i++) {
                if (visited[i]) {
                    continue;
                }
                int len = doStamp(stamp, i, replace);
                if (len == 0) {
                    continue;
                }
                visited[i] = true;
                count += len;
                index.add(i);
                found = true;
            }
            if (!found) {
                return new int[]{};
            }
        }

        int size = index.size();
        int[] rst = new int[size--];
        for (int i = 0; i <= size; i++) {
            rst[i] = index.get(size - i);
        }
        return rst;
    }

    // 对从 start 位置开始的字串进行stamp操作，返回替换的字符个数
    // 如果无法匹配的话返回0
    private int doStamp(String stamp, int start, String replace) {
        int changed = stamp.length();
        for (int i = 0; i < stamp.length(); i++) {
            if (myTarget.charAt(start + i) == '*') {
                changed--;
            } else if (myTarget.charAt(start + i) != stamp.charAt(i)) {
                return 0;
            }
        }

        if (changed != 0) {
            myTarget = myTarget.substring(0, start) + replace + myTarget.substring(start + stamp.length());
        }
        return changed;
    }
}