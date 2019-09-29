/*
Implementing the class MajorityChecker, which has the following API:
    ·MajorityChecker(int[] arr) constructs an instance of MajorityChecker with the given array arr;
    ·int query(int left, int right, int threshold) has arguments such that:
        0 <= left <= right < arr.length representing a subarray of arr;
        2 * threshold > right - left + 1, ie. the threshold is always a strict majority of the length of the subarray
Each query(...) returns the element in arr[left], arr[left+1], ..., arr[right] that occurs at least threshold times, or -1 if no such element exists.

Example:
MajorityChecker majorityChecker = new MajorityChecker([1,1,2,2,1,1]);
majorityChecker.query(0,5,4); // returns 1
majorityChecker.query(0,3,3); // returns -1
majorityChecker.query(2,3,2); // returns 2

Constraints:
    1. 1 <= arr.length <= 20000
    2. 1 <= arr[i] <= 20000
    3. For each query, 0 <= left <= right < len(arr)
    4. For each query, 2 * threshold > right - left + 1
    5. The number of queries is at most 10000
 */

/**
 * Approach 1: Segment Tree + Binary Search
 * Segment tree is a perfect data structure for this problem because it can divide an interval into consecutive sub-intervals.
 * The nodes in the segment tree store the "more than half" majority (if it exists) or -1 of the corresponding interval [left, right].
 * Q1：How should we merge the interval [left, mid] and [mid + 1, right] in the "build tree" process?
 * A1：if the mode of interval [left, mid] and [mid + 1, right] are the same, then the ans's mode is majority of left|right sub-interval,
 * if not, we take the one which has more occurrence as the result, and the count=bigger-smaller.
 * During the query process, we can search the interval [left, right] on the segment tree and get O(log(n)) sub-intervals.
 * Q2：When we get the mode in [left, right], How should we get the occurrence of mode. (we can't guarantee the occurrence is no smaller than threshold)
 * A2：we can use Map<Integer, List<Integer>> map to store the positions of each number in [left, right].
 * The key in the Map represents a number and the value represents the positions of that number in increasing order.
 * Thus we can count the occurrences of a number in a given interval [left, right] in O(log(n)) time by using binary search, which is another O(log(n)) time.
 *
 * Time Complexity: Build: O(2n), Query: O(log(n) * log(n))
 * Space Complexity: O(2n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Build%20II.java
 *  https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Query.java
 *  https://github.com/cherryljr/LintCode/blob/master/Interval%20Sum%20II.java
 */
class MajorityChecker {
    private Map<Integer, List<Integer>> map;
    Node root;

    public MajorityChecker(int[] arr) {
        root = build(arr, 0, arr.length - 1);
        map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.computeIfAbsent(arr[i], x -> new ArrayList<>()).add(i);
        }
    }

    public int query(int left, int right, int threshold) {
        int num = query(root, left, right).num;
        int start = Collections.binarySearch(map.get(num), left);
        int end = Collections.binarySearch(map.get(num), right);
        start = start < 0 ? ~start : start;
        end = end < 0 ? ~end - 1 : end;
        return end - start + 1 >= threshold ? num : -1;
    }

    class Node {
        int num, count;
        int start, end;
        Node left, right;

        public Node(int num, int count, int start, int end) {
            this.num = num;
            this.count = count;
            this.start = start;
            this.end = end;
        }
    }

    private Node build(int[] arr, int start, int end) {
        if (start > end) {
            return  null;
        }

        Node root = new Node(arr[start], 1, start, end);
        if (start == end) {
            return root;
        }
        int mid = start + (end - start >> 1);
        root.left = build(arr, start, mid);
        root.right = build(arr, mid + 1, end);
        if (root.left != null) {
            root.num = root.left.num;
            root.count = root.left.count;
        }
        if (root.right != null) {
            if (root.num == root.right.num) {
                root.count += root.right.count;
            } else if (root.count >= root.right.count) {
                root.count -= root.right.count;
            } else {
                root.num = root.right.num;
                root.count = root.right.count - root.count;
            }
        }
        return root;
    }

    private Node query(Node root, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start <= root.start && end >= root.end) {
            return root;
        }

        Node ans = new Node(-1, 0, start, end), leftAns = null, rightAns = null;
        int mid = root.start + (root.end - root.start >> 1);
        if (start <= mid) {
            leftAns = query(root.left, start, end);
        }
        if (mid < end) {
            rightAns = query(root.right, start, end);
        }
        if (leftAns != null && rightAns != null) {
            if (leftAns.num == rightAns.num) {
                ans.num = leftAns.num;
                ans.count = leftAns.count + rightAns.count;
            } else if (leftAns.count >= rightAns.count) {
                ans.num = leftAns.num;
                ans.count = leftAns.count - rightAns.count;
            } else {
                ans.num = rightAns.num;
                ans.count = rightAns.count - leftAns.count;
            }
            return ans;
        }
        return leftAns == null ? rightAns : leftAns;
    }

}

/**
 * Approach 2: Random Pick
 * For a given interval [left, right] and threshold, we would like to find the majority which occurs at least threshold times.
 * The problem also gives an inequality 2 * threshold > right - left + 1, which means the majority must occur more than half.
 * We denote the majority which satisfies the inequality as "more than half" majority.
 * (if the majority exists, it must be the only one)
 *
 * As the majority occurs more than half in the interval [left, right],
 * we will have the probability of more than 1/2 to find the "more than half" majority if we randomly pick a number in the interval.
 * Thus, if we randomly pick try_bound times, we will have the probability of (1-(1/2))**TRY_TIMES not to find the "more than half" majority.
 * The probability will be less than 1e-6 if we set TRY_TIMES as 20.
 * If we find nothing in TRY_TIMES times, we can claim that there is no "more than half" majority.
 *
 * We can use a Map<Integer, List<Integer>> to store the positions of each number in [left, right].
 * The key in the Map represents a number and the value represents the positions of that number in increasing order.
 * Thus we can count the occurrences of a number in a given interval [left, right] in O(log(n)) time by using binary search
 * (in C++ we can use lower_bound and upper_bound to simplify the code).
 *
 * Time Complexity: Build: O(n), Query: O(TRY_TIMES * log(n))
 * Space Complexity: O(n)
 */
class MajorityChecker {
   private Map<Integer, List<Integer>> map;
   private int[] arr;
   // In fact, for those test case 10 times is enough
   private static final int TRY_TIMES = 10;
   private Random random = new Random();

   public MajorityChecker(int[] arr) {
       this.arr = arr;
       map = new HashMap<>();
       for (int i = 0; i < arr.length; i++) {
           map.computeIfAbsent(arr[i], x -> new ArrayList<>()).add(i);
       }
   }

   public int query(int left, int right, int threshold) {
       for (int i = 0; i < TRY_TIMES; i++) {
           int num = arr[random.nextInt(right - left + 1) + left];
           if (getOccurrence(map.get(num), left, right) >= threshold) {
               return num;
           }
       }
       return -1;
   }

   private int getOccurrence(List<Integer> list, int left, int right) {
       /* int start = lowerBound(list, left);
       int end = upperBound(list, right);
       if (start == list.size()) {
           return -1;
       }*/
       // If you are familiar with Collections.binarySearch method, you can use it in this problem.
       int start = Collections.binarySearch(list, left);
       int end = Collections.binarySearch(list, right);
       start = start < 0 ? ~start : start;
       end = end < 0 ? ~end - 1 : end;
       return end - start + 1;
   }

   private int lowerBound(List<Integer>list, int target) {
       int left = 0, right = list.size();
       while (left < right) {
           int mid = left + (right - left >> 1);
           if (list.get(mid) >= target) {
               right = mid;
           } else {
               left = mid + 1;
           }
       }
       return left;
   }

   private int upperBound(List<Integer> list, int target) {
       int left = -1, right = list.size() - 1;
       while (left < right) {
           int mid = left + (right - left + 1 >> 1);
           if (list.get(mid) <= target) {
               left = mid;
           } else {
               right = mid - 1;
           }
       }
       return right;
   }

}

/**
 * Your MajorityChecker object will be instantiated and called as such:
 * MajorityChecker obj = new MajorityChecker(arr);
 * int param_1 = obj.query(left,right,threshold);
 */