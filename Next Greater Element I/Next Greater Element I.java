/*
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2.
Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2.
If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.

Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.

Note:
    1. All elements in nums1 and nums2 are unique.
    2. The length of both nums1 and nums2 would not exceed 1000.
 */

/**
 * Approach 1: HashMap
 * 直接利用HashMap存储 nums2 所有元素在数组中对应的位置。（各个元素是 unique 的，所以不用担心覆盖问题）
 * 然后遍历 nums1 中的各个元素，因为 nums1 是 nums2 的 subset.
 * 因此必定能够找到元素在 nums2 中的位置，然后从该位置开始向后遍历寻找第一个遇到的 greater number 即可。
 * 如果找不到返回 -1 即可。
 *
 * 时间复杂度：O(N * M)
 * 空间复杂度：O(N)
 */
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }

        int[] ans = new int[nums1.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = getNextGreater(nums2, map.get(nums1[i]), nums1[i]);
        }
        return ans;
    }

    private int getNextGreater(int[] arr, int index, int x) {
        while (index < arr.length) {
            if (arr[index] > x) {
                break;
            }
            index++;
        }
        return index == arr.length ? -1 : arr[index];
    }
}

/**
 * Approach 2: Monotonic Stack (Using ArrayDeque)
 * 寻找下一个比当前元素要大的元素，很明显是对 单调栈 知识点的考察。
 * 因为 nums1 中的元素全部都在 nums2 里面。
 * 所以我们可以求 nums2 中所有元素的 next greater element. 然后将求得的结果存储在 map 中，
 * 最后遍历一遍 nums1，从 map 中获取到对应的结果，
 * 如果map中找不到就说明该元素不存在 next greater element，返回 -1 即可.
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * 类似的问题：
 * Daily Temperatures:
 *  https://github.com/cherryljr/LeetCode/blob/master/Daily%20Temperatures.java
 * Max Tree:
 *  https://github.com/cherryljr/LintCode/blob/master/Max%20Tree.java
 */
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> stack = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }

        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.getOrDefault(nums1[i], -1);
        }
        return ans;
    }
}
