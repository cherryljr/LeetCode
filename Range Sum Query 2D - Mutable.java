/*
Given a 2D matrix matrix, 
find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 image:http://leetcode.com/problems/range-sum-query-2d-mutable/
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
update(3, 2, 2)
sumRegion(2, 1, 4, 3) -> 10

Note:
The matrix is only modifiable by the update function.
You may assume the number of calls to update and sumRegion function is distributed evenly.
You may assume that row1 ≤ row2 and col1 ≤ col2.
*/

/**
 * Approach 1: Segment Tree (2D)
 * 该题与 Range Sum Query - Mutable 相同，只不过是将原来的一维数组转换成了二维矩阵。
 * 但是解法仍然相同，只需要在线段树的结构上进行稍微的调整即可。
 * 根据需求，我们需要构建2D Segment Tree。依然是使用Divide and Conquer的思想。
 * 我们要把整个平面分成 4 个部分，每个节点有四个子节点，NW, NE, SW, SE，节点的 sum 是四个子节点的 sum 之和。
 * 这样我们就可以用与 1D Segment Tree 类似的方法来写 rangeSum 以及 update。
 *
 * 注意：进行查询时因为是二维结构，因此总共有四块部分。要考虑的情况比较复杂一些。
 * 我们可以试着这样进行 Divide：
 * 首先考虑查询范围只分布在上半部分，然后再对上半部分分为左右两块进行讨论。（左，右，横跨左右）
 * 然后考虑查询范围只分布在下半部分，然后再对下半部分分为左右两块进行讨论。（左，右，横跨左右）
 * 如果均不符合以上情况，说明查询范围横跨上下两个部分，然后同样将其分为左右两块进行讨论（左，右，横跨整个矩阵）
 */
class NumMatrix {
    private class SegmentTreeNode2D {
        public int tlRow; // the upper left corner's row-coordinate
        public int tlCol; // the upper left corner's col-coordinate
        public int brRow; // the lower right corner's row-coordinate
        public int brCol; // the lower right corner's col-coordinate
        public int sum;
        public SegmentTreeNode2D nw, ne, sw, se;

        SegmentTreeNode2D(int tlRow, int tlCol, int brRow, int brCol) {
            this.tlRow = tlRow;
            this.tlCol = tlCol;
            this.brRow = brRow;
            this.brCol = brCol;
            this.sum = 0;
            this.nw = this.ne = this.sw = this.se = null;
        }
    }

    public SegmentTreeNode2D root;

    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }
        root = buildTree(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1);
    }

    private SegmentTreeNode2D buildTree(int[][] matrix, int tlRow, int tlCol, int brRow, int brCol) {
        if (tlRow > brRow || tlCol > brCol) {
            return null;
        }

        SegmentTreeNode2D node = new SegmentTreeNode2D(tlRow, tlCol, brRow, brCol);
        if (tlRow == brRow && tlCol == brCol) {
            node.sum = matrix[tlRow][tlCol];
            return node;
        }
        int rowMid = tlRow + (brRow - tlRow) / 2;
        int colMid = tlCol + (brCol - tlCol) / 2;
        node.nw = buildTree(matrix, tlRow, tlCol, rowMid, colMid);
        node.ne = buildTree(matrix, tlRow, colMid + 1, rowMid, brCol);
        node.sw = buildTree(matrix, rowMid + 1, tlCol, brRow, colMid);
        node.se = buildTree(matrix, rowMid + 1, colMid + 1, brRow, brCol);

        node.sum = 0;
        if (node.nw != null) {
            node.sum += node.nw.sum;
        }
        if(node.ne != null) {
            node.sum += node.ne.sum;
        }
        if(node.sw != null) {
            node.sum += node.sw.sum;
        }
        if(node.se != null) {
            node.sum += node.se.sum;
        }
        return node;
    }

    public void update(int row, int col, int val) {
        modify(root, row, col, val);
    }

    private void modify(SegmentTreeNode2D node, int row, int col, int val) {
        if (node.tlRow == row && node.brRow == row && node.tlCol == col && node.brCol == col) {
            node.sum = val;
            return;
        }

        int rowMid = node.tlRow + (node.brRow - node.tlRow) / 2;
        int colMid = node.tlCol + (node.brCol - node.tlCol) / 2;
        if (row <= rowMid) {
            if (col <= colMid) {
                // the pointer in the north-west quadrant
                modify(node.nw, row, col, val);
            } else {
                // the pointer in the north-east quadrant
                modify(node.ne, row, col, val);
            }
        } else {
            if (col <= colMid) {
                // the pointer in the south-west quadrant
                modify(node.sw, row, col, val);
            } else {
                // the pointer in the south-east quadrant
                modify(node.se, row, col, val);
            }
        }

        // Update the result
        node.sum = node.nw.sum + node.ne.sum + node.sw.sum + node.se.sum;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return query(root, row1, col1, row2, col2);
    }

    private int query(SegmentTreeNode2D node, int tlRow, int tlCol, int brRow, int brCol) {
        // 根据线段树的性质，两个节点要么被包含，要么没有任何重叠部分
        // 因此当前节点所表示的范围被所要查询的范围所包含时，直接返回结果即可
        if (node.tlRow >= tlRow && node.tlCol >= tlCol && node.brRow <= brRow && node.brCol <= brCol) {
            return node.sum;
        }

        int rowMid = node.tlRow + (node.brRow - node.tlRow) / 2;
        int colMid = node.tlCol + (node.brCol - node.tlCol) / 2;
        if (brRow <= rowMid) {  // top-half plane
            if (brCol <= colMid) {         // north-west quadrant
                return query(node.nw, tlRow, tlCol, brRow, brCol);
            } else if (tlCol > colMid) {    // north-east quadrant
                return query(node.ne, tlRow, tlCol, brRow, brCol);
            } else {                // intersection between nw and ne
                return query(node.nw, tlRow, tlCol, brRow, colMid) + query(node.ne, tlRow, colMid + 1, brRow, brCol);
            }
        } else if (tlRow > rowMid) {         // bot-half plane
            if (brCol <= colMid) {         // south-west quadrant
                return query(node.sw, tlRow, tlCol, brRow, brCol);
            } else if (tlCol > colMid) {    // south-east quadrant
                return query(node.se, tlRow, tlCol, brRow, brCol);
            } else {                //intersection between sw and sw
                return query(node.sw, tlRow, tlCol, brRow, colMid) + query(node.se, tlRow, colMid + 1, brRow, brCol);
            }
        } else {                // full-plane intersection
            if (brCol <= colMid) {         // left half plane
                return query(node.nw, tlRow, tlCol, rowMid, brCol) + query(node.sw, rowMid + 1, tlCol, brRow, brCol) ;
            } else if(tlCol > colMid) {    // right half plane
                return query(node.ne, tlRow, tlCol, rowMid, brCol) + query(node.se, rowMid + 1, tlCol, brRow, brCol) ;
            } else {                // full-plane intersection
                return query(node.nw, tlRow, tlCol, rowMid, colMid)
                        + query(node.ne, tlRow, colMid + 1, rowMid, brCol)
                        + query(node.sw, rowMid + 1, tlCol, brRow, colMid)
                        + query(node.se, rowMid + 1, colMid + 1, brRow, brCol);
            }
        }
    }
}

// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.update(1, 1, 10);
// numMatrix.sumRegion(1, 2, 3, 4);


/**
 * Approach 2: Binary Index Tree 2D
 * 基本方法和 1D的BITree 非常类似，这种方法甚至可以简单地扩展到更高维度.
 * 1D 的解法可以参见：
 * https://github.com/cherryljr/LeetCode/blob/master/Range%20Sum%20Query%20-%20Mutable.java
 * 毫无疑问该解法比线段树不管是 时空复杂度 还是 代码实现的复杂度 都有了很大的提升。
 * 并且同意简单易懂，十分推荐。
 * 不了解 Binary Index Tree 的可以参见:
 * https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 */
class NumMatrix {
    private int[][] BITree2D;
    private int[][] matrix;

    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }

        // 仍然以比 matrix 长，宽都要大1的 size 创建 BITree2D
        this.BITree2D = new int[matrix.length + 1][matrix[0].length + 1];
        // 在初始化 BITree2D 时需要，或者也可以单独写一个 init 函数
        // 但是这里为了实现 update() 函数的复用创建了该数组
        this.matrix = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                update(row, col, matrix[row][col]);
            }
        }
    }

    public void update(int row, int col, int val) {
        int delta = val - matrix[row][col];
        matrix[row][col] = val;
        for (int i = row + 1; i < BITree2D.length; i += i & -i) {
            for (int j = col + 1; j < BITree2D[i].length; j += j & -j) {
                BITree2D[i][j] += delta;
            }
        }
    }

    // 画图或者想像一下即可明白：利用[(0,0) - (row2, col2)]的大矩形分别减去
    // 矩形[(0,0) - (row1, col2)] 和 矩形[(0,0) - (row2, col1)] 最后加上被重复减去的部分矩形 [(0,0) - (row1, col1)]
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return getSum(row2 + 1, col2 + 1) - getSum(row1, col2 + 1) - getSum(row2 + 1, col1) + getSum(row1, col1);
    }

    private int getSum(int row, int col) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & -i) {
            for (int j = col; j > 0; j -= j & -j) {
                sum += BITree2D[i][j];
            }
        }
        return  sum;
    }
}