/*
Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU,
find the exclusive time of these functions.

Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.

A log is a string has this format : function_id:start_or_end:timestamp. For example,
"0:start:0" means function 0 starts from the very beginning of time 0.
"0:end:0" means function 0 ends to the very end of time 0.

Exclusive time of a function is defined as the time spent within this function,
the time spent by calling other functions should not be considered as this function's exclusive time.
You should return the exclusive time of each function sorted by their function id.

Example 1:
Input:
n = 2
logs =
["0:start:0",
 "1:start:2",
 "1:end:5",
 "0:end:6"]
Output:[3, 4]
Explanation:
Function 0 starts at time 0, then it executes 2 units of time and reaches the end of time 1.
Now function 0 calls function 1, function 1 starts at time 2, executes 4 units of time and end at time 5.
Function 0 is running again at time 6, and also end at the time 6, thus executes 1 unit of time.
So function 0 totally execute 2 + 1 = 3 units of time, and function 1 totally execute 4 units of time.

Note:
Input logs will be sorted by timestamp, NOT log id.
Your output should be sorted by function id, which means the 0th element of your output corresponds to the exclusive time of function 0.
Two functions won't start or end at the same time.
Functions could be called recursively, and will always end.
1 <= n <= 100
 */

/**
 * Approach: Simulation (Using Stack)
 * 模拟 CPU 进程调度的过程，计算每个进程运行的时间。
 * 因此首先想到的就是使用 Stack 来模拟整个过程。
 * 因为进程的运行和结束可以正好对应 进栈 和 出栈 的过程。
 * 而对于时间而言，其时间计算的点不外乎两个地方：
 *  1. 进程被另外一个进程抢占从而挂起，此时需要进行其已经运行了多长时间 curr - prev.
 *  2. 进程运行结束，此时通用需要计算运行的时间，但是需要注意这里的 end 需要 1格 的时间来进行停止操作。
 *  因此需要对 curr+1.
 * 后续的就是编码能力的考察了，注意对 stack 是否为空进行判断即可。其实也就是对应：之前是否已经有任务在运行的状态。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n/2) => O(n)
 */
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] rst = new int[n];
        Stack<Integer> stack = new Stack<>();
        int prev = 0;
        int curr = 0;

        for (String log : logs) {
            String[] strs = log.split(":");
            int id = Integer.parseInt(strs[0]);
            curr = Integer.parseInt(strs[2]);
            // 如果 function 是 start，将其 push 到 stack 中
            // 同时记录下上一个任务运行的时间，并更新prev
            if (strs[1].charAt(0) == 's') {
                if (!stack.isEmpty()) {
                    rst[stack.peek()] += curr - prev;
                }
                prev = curr;
                stack.push(id);
            } else {
                // 如果 function 是 end，将栈顶元素弹出
                // 注意 end 时，任务需要 1格 的时间来停止，因此实际结束时间为 curr+1.
                rst[stack.pop()] += curr - prev + 1;
                prev = curr + 1;
            }
        }

        return rst;
    }
}