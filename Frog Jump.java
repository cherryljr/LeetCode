/*
A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone.
The frog can jump on a stone, but it must not jump into the water.
Given a list of stones' positions (in units) in sorted ascending order,
determine if the frog is able to cross the river by landing on the last stone. Initially,
the frog is on the first stone and assume the first jump must be 1 unit.
If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units.
Note that the frog can only jump in the forward direction.
Note:
The number of stones is ≥ 2 and is < 1,100.
Each stone's position will be a non-negative integer < 231.
The first stone's position is always 0.
Example 1:
[0,1,3,5,6,8,12,17]
There are a total of 8 stones.
The first stone at the 0th unit, second stone at the 1st unit,
third stone at the 3rd unit, and so on...
The last stone at the 17th unit.
Return true. The frog can jump to the last stone by jumping
1 unit to the 2nd stone, then 2 units to the 3rd stone, then
2 units to the 4th stone, then 3 units to the 6th stone,
4 units to the 7th stone, and 5 units to the 8th stone.
Example 2:
[0,1,2,3,4,8,9,11]
Return false. There is no way to jump to the last stone as
the gap between the 5th and 6th stone is too large.
*/

/**
 * Approach 1: DFS Recursion (Time Limit Exceeded)
 * 一开始以为这是一道 DP 问题，但是后来发现这道题目中每次操作是 有后效性 的。
 * 即当青蛙到达 ith 位置时，ith 的答案是与如何到达当前状态是相关的（当前所跳的步数与上一步的步数k有关系）
 * 因此这是无法使用 DP 解决的。
 * 那么我们直接采用 递归尝试求解 的方法试试看。
 * 
 * PS.这个方法之前是能过的，更新测试用例之后过去不了...
 */
class Solution {
    public boolean canCross(int[] stones) {
        if (stones[1] > 1) {
            return false;
        }
        if (stones.length == 2) {
            return true;
        }

        return helper(stones, 1, 1);
    }

    private boolean helper(int[] arr, int startIndex, int step) {
        boolean pass = false;
        if (startIndex == arr.length - 1) {
            return true;
        }
        for (int i = startIndex + 1; i < arr.length; i++) {
            if(arr[i] <= arr[startIndex] + step + 1 && arr[i] >= arr[startIndex] + step - 1){
                pass = pass || helper(arr, i, arr[i] - arr[startIndex]);
            }
        }
        return pass;
    }
}

/**
 * Approach 2: Using HashMap
 * Use map to represent a mapping from the stone (not index) to the steps that can be taken from this stone.
 * so this will be
 *      [0,1,3,5,6,8,12,17]
 * {17=[], 0=[1], 1=[1, 2], 3=[1, 2, 3], 5=[1, 2, 3], 6=[1, 2, 3, 4], 8=[1, 2, 3, 4], 12=[3, 4, 5]}
 * Notice that no need to calculate the last stone.
 * On each step, we look if any other stone can be reached from it,
 * if so, we update that stone’s steps by adding step, step + 1, step - 1.
 * If we can reach the final stone, we return true.
 * No need to calculate to the last stone.
 */
class Solution {
    public boolean canCross(int[] stones) {
        if (stones.length == 0) {
            return true;
        }

        // Initialize the map
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>(stones.length);
        map.put(0, new HashSet<>());
        map.get(0).add(1);
        for (int i = 1; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>() );
        }

        for (int i = 0; i < stones.length - 1; i++) {
            int stone = stones[i];
            // calculate the steps of each stone
            for (int step : map.get(stone)) {
                int reach = step + stone;
                if (reach == stones[stones.length - 1]) {
                    return true;
                }
                HashSet<Integer> set = map.get(reach);
                if (set != null) {
                    set.add(step);
                    if (step - 1 > 0) {
                        set.add(step - 1);
                    }
                    set.add(step + 1);
                }
            }
        }

        return false;
    }
}