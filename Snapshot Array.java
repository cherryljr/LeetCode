/*
Implement a SnapshotArray that supports the following interface:
    ·SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element equals 0.
    ·void set(index, val) sets the element at the given index to be equal to val.
    ·int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
    ·int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id

Example 1:
Input: ["SnapshotArray","set","snap","set","get"]
[[3],[0,5],[],[0,6],[0,0]]
Output: [null,null,0,null,5]
Explanation:
    SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
    snapshotArr.set(0,5);  // Set array[0] = 5
    snapshotArr.snap();  // Take a snapshot, return snap_id = 0
    snapshotArr.set(0,6);
    snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5

Constraints:
    1. 1 <= length <= 50000
    2. At most 50000 calls will be made to set, snap, and get.
    3. 0 <= index < length
    4. 0 <= snap_id < (the total number of times we call snap())
    5. 0 <= val <= 10^9
 */

/**
 * Approach: TreeMap (Binary Search)
 * According to the data scale, we can see that the limitation lies in space complexity rather than time complexity.
 * So instead of copy the whole array, we can only record the changes of set.
 *
 * Explanation
 * The main idea is, the whole array can be large, and we may take the snap tons of times.
 * Instead of record the history of the whole array, we will record the history of each cell.
 * And this is the minimum space that we need to record all information.
 *
 * For each snapshotArray[i], we will record its history, with a snap_id and a its value.
 * When we want to get the value in history, just binary search the time point.
 * (In java, we can use TreeMap to do this)
 *
 * Complexity
 *  Time Complexity: O(logS)
 *  Space complexity: O(S)
 *  where S is the number of set called.
 *  SnapshotArray(int length) is O(N) time
 *  set(int index, int val) is O(log(snap_id)) in Java
 *  snap() is O(1)
 *  get(int index, int snap_id) is O(log(snap_id))
 */
class SnapshotArray {

    TreeMap<Integer, Integer>[] snapshotArray;
    int snap_id;

    public SnapshotArray(int length) {
        this.snapshotArray = new TreeMap[length];
        this.snap_id = 0;
        for (int i = 0; i < length; i++) {
            snapshotArray[i] = new TreeMap<>();
            snapshotArray[i].put(0, 0);
        }
    }

    public void set(int index, int val) {
        snapshotArray[index].put(snap_id, val);
    }

    public int snap() {
        return snap_id++;
    }

    public int get(int index, int snap_id) {
        return snapshotArray[index].floorEntry(snap_id).getValue();
    }
}

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */
