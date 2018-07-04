/*
You are given a binary tree in which each node contains an integer value.
Find the number of paths that sum to a given value.
The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

Example:
root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
 */

/**
 * Approach 1: Divide and Conquer
 * 思想是分治的思想。root的结果 = 左孩子的结果 + 右孩子的结果。
 * 因此使用递归即可实现。
 *
 * 时间复杂度：O(nlogn)
 * 因为每次递归调用时间消耗为 O(n),因此对于一棵平衡树而言时间复杂度为：T(n) = n + 2T(n/2) = O(nlogn)
 * 在最坏情况下，树退化成单向链表，则时间复杂度为 T(n) = n + T(n-1) = O(n^2)
 * 空间复杂度：O(logn)
 * 同理最坏情况下，该树退化成单向链表，此时递归深度为 O(n),故空间复杂度为 O(n)
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
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        // 取当前节点的路径 + 不取当前节点的路径(左孩子，右孩子)
        return countPath(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    private int countPath(TreeNode root, int target) {
        int count = 0;
        if (root == null) {
            return count;
        }
        if (root.val == target) {
            count++;
        }

        count += countPath(root.left, target - root.val);
        count += countPath(root.right, target - root.val);
        return count;
    }
}

/**
 * Approach 2: Prefix Sum + Backtracking
 * 因为在 Approach 1 中，我们在计算路径时进行了重复计算。
 * 比如 sum = 4, 那么 2 -> 2 和 2 -> 2 -> -3 -> 3 都是合法的路径，
 * 并且二者具有相同的前缀，因此我们可以想到通过 存储前缀和 的方式来去除重复计算。
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * 参考资料：
 *  https://leetcode.com/problems/path-sum-iii/discuss/91884/Simple-AC-Java-Solution-DFS?page=1
 *
 * Prefix Sum 详细解析与其他应用可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 */
class Solution {
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // Default sum = 0 has one count
        map.put(0, 1);
        return countPath(root, sum, 0, map);
    }

    private int countPath(TreeNode root, int target, int currSum, Map<Integer, Integer> map) {
        if (root == null) {
            return 0;
        }
        currSum += root.val;
        // See if there is a subarray sum equals to target
        int count = map.getOrDefault(currSum - target, 0);
        map.put(currSum, map.getOrDefault(currSum, 0) + 1);
        // Extend to left and right child
        count += countPath(root.left, target, currSum, map) + countPath(root.right, target, currSum, map);
        // Remove the current node so it won't affect other path (Backtracking)
        map.put(currSum, map.get(currSum) - 1);
        return count;
    }
}