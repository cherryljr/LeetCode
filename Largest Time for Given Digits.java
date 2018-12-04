/*
Given an array of 4 digits, return the largest 24 hour time that can be made.
The smallest 24 hour time is 00:00, and the largest is 23:59.
Starting from 00:00, a time is larger if more time has elapsed since midnight.

Return the answer as a string of length 5.  If no valid time can be made, return an empty string.

Example 1:
Input: [1,2,3,4]
Output: "23:41"

Example 2:
Input: [5,5,5,5]
Output: ""

Note:
A.length == 4
0 <= A[i] <= 9
 */

// https://leetcode.com/problems/largest-time-for-given-digits/

/**
 * Approach 1: Using DFS(Backtracking) to Get Permutations
 * 根据题目的数据规模，我们知道 A[] 大小只有 4.
 * 因此本题并没有考察什么难的算法，我们只需要使用 Backtracking 的方法
 * 列举出所有的 Permutations,并判断各个 组合 是否合法（时间）。
 * 然后取最大值即可。
 *
 * 时间复杂度：O(4!) = O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public String largestTimeFromDigits(int[] A) {
        List<List<Integer>> permutations = new ArrayList<>();
        boolean[] visited = new boolean[A.length];
        dfs(A, permutations, visited, new ArrayList<>());
        int hourRst = -1, minuteRst = -1;
        for (List<Integer> time : permutations) {
            int hour = getHour(time);
            int minute = getMinute(time);
            // 判断该排列所得的时间（小时和分钟）合法，并且大于当前的最大时间，则更新最大值
            if ((hour > hourRst && minute != -1) || (hour == hourRst && minute > minuteRst)) {
                hourRst = hour;
                minuteRst = minute;
            }
        }
        // 这里虽然利用 String.format() 会使得代码更加简洁优美
        // 但是更加高效的做法是使用 StringBuilder 将最大值的permutation结果拼接起来（可以快上一倍的时间）
        // sb.append(time.get(0)).append(time.get(1)).append(":").append(time.get(2)).append(time.get(3));
        return (hourRst != -1 && minuteRst != -1) ? String.format("%02d:%02d", hourRst, minuteRst) : "";
    }

    void dfs(int[] A, List<List<Integer>> permutations, boolean[] visited, List<Integer> list) {
        if (list.size() == A.length) {
            permutations.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < A.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            list.add(A[i]);
            dfs(A, permutations, visited,list);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }

    // 获取当前排列时间的 小时，如果非法则返回 -1
    int getHour(List<Integer> time) {
        int hour = time.get(0) * 10 + time.get(1);
        if (hour >= 0 && hour <= 23) {
            return hour;
        } else {
            return -1;
        }
    }

    // 获取当前排列时间的 分钟，如果非法则返回 -1
    int getMinute(List<Integer> time) {
        int minutes = time.get(2) * 10 + time.get(3);
        if (minutes >= 0 && minutes <= 59) {
            return minutes;
        } else {
            return -1;
        }
    }
}

/**
 * Approach 2: Using For Loop to Get Permutations
 * 不适用 Backtracking 的方法，而是采用 for 循环遍历的方法。
 * 寻找所有 i, j, k, l 的组合，分别对应时间的：
 * 小时第一位，小时第二位，分钟第一位，分钟第二位。
 * 从而来获取所有的时间。
 *
 * 时间复杂度：O(4*4*4) = O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public String largestTimeFromDigits(int[] A) {
        String rst = "";
        // Choose different indices i, j, k, l as a permutation of 0, 1, 2, 3
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    // avoid duplicate among i, j and k.
                    if (i == j || i == k || j == k) {
                        continue;
                    }
                    String hour = "" + A[i] + A[j];
                    // the last indices l = 6 - i - j - k
                    String minute = "" + A[k] + A[6 - i - j - k];
                    String time = hour + ":" + minute;
                    // hour < 24; minute < 60; update result.
                    if (hour.compareTo("24") < 0 && minute.compareTo("60") < 0 && rst.compareTo(time) < 0) {
                        rst = time;
                    }
                }
            }
        }
        return rst;
    }
}