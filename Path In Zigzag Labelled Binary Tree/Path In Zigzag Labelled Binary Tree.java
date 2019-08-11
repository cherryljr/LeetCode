/*
In an infinite binary tree where every node has two children, the nodes are labelled in row order.
In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right,
while in the even numbered rows (second, fourth, sixth,...), the labelling is right to left.

Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with that label.

https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
Example 1:
Input: label = 14
Output: [1,3,4,14]

Example 2:
Input: label = 26
Output: [1,2,6,10,26]

Constraints:
    1. 1 <= label <= 10^6
 */

/**
 * Approach: The Property of Full Binary Tree
 * 因为题目给出的是一棵无限的树，即我们可以把它看作是一颗满二叉树，或者是一颗完全二叉树。
 * 假设树的节点排列顺序是正常的，而非 Zigzag。则根据满二叉树的性质，我们可以知道它的父亲节点应该是 label/2。
 * 那么对于 Zigzag 排列的话，我们需要对节点进行一次逆序即可。
 * 那么，应该如何完成这个 Reverse 操作呢？如果对层内全部的元素进行逆序操作的话，时间复杂度将会太高。
 * 但是我们发现因为是 Zigzag 排列的，所以它当前的父亲节点应该是与 label/2 对称的。
 * 而我们知道当前层的 最大值 和 最小值 分别为：(1<<level)-1 和 1<<(level-1)
 *  （最大值对应为：满二叉树总节点的个数2^n - 1, n为树的高度；最小值对应为第i层的节点个数：2^(i-1)）
 * 因此当前 label 的父亲节点应该是其对称点的父亲节点。
 * 而对称点的值为：max + min - label == (1<<level)-1 + (1<<level-1) - label.
 *
 * 所以我们只需要事先计算出当前 label 在第几层（level）。
 * 然后依据满二叉树的性质，通过 level 计算出当前层的 max 和 min。
 * 最后不断向上推进即可。
 *
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 */
class Solution {
    public List<Integer> pathInZigZagTree(int label) {
        int level = 1;
        while ((1 << level) <= label) {
            level++;
        }

        List<Integer> ans = new ArrayList<>();
        for (; label >= 1; label >>= 1, level--) {
            ans.add(0, label);
            // 计算出当前层 label 的对称点，从而通过 label/2 得到其Zigzag排列的父亲节点
            label = (1 << level) - 1 + (1 << level - 1) - label;
        }
        return ans;
    }
}
