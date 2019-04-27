/*
Given a binary tree, each node has value 0 or 1. Each root-to-leaf path represents a binary number starting with the most significant bit.
For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary, which is 13.

For all leaves in the tree, consider the numbers represented by the path from the root to that leaf.

Return the sum of these numbers.

Example 1:
Input: [1,0,1,0,1,0,1]
Output: 22
Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22

Note:
    1. The number of nodes in the tree is between 1 and 1000.
    2. node.val is 0 or 1.
    3. The answer will not exceed 2^31 - 1.
 */

/**
 * Approach 1: Divide and Conquer (Top-Down DFS)
 * 对于 Tree 类型的问题，这里很容易就能反应过来是使用 Divide and Conquer 来实现。
 * 接下来就需要确定 递归函数 的作用了。
 * 这里，我们需要计算各条路径所表示元素的值总和。
 * 因此，可以确定递归函数的作用为，返回当前节点左右子树所表示元素值的总和。
 * 递归调用时 prevSum 表示从父节点获取到的值。
 * 然后通过 prevSum << 1 | root.val 操作即可得到该节点所表示的值。
 * 如果当前节点的左右节点均为 null，则直接返回 val 即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(logn)
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
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    // 返回当前节点下所有元素值的总和（包括当前节点）
    // prevSum 代表从父节点获取到的值
    private int dfs(TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }

        // 计算当前节点所表示元素的值（10进制）
        prevSum = prevSum << 1 | root.val;
        // 如果是叶子节点，则此时的 prevSum 就是最后的结果了，直接 return prevSum 即可
        return root.left == null && root.right == null ? prevSum : dfs(root.left, prevSum) + dfs(root.right, prevSum);
    }
}

/**
 * Approach 2: Backtracking (Brute Force)
 * 该解法主要是为了让大家能够更加直白地理解我的思路而写的...
 * Approach 1 中，我将 二进制转换 和 元素值相加 的这两个操作整合起来了。
 * 但是事后有小伙伴反应不够直白，大脑没转过弯来...所以就有了这个版本。
 * 其实思路很简单：
 *  1. 通过 DFS 遍历出路径所组成的所有元素。（这里直接用 Backtracking 即可）
 *  2. 将所有遍历得到的二进制元素，转换成 十进制，然后相加即可得到最后的结果。
 *
 * 第一步的操作与 Binary Tree Path Sum 这个问题基本类似，大家可以参考一下：
 *  https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Path%20Sum.java
 * 希望这个解法和说明对大家理解有所帮助...
 *
 * PS.题目最近做了修改，说明了数值和不会超过 2^31-1。所以不需要再进行取模操作了。
 */
class Solution {
    public int sumRootToLeaf(TreeNode root) {
        List<String> list = new ArrayList<>();
        dfs(root, list, new StringBuilder());
        int ans = 0;
        for (String str : list) {
            ans += Integer.valueOf(str, 2);
        }
        return ans;
    }

    private void dfs(TreeNode root, List<String> list, StringBuilder sb) {
        if (root.left == null && root.right == null) {
            String str = sb.toString();
            list.add(str + root.val);
            return;
        }

        if (root.left != null) {
            dfs(root.left, list, sb.append(root.val));
            sb.deleteCharAt(sb.length() - 1);
        }
        if (root.right != null) {
            dfs(root.right, list, sb.append(root.val));
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}