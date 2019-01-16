/*
Given two strings S and T, each of which represents a non-negative rational number,
return True if and only if they represent the same number.
The strings may use parentheses to denote the repeating part of the rational number.

In general a rational number can be represented using up to three parts: an integer part, a non-repeating part, and a repeating part.
The number will be represented in one of the following three ways:

<IntegerPart> (e.g. 0, 12, 123)
<IntegerPart><.><NonRepeatingPart>  (e.g. 0.5, 1., 2.12, 2.0001)
<IntegerPart><.><NonRepeatingPart><(><RepeatingPart><)> (e.g. 0.1(6), 0.9(9), 0.00(1212))
The repeating portion of a decimal expansion is conventionally denoted within a pair of round brackets.
For example:
1 / 6 = 0.16666666... = 0.1(6) = 0.1666(6) = 0.166(66)
Both 0.1(6) or 0.1666(6) or 0.166(66) are correct representations of 1 / 6.

Example 1:
Input: S = "0.(52)", T = "0.5(25)"
Output: true
Explanation:
Because "0.(52)" represents 0.52525252..., and "0.5(25)" represents 0.52525252525..... , the strings represent the same number.

Example 2:
Input: S = "0.1666(6)", T = "0.166(66)"
Output: true

Example 3:
Input: S = "0.9(9)", T = "1."
Output: true
Explanation:
"0.9(9)" represents 0.999999999... repeated forever, which equals 1.  [See this link for an explanation.]
"1." represents the number 1, which is formed correctly: (IntegerPart) = "1" and (NonRepeatingPart) = "".

Note:
Each part consists only of digits.
The <IntegerPart> will not begin with 2 or more zeros.  (There is no other restriction on the digits of each part.)
1 <= <IntegerPart>.length <= 4
0 <= <NonRepeatingPart>.length <= 4
1 <= <RepeatingPart>.length <= 4
 */

/**
 * Approach 1: Numeric Computation (Cheat Method)
 * 这个做法并不是题目想要考察或者推崇的做法，但是因为计算机在存储浮点数时的精度问题。
 * 该做法是行得通的，精度要求与我们平时常见的 1e-6 不同，这里要求稍微高些，需要到 1e-9 级别。
 * 但是 double 支持的级别在小数点后 16 位。因此是完全没有问题。
 * 所以这里我们只需要将字符串转换成其所要表现的 小数 即可。
 * 然后利用 double 类型之间的数值比较看值是否相同即可。
 *
 * 字符串中主要包含 3 个部分的值：
 *  1. 整数部分
 *  2. 小数部分中的非重复部分
 *  3. 小数部分中的重复部分
 * 然后我们将以上 3 个部分拼接起来，将小数位扩展到 16 位，然后转成 double 类型的元素进行比较即可。
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 *
 * 关于计算机中浮点数的存储，可以参考：
 *  https://www.youtube.com/watch?v=nYFDICLRXes
 */
class Solution {
    public boolean isRationalEqual(String S, String T) {
        return Math.abs(convert(S) - convert(T)) <= 1e-9;
    }

    private double convert(String str) {
        int index = str.indexOf('(');
        if (index > 0) {
            StringBuilder num = new StringBuilder(str.substring(0, index));
            String rep = str.substring(index + 1, str.length() - 1);
            while (num.length() <= 20) {
                num.append(rep);
            }
            return Double.valueOf(num.toString());
        }
        return Double.valueOf(str);
    }
}

/**
 * Approach 2: Convert to Fraction Number
 * 因为 Approach 1 中的方法属于偷鸡型做法，利用了计算机精度方面的问题钻了个空子。
 * 故这里给出了一个可以精确判断的做法。
 * 本题在题目中已经明确告知：所有数都是 有理数。
 * 这就说明：字符中所表达的所有数都可以被转换成 分数。
 * 而对于分数的比较而言，这无疑是精确的。只需要比较两个分数 约分后 的分母和分子是否相等即可。
 * （或者也可以使用交叉相乘法进行判断）
 * 
 * 从 Approach 1 中的分析可得，字符串总有由 3 部分组成。
 * 为 整数部分(base)、小数中的非重复部分(decimal) 和 小数中的重复部分(repeating)。
 * 那么将其转换成分数表示就是：
 *  base = base / 1;
 *  decimal = decimal / 10^(decimal.length)
 *  repeating = repeating / ( 10^(decimal.length) * 10^(repeating.length-1) )
 * 以 0.5(25) 为例：
 *  base = 0
 *  decimal = 5 / 10
 *  repeating = 1/10 * 25 * ( 1/100 + 1/100^2 + 1/100^3 + ...) = 1/10 * 25 * 1/99
 *  （根据等比数列的公式计算即可）
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 *
 * References:
 *  https://youtu.be/PAsHEeaB-xA
 *  https://leetcode.com/problems/equal-rational-numbers/solution/
 */
class Solution {
    public boolean isRationalEqual(String S, String T) {
        Fraction f1 = convert(S);
        Fraction f2 = convert(T);
        return f1.n == f2.n && f1.d == f2.d;
    }

    public Fraction convert(String str) {
        String[] parts = str.split("[.()]");
        String integer = parts[0];
        Fraction num = new Fraction(Long.valueOf(integer), 1);
        String decimal = "", repeating = "";
        if (parts.length > 1) {
            decimal = parts[1];
            if (decimal.length() > 0) {
                long dec = Long.valueOf(decimal);
                num.add(new Fraction(dec, (long)Math.pow(10, decimal.length())));
            }
        }
        if (parts.length > 2) {
            repeating = parts[2];
            if (repeating.length() > 0) {
                long rep = Long.valueOf(repeating);
                long denom = (long)Math.pow(10, decimal.length()) * (long)(Math.pow(10, repeating.length()) - 1);
                num.add(new Fraction(rep, denom));
            }
        }
        return num;
    }

    class Fraction {
        long n, d;

        Fraction(long n, long d) {
            // 利用 最大公约数 对 分子和分母 进行约分
            long g = gcd(n, d);
            this.n = n / g;
            this.d = d / g;
        }

        public void add(Fraction other) {
            long numerator = this.n * other.d + this.d * other.n;
            long denominator = this.d * other.d;
            long g = gcd(numerator, denominator);
            this.n = numerator / g;
            this.d = denominator / g;
        }

        public long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }
    }
}