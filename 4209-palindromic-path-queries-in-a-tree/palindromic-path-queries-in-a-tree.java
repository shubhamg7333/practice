import java.util.*;

class Solution {
    int LOG;
    int[][] up;
    int[] depth, tin, tout, bit, val;
    int timer;
    List<Integer>[] adj;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        bit = new int[n + 2];
        val = new int[n];

        Arrays.fill(up[0], -1);
        buildTree(n);

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                int p = up[k - 1][i];
                up[k][i] = p == -1 ? -1 : up[k - 1][p];
            }
        }

        for (int i = 0; i < n; i++) {
            val[i] = 1 << (s.charAt(i) - 'a');
            add(tin[i] + 1, val[i]);
            add(tout[i] + 2, val[i]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int nv = 1 << (p[2].charAt(0) - 'a');
                int diff = val[u] ^ nv;

                val[u] = nv;
                add(tin[u] + 1, diff);
                add(tout[u] + 2, diff);
            } else {
                int v = Integer.parseInt(p[2]);
                int l = lca(u, v);

                int mask = prefix(tin[u] + 1)
                         ^ prefix(tin[v] + 1)
                         ^ val[l];

                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    void buildTree(int n) {
        int[] stack = new int[n];
        int[] iter = new int[n];
        int top = 0;

        stack[top++] = 0;
        up[0][0] = -1;
        tin[0] = timer++;

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);

                if (v == up[0][u]) continue;

                up[0][v] = u;
                depth[v] = depth[u] + 1;
                tin[v] = timer++;
                stack[top++] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int t = u;
            u = v;
            v = t;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0)
                u = up[k][u];
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    void add(int i, int x) {
        for (; i < bit.length; i += i & -i)
            bit[i] ^= x;
    }

    int prefix(int i) {
        int x = 0;
        for (; i > 0; i -= i & -i)
            x ^= bit[i];
        return x;
    }
}
