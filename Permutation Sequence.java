/*
The set [1,2,3,…,n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order,
We get the following sequence (ie, for n = 3):
	1. "123"
	2. "132"
	3. "213"
	4. "231"
	5. "312"
	6. "321"
Given n and k, return the kth permutation sequence.

Note: Given n will be between 1 and 9 inclusive.
*/

// Approach 1: Backtracking / DFS
// Apparently, it could be solved by using backtracking method...
// And it is Time Limit Exceeded (of course...O(╥﹏╥)O)
class Solution {
    String rst = null;
    int count = 0;
    
    public String getPermutation(int n, int k) {        
        helper(n, k, new ArrayList<Integer>());
        return rst;
    }
    
    public void helper(int n, int k, List<Integer> list) {
        if (list.size() == n) {
            count++;
            if (count == k) {
                StringBuilder sb = new StringBuilder();
                for (int i : list) {
                    sb.append(i);
                }
                rst = sb.toString();
            }
            return;
        }
        
        for (int i = 1; i <= n; i++) {
        	if (list.contains(i)) {
        		continue;
        	}
        	list.add(i);
        	helper(n, k, list);
        	list.remove(list.size() - 1);
        }
    }
}


// Approach 2: An iterative Solution
/**
 * Recursion will use more memory, while this problem can be solved by iteration. 
 * 
 * The logic is as follows: 
 * take k = k-1 (subtract 1 because of index always starting at 0)
 * for n numbers the permutations can be divided to (n-1)! groups, 
 * for n-1 numbers can be divided to (n-2)! groups, and so on. 
 * Thus k/(n-1)! indicates the index of current number, 
 * and k%(n-1)! denotes remaining index for the remaining n-1 numbers.
 * We keep doing this until n reaches 0, then we get n numbers permutations that is kth.
 * (when we get the ith number is x, remember to remove x form the nums array)
 * 
 * Time Complexity is actually O(n^2), since remove in an arrayList will take O(n) complexity.
 * Note:
 * Using k = k-1 would avoid dealing with case k % (n-1) != 0. 
 * I have to use a list to store the remaining numbers, 
 * neither linkedlist nor arraylist are very efficient, anyone has a better idea?
 * 
 * You can get more detail explianations and examples here:
 * https://discuss.leetcode.com/topic/17348/explain-like-i-m-five-java-solution-in-o-n/5
 */
 class Solution {
    public String getPermutation(int n, int k) {
        // create a list of numbers to get index
        // numbers = {1, 2, 3, 4, ... n}
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
        
        // create an array of factorial lookup
        // factorial[] = {1, 1, 2, 6, 24, ... n!}
        int[] factorial = new int[n];
        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            factorial[i] = i * factorial[i - 1];
        }
        
        k = k - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--) {
            int index = k / factorial[i - 1];
            k = k % factorial[i - 1];
            sb.append(nums.get(index));
            // after adding the selected number, remove it from nums array.
            nums.remove(index);
        }
        return sb.toString();
    }
}