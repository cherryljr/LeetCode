/*
The gray code is a binary numeral system where two successive values differ in only one bit.
Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. 
A gray code sequence must begin with 0.

For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
00 - 0
01 - 1
11 - 3
10 - 2

Note:
For a given n, a gray code sequence is not uniquely defined.
For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
*/

// Before this, let's look at two functions about Gray Code first.
/**
 * Funtion 1
 * The purpose of this function is to convert an unsigned
 * binary number to reflected binary Gray code.
 * The operator >> is shift right. The operator ^ is exclusive or.
 */
long binaryToGray(long num)
{
    return (num >> 1) ^ num;
}

/**
 * Funtion 2
 * The purpose of this function is to convert a reflected binary
 * Gray code number to a binary number.
 * Each Gray code bit is exclusive-ored with all
 * more significant bits.
 */
long grayToBinary(long num)
{
    long mask;
    for (mask = num >> 1; mask != 0; mask = mask >> 1)
    {
        num = num ^ mask;
    }
    return num;
}

// Now, we can write the solution easily.
class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> rst = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            // convert an number to Gray Code.
            rst.add(i ^ (i >> 1));
        }
        return rst;
    }
}