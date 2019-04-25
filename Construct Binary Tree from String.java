/*
You need to construct a binary tree from a string consisting of parenthesis and integers.
The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis.
The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.
You always start to construct the left child node of the parent first if it exists.

Example:
Input: "4(2(3)(1))(6(5))"
Output: return the tree root node representing the following tree:

       4
     /   \
    2     6
   / \   /
  3   1 5


Note:
    1. There will only be '(', ')', '-' and '0' ~ '9' in the input string.
    2. An empty tree is represented by "" instead of "()".
*/

/**
 * Approach: Recursion
 * 这道题让我们根据一个字符串来创建一个二叉树，其中结点与其左右子树是用括号隔开，每个括号中又是数字后面的跟括号的模式，
 * 这种模型就很有递归的感觉，所以我们当然可以使用递归来做。（这里数字前 未匹配的左括号 个数就相当于节点的 深度 ）
 *
 * 首先我们要做的是先找出根结点值，我们找第一个左括号的位置，如果找不到，说明当前字符串都是数字，直接转化为整型，然后新建结点返回即可。
 * 否则的话从当前位置开始遍历，因为当前位置是一个左括号，我们的目标是找到与之对应的右括号的位置，
 * 但是由于中间还会遇到左右括号，所以我们需要用一个变量 leftParenCount 来记录左括号的个数。
 * 如果遇到左括号，leftParenCount 自增1，如果遇到右括号，leftParenCount 自减1，
 * 这样当某个时刻 leftParenCount为0 时，我们就确定了一棵完整的子树的位置。
 * 那么问题来了，这个子树到底是左子树还是右子树呢，我们需要辅助变量 firstParen 和 start，
 * 当最开始找到第一个左括号的位置时，将 start 赋值为 firstParen，
 * 那么当 leftParenCount为0 时，如果 start 还是在原来的位置，说明这个是左子树，我们对其调用递归函数，然后更新 start 的位置。
 * 否则就说明这是一棵右子树。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 * 
 * PS. 这个问题实际上也可以将 () 转换成 树的深度 去解决。
 * 类似的问题：
 *  Recover a Tree From Preorder Traversal:
 *  https://github.com/cherryljr/LeetCode/tree/master/Recover%20a%20Tree%20From%20Preorder%20Traversal
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode str2tree(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        int firstParen = s.indexOf("(");
        int val = firstParen == -1 ? Integer.parseInt(s) : Integer.parseInt(s.substring(0, firstParen));
        TreeNode root = new TreeNode(val);
        if (firstParen == -1) {
            return root;
        }

        int start = firstParen, leftParenCount = 0;
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                leftParenCount++;
            } else if (s.charAt(i) == ')') {
                leftParenCount--;
            }
            if (leftParenCount == 0 && start == firstParen) {
                root.left = str2tree(s.substring(start + 1, i));
                start = i + 1;
            } else if (leftParenCount == 0) {
                root.right = str2tree(s.substring(start + 1, i));
            }
        }

        return root;
    }
}
