/*
Given a single positive integer x, we will write an expression of the form x (op1) x (op2) x (op3) x ...
where each operator op1, op2, etc. is either addition, subtraction, multiplication, or division (+, -, *, or /).
For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.

When writing such an expression, we adhere to the following conventions:
The division operator (/) returns rational numbers.
There are no parentheses placed anywhere.
We use the usual order of operations: multiplication and division happens before addition and subtraction.
It's not allowed to use the unary negation operator (-).
For example, "x - x" is a valid expression as it only uses subtraction, but "-x + x" is not because it uses negation.
We would like to write an expression with the least number of operators such that the expression equals the given target.
Return the least number of operators used.

Example 1:
Input: x = 3, target = 19
Output: 5
Explanation: 3 * 3 + 3 * 3 + 3 / 3.  The expression contains 5 operations.

Example 2:
Input: x = 5, target = 501
Output: 8
Explanation: 5 * 5 * 5 * 5 - 5 * 5 * 5 + 5 / 5.  The expression contains 8 operations.

Example 3:
Input: x = 100, target = 100000000
Output: 3
Explanation: 100 * 100 * 100 * 100.  The expression contains 3 operations.

Note:
2 <= x <= 100
1 <= target <= 2 * 10^8
 */

/**
 * Approach: DP (Express Target Number in Base X)
 * 这道题目考察的是对 进制 方面的掌握和理解。但是在此基础上有进行了一个的升级，使得难度有所提高了。
 * 题目给出了一个 base number: x.需要我们通过对 x 进行四则运算最后得到 target.
 * 但是运算过程中不能有 括号。这点非常重要，因为这就以为着 除法 的用处只有一个：
 *  用来产生 1.除此之外其没有任何用处（因为题目要求的是使用最少的运算符）
 *  eg. x^2 = x * x = x * x * x / x
 *  毫无以为此时引入除法只会增加操作符的使用，带来负面影响
 * 那么根据以上条件，可以有如下表示：
 *  target = c0*x^0 + c1*x^1 + c2*x^2 + c3*x^3 + ... + cn*x^n,   -x < c < x
 * 因此最后我们需要求的结果就是：
 *  ops = 2*|c0| + 1*|c1| + 2*|c2| + 3*|c3| + ... + n*|cn| - 1
 * 这里注意到我们进行了一次 -1 操作。
 * 这是因为，我们以上所有的操作都是带符号进行运算的。
 * 但是表达式第一项的符号是可以省略掉的。可能有人会有疑问：负号的话就不能省略了。
 * 然而根据题目的数据范围可知：x 与 target 均为 正整数。
 * 因此表达式中必定至少存在一项正的，所以我们可以把 负的这一项 移到后面去，这样就能省略掉一个操作符了。
 * 同时，对于第一项，我们这里 *2，这是因为要产生一个 1，或者是 -1.我们都需要两个操作符。（±x/x）
 *
 * 此外，我们还需要知道非常重要的一点就是：
 *  target 可以由比较小的数叠加而来，而可以由比较大的数减去一个值得到。
 * 而我们需要求的就是最短的路径（最少需要多少个操作符）。
 * 因此这里实际上是一个 DP 问题，即我们只关心由 x 经过运算得到某一个值 n 最少需要的操作符个数。
 * 但是并不关心中间的具体操作过程（无后效性问题）。
 *  eg. x = 3, target = 7
 *  target = 3*3 - 3/3 - 3/3, ops = 5
 *  target = 3 + 3 + 3/3 , ops = 3
 *  （注意：7的三进制是 21）即：7 = 2*3^1 + 1 * 3^0, 对于 ops = 2 * 1 + 2 - 1 = 3
 * References:
 *  https://leetcode.com/problems/least-operators-to-express-number/discuss/208349/JavaC%2B%2BPython-O(logY)-Time-and-O(1)-Space
 *  https://zxi.mytechroad.com/blog/uncategorized/leetcode-964-least-operators-to-express-number/
 */
class Solution {
    public int leastOpsExpressTarget(int x, int target) {
        // pos the number of operations needed to get y % x^(k+1)
        // neg the number of operations needed to get x^(k + 1) - y % x^(k + 1)
        int pos = 0, neg = 0, k = 0;
        while (target > 0) {
            int remainder = target % x;
            target /= x;

            if (k > 0) {
                int pos2 = Math.min(remainder * k + pos, (remainder + 1) * k + neg);
                int neg2 = Math.min((x - remainder) * k + pos, (x - remainder - 1) * k + neg);
                pos = pos2;
                neg = neg2;
            } else {
                pos = 2 * remainder;
                neg = 2 * (x - remainder);
            }
            k++;
        }
        return Math.min(pos, neg + k) - 1;
    }
}