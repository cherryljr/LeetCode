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
 
 
// Ordinary Method
class Solution {
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{ball[0], ball[1]});
        int m = maze.length, n = maze[0].length;
        int[][] dir = new int[][]{{1, -1, 0, 0}, {0, 0, 1, -1}};
        String[] pa = new String[]{"d", "u", "r", "l"};
        int[][] dp = new int[m][n];
        String[][] dpPath = new String[m][n];
        for (int[] d: dp) {
            Arrays.fill(d, Integer.MAX_VALUE);· 
        }
        for (String[] d: dpPath) {
            Arrays.fill(d, "z");
        }
        dp[ball[0]][ball[1]] = 0;
        dpPath[ball[0]][ball[1]] = "";
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int row = cur[0];
                int col = cur[1];
                String path = dpPath[row][col];
                int dist = dp[row][col];
                path += pa[i];
                while (row >= 0 && row < m && col >= 0 && col < n && maze[row][col] != 1) {
                    if (row == hole[0] && col == hole[1]) {
                        break;
                    }
                    row += dir[0][i];
                    col += dir[1][i];
                    dist++;
                }
                if (row != hole[0] || col != hole[1]) {
                    row -= dir[0][i];
                    col -= dir[1][i];
                    dist--;
                }
                if (row == cur[0] && col == cur[1]) {
                    continue;
                }
                if (dist <= dp[row][col]) { 
                    if (dist < dp[row][col]) {
                        dp[row][col] = dist;
                        dpPath[row][col] = path;
                    } else if (path.compareTo(dpPath[row][col]) < 0) {
                        dpPath[row][col] = path;
                    }
                    queue.offer(new int[]{row, col});
                }
            }
        }
        return dpPath[hole[0]][hole[1]].equals("z")? "impossible": dpPath[hole[0]][hole[1]];
    }
}

/**
 * Approach 2: BFS
 * Using Point Class and PriorityQueue to make the code more concise
 * and easy understanding.
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
        public int compareTo(Point point) {
            return this.dis == point.dis ? this.path.compareTo(point.path) : this.dis - point.dis;
        }
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];

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
                while (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 0 && (x != hole[0] || y != hole[1])) {
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
                    pq.offer(new Point(x, y, dis,path + dstr[i]));
                }
            }
        }

        return "impossible";
    }
}