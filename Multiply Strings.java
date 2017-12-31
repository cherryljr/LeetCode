/*
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2.

Note:
The length of both num1 and num2 is < 110.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
*/

/**
 * Remember how we do multiplication?
 * Start from right to left, perform multiplication on every pair of digits, and add them together.
 * Let’s draw the process! From the following draft, we can immediately conclude:
 * num1[i] * num2[j] will be placed at indices [i + j, i + j + 1]
 * It meas that:
 *  rst[i+j] = sum / 10;
 *  rst[i+j+1] = sum % 10;
 *
 * The image is here:
 * https://drscdn.500px.org/photo/130178585/m=2048/300d71f784f679d5e70fadda8ad7d68f
 */
class Solution {
    public String multiply(String num1, String num2) {
        int[] pos = new int[num1.length() + num2.length()];

        // Calculate the result of each index in the pos array.
        for (int i = num1.length() - 1; i >= 0; i--) {
            for (int j = num2.length() - 1; j >= 0; j--) {
                int p1 = i + j, p2 = i + j + 1;
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                // The sum should plus the carry bit
                int sum = mul + pos[p2];

                pos[p1] += sum / 10;
                pos[p2] = sum % 10;
            }
        }

        // Turn the pos array into String result
        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}