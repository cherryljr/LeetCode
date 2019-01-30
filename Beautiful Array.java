/*
For some fixed N, an array A is beautiful if it is a permutation of the integers 1, 2, ..., N, such that:
For every i < j, there is no k with i < k < j such that A[k] * 2 = A[i] + A[j].
Given N, return any beautiful array A.  (It is guaranteed that one exists.)

Example 1:
Input: 4
Output: [2,1,4,3]

Example 2:
Input: 5
Output: [3,1,2,5,4]

Note:1 <= N <= 1000
 */

/**
 * Approach 1: Divide and Conquer (No Recursion)
 * 题目对于 Beautiful Array 的要求为：
 *  两个数的平均数不能在这两个数中间。
 * 因此，本题的两个解题要点有如下两个：
 *  1. 对于等式 A[k] * 2 = A[i] + A[j]
 *  对数组 A[] 中的元素，同时进行 加法/减法/乘法 的运算，等式依然成立。
 *  2. 因为 奇数 + 偶数 = 奇数，并且 奇数 无法被 2 整除，
 *  因此只要两个数和为奇数，就能保证平均数不会存在于这两个数中间。
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(N)
 *
 * 进一步的详细解析可以参考：
 *  https://youtu.be/9L6bPGDfyqo
 */
class Solution {
    public int[] beautifulArray(int N) {
        List<Integer> res = Arrays.asList(1);
        while (res.size() < N) {
            ArrayList<Integer> temp = new ArrayList<>();
            // 扩展新数组 左侧的奇数 部分
            for (int num : res) {
                if (2 * num - 1 <= N) {
                    temp.add(2 * num - 1);
                }
            }
            // 扩展新数组 右侧的偶数 部分
            for (int num : res) {
                if (2 * num <= N) {
                    temp.add(2 * num);
                }
            }
            res = temp;
        }

        return res.stream().mapToInt(x -> x).toArray();
    }
}

/**
 * Approach 2: Divide and Conquer with Memory Search
 * 相比与 Approach 1 中的暴力遍历求解。
 * 这个做法使用了分治的思想（利用递归实现）同时结合了 记忆化搜索 去避免了重复计算。
 * 使得速度上得到了较大的提升。
 * 相当于是 Approach 1 逆过程的处理方式。
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(N)
 */
class Solution {
    public int[] beautifulArray(int N) {
        HashMap<Integer, int[]> mem = new HashMap<>();
        return dfs(N, mem);
    }

    private int[] dfs(int N, HashMap<Integer, int[]> mem) {
        if (mem.containsKey(N)) {
            return mem.get(N);
        }
        int[] res = new int[N];
        if (N == 1) {
            res[0] = 1;
            return res;
        }

        int index = 0;
        // 当数组长度为 奇数 时，左侧奇数部分需要多一个元素（即长度要多1）
        for (int num : dfs(N + 1 >> 1, mem)) {
            res[index++] = 2 * num - 1;
        }
        for (int num : dfs(N >> 1, mem)) {
            res[index++] = 2 * num;
        }
        mem.put(N, res);
        return res;
    }
}