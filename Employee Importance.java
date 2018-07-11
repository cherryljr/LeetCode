/*
You are given a data structure of employee information, which includes the employee's unique id,
his importance value and his direct subordinates' id.

For example, employee 1 is the leader of employee 2, and employee 2 is the leader of employee 3.
They have importance value 15, 10 and 5, respectively. Then employee 1 has a data structure like [1, 15, [2]],
and employee 2 has [2, 10, [3]], and employee 3 has [3, 5, []].
Note that although employee 3 is also a subordinate of employee 1, the relationship is not direct.

Now given the employee information of a company, and an employee id,
you need to return the total importance value of this employee and all his subordinates.

Example 1:
Input: [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
Output: 11
Explanation:
Employee 1 has importance value 5, and he has two direct subordinates: employee 2 and employee 3.
They both have importance value 3. So the total importance value of employee 1 is 5 + 3 + 3 = 11.

Note:
One employee has at most one direct leader and may have several subordinates.
The maximum number of employees won't exceed 2000.
 */

/**
 * Approach 1: DFS
 * 利用一个 HashMap 建立各个员工id的映射关系后直接进行 Graph Search 即可。
 * 这里提供了 DFS 和 BFS 两种做法。
 *
 * 详细解析：
 *  http://zxi.mytechroad.com/blog/searching/leetcode-690-employee-importance/
 */

/*
// Employee info
class Employee {
    // It's the unique id of each node;
    // unique id of this employee
    public int id;
    // the importance value of this employee
    public int importance;
    // the id of direct subordinates
    public List<Integer> subordinates;
};
*/
class Solution {
    public int getImportance(List<Employee> employees, int id) {
        HashMap<Integer, Employee> graph = new HashMap<>();
        for (Employee employee : employees) {
            graph.put(employee.id, employee);
        }

        return dfs(graph, id);
    }

    private int dfs(HashMap<Integer, Employee> graph, int id) {
        Employee e = graph.get(id);
        int sum = e.importance;
        for (Integer subordinate : e.subordinates) {
            sum += dfs(graph, subordinate);
        }
        return sum;
    }
}

/**
 * Approach 2: BFS
 */
class Solution {
    public int getImportance(List<Employee> employees, int id) {
        HashMap<Integer, Employee> graph = new HashMap<>();
        for (Employee employee : employees) {
            graph.put(employee.id, employee);
        }

        Queue<Employee> queue = new LinkedList<>();
        queue.offer(graph.get(id));
        int sum = 0;
        while (!queue.isEmpty()) {
            Employee curr = queue.poll();
            sum += curr.importance;
            for (Integer subordinate : curr.subordinates) {
                queue.offer(graph.get(subordinate));
            }
        }
        return sum;
    }
}