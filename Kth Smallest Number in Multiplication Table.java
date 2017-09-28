The same as Kth Smallest Number in Sorted Matrix in LintCode.
You can get more details here:
https://github.com/cherryljr/LintCode/blob/master/Kth%20Smallest%20Number%20in%20Sorted%20Matrix.java

/*
Nearly every one have used the Multiplication Table. 
But could you find out the k-th smallest number quickly from the multiplication table?

Given the height m and the length n of a m * n Multiplication Table, 
and a positive integer k, you need to return the k-th smallest number in this table.

Example 1:
Input: m = 3, n = 3, k = 5

Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
3	6	9
The 5-th smallest number is 3 (1, 2, 2, 3, 3).

Example 2:
Input: m = 2, n = 3, k = 6

Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6

The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).

Note:
The m and n will be in the range [1, 30000].
The k will be in the range [1, m * n]
*/

class Solution {
    public int findKthNumber(int m, int n, int k) {
        int start = 1;
        int end = m * n;
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            int c = count(mid, m, n);
            if (c < k) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        
        return start;
    }
    
    public int count(int x, int m, int n) {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            int temp = Math.min(x / i, n);
            count += temp;
        }
        return count;
    }
}