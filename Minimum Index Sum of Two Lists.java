/*
Suppose Andy and Doris want to choose a restaurant for dinner, 
and they both have a list of favorite restaurants represented by strings.

You need to help them find out their common interest with the least list index sum. 
If there is a choice tie between answers, output all of them with no order requirement. 
You could assume there always exists an answer.

Example 1:
Input:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
Output: ["Shogun"]
Explanation: The only restaurant they both like is "Shogun".

Example 2:
Input:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["KFC", "Shogun", "Burger King"]
Output: ["Shogun"]
Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).

Note:
The length of both lists will be in the range of [1, 1000].
The length of strings in both lists will be in the range of [1, 30].
The index is starting from 0 to the list length minus 1.
No duplicates in both lists.
 */

/**
 * Approach: HashMap (Linear)
 * We make use of a HashMap to solve the given problem in a different way in this approach.
 * Firstly, we traverse over the whole list1 and create an entry for each element of list1 in a HashMap map, of the form (list[i], i).
 * Here, i refers to the index of the i​th element, and list[i] is the ith element itself.
 * Thus, we create a mapping from the elements of list1 to their indices.
 * Now, we traverse over list2.
 * For every element ,list2[i], of list2 encountered, we check if the same element already exists as a key in the map.
 * If so, it means that the element exists in both list1 and list2.
 * Thus, we find out the sum of indices corresponding to this element in the two lists,
 * given by sum = map.get(list[i]) + i.
 * If this sum is lesser than the minimum sum obtained till now, we update the result list to be returned, rst,
 * with the element list2[i] as the only entry in it.
 * If the sum is equal to the minimum sum obtained till now, we put an extra entry corresponding to the element list2[i] in the rst list.
 */
class Solution {
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        // Using HashMap to record each element and their indices
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }

        List<String> rst = new ArrayList<>();
        int minSum = Integer.MAX_VALUE, sum;
        for (int i = 0; i < list2.length; i++) {
            if (map.containsKey(list2[i])) {
                sum = i + map.get(list2[i]);
                // If this sum is lesser than the minimum sum
                // clear the result list and add the list2[i] into the rst list
                if (sum < minSum) {
                    rst.clear();
                    rst.add(list2[i]);
                    minSum = sum;
                // If the sum is equal to the minimum sum
                // we put an extra entry corresponding to the element list2[i] in the rst list.
                } else if (sum == minSum) {
                    rst.add(list2[i]);
                }
            }
        }

        return rst.toArray(new String[rst.size()]);
    }
}