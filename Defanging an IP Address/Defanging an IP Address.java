/*
Given a valid (IPv4) IP address, return a defanged version of that IP address.
A defanged IP address replaces every period "." with "[.]".

Example 1:
Input: address = "1.1.1.1"
Output: "1[.]1[.]1[.]1"

Example 2:
Input: address = "255.100.50.0"
Output: "255[.]100[.]50[.]0"

Constraints:
    1. The given address is a valid IPv4 address.
 */

/**
 * Approach 1: Replace Function of String
 * 为什么我要把一道这么简单的题目放上来呢。。。
 * 不过大家可以去看下 String 中的 replace 和 replaceAll 这两个方法的源码，
 * 相信会有所收获的（replace 实际上调用了 replaceAll 方法）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public String defangIPaddr(String address) {
        // return address.replace(".", "[.]");
        return address.replaceAll("\\.", "[.]");
    }
}

/**
 * Approach 2: Using StringBuilder
 * 如果你认真去看了 replaceAll 函数的实现，那么你应该很容易明白为什么这个解法在速度上快了一倍。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public String defangIPaddr(String address) {
        StringBuilder ans = new StringBuilder();
        for (char c : address.toCharArray()) {
            if (c != '.') {
                ans.append(c);
            } else {
                ans.append("[.]");
            }
        }
        return ans.toString();
    }
}