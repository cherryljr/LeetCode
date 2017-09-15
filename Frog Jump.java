
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

// Solution 1: DFS  ->  TLE
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

// Solution 2: DP
class Solution {
    public boolean canCross(int[] stones) {
        if (stones.length == 0) {
        	return true;
        }
        
        HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(stones.length);
        map.put(0, new HashSet<Integer>());
        map.get(0).add(1);
        for (int i = 1; i < stones.length; i++) {
        	map.put(stones[i], new HashSet<Integer>() );
        }
        
        for (int i = 0; i < stones.length - 1; i++) {
        	int stone = stones[i];
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

