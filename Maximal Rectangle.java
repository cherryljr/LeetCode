A good question!
Solution 1: Turn to histogram
It is very similar as [Largest Rectangle in Histogram]:
https://github.com/cherryljr/LintCode/blob/master/Largest%20Rectangle%20in%20Histogram.java
What we need do is turning this matrix to a histogram.

You can maintain a 2D array height[][] to recorded a rectangle's height of '1's, 
and scan and update row by row to find out the largest rectangle of each row.

For each row, if matrix[row][i] == '1'. height[row][i] += 1, or reset the height[row][i] to zero.
and accroding the algorithm of [Largest Rectangle in Histogram], to update the maximum area.

Time complexity  : O(n^2). Calculate the height matrix.
Space complexity : O(n^2). Another matrix of same size is used for height.
 
Solution 2: DP
Of course, there is another solution which using DP.
The DP solution proceeds row by row, starting from the first row. 
Let the maximal rectangle area at row i and column j be computed by [right(i,j) - left(i,j)] * height(i,j).

All the 3 variables left, right, and height can be determined by the information from previous row, 
and also information from the current row. So it can be regarded as a DP solution. The transition equations are:

    left(i,j) = max(left(i-1,j), cur_left),     cur_left can be determined from the current row
    right(i,j) = min(right(i-1,j), cur_right),  cur_right can be determined from the current row
    height(i,j) = height(i-1,j) + 1,            if matrix[i][j]=='1';
    height(i,j) = 0,                            if matrix[i][j]=='0'

/*
Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

For example, given the following matrix:

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
Return 6.
*/

// Solution 1: Turn to histogram
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] height = new int[row][col];
        
        // calculate the height array
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '1') {
                    height[i][j] = i == 0 ? 1 : height[i - 1][j] + 1;
                } else {
                    height[i][j] = 0;
                }
            }
        }
        
        // calculate the max rectangle row by row
        int maxArea = 0;
        for (int i = 0; i < row; i++) {
            maxArea = Math.max(maxArea, maxAreaInHist(height[i]));
        }
        
        return maxArea;
    }
    
    public int maxAreaInHist(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        
        for (int i = 0; i <= height.length; i++) {
            int cur = i == height.length ? -1 : height[i];
            while (!stack.isEmpty() && cur < height[stack.peek()]) {
                int h = height[stack.pop()];
                int w = stack.isEmpty() ? i : (i - stack.peek() - 1); 
                max = Math.max(max, w * h);
            }
            stack.push(i);
        }
        
        return max;
    }
}

// Solution 2: DP
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0
           || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        int[] left = new int[cols];
        int[] right = new int[cols];
        int[] height = new int[cols];
        Arrays.fill(left, 0); 
        Arrays.fill(right, cols); 
        Arrays.fill(height, 0);
        
        int maxA = 0;
        for (int i = 0; i < rows; i++) {
            int cur_left = 0, cur_right = cols; 
            // compute height (can do this from either side)
            for (int j = 0; j < cols; j++) { 
                if (matrix[i][j] == '1') {
                    height[j]++; 
                }
                else {
                    height[j]=0;
                }
            }
            // compute left (from left to right)
            for (int j = 0; j < cols; j++) { 
                if (matrix[i][j] == '1') {
                    left[j]=Math.max(left[j], cur_left);
                }
                else {
                    left[j] = 0; 
                    cur_left = j + 1;
                }      
            }
            // compute right (from right to left)
            for (int j = cols - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], cur_right);
                }
                else {
                    right[j] = cols; 
                    cur_right = j;   
                }
            }
            // compute the area of rectangle (can do this from either side)
            for (int j = 0; j < cols; j++) {
                maxA = Math.max(maxA, (right[j] - left[j]) * height[j]);
            }   
        }
        
        return maxA;
    }
}