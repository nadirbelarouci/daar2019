package com.sorbonne.classification;

import org.junit.Test;

import static org.junit.Assert.*;

public class BFSTest {

    @Test
    public void shortestPath(){
        Digraph digraph = new Digraph(5);

        digraph.addEdge(0, 1);
        digraph.addEdge(0, 2);
        digraph.addEdge(0, 3);

        digraph.addEdge(1, 0);
        digraph.addEdge(1, 3);

        digraph.addEdge(2, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(2, 4);

        digraph.addEdge(3, 0);
        digraph.addEdge(3, 1);
        digraph.addEdge(3, 2);

        digraph.addEdge(4, 2);

        BFS bfs = new BFS(digraph);

        assertEquals(1, bfs.shortestPath(0, 3));
        assertEquals(1, bfs.shortestPath(0, 2));
        assertEquals(2, bfs.shortestPath(1, 2));

        assertEquals(2, bfs.shortestPath(0, 4));

        assertEquals(3, bfs.shortestPath(1, 4));

        assertEquals(0, bfs.shortestPath(1, 1));
    }


}
