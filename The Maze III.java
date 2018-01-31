/*
There is a ball in a maze with empty spaces and walls. 
The ball can go through empty spaces by rolling up (u), down (d), left (l) or right (r), but it won't stop rolling until hitting a wall. 
When the ball stops, it could choose the next direction.
There is also a hole in this maze. The ball will drop into the hole if it rolls on to the hole.

Given the ball position, the hole position and the maze, find out how the ball could drop into the hole by moving the shortest distance. 
The distance is defined by the number of empty spaces traveled by the ball from the start position (excluded) to the hole (included). 
Output the moving directions by using 'u', 'd', 'l' and 'r'. 
Since there could be several different shortest ways, you should output the lexicographically smallest way. 
If the ball cannot reach the hole, output "impossible".

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. 
You may assume that the borders of the maze are all walls. The ball and the hole coordinates are represented by row and column indexes.

Example 1
Input 1: a maze represented by a 2D array
0 0 0 0 0
1 1 0 0 1
0 0 0 0 0
0 1 0 0 1
0 1 0 0 0
Input 2: ball coordinate (rowBall, colBall) = (4, 3)
Input 3: hole coordinate (rowHole, colHole) = (0, 1)
Output: "lul"
Explanation: There are two shortest ways for the ball to drop into the hole.
The first way is left -> up -> left, represented by "lul".
The second way is up -> left, represented by 'ul'.
Both ways have shortest distance 6, but the first way is lexicographically smaller because 'l' < 'u'. So the output is "lul".

Example 2
Input 1: a maze represented by a 2D array
0 0 0 0 0
1 1 0 0 1
0 0 0 0 0
0 1 0 0 1
0 1 0 0 0
Input 2: ball coordinate (rowBall, colBall) = (4, 3)
Input 3: hole coordinate (rowHole, colHole) = (3, 0)
Output: "impossible"
Explanation: The ball cannot reach the hole.

Note:
There is only one ball and one hole in the maze.
Both the ball and hole exist on an empty space, and they will not be at the same position initially.
The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
The maze contains at least 2 empty spaces, and the width and the height of the maze won't exceed 30.
 */

/**
 * Approach: BFS
 * Using Point Class and PriorityQueue to make the code more concise
 * and easy understanding.
 *
 * We can solve this problem on the basis of The Maze.
 */
class Solution {
    class Point implements Comparable<Point> {
        int x;
        int y;
        int dis;  // distance from ball
        String path;  // directions from ball
        Point(int x, int y, int dis, String path) {
            this.x = x;
            this.y = y;
            this.dis = dis;
            this.path = path;
        }
        // if both ways have shortest distance, they should be ordered lexicographically
        public int compareTo(Point point) {
            return this.dis == point.dis ? this.path.compareTo(point.path) : this.dis - point.dis;
        }
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];

        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.offer(new Point(ball[0], ball[1], 0, ""));

        // arrays used for exploring 4 directions from a point
        char[] dstr = {'u', 'd', 'l', 'r'};
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!pq.isEmpty()) {
            Point position = pq.poll();
            if (position.x == hole[0] && position.y == hole[1]) {
                return position.path;
            }

            for (int i = 0; i < dirs.length; i++) {
                int x = position.x;
                int y = position.y;
                int dis = position.dis;
                String path = position.path;

                // Explore current direction until hitting a wall or the hole
                while (x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0 && (x != hole[0] || y != hole[1])) {
                    x += dirs[i][0];
                    y += dirs[i][1];
                    dis += 1;
                }
                // if the ball didn't encounter the hole, we need to roll back one step
                // to get the right position that the ball can reach (in range)
                if (x != hole[0] || y != hole[1]) {
                    x -= dirs[i][0];
                    y -= dirs[i][1];
                    dis -= 1;
                }
                if (!visited[x][y]) {
                    visited[position.x][position.y] = true;
                    pq.offer(new Point(x, y, dis, path + dstr[i]));
                }
            }
        }

        return "impossible";
    }
}