/*
Given an integer array with no duplicates. A max tree building on this array is defined as follow:

The root is the maximum number in the array
The left subtree and right subtree are the max trees of the subarray divided by the root number.
Construct the max tree by the given array.

Example
Given [2, 5, 6, 0, 3, 1], the max tree constructed by this array is:

    6
   / \
  5   3
 /   / \
2   0   1
Challenge
O(n) time and memory.

Tags Expand 
LintCode Copyright Stack Cartesian Tree
*/

/**
 * Approach: Stack
 * 因为没有重复元素，因此我们由题目分析可得：
 * 一个结点的父节点是离它最近的一个大于该节点的数；
 * 如果相邻的两个节点均大于该结点，则取较小者。
 * 这是为了保证能够形成一颗 二叉树，而不是 多叉树。
 * 原因可以通过反证法进行证明。
 * 举个例子：3 7 4 5 2 1.如果按照取 较大值 的方法，会导致 7 有 3 个孩子。
 *
 * 这样问题就转换成了在一个 Array 中，寻找一个结点 左/右第一个大于 它的节点。
 * 而这个问题可以通过 单调栈 以O (n) 的时间复杂度巧妙地解决。
 * 具体做法如下：
 *  1. 建立一个 单调栈，栈底到栈顶按照 从大到小 的顺序排列(递减).
 *  2. 遍历整数数组，对当前元素 nums[i] 建立新的节点 curr.
 *  当 curr.val < stack.peek().val 的时候，说明符合要求。
 *      也说明 curr 左边第一个大于它的节点为 stack.peek().
 *      同样，换个说法就是 curr 应该作为 stack.peek() 的右孩子 => stack.peek().right = curr
 *      (至于为什么是右孩子呢？这是由题目规定的，右子树由数组中该元素 右边 的部分构成，curr更晚入栈，必定在右边)
 *  当 curr.val > stack.peek().vai 的时候，说明不符合要求。
 *      栈顶元素应该被弹出，也说明 stack.peek() 右边 第一个大于它的节点为 curr，
 *      同样，换个说法就是 stack.peek() 应该作为 curr 的左孩子 => curr.left = stack.pop()
 *  3. 将 curr 推入栈中，继续遍历即可。
 *  4. 最后我们将仍然留在 stack 中的元素 pop 出来即可，位于 栈底 的元素就是整棵树的 root.(最大值)
 *
 * 有人或许会疑问，比较 左右第一个大于该节点中 较小值 的部分去了哪里呢？
 * 实际上，以上步骤确实没有进行这个比较过程。
 * 但是效果是等同的，因为我们在进行 左右孩子连接 的过程中，自然会将其调整正确。
 * 仍然以 3 7 4 5 2 1 为例。
 * 过程如下：...; 7.left = 3; pop(3); push(7); 7.right = 4; push(4);
 *               5.left = 4; pop(4); 7.right = 5; push(5)
 * 由上可以发现，7的右指针由原来的指向 4，改为指向了 5，
 * 相应的：4由原来的作为 7 的右子节点，改成了 5 的左子节点。
 * 即 4 在 5 和 7 中选择了 较小者 作为其 父亲节点。
 *
 * 时间复杂度为：O(n)
 * 空间复杂度为：O(n)
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (nums.length == 1) {
            return new TreeNode(nums[0]);
        }

        Stack<TreeNode> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while (!stack.isEmpty() && stack.peek().val < nums[i]) {
                // if the peek element is smaller than current number,
                // then it would be the left child of current number then pop it.
                curr.left = stack.pop();
            }
            if (!stack.isEmpty()) {
                // the bigger number's (if exist) right child is current node.
                stack.peek().right = curr;
            }
            stack.push(curr);
        }

        // get the bottom element of stack (the largest one)
        TreeNode root = null;
        while (!stack.isEmpty()) {
            root = stack.pop();
        }
        return root;
    }
}