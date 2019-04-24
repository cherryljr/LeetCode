/*
We run a preorder depth first search on the root of a binary tree.
At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.
(If the depth of a node is D, the depth of its immediate child is D+1.  The depth of the root node is 0.)
If a node has only one child, that child is guaranteed to be the left child.

Given the output S of this traversal, recover the tree and return its root.

https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
Example 1:
Input: "1-2--3--4-5--6--7"
Output: [1,2,5,3,4,6,7]

Example 2:
Input: "1-2--3---4-5--6---7"
Output: [1,2,5,3,null,6,null,4,null,7]

Example 3:
Input: "1-401--349---90--88"
Output: [1,401,null,349,88,90]

Note:
    1. The number of nodes in the original tree is between 1 and 1000.
    2. Each node will have a value between 1 and 10^9.
 */

/**
 * Approach 1: Recursion
 * 涉及到树的遍历问题，我们很自然地就想到了使用 Recursion 来解决。
 * 树的前序遍历就是 Recursion 第一次遍历到该结点的时候直接输出该节点即可。
 * 因此在 Recover 时，我们第一次遇到一个节点，则其就是对应的 root 节点，然后分别再是 左孩子 和 右孩子。
 * 转换成代码就是 
 * 	TreeNode root = new TreeNode(getValue(S));
 * 	root.left = recover(S, depth + 1);
 * 	root.right = recover(S, depth + 1);
 * 
 * 而对于本题给出的 深度信息。我们可以这么利用，以确定树的结构：
 * 	如果当前深度 != 预期深度，说明当前位置为空节点。
 * 	预期深度指的是，预计当前节点存在，那么其深度值应该为 父节点的深度值+1.
 * 	因此如果不匹配，就说明这个位置为空节点。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 *
 * 对于树的前序遍历和各个遍历方式实质的讲解可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Preorder%20Traversal.java
 * 	https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 * 类似的问题：
 *  Construct Binary Tree from String:
 *  https://github.com/cherryljr/LeetCode/blob/master/Construct%20Binary%20Tree%20from%20String.java
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
    // 当前处理字符的位置（下标），因为Java无法使用指针，所以使用了一个类变量以供全局进行访问更新。
    int index = 0;

    public TreeNode recoverFromPreorder(String S) {
        return recover(S, 0);
    }

    private TreeNode recover(String S, int exceptedDepth) {
        int depth = getDepth(S);
        // 如果当前深度 != 预期深度，说明当前位置为空节点
        // 同时注意将 index 回调 depth 个位置
        if (depth != exceptedDepth) {
            index -= depth;
            return null;
        }

        // 根据 PreOrder 的遍历顺序进行还原即可
        TreeNode root = new TreeNode(getValue(S));
        root.left = recover(S, depth + 1);
        root.right = recover(S, depth + 1);
        return root;
    }

    // 计算 Dash 的个数，从而获取节点所对应的深度
    private int getDepth(String S) {
        int depth = 0;
        while (index < S.length() && S.charAt(index) == '-') {
            index++;
            depth++;
        }
        return depth;
    }

    // 计算获取当前节点的数值
    private int getValue(String S) {
        int value = 0;
        while (index < S.length() && Character.isDigit(S.charAt(index))) {
            value = value * 10 + S.charAt(index++) - '0';
        }
        return value;
    }

}


/**
 * Approach 2: Using Stack
 * 虽然我们利用 递归 依据 前序遍历 的特性较为直接地解决了这个问题，但是我们都知道实际上可以利用 Stack 这个数据结构实现非递归的解法。
 * 为了方便，我们同样保留了 index 这个成员变量和相应的 getDepth() 和 getValue() 函数。作用与 Approach 1 中相同。
 * 这边我们同样需要利用到 PreOrder Traversal 的顺序特性和 深度信息。（详见代码注释）
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
 */
class Solution {
    int index = 0;

    public TreeNode recoverFromPreorder(String S) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (index < S.length()) {
        	// 获取当前节点的 深度信息
            int depth = getDepth(S);
            // 获取当前节点的数值信息并以此建立出一个新的节点
            TreeNode newNode = new TreeNode(getValue(S));
            // 如果当前节点的深度小于 stack 中元素的个数，说明当前节点所在的 level 较低，需要 stack 进行退栈处理
            // 直到到达对应的深度位置（stack中只会记录 PreOrder 的一条路径，故栈中的元素个数即对应的深度信息）
            // 无法理解这一步操作的话，推荐去了解下 非递归实现树遍历 的过程。
            while (stack.size() > depth) {
                stack.pop();
            }

            // 依据 PreOrder 的遍历顺序，如果栈顶节点非空，那么当前节点为栈顶元素的孩子
            if (!stack.isEmpty()) {
            	// 依据遍历顺序，首先为左孩子，因此如果栈顶节点的左孩子为空，那么将其指向当前的 newNode
            	// 否则 newNode 就是栈顶节点的右孩子
                if (stack.peek().left == null) {
                    stack.peek().left = newNode;
                } else {
                    stack.peek().right = newNode;
                }
            }
            stack.push(newNode);
        }

        while (stack.size() > 1) {
            stack.pop();
        }
        // 返回整棵树的 root 节点
        return stack.pop();
    }

    private int getDepth(String S) {
        int depth = 0;
        while (index < S.length() && S.charAt(index) == '-') {
            index++;
            depth++;
        }
        return depth;
    }

    private int getValue(String S) {
        int value = 0;
        while (index < S.length() && Character.isDigit(S.charAt(index))) {
            value = value * 10 + S.charAt(index++) - '0';
        }
        return value;
    }

}