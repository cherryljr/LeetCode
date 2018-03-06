/*
Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

Try to solve it in linear time/space.
Return 0 if the array contains less than 2 elements.
You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
*/

/**
 * Approach: Buckets and The Pigeonhole Principle
 * 这是非常经典的一道题目。
 * 由题意可得：题目中的数据均为 整数，并且范围在 0 到 32bit的符号型int 数据之间。
 * 介于 数据的特殊性 我们完全可以使用 Radix Sort 来解决这道题目。
 * 具体实现与分析可以参考：
 * https://leetcode.com/problems/maximum-gap/solution/
 *
 * 但是如果题目要求不能够使用 非比较的排序呢？
 * 这个时候我们就需要来谈一谈这道题目一个比较巧妙的解法了。
 * 它用到了 Bucket Sort 的思想 和 鸽巢原理。
 *
 * 具体做法如下：
 *  首先我们遍历整个数组得到数组的 最大值 与 最小值，并准备 len+1 个桶 (为什么这里准备 len+1 个桶呢？)。
 *  然后我们分别将数组的 min 和 max 放到 第一个桶 和 最后一个桶 中。
 *  然后我们再将 [min...max] 分成 num.length 等份，每个桶所能表示的数值范围为：gap = (max - min) / len.
 *  每个数值 nums[i] 根据其大小被归入其所在的桶中,即 nums[i] 所在的桶为：
 *      (int) ((nums[i] - min)) / gap) = (int) ((nums[i] - min) * len / (max - min))
 * 接下来，我们先来介绍一下什么是 鸽巢原理(Pigeonhole Principle).
 *  假如有 n 只鸽子，n+1 个巢穴.每只鸽子都进入巢穴后，至少会有一个巢穴 是空的。（很简单吧）
 *  然后我们回到这道题目，因为我们将所有的数都扔到了各个桶里，并且桶的总个数为 nums.length+1，因此必定有一个桶是空的。
 *  所以我们可以知道：相邻差最大的两个数 必定不存在 于同一个桶中。
 *  这样我们没有就必要考虑同一个桶中的数据。因为他们之间的差值必定 小于 一个空桶的大小。
 *  那么 MaxGap 产生的地方就只有：这个桶的 max 与 后一个桶的 min 之间了。
 *  注意，我们设计空桶的目的是为了 否定 答案来自一个桶的内部，但并不是答案一定来自空桶的两侧。
 *  比如：19， 空， 30， 49. MaxGap在 49-30=19 之间，并不是在空桶两侧 30-19=11.
 * 因此我们需要建立 3 个大小为 nums.length+1 的数组。分别为：
 *      hasNum[] 表示当前桶是否已经有存放数据;
 *      minBucket[] 表示当前桶所存放数据的最小值;
 *      maxBucket[] 表示当前桶所存放数据的最大值;
 *（hasNum数据的空间可以被优化掉，但是为了大家理解方便，我决定保留它）
 * 然后按照以上的做法，算出各个桶的 max 和 min, 最后遍历一遍所有的桶，便可以得到 MaxGap 了
 */
class Solution {
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // get the max and min value of the array
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i : nums) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }

        int len = nums.length;
        // the minimum possible gap
        double gap = ((double) (max - min)) / len;
        boolean[] hasNums = new boolean[len + 1];    // store the bucket has number or not
        int[] bucketsMIN = new int[len + 1];         // store the min value in that bucket
        int[] bucketsMAX = new int[len + 1];         // store the max value in that bucket

        // put numbers into buckets
        for (int i = 0; i < len; i++) {
            int index = (int) ((nums[i] - min) / gap);
            bucketsMIN[index] = hasNums[index] ? Math.min(bucketsMIN[index], nums[i]) : nums[i];
            bucketsMAX[index] = hasNums[index] ? Math.max(bucketsMAX[index], nums[i]) : nums[i];
            hasNums[index] = true;
        }

        // scan the buckets for the max gap
        int maxGap = 0;
        int lastMax = bucketsMAX[0];
        for (int i = 1; i <= len; i++) {
            if (hasNums[i]) {
                maxGap = Math.max(maxGap, bucketsMIN[i] - lastMax);
                lastMax = bucketsMAX[i];
            }
        }
        return maxGap;
    }
}