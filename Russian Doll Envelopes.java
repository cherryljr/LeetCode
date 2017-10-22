首先我们考虑一个简单的情况：
    所有信封的长和宽并不重复。
    这样我们可以：先对长进行排序，然后对 宽 用二分法求 最长递增子序列（LIS）即可。
    时间复杂度为：O(nlogn) 用 DP 解法的话就要 O(N^2)了。

接下来，我们来考虑该题的情况，即：存在长或者宽相等的情况。在这种情况下，
一个信封是无法被装入另一个 长/宽 相等的一个信封中的。因此我们这边使用了一个技巧：
    仍然先对信封的长进行 从小到大 的排序，当长度相等的时候，对宽度进行 从大到小 的排序。
    这边都宽度进行 从大到小 的排序是为了使得：当长度相等时，宽度最大的信封是排在最前面的。
    这样就避免了会将长度相等，但是宽度更小的信封装入一个宽度更大的信封中的情况。
    比如： [[5,4],[5,5],[6,7],[2,3]], 如果长度相等时，宽度仍然按照 从小到大 的排序的话，结果为：[[2,3],[5,4],[5,5],[6,7]]
    就会出现：[5,4] => [5,5],即将 宽4长5 的信封装入 宽5长5 的信封中的情况，而这是错误的。
    但是如果长度相等时，将宽度按照 从大到小 的顺序排列的话，结果为：[[2,3],[5,5],[5,4],[6,7]].
    这样就能保证：所有宽度大于前面信封宽度的信封的长度必定是大于之前信封长度的。（比较绕，但是意思相信大家已经理解了）
    最后只需要对 信封的宽度 求LIS即可。这边使用了二分查找最小结尾数组的方法。较为巧妙，可以达到 O(nlogn) 的时间复杂度。
    
/*
You have a number of envelopes with widths and heights given as a pair of integers (w, h).
One envelope can fit into another 
if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

What is the maximum number of envelopes can you Russian doll? (put one inside other)

Example:
Given envelopes = [[5,4],[6,4],[6,7],[2,3]], 
the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
*/

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0
           || envelopes[0] == null || envelopes[0].length != 2) {
            return 0;
        }
        // 对信封进行约定好的排序
        Arrays.sort(envelopes, new Comparator<int[]>(){
           public int compare(int[] arr1, int[] arr2) {
               if (arr1[0] != arr2[0]) {
                   return arr1[0] - arr2[0];
               } else {
                   return arr2[1] - arr1[1];
               }
           } 
        });
        
        // 初始化最小结尾数组
        int[] minLast = new int[envelopes.length];
        Arrays.fill(minLast, Integer.MAX_VALUE);
        // 利用最小结尾数组，求宽度的 LIS
        for (int i = 0; i < envelopes.length; i++) {
            int index = binarySearch(minLast, envelopes[i][1]);
            minLast[index] = envelopes[i][1];
        }
        
        for (int i = minLast.length - 1; i >= 0; i--) {
            if (minLast[i] != Integer.MAX_VALUE) {
                return i + 1;
            }
        }
        return 0;
    }
    
    // 二分搜索法寻找 minLast 数组中第一个大于等于 n 的数
    public static int binarySearch(int[] minLast, int n) {
        int start = 0;
        int end = minLast.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (minLast[mid] < n) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        
        return start;
    }
}
