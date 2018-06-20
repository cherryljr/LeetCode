/*
Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number).
Given some queries, return the answers. If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0.
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries ,
where equations.size() == values.size(), and the values are positive. This represents the equations.
Return vector<double>.

According to the example above:
equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].

The input is always valid.
You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 */

/**
 * Approach: Union Find
 * 与 分数调查 基本相同，只不过将二者的关系由 差值 转换成了 比值。其余部分相同。
 * rank[a] 表示：aFather * rank[a] = a
 * 然后基于此进行关系分析，得出 union 和 find 时的比值关系。
 * 路径压缩查询时，做法与 分数查询 基本相同，只不过换成了乘法。（记得事先记住 value 的 preFather 即可）
 * Union 时有：
 *  rank[a] * aFather = a
 *  rank[b] * bFather = b
 *  quotient * b = a
 * 因为 rank[aFather] * aFather = bFather 则可以推算出：
 *  rank[aFather] = a / b * (rank[b] / rank[a])
 *  => rank[aFather] = quotient * rank[b] / rank[a]
 *
 * 时间复杂度：O(e + q) e代表节点个数，q代表询问次数
 * 空间复杂度：O(e)
 */
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        HashSet<String> set = new HashSet<>();
        for (String[] equation : equations) {
            set.add(equation[0]);
            set.add(equation[1]);
        }
        UnionFind uf = new UnionFind(set);
        for (int i = 0; i < equations.length; i++) {
            uf.union(equations[i][0], equations[i][1], values[i]);
        }

        double[] rst = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String aFather = uf.compressedFind(queries[i][0]);
            String bFather = uf.compressedFind(queries[i][1]);
            if (aFather.equals("#") || bFather.equals("#") || !aFather.equals(bFather)) {
                rst[i] = -1.0;
            } else {
                rst[i] = uf.rank.get(queries[i][0]) / uf.rank.get(queries[i][1]);
            }
        }
        return rst;
    }

    class UnionFind {
        HashMap<String, String> parent;
        HashMap<String, Double> rank;

        UnionFind(HashSet<String> set) {
            this.parent = new HashMap<>();
            this.rank = new HashMap<>();

            for (String str : set) {
                parent.put(str, str);
                rank.put(str, 1.0);
            }
        }

        public String compressedFind(String value) {
            // Can't find the value
            if (!parent.containsKey(value)) {
                return "#";
            }
            if (!value.equals(parent.get(value))) {
                String pre = parent.get(value);
                parent.put(value, compressedFind(parent.get(value)));
                rank.put(value, rank.get(pre) * rank.get(value));
            }
            return parent.get(value);
        }

        public void union(String a, String b, double quotient) {
            String aFather = compressedFind(a);
            String bFather = compressedFind(b);
            if (aFather.equals(bFather)) {
                return;
            }
            parent.put(aFather, bFather);
            rank.put(aFather, quotient * rank.get(b) / rank.get(a));
        }
    }
}