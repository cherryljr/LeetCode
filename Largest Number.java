/*
Given a list of non negative integers, arrange them such that they form the largest number.
For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
Note: The result may be very large, so you need to return a string instead of an integer.
*/

/**
 * The idea here is basically implement a String comparator to decide which String should come first during concatenation. 
 * Because when you have 2 numbers (let's convert them into String), you'll face only 2 cases:
 * 	For example:
 * 	String s1 = "9";
 * 	String s2 = "31";
 * 	String case1 =  s1 + s2; // 931
 * 	String case2 = s2 + s1; // 319
 * 
 * Apparently, case1 is greater than case2 in terms of value.
 * So, we should always put s1 in front of s2.
 * 
 * Time and Space Complexity:
 * Let's assume:
 * the length of input array is n,
 * average length of Strings in strs is k,
 * Then, compare 2 strings will take O(k).
 * Sorting will take O(nlogn)
 * Appending to StringBuilder takes O(n).
 * So total will be O(nklognk) + O(n) = O(nklgnk).
 * Space is pretty straight forward: O(n).
 */
class Solution {
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }

        // Convert int array to String array, so we can sort later on
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        // Comparator to decide which string should come first in concatenation
        Arrays.sort(strs, (str1, str2) -> (str2 + str1).compareTo(str1 + str2));

        // An extreme edge case that you have only a bunch of 0 in your int array
        if (strs[0].charAt(0) == '0') {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }
}