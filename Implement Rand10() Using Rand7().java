/*
Given a function rand7 which generates a uniform random integer in the range 1 to 7,
write a function rand10 which generates a uniform random integer in the range 1 to 10.
Do NOT use system's Math.random().

Example 1:
Input: 1
Output: [7]

Example 2:
Input: 2
Output: [8,4]

Example 3:
Input: 3
Output: [8,1,10]

Note:
rand7 is predefined.
Each testcase has one argument: n, the number of times that rand10 is called.

Follow up:
What is the expected value for the number of calls to rand7() function?
Could you minimize the number of calls to rand7()?
 */

/**
 * The rand7() API is already defined in the parent class SolBase.
 * public int rand7();
 * @return a random integer in the range 1 to 7
 *
 * Approach: Rejection Sampling
 * This solution is based upon Rejection Sampling.
 * The main idea is when you generate a number in the desired range, output that number immediately.
 * If the number is out of the desired range, reject it and re-sample again.
 * As each number in the desired range has the same probability of being chosen, a uniform distribution is produced.
 *
 * Obviously, we have to run rand7() function at least twice, as there are not enough numbers in the range of 1 to 10.
 * By running rand7() twice, we can get integers from 1 to 49 uniformly.
 * A table is used to illustrate the concept of rejection sampling.
 * Calling rand7() twice will get us row and column index that corresponds to a unique position in the table above.
 * Imagine that you are choosing a number randomly from the table above.
 * If you hit a number, you return that number immediately.
 * If you hit a * , you repeat the process again until you hit a number.
 *
 * Since 49 is not a multiple of 10, we have to use rejection sampling.
 * Our desired range is integers from 1 to 40, which we can return the answer immediately.
 * If not (the integer falls between 41 to 49), we reject it and repeat the whole process again.
 *
 * Complexity Analysis
 *  Time Complexity: O(1) average, but O(∞) worst case.
 *                   expected value = 2 * 49 / 40 = 2.45
 *  Space Complexity: O(1)
 *
 * Reference:
 *  https://www.youtube.com/watch?v=Wyauxe92JJA
 *  https://leetcode.com/problems/implement-rand10-using-rand7/solution/
 *  https://leetcode.com/problems/implement-rand10-using-rand7/discuss/151567/C++JavaPython-Average-1.199-Call-rand7-Per-rand10
 */
class Solution extends SolBase {
    public int rand10() {
        int index;
        do {
            index = 7 * (rand7() - 1) + rand7();
        } while (index > 40);
        // return (index - 1) % 10 + 1;
        return index % 10 + 1;
    }
}