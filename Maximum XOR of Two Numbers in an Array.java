/*
Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.
Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.

Could you do this in O(n) runtime?

Example:
Input: [3, 10, 5, 25, 2, 8]
Output: 28

Explanation: The maximum result is 5 ^ 25 = 28.
 */

/**
 * Approach: Trie
 * 这道题目与 Find the Maximum Subarray XOR in an Array 基本相同。
 * https://github.com/cherryljr/NowCoder/blob/master/Find%20the%20Maximum%20Subarray%20XOR%20in%20an%20Array.java
 *
 * 对此我们同样可以利用 Trie Tree 来解决问题。
 * 其过程为：我们每次在 Trie 中寻找与 nums[i] 异或起来最大的元素，
 * 并得到异或之后的结果，并与 max 比较。然后再将 nums[i] 加入到 Trie 中。
 * 最后 max 就是我们想要的答案。
 *
 * 那么Trie如何实现快速得到 异或后的最大值 呢？（本题的核心部分）
 * 我们是按照这样的策略（其实是一个贪心的做法）：
 *  对于一个 int 类型，总共有 32 位。因此我们 Trie Tree 的高度为 32.
 *  当我们想要查询如何获得最大的异或值时，
 *  我们可以 从高位开始 向低位逐位查找 最佳异或值。遵循着 首先满足高位 的做法依次向下查询。
 *  同时：我们还需要注意符号位，这里比较特殊，因为 0 代表正，1代表负。
 *  所以对于 符号位 我们要取与原来位置上 相同 的数，
 *  而对于其他位我们要去与原来位置上 不同的数（异或1后的数）。
 *  如果 取不到 最佳值的话，我们就只能取现有的值了（总共就两个...）
 *  这样查找下来我们需要循环 32 次，时间复杂度为 O(1)
 * 而我们需要查找 n 个数，因此总体时间复杂度为 O(n)
 */
class Solution {
    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        Trie trie = new Trie();
        // 初始化，将第一个元素加入到 Trie 中
        trie.add(nums[0]);
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            // 在 Trie 中寻找与 nums[i] 异或起来最大的元素，返回异或之后的结果
            max = Math.max(max, trie.maxXOR(nums[i]));
            // 将 nums[i] 加入到 Trie Tree 中
            trie.add(nums[i]);
        }

        return max;
    }

    class TrieNode {
        // Trie Tree 的节点，因为是 2进制，所以我们只需要 0,1 两个节点即可
        TrieNode[] child;

        TrieNode() {
            child = new TrieNode[2];
        }
    }

    class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        // 将 nums[i] 加入到 Trie Tree 中
        public void add(int num) {
            TrieNode curr = root;
            for (int i = 31; i >= 0; i--) {
                int path = ((num >> i) & 1);    // 获得第 i 位上的值
                if (curr.child[path] == null) {
                    curr.child[path] = new TrieNode();
                }
                curr = curr.child[path];
            }
        }

        public int maxXOR(int num) {
            TrieNode curr = root;
            int rst = 0;
            for (int i = 31; i >= 0; i--) {
                // 获得第 i 位上的值
                int path = ((num >> i) & 1);
                // 根据 path，我们需要得到相应的最佳 异或值
                // 注意：如果是 符号位 的话，我们取与path相同的值，否则取与 path 不同的值
                int best = i == 31 ? path : (path ^ 1);
                // 判断 curr.child[best] 节点是否存在，存在的话我们就能取到最佳值
                // 否则只能取另外一个值了
                best = curr.child[best] != null ? best : (best ^ 1);
                // 将 rst 的第 i 位置为 path^best
                rst |= ((path ^ best) << i);
                curr = curr.child[best];
            }
            return rst;
        }
    }
}