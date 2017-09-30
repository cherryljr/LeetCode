Time Complexity: O(klogk)
Basic idea: 
Use minHeap to keep track on next minimum pair sum, 
and we only need to maintain K possible candidates in the data structure.

Some observations: 
For every numbers in nums1, its best partner(yields min sum) always strats from nums2[0] since arrays are all sorted; 
And for a specific number in nums1, its next candidate sould be [this specific number] + nums2[current_associated_index + 1], 
unless out of boundary; Due to the next candidate, we must restore the index of curr element in nums2.

Method:
Frist, we take the first k elements of nums1 and paired with nums2[0] as the starting pairs 
so that we have (0,0), (1,0), (2,0),.....(k-1,0) in the minHeap.
Each time after we poll the peek element in minHeap, we put the new pair with the second index + 1 (candidate element).

Here is a simple example demonstrate how this algorithm works. 
the image: https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/ (so vividly, you can understand it clearly)


/*
You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
Define a pair (u,v) which consists of one element from the first array and one element from the second array.
Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.

Example 1:
Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3
Return: [1,2],[1,4],[1,6]

The first 3 pairs are returned from the sequence:
[1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

Example 2:
Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
Return: [1,1],[1,1]

The first 2 pairs are returned from the sequence:
[1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

Example 3:
Given nums1 = [1,2], nums2 = [3],  k = 3 
Return: [1,3],[2,3]

All possible pairs are returned from the sequence:
[1,3],[2,3]
*/

class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> rst = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return rst;
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, new Comparator<int[]>(){
            // Overwrite the compare method
            public int compare(int[] a, int[] b) {
                return (a[0] + a[1]) - (b[0] + b[1]);
            }
        });
        // Initialize the minHeap
        // when offer the next element, we need to know the curr index of nums2.
        // So the array is consist of: the element in nums1, the element in nums2, the curr element's index in nums2.
        for (int i = 0; i < k && i < nums1.length; i++) {
            pq.offer(new int[]{nums1[i], nums2[0], 0});
        }
        // poll the peak element (minElement currently), and offer the next element
        while (k-- > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            rst.add(new int[]{cur[0], cur[1]});
            // check the index of nums2 is out of bound or not
            if (cur[2] == nums2.length - 1) {
                continue;
            }
            pq.offer(new int[]{cur[0], nums2[cur[2] + 1], cur[2] + 1});
        }
        
        return rst;
    }
}