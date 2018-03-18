/*
Given two non-empty binary trees s and t, check whether tree t has exactly the same structure 
and node values with a subtree of s. 
A subtree of s is a tree consists of a node in s and all of this node's descendants. 
The tree s could also be considered as a subtree of itself.

Example 1:
Given tree s:
     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.

Example 2:
Given tree s:
     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
 */

/**
 * Approach 1: By Comparison of Nodes
 * 采用 分治 的方法对两棵树的所有节点进行一一对比。
 * 如果 头节点 相等，那么久比较头节点的 左子树 和 右子树。
 * 直到其完全匹配为止。
 *
 * 时间复杂度为：O(m*n) 最差情况下，我们可能需要对比两个树中 所有 的节点
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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        if (s.val == t.val) {
            if (isSameTree(s,t)) {
                return true;
            }
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    public boolean isSameTree(TreeNode s, TreeNode t){
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }
        if (s.val != t.val){
            return false;
        }
        return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
    }
}

/**
 * Approach 2: Preorder Serialization and KMP
 * 这个方法比较巧妙，希望大家可以学习一下。
 * 首先我们可以将两棵树进行一个 序列化。
 * 这样我们便能够得到可以唯一表示树的两个 字符串 treeS, treeP。
 * 这样问题便转换成了：求解 treeP 是否为 treeS 的字串。
 * 因此我们可以使用 KMP算法 在 O(n) 的时间内找到答案。
 * 总体时间复杂度为：
 *  树的序列化 O(n) + KMP算法匹配 O(n) => O(n)
 * 总体空间复杂度为：
 *  树的序列化 O(n) + KMP中计算next[] O(n) => O(n)
 *
 * 注意点：
 *  1. 对树的序列化，我们可以使用 前序/中序/后序 三种方式进行序列化。
 *  但是千万不能够使用 层序序列化。因为虽然 层序 也能够唯一标识一棵树的结构，
 *  但是它会 毁掉子树的结构 ，使得我们无法按照 寻找字串 的方式来寻找子树。
 *  2. 树的序列化/遍历等 方法的实现，尽量使用非递归的方式来写，不要让系统帮我们压栈。
 *  不仅会降低运行效率，还有栈溢出的风险。况且这些非递归的代码实现还是很简单的。
 *
 * 关于 树的序列化 可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Serialization.java
 * 关于 KMP算法 可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/KMP%20Template.java
 */
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        String treeS = preSerialize(s);
        String treeT = preSerialize(t);

        return kmp(treeS, treeT);
    }

    // PreOrder Serialization (Using Stack)
    private String preSerialize(TreeNode s) {
        StringBuilder sb = new StringBuilder();
        Stack<TreeNode> stack = new Stack<TreeNode>();

        stack.push(s);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr == null) {
                // "#" denote the null nodes.
                // Using space to split nodes.
                sb.append("# ");
                continue;
            }
            sb.append(curr.val + ' ');
            stack.push(curr.right);
            stack.push(curr.left);
        }

        return sb.toString();
    }

    private boolean kmp(String s, String p) {
        if (s == null || p.length() > s.length()) {
            return false;
        }

        char[] arr_s = s.toCharArray();
        char[] arr_p = p.toCharArray();
        int[] next = getNextArray(arr_p);
        int i = 0, j = 0;
        while (i < arr_s.length && j < arr_p.length) {
            if (arr_s[i] == arr_p[j]) {
                i++;
                j++;
            } else if (j > 0) {
                j = next[j];
            } else {
                i++;
            }
        }

        return j == arr_p.length;
    }

    private int[] getNextArray(char[] arr) {
        int[] next = new int[arr.length];
        next[0] = -1;
        int pos = 2, cn = 0;
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }
}