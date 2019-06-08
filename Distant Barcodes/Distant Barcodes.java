/*
In a warehouse, there is a row of barcodes, where the i-th barcode is barcodes[i].
Rearrange the barcodes so that no two adjacent barcodes are equal.
You may return any answer, and it is guaranteed an answer exists.

Example 1:
Input: [1,1,1,2,2,2]
Output: [2,1,2,1,2,1]

Example 2:
Input: [1,1,1,1,2,2,3,3]
Output: [1,3,1,3,2,1,2,1]

Note:
    1. 1 <= barcodes.length <= 10000
    2. 1 <= barcodes[i] <= 10000
 */

/**
 * Approach 1: HashMap + PriorityQueue
 * 首先利用 HashMap 来存储各个 barcode 和对应的出现次数；
 * 然后利用 PriorityQueue 对 barcode 依据在 map 中记录好的 出现次数 按照 从大到小 进行排序；
 * 最后从出现次数最多的 barcode 开始，将其填入到结果的 偶数位 上。填满后，再填奇数位上的数。
 * 利用了 间接填数 的方法，从而分割开相同的数。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        // key == barcode; value == code
        Map<Integer, Integer> map = new HashMap<>();
        for (int barcode : barcodes) {
            map.put(barcode, map.getOrDefault(barcode, 0) + 1);
        }
        // Sort the code according to the frequency of each code in decreasing order
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
        pq.addAll(map.keySet());

        int[] ans = new int[barcodes.length];
        int index = 0;
        while (!pq.isEmpty()) {
            // get current most frequent code and its frequency
            int barcode = pq.poll(), freq = map.remove(barcode);
            // fill ans with the code for freq times
            while (freq-- > 0) {
                ans[index] = barcode;
                // if even indices depleted, start to use odd ones
                index = index + 2 >= ans.length ? 1 : index + 2;
            }
        }
        return ans;
    }
}

/**
 * Approach 2: Just Find the Most Frequent Code (Without Sorting)
 * 其实我们只需要找出 出现次数最多 的 barcode，然后按照间隔的方式将其填入到结果的 偶数位 上即可。
 * 然后对于剩下的元素，我们依然按照 间隔填数 的方式填入，但是先填哪个数此时已经并不重要了。
 * 这是因为我们已经使用了 出现次数最多 的 barcode 作为分割，剩下的数出现次数必定不大于 maxFreqCode。
 * 因此只要我们按照 间隔填数 的方法，就不可能出现相邻元素重复的情况。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int[] freqs = new int[10001];
        // the most frequent code
        int maxFreqCode = 0;
        for (int barcode : barcodes) {
             // update the code of max frequency.
            if (++freqs[barcode] > freqs[maxFreqCode]) {
                maxFreqCode = barcode;
            }
        }

        int[] ans = new int[barcodes.length];
        for (int i = 0, index = 0; i < freqs.length; i++) {
            // fill in most frequent code first
            int code = i == 0 ? maxFreqCode : i;
            while (freqs[code]-- > 0) {
                ans[index] = code;
                // fill even indices first, if depleted, use odd ones
                index = index + 2 >= ans.length ? 1 : index + 2;
            }
        }
        return ans;
    }
}