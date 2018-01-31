/*
There is a ball in a maze with empty spaces and walls.
The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
When the ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination.
The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
You may assume that the borders of the maze are all walls.
The start and destination coordinates are represented by row and column indexes.

Example 1
Input 1: a maze represented by a 2D array
0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0
Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)
Output: true
Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.

Example 2
Input 1: a maze represented by a 2D array
0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0
Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (3, 2)
Output: false
Explanation: There is no way for the ball to stop at the destination.

Note:
There is only one ball and one destination in the maze.
Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 */

/**
 * Approach 1: DFS (Time Limit Exceeded)
 * Algorithm
 * We can view the given search space in the form of a tree.
 * The root node of the tree represents the starting position.
 * Four different routes are possible from each position
 * i.e. left, right, up or down. These four options can be represented by 4 branches of each node in the given tree.
 * Thus, the new node reached from the root traversing over the branch represents the new position occupied by the ball
 * after choosing the corresponding direction of travel.
 * In order to do this traversal, one of the simplest schemes is to undergo depth first search.
 * In this case, we choose one path at a time and try to go as deep as possible into the levels of the tree before going for the next path.
 * In order to implement this, we make use of a recursive function dfs(maze, start, destination, visited).
 * This function takes the given maze array, the start position and the destination position as its arguments along with a visited array.
 * visited array is a 2-D boolean array of the same size as that of maze.
 * A True value at visited[i][j] represents that the current position has already been reached earlier during the path traversal.
 * We make use of this array so as to keep track of the same paths being repeated over and over.
 * We mark a True at the current position in the visited array once we reach that particular position in the maze.
 *
 * From every start position, we can move continuously in either left, right, upward or downward direction till we reach the boundary or a wall.
 * Thus, from the start position, we determine all the end points which can be reached by choosing the four directions.
 * For each of the cases, the new endpoint will now act as the new start point for the traversals.
 * The destination, obviously remains unchanged. Thus, now we call the same function four times for the four directions,
 * each time with a new start point obtained previously.
 * If any of the function call returns a True value, it means we can reach the destination.
 *
 * Complexity Analysis
 *  Time complexity : O(mn). 
 *  Complete traversal of maze will be done in the worst case. Here, m and n refers to the number of rows and columns of the maze.
 *  Space complexity : O(mn). 
 *  visited array of size m*nm∗n is used.
 * 
 * You can get more detail explanations here:
 * https://leetcode.com/articles/the-maze/
 */
class Solution {
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return dfs(maze, start, destination, visited);
    }

    public boolean dfs(int[][] maze, int[] position, int[] destination, boolean[][] visited) {
        if (visited[position[0]][position[1]]) {
            return false;
        }
        if (position[0] == destination[0] && position[1] == destination[1]) {
            return true;
        }
        // mark the point has been visited
        visited[position[0]][position[1]] = true;

        // Learn form BFS's code, we can write the code more concise
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        for (int[] dir : dirs) {
            int x = position[0];
            int y = position[1];
            while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                x += dir[0];
                y += dir[1];
            }
            // Roll Back - When the program break from while loop above,
            // it meas that x, y has been added dir[0], dir[1] one more time.
            // But the correct answer (in the range) is less than it, so we should minus dir[0] and dir[1] here.
            if (dfs(maze, new int[]{x - dir[0], y - dir[1]}, destination, visited)) {
                return true;
            }
        }

        return false;
    }
}

/**
 * Approach 2: BFS
 * Algorithm
 * The same search space tree can also be explored in a Depth First Search manner.
 * In this case, we try to explore the search space on a level by level basis.
 * i.e. We try to move in all the directions at every step.
 * When all the directions have been explored and we still don't reach the destination,
 * then only we proceed to the new set of traversals from the new positions obtained.
 *
 * In order to implement this, we make use of a queue.
 * We start with the ball at the start position.
 * For every current position, we add all the new positions possible by traversing in all the four directions(till reaching the wall or boundary) into
 * the queue to act as the new start positions and mark these positions as True in the visited array.
 * When all the directions have been covered up, we remove a position value, position,
 * from the front of the queue and again continue the same process with s acting as the new start position.
 *
 * Further, in order to choose the direction of travel, we make use of a dir array,
 * which contains 4 entries. Each entry represents a one-dimensional direction of travel.
 * To travel in a particular direction, we keep on adding the particular entry of the dirs array till we hit a wall or a boundary.
 * For a particular start position, we do this process of dir addition for all all the four directions possible.
 * If we hit the destination position at any moment, we return a True directly.
 *
 * Complexity Analysis
 * Time complexity : O(mn). 
 * Complete traversal of maze will be done in the worst case. Here, m and n refers to the number of rows and columns of the maze.
 * Space complexity :O(mn). 
 * visited array of size m*n is used and queue size can grow upto m*n in worst case.
 */
class Solution {
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> queue = new LinkedList<>();
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        queue.add(start);
        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            if (position[0] == destination[0] && position[1] == destination[1]) {
                return true;
            }

            for (int[] dir : dirs) {
                int x = position[0];
                int y = position[1];

                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                }
                // Roll Back - When the program break from while loop above,
                // it meas that x, y has been added dir[0], dir[1] one more time.
                // But the correct answer (in the range) is less than it, so we should minus dir[0] and dir[1] here.
                x -= dir[0];
                y -= dir[1];
                if (!visited[x][y]) {
                    // mark the point has been visited
                    visited[x][y] = true;
                    queue.add(new int[]{x, y});
                }
            }
        }

        return false;
    }
}
