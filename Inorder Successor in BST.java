BST中找中序遍历的后继节点
该题因为通常需要求其父节点，我们要看题目有没有说明可以使用 findParent() 方法。

Method 1: (Uses Parent Pointer) -- Better Time Complexity
In this method, we assume that every node has parent pointer.
The Algorithm is divided into two cases on the basis of right subtree of the input node being empty or not.
    Input: node // node is the node whose Inorder successor is needed.
    output: succ // succ is Inorder successor of node.
1) If right subtree of node is not NULL, then succ lies in right subtree. Do following.
Go to right subtree and return the node with minimum key value in right subtree.
2) If right sbtree of node is NULL, then succ is one of the ancestors. Do following.
Travel up using the parent pointer until you see a node which is left child of it’s parent. 
The parent of such a node is the succ.
Time Complexity: O(d) where d is distance between node and its successor node.  

Method 2: (Search from root) 
Parent pointer is NOT needed in this algorithm. 
The Algorithm is divided into two cases on the basis of right subtree of the input node being empty or not.
    Input: node, root // node is the node whose Inorder successor is needed.
    output: succ // succ is Inorder successor of node.
1) If right subtree of node is not NULL, then succ lies in right subtree. Do following.
Go to right subtree and return the node with minimum key value in right subtree.
2) If right sbtree of node is NULL, then start from root and us search like technique. Do following.
Travel down the tree, if a node’s data is greater than root’s data then go right side, otherwise go to left side.
Time Complexity: O(h) where h is height of tree.
该方法还有一个使用递归的写法，参见：https://discuss.leetcode.com/topic/25076/share-my-java-recursive-solution
（包含 Predecessor 的求法）

/*
Given a binary search tree and a node in it, find the in-order successor of that node in the BST.

Note: If the given node has no in-order successor in the tree, return null.
*/

public class BSTSuccessor {  
  
    public static void main(String[] args) {  
        Node root = null;  
        root = insert(root, 6);  
        root = insert(root, 3);
        root = insert(root, 9);  
        root = insert(root, 1);  
        root = insert(root, 4);  
        root = insert(root, 8);  
        root = insert(root, 10);  
        root = insert(root, 2);  
        root = insert(root, 5);  
        root = insert(root, 7);  
  
        Node temp = root.right.right.right;  
        Node succ = inorderSuccessor(root, temp);  
        // succ = inorderSuccessor2(root, temp);  
        if (succ != null) {  
            System.out.println(temp.val + "'s successor is " + succ.val);  
        } else {  
            System.out.println("This node don't have successor");  
        }  
    }  
  
    // 1）需要parent指针的做法  
    // 找inorder successor 分右孩子是否存在的两种情况考虑  
    public static Node inorderSuccessor(Node root, Node node) {  
        if (node.right != null) {       // 有右孩子，直接找右子树的最小节点  
            return minValue(node.right);  
        }  
  
        // 否则利用父指针不断向上找，直到父节点的值大于当前节点的值  
        // 或者该节点成为父节点的左孩子  
        Node parent = node.parent;  
        while (parent != null && node.val > parent.val) {  
            parent = parent.parent;  
        }  
        return parent;  
    }  
      
      
    // 2）不需要parent指针的做法  
    // 过程其实就是个从root查找node节点的过程，同时保存旧的比node大的root节点，作为succ  
    public static Node inorderSuccessor2(Node root, Node node) {  
        if (node.right != null) {       // 有右孩子，直接找右子树的最小节点  
            return minValue(node.right);  
        }  
          
        Node succ = null;  
        while(root != null) {  
            if(root.val > node.val) {  // 继续找更小的  
                succ = root;        // 后继节点必然比node要大，所以只能在这里保存  
                root = root.left;  
            }  
            else if(root.val < node.val){      // 继续找更大的  
                root = root.right;  
            }  
            else{       // root节点和node节点重复，停止  
                break;  
            }  
        }  
        return succ;  
    }  
      
      
  
    /* 
     * Given a non-empty binary search tree, return the minimum data value found 
     * in that tree. Note that the entire tree does not need to be searched. 
     */  
    public static Node minValue(Node node) {  
        Node cur = node;  
  
        // 最小节点必定在最左下角  
        while (cur.left != null) {  
            cur = cur.left;  
        }  
        return cur;  
    }  
  
    /* 
     * Give a binary search tree and a number, inserts a new node with the given 
     * number in the correct place in the tree. Returns the new root pointer 
     * which the caller should then use (the standard trick to avoid using 
     * reference parameters). 
     *  
     * 返回插入后节点的引用 
     */  
    public static Node insert(Node node, int val) {  
        if (node == null) {  
            return new Node(val);  
        } else {            // node 存在  
            Node temp;  
  
            if (val <= node.val) {  
                temp = insert(node.left, val);  
                node.left = temp;  
                temp.parent = node;  
            } else {  
                temp = insert(node.right, val);  
                node.right = temp;  
                temp.parent = node;  
            }  
            return node;  
        }  
    }  
  
    static class Node {  
        int val;  
        Node left;  
        Node right;  
        Node parent;  
  
        public Node(int val) {  
            this.val = val;  
        }  
    }  
  
}  

