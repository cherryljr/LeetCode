/**
 * Binary Index Tree (树状数组)
 *
 * 为什么我们需要BITree / BITree的应用场景
 * 该数据结构可被用于求一个数组的区间和。
 * 对于求区间和而言，我们可以使用的方法有：前缀和；线段树；BITree
 * 因为前缀和虽然能够在 O(1) 的时间内完成查询，但是需要 O(n) 的时间类完成数据的更新（单点修改）。
 * 而 Segment Tree 和 Binary Index Tree 就是为了解决该问题而产生的。
 *（注：线段树还可以有其他更多的应用）
 * 相比于线段树，BITree的优点在于：虽然二者的时间复杂度均相同（都是O(logn))，
 * 但是 BITree 花费了更少的时间与空间，并且实现代码相对简单。
 * 而复杂度差距的原因是在于 数据大小n，以一维的 Segment Tree 和 BITree 为例(较为常见，不过是存在二维的哦)
 * BITree 大小 n 与 输入数组 arr 的大小是相同的；
 * 而 Segment Tree 的大小则为 2n-1 这在面临大数据时，差距可以达到几百ms以上。
 *
 * BITree的基本操作
 * BITree 正如其中文译名“树状数组”，可见其本质是以一个数组的形式来表现的。
 * 其大小与输入数组 arr[] 相等，在下面代码中我们使用了 arr.length+1 大小的 BITree[].
 * BITree[0] 为一个 Dummy Node.
 * 关键点：
 * 1. 2^k 就是其向前包含的数的个数(包含其本身), k为index二进制中结尾0的个数.
 * 2. 设 fa 与 son 为其对应的二进制,记 Li 为二进制i结尾的0个数
 *    我们根据性质已知 Lson+1 = Lfa
 *    根据其思想,son的父亲节点就是求最小(在树状数组中son的位置之后的最近的fa)的fa使得 Lson+1 = Lfa
 * 3. LowBit(index) 函数，其作用是求出某一个数的二进制表示中最低的一位1所表示的数，比如 6(0110) 就是 2(0010)
 *    对此我们想到可以利用 i&(i-1) 这个方法消掉二进制的最后一位，然后在用原数减去它即可：i - i&(i-1)
 *    但是还有另外一种更加简便的方法：
 *    首先我们知道，正数的 补码 与 原码 相等；
 *    其次求负数的补码的简便方式为：把这个数对应的正数的二进制写出来，然后从右向左找到第一个1(这个1就是我们要求的结果，后来的操作就是让这个1能表示出来)，
 *    这个1和这个1右边的二进制不变，左边的二进制依次取反(包括符号位)，这样就求出的一个数的补码。
 *    说这个方法主要是让我们理解一个负数的补码在二进制上的特征，然后我们把这个负数对应的正数与该负数与运算一下。
 *    由于这个1的左边的二进制与正数的原码对应的部分是相反的，所以相与一定都为0；
 *    由于这个1和这个1右边的二进制都是不变的，因此，相与后还是原来的样子，故，这样运算出来的结果就是LowBit(index)的结果.
 *    即：index & -index
 * 4. index += LowBit(index) 得到的值，正是子节点index的父节点；
 *    应用：update()操作：更新当前结点值，因为会影响到其父节点的值，因此需要向上更新父节点的值。
 * 5. index -= LowBit(index) 得到的是比index小的，第一个未包含index的节点。
 *    注意：这里比较绕，可以看图多理解一下，之所以不说是子节点是因为不准确。
 *    比如 BITree[6]是被拆分成 BITree[6](arr[4],arr[5]的和) 和 BITree[4] （arr[0...3]的和）
 *    但是 BITree[4] 并不是 BITree[6] 的子节点，而是向前（比6小）第一个未包含 arr[6] 的节点。
 *    应用：getSum()操作：将当前数 BITree[index] 加入结果,然后向前找第一个他未包含的数,即index -= LowBit(index),重复上述步骤
 *
 * 详细原理的讲解由于篇幅问题可以参见：
 * http://blog.csdn.net/flushhip/article/details/79165701
 * http://blog.csdn.net/jokerwyt/article/details/51939217
 * https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 * https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/#find
 *
 * 应用到了 BITree 的题目有:
 * https://github.com/cherryljr/LintCode/blob/master/Interval%20Sum%20II.java
 * https://github.com/cherryljr/LeetCode/blob/master/Range%20Sum%20Query%20-%20Mutable.java
 */
public class LeetCode {
    public static void main(String[] args) {
        BinaryIndexTree BITree = new BinaryIndexTree();
        int[] arr = {0, 9, 5, 7, 3};
        BITree.buildBITree(arr);

        System.out.println("Sum of elements in arr[0..4] is: " + BITree.getSum(4));
        System.out.println("Sum of elements in arr[2..4] is: " + BITree.getSum(2, 4));
        BITree.updateBIT(1, 2);
        System.out.println("Sum of elements in arr[0..4] is: " + BITree.getSum(4));
        System.out.println("Sum of elements in arr[2..4] is: " + BITree.getSum(2, 4));
    }
}

class BinaryIndexTree {
    /*
    size  --> number of elements present in input array.
    BITree[0..n] --> Array that represents Binary
                     Indexed Tree.
    arr[0..n-1]  --> Input array.
    */
    int size;
    static int[] BITree;
    int[] arr;

    /**
     * Function to construct fenwick tree (BITree) from given array.
     * @param arr the input array
     */
    public void buildBITree(int[] arr) {
        size = arr.length;
        BITree = new int[size + 1];
        this.arr = arr;
        // Initialize BITree[] as 0
        // for (int i = 1; i <= arr.length; i++) {
        //     BITree[i] = 0;
        // }

        // Store the actual values in BITree[] using updateBIT()
        for (int i = 0; i < size; i++) {
            updateBIT(i, arr[i]);
        }
    }

    /**
     * Updates a node in Binary Index Tree (BITree) at given index in BITree.
     * The given value 'val' is added to BITree[index] and all of its ancestors in tree.
     * @param index the index of arr[]
     * @param val the value would be added
     */
    public void updateBIT(int index, int val) {
        // index in BITree[] is 1 more than the index in arr[]
        index = index + 1;

        // Traverse all ancestors and add 'val'
        // Pay attention to the range of index
        while (index < BITree.length) {
            // Add 'val' to current node of BIT Tree
            BITree[index] += val;
            // Update index to that of parent in update view
            index += index & -index;
        }
    }

    /**
     * This function assumes that the array is preprocessed
     * and partial sums of array elements are stored in BITree[].
     * @param index
     * @return sum of arr[0..index].
     */
    public int getSum(int index) {
        // if the index of arr is out of range, return 0
        if (index < 0 || index >= size) {
            return 0;
        }
        // Initialize the result
        int sum = 0;
        // Index in BITree[] is one more than the index in arr[]
        index = index + 1;

        // Pay attention to the range of index
        while (index > 0) {
            // Add the current element of BITree to sum
            sum += BITree[index];
            // Move index
            index -= index & -index;
        }
        return sum;
    }

    /**
     * The Function get the sum of arr[] between start and end
     * with the help of getSum(index)
     * @param start
     * @param end
     * @return sum of arr[start...end]
     */
    public int getSum(int start, int end) {
        return (start > end || start < 0 || end >= size) ? 0 :
                start == end ? arr[start] : getSum(end) - getSum(start - 1);
    }

}
