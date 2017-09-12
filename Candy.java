Sequence DP
记得需要 从左到右 和 从右到左 扫两边
来保证 rate 值较大者获得的糖果比左右两边的孩子要多

/*
There are N children standing in a line. Each child is assigned a rating value.
You are giving candies to these children subjected to the following requirements:
Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
What is the minimum candies you must give?
*/

public class Solution {
    public int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0) {
            return 0;
        }
         
        int[] count = new int[ratings.length];
        // 每个孩子初始都有一个糖果
        Arrays.fill(count,1);
        int sum = 0;
        // 从左向右扫描，保证一个方向上分数更大的糖果更多
        for(int i = 1; i < ratings.length; i++) {
            if(ratings[i] > ratings[i-1]) {
                count[i] = count[i-1] + 1;
            }
        }
        
        // 从右向左扫描，保证另一个方向上分数更大的糖果更多
        for(int i = ratings.length-1; i>0; i--) {
            sum += count[i];
            if(ratings[i] < ratings[i-1] && count[i] >= count[i-1]) {
                count[i-1] = count[i] + 1;
            }
        }
        sum += count[0];
         
        return sum;
    }
}
