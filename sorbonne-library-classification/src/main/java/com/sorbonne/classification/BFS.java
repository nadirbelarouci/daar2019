package com.sorbonne.classification;

import java.util.LinkedList;

public class BFS {
    private final Digraph digraph;

    public BFS(Digraph digraph) {
        this.digraph = digraph;
    }

    public int shortestPath(int src, int dest) {
        if (src == dest)
            return 0;
        int pred[] = new int[digraph.V()];
        int dist[] = new int[digraph.V()];

        // a queue to maintain queue of vertices  whose
        // adjacency list is to be scanned as per normal
        // DFS algorithm
        LinkedList<Integer> queue = new LinkedList<>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[digraph.V()];

        // initially all vertices are unvisited
        // so visited[i] for all is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < digraph.V(); i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // standard com.sorbonne.classification.BFS algorithm
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Integer i : digraph.adj(u)) {
                if (!visited[i]) {
                    visited[i] = true;
                    dist[i] = dist[u] + 1;
                    pred[i] = u;
                    queue.add(i);

                    //We stop com.sorbonne.classification.BFS when we find
                    //destination
                    if (i == dest) {
                        return dist[i];
                    }
                }
            }
        }

        return -1;
    }


}
