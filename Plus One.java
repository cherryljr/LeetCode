From end to start,
if the number[index] != 9, we plus one directly then quit the loop.
if the number[index] == 9, we set it to 0, and continue the loop until we encounter the number don't equals to 9.
After the loop, if number[0] == 0, it means that we need a bigger array to represent the number.
so we create a new array rst, and set rst[0] to 1.

/*
Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.
You may assume the integer do not contain any leading zero, except the number 0 itself.
The digits are stored such that the most significant digit is at the head of the list.

The description of this question is poor.
You can look the explianation here for better understanding:

suppose you have a number in your list/array such that adding 1 would make it a two digit number,
eg: [9]
output: [1,0]
Plusone(9) would be [10], but the expected output should be [1,0] such that the most significant digit is on the head
*/

class Solution {
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                digits[i]++;
                break;
            } else {
                digits[i] = 0;
            }
        }
        if (digits[0] == 0) {
            int[] rst = new int[digits.length + 1];
            rst[0] = 1;
            return rst;
        }
        return digits;
    }
}