/*
There is a ball in a maze with empty spaces and walls.
The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
When the ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, find the shortest distance for the ball to stop at the destination.
The distance is defined by the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).
If the ball cannot stop at the destination, return -1.

The maze is represented by a binary 2D array.
1 means the wall and 0 means the empty space. You may assume that the borders of the maze are all walls.
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
Output: 12
Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
             The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.

Example 2
Input 1: a maze represented by a 2D array
0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0
Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (3, 2)
Output: -1
Explanation: There is no way for the ball to stop at the destination.

Note:
There is only one ball and one destination in the maze.
Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 */

/**
 * Approach 1: DFS
 * Algorithm
 * we make use of a 2-D distance array.
 * distance[i][j] represents the minimum number of steps required to reach the position (i,j) starting from the start position.
 * This array is initialized with largest integer values in the beginning.
 * When we reach any position next to a boundary or a wall during the traversal in a particular direction,
 * we keep a track of the number of steps taken in the last direction in count variable.
 * Suppose, we reach the position (i,j) starting from the last position (k,l).
 * Now, for this position, we need to determine the minimum number of steps taken to reach this position starting from the start position.
 * For this, we check if the current path takes lesser steps to reach (i,j) than any other previous path taken to reach the same position
 * i.e. we check if distance[k][l] + count is lesser than distance[i][j].
 * If not, we continue the process of traversal from the position (k,l) in the next direction.
 * If distance[k][l] + count is lesser than distance[i][j], we can reach the position(i,j) from the current route in lesser number of steps.
 * Thus, we need to update the value of distance[i][j] as distance[k][l]+count.
 * Further, now we need to try to reach the destination, dest, from the end position (i,j),
 * since this could lead to a shorter path to dest.
 * Thus, we again call the same function dfs but with the position (i,j) acting as the current position.
 *
 * After this, we try to explore the routes possible by choosing all the other directions of travel from the current position (k,l) as well.
 * At the end, the entry in distance array corresponding to the destination,
 * dest's coordinates gives the required minimum distance to reach the destination.
 * If the destination can't be reached, the corresponding entry will contain Integer.MAX_VALUE.
 *
 * Complexity Analysis
 *  Time complexity : O(m*n*max(m,n)).
 *  Complete traversal of maze will be done in the worst case.
 *  Here, m and n refers to the number of rows and columns of the maze.
 *  Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
 *  Space complexity : O(mn).
 *  distance array of size m*n is used.
 *
 * You can get more detail explanations here:
 * https://leetcode.com/articles/the-maze-ii/
 */
class Solution {
    public int shortestDistance(int[][] maze, int[] start, int[] dest) {
        int[][] distance = new int[maze.length][maze[0].length];
        // Initialize the distance array
        for (int[] row: distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distance[start[0]][start[1]] = 0;
        dfs(maze, start, distance);
        return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
    }

    public void dfs(int[][] maze, int[] position, int[][] distance) {
        // Use the direction array to make code more concise
        int[][] dirs={{0,1}, {0,-1}, {-1,0}, {1,0}};
        for (int[] dir: dirs) {
            int x = position[0];
            int y = position[1];
            int count = 0;

            while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                x += dir[0];
                y += dir[1];
                count++;
            }

            // Roll Back - When the program break from while loop above,
            // it meas that x, y has been added dir[0], dir[1] one more time and count also has been added 1.
            // But the correct answer (in the range) is less than it, so we should minus dir[0], dir[1] and count-- here.
            x -= dir[0];
            y -= dir[1];
            count--;
            if (distance[position[0]][position[1]] + count < distance[x][y]) {
                distance[x][y] = distance[position[0]][position[1]] + count;
                dfs(maze, new int[]{x, y}, distance);
            }
        }
    }
}

/**
 * Approach 2: BFS
 * Algorithm
 * Instead of making use of Depth First Search for exploring the search space, we can make use of Breadth First Search as well.
 * In this, instead of exploring the search space on a depth basis, we traverse the search space(tree) on a level by level basis
 * i.e. we explore all the new positions that can be reached starting from the current position first,
 * before moving onto the next positions that can be reached from these new positions.
 *
 * In order to make a traversal in any direction, we again make use of dirs array as in the DFS approach.
 * Again, whenever we make a traversal in any direction,
 * we keep a track of the number of steps taken while moving in this direction in count variable as done in the last approach.
 * We also make use of distance array initialized with very large values in the beginning.
 * distance[i][j] again represents the minimum number of steps required to reach the position (i,j) from thestart position.
 *
 * This approach differs from the last approach only in the way the search space is explored.
 * In order to reach the new positions in a Breadth First Search order, we make use of a queue,
 * which contains the new positions to be explored in the future.
 * We start from the current position (k,l), try to traverse in a particular direction, obtain the corresponding count for that direction,
 * reaching an end position of (i,j) just near the boundary or a wall.
 * If the position (i,j) can be reached in a lesser number of steps from the current route than any other previous route checked,
 * indicated by distance[k][l] + count < distance[i][j], we need to update distance[i][j] as distance[k][l] + count.
 *
 * After this, we add the new position obtained, (i,j) to the back of a queue, so that the various paths possible from
 * this new position will be explored later on when all the directions possible from the current position (k,l) have been explored.
 * After exploring all the directions from the current position,
 * we remove an element from the front of the queue and continue checking the routes possible through all the directions now taking the new position
 * (obtained from the queue) as the current position.
 * Again, the entry in distance array corresponding to the destination,
 * dest's coordinates gives the required minimum distance to reach the destination.
 * If the destination can't be reached, the corresponding entry will contain Integer.MAX_VALUE
 *
 * Complexity Analysis
 *  Time complexity :O(m*n*max(m,n)). 
 *  Complete traversal of maze will be done in the worst case.
 *  Here, m and n refers to the number of rows and columns of the maze.
 *  Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
 *  Space complexity :O(mn). queue size can grow upto m*n in the worst case..
 */
class Solution {
    public int shortestDistance(int[][] maze, int[] start, int[] dest) {
        int[][] distance = new int[maze.length][maze[0].length];
        // Initialize the distance array
        for (int[] row: distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distance[start[0]][start[1]] = 0;

        Queue<int[]> queue = new LinkedList<>();
        int[][] dirs={{0, 1} ,{0, -1}, {-1, 0}, {1, 0}};
        queue.add(start);
        while (!queue.isEmpty()) {
            int[] position = queue.poll();

            for (int[] dir: dirs) {
                int x = position[0];
                int y = position[1];
                int count = 0;

                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }

                // Roll Back - When the program break from while loop above,
                // it meas that x, y has been added dir[0], dir[1] one more time and count also has been added 1.
                // But the correct answer (in the range) is less than it, so we should minus dir[0], dir[1] and count-- here.
                x -= dir[0];
                y -= dir[1];
                count--;
                if (distance[position[0]][position[1]] + count < distance[x][y]) {
                    distance[x][y] = distance[position[0]][position[1]] + count;
                    queue.add(new int[] {x, y});
                }
            }
        }

        return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
    }
}