/*
Given a non-empty array of integers, return the k most frequent elements.

For example,
Given [1,1,1,2,2,3] and k = 2, return [1,2].

Note: 
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
*/

// This question is similar to Top K Frequent Words.
// You can refer to here: 

/**
 * Approach 1: Using HashMap and PriorityQueue(maxHeap)
 * Put entry into maxHeap so we can always poll a number with largest frequency.
 * 
 * Complexity Analysis:
 * Time complexity : O(nlogk)
 * n is the size of HashMap, k is the size of maxHeap.
 * Because PriorityQueue add/poll a number will cost O(logk) time.
 * We have to deal with n numbers. So the time complexity is: O(nlogk).
 * Space complexity : O(n)
 * We need a HashMap to restore the element and its occurences, the size is O(n).
 * A maxHeap to sort the occurences of elements, the size is O(k).
 */
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<Integer>();
        }
        
        // Using a map to store the element and its occurences
        Map<Integer, Integer> map = new HashMap<>();
        for (int i: nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        
        // Using a maxHeap to auto-sort the EntrySet according to the occurences of elements, 
        // so we need to Override the compare method (Using  Lambda Expression)
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
                         new PriorityQueue<>((a,b) -> (b.getValue() - a.getValue()));
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            maxHeap.add(entry);
        }
        
        List<Integer> rst = new ArrayList<>();
        while (k > 0) {
        	// poll the number with largest frequency currently.
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            rst.add(entry.getKey());
            k--;
        }
        return rst;
    }
}

/**
 * Approach 2: Bucket Sort
 * Use an array to save numbers into different bucket whose index is the frequency
 * 
 * Complexity Analysis:
 * Time complexity : O(n)
 * We only need to traverse the array only once - O(n).
 * And Bucket will cost O(n) time.
 * So the time complexity is: O(n)
 * Space complexity : O(n)
 * We need a HashMap to restore the element and its occurences, the size is O(n).
 * We also need a array of list to be buckets with length 1 to sort.
 * So the space complexity is: O(n)
 */
public class Solution {
	public List<Integer> topKFrequent(int[] nums, int k) {
		// corner case: if there is only one number in nums, we need the bucket has index 1.
		List<Integer>[] bucket = new List[nums.length + 1];
		Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

		for (int n : nums) {
			frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
		}

		for (Map.Entry<Integer, Integer> set : frequencyMap.entrySet()) {
			int frequency = set.getValue();
			if (bucket[frequency] == null) {
				bucket[frequency] = new ArrayList<>();
			}
			bucket[frequency].add(set.getKey());
		}

		List<Integer> rst = new ArrayList<>();
		for (int pos = bucket.length - 1; pos >= 0 && rst.size() < k; pos--) {
			if (bucket[pos] != null) {
                rst.addAll(bucket[pos]);
			}
		}
		return rst;
	}
}