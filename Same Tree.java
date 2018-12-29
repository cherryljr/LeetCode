/*
Given two binary trees, write a function to check if they are the same or not.
Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

Example 1:
Input:     1         1
          / \       / \
         2   3     2   3

        [1,2,3],   [1,2,3]
Output: true

Example 2:
Input:     1         1
          /           \
         2             2

        [1,2],     [1,null,2]
Output: false

Example 3:
Input:     1         1
          / \       / \
         2   1     1   2

        [1,2,1],   [1,1,2]
Output: false
 */

/**
 * 分治法在树中的典型应用
 * 因为 Tree 这类数据结构的特殊性，经常会使用到 分治(递归) 的做法来解决问题。
 * 但是一直没有很好地写一些总结性的分析。这里就借这道简单的问题我们来总结一下 Tree 该类问题的解决模板。
 * 分治法的核心正如其名字所言，有两个过程：分(Divide) 和 治(Conquer).
 * 而其写法又是通过 递归(Recursion) 来实现的，因此我们需要像 计算机 那样按照 递归 的过程去思考问题。
 *
 * 首先最开始最重要的就是 递归的终止条件。如果没有这点就会出现 StackOverflow 的情况。
 * 那么对于具体问题而言，Tree 的终止条件通常就是（这里我们先讨论只有一棵树的情况^_^）：
 *  节点为空，或者不符合题目中的某些条件。然后根据终止情况，返回我们所需要的信息。
 * 因此代码就可以写成：
 *  if (root == null) return xxx
 *
 * 然后就是 分(Divide) 的过程：
 *  因为对于一棵二叉树而言，我们需要 Divide 的就是该节点的 左右子树。
 *  然后对其分别调用该 func() 函数，便可以得到两个 左右子树的结果。
 * 因此代码就可以写成：
 *  leftRst = func(root.left);   rightRst = func(root.right)
 *
 * 最后一步就是 治(Conquer) 的过程：
 *  最后这里我们需要做的就是将 当前节点的情况 以及 已经求得的 子结果（leftRst, rightRst）
 *  根据题目所给出的条件进行一次 整合求解 的过程，返回最终需要的结果
 * 因此代码就可以写成：
 *  return conquerFunc(root, leftRst, rightRst)
 * PS.
 *  这里对于 当前节点情况的分析 其实也可以放到 第一步（递归的终止条件判断）中，这里大家根据各自习惯编写代码即可。
 *
 * 因此将上述分析过程，整合出来的伪代码就是：
 *  // 终止条件
 *  if (root == null) return xxx;
 *  // Divide
 *  leftRst = func(root.left);  rightRst = func(root.right);
 *  // Conquer
 *  return conquerFunc(root, leftRst, rightRst);
 *
 * 该模板适用于 单棵树 问题的解决。案例有（比较多，这边仅仅列举几个比较典型的问题）：
 * Balanced Binary Tree:
 *  https://github.com/cherryljr/LintCode/blob/master/Balanced%20Binary%20Tree.java
 * Binary Tree Maximum Path Sum:
 *  https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Maximum%20Path%20Sum.java
 * Maximum Depth of Binary Tree:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Depth%20of%20Binary%20Tree.java
 * Minimum Depth of Binary Tree:
 *  https://github.com/cherryljr/LintCode/blob/master/Minimun%20Depth%20of%20Binary%20Tree.java
 *
 * 讨论完 单棵树 的解决方案，我们不妨扩展一下，试着解决 多颗树（通常为两棵） 之间关系的问题。
 * 因为流程与上述完全相同，只是在条件判断时有所不同，因此这里就直接给出模板伪代码与注释了。
 *  // 递归结束条件，通常为 两棵树的节点均为叶子结点，此时根据条件返回需要的结果;
 *  // 或者其中一个节点为空，另一个非空，同样根据条件返回需要的结果
 *  if (root1 == null && root2 == null)  return xxx;
 *  if (root1 == null || root2 == null)  return xxx;
 *
 *  // Divide
 *  // 因为有两棵树，所以可以将 root1的左右子树 与 root2的左右子树 进行匹配处理（具体情况根据题目要求而定）
 *  sub1 = func(root1.child, root2.child)
 *  sub2 = func(root1.child, root2.child)
 *
 *  // Conquer
 *  // 根据题目要求将 当前节点的情况 以及 已经求得的 子结果（leftRst, rightRst）整合起来得到最终结果
 *  return conquerFunc(root1, root2, sub1, sub2)
 *
 * 该模板适用于 多棵树 问题的解决。案例有：
 *
 * Flip Equivalent Binary Trees:
 *  https://github.com/cherryljr/LeetCode/blob/master/Flip%20Equivalent%20Binary%20Trees.java
 *
 * Reference:
 *  https://zxi.mytechroad.com/blog/tree/leetcode-binary-trees-sp12/
 */

/**
 * Approach: Divide and Conquer
 * 利用上述分析的 处理多棵树之间关系的模板 即可解决。这里就不再赘述了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 递归终止条件
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        // Divide
        boolean left = isSameTree(p.left, q.left);
        boolean right = isSameTree(p.right, q.right);

        // Conquer
        return p.val == q.val && left && right;
    }
}