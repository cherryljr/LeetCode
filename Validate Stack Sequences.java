/*
Given two sequences pushed and popped with distinct values,
return true if and only if this could have been the result of a sequence of push and pop operations on an initially empty stack.

Example 1:
Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
Output: true
Explanation: We might do the following sequence:
push(1), push(2), push(3), push(4), pop() -> 4,
push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1

Example 2:
Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
Output: false
Explanation: 1 cannot be popped before 2.

Note:
    1. 0 <= pushed.length == popped.length <= 1000
    2. 0 <= pushed[i], popped[i] < 1000
    3. pushed is a permutation of popped.
    4. pushed and popped have distinct values.
 */

/**
 * Approach: Simulate stack operations
 * Loop through the pushed array:
 *  1. Keep pushing pushed elements into stack if the top element on the stack is different from the current one of popped;
 *  2. Keep poping out of the top element if it is same as the current one of popped;
 *  3. Check if the stack is empty after loop.
 *
 * Time  Complexity: O(n)
 * Space Complexity: O(n)
 */
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new ArrayDeque<>();
        int index = 0;
        for (int num : pushed) {
            stack.push(num);
            while (!stack.isEmpty() && popped[index] == stack.peek()) {
                stack.pop();
                index++;
            }
        }
        return stack.isEmpty();
    }
}