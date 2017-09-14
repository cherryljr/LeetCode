Ê¹ÓÃ¶ş·Ö·¨Ñ°ÕÒ ¾Ö²¿×î´óÖµ¡£
½«¸´ÔÓ¶ÈÓÅ»¯Îª O(nlogn)
Õâ¸öÆôÊ¾ÎÒÃÇ£ºÖ»ÒªÊÇ¾ßÓĞÅÅËûĞÔ£¬±ãÄÜ¹»Ê¹ÓÃ¶ş·Ö·¨¡£¶ø²»Ò»¶¨ÒªÇó±ØĞëÅÅĞò¡£

Í¬£ºNowCoder ÖĞµÄ ¶ş·Ö·¨Çó¾Ö²¿×îĞ¡Öµ

/*
A peak element is an element that is greater than its neighbors.

Given an input array where num[i] â‰  num[i+1], find a peak element and return its index.

The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

You may imagine that num[-1] = num[n] = -âˆ.

For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.
*/

class Solution {
    public int findPeakElement(int[] nums) {
		if (nums == null || nums.length <= 1) {
			return 0;
		}
		
		int len = nums.length;
		if (nums[0] > nums[1]) {
			return 0;
		}
		if (nums[len - 1] > nums[len - 2]) {
			return len - 1;
		}
		
		int start = 0;
		int end = len - 1;
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] < nums[mid + 1]) {
				start = mid;
			} else {
				end = mid;
			}
		}
		
		return (nums[start] > nums[start + 1]) ? start : end;
    }
}