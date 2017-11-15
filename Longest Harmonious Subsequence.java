```
Algorithm
    In this approach, we make use of a hashmap mapmap 
    which stores the number of times an element occurs in the array along with the element's value in the form (num, count_num), 
    where num refers to an element in the array and count_num refers to the number of times this num occurs in the numsnums array. 
    We traverse over the nums array and fill this map once.
    After this, we traverse over the keys of the map created. 
    For every key of the map considered, say key, we find out if the map contains the key+1. 
    Such an element is found, since only such elements can be counted for the harmonic subsequence 
    if key is considered as one of the element of the harmonic subsequence. 
    We need not care about key−1, because if key is present in the harmonic subsequence, 
    at one time either key+1 or key−1 only could be included in the harmonic subsequence. 
    The case of key−1 being in the harmonic subsequence will automatically be considered, when key−1 is encountered as the current key.
    Now, whenver we find that key+1 exists in the keys of mapmap, 
    we determine the count of the current harmonic subsequence as count_{key} + count_{key+1}, 
    where count_​i refers to the value corresponding to the key_i in mapmap, which reprents the number of times i occurs in the array nums.
Complexity Analysis
    Time  complexity : O(n). One loop is required to fill map and one for traversing the map.
    Space complexity : O(n). In worst case map size grows upto size n.
```
        
/*
We define a harmonious array is an array where the difference between its maximum value and its minimum value is exactly 1.
Now, given an integer array, you need to find the length of its longest harmonious subsequence among all its possible subsequences.

Example 1:
Input: [1,3,2,2,5,2,3,7]
Output: 5
Explanation: The longest harmonious subsequence is [3,2,2,2,3].
Note: The length of the input array will not exceed 20,000.
*/

class Solution {
    public int findLHS(int[] nums) {
        int rst = 0;
        Map<Integer, Integer> map = new HashMap<>();
        
        // put the number and its occurence in the map
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        // Traverse the map to get the longest harmonious subsequence
        for (int key : map.keySet()) {
            if (map.containsKey(key + 1)) {
                rst = Math.max(rst, map.get(key) + map.get(key + 1));
            }
        }
        return rst;
    }
}
