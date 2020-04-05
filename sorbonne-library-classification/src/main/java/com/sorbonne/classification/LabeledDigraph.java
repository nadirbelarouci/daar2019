package com.sorbonne.classification;

import java.util.*;

public class LabeledDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private Digraph digraph;
    private Map<String, Integer> stringToInt;
    private Map<Integer, String> intToString;

    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public LabeledDigraph(Collection<String> V){
        stringToInt = new HashMap<>();
        intToString = new HashMap<>();
        List<String> collect = new ArrayList<>(V);

        for (int i = 0; i < V.size(); i++){
            stringToInt.put(collect.get(i), i);
            intToString.put(i, collect.get(i));
        }

        digraph = new Digraph(V.size());
    }

    /**
     * Initializes a new digraph that is a deep copy of the specified digraph.
     *
     * @param labeledDigraph the digraph to copy
     * @throws IllegalArgumentException if {@code G} is {@code null}
     */
    public LabeledDigraph(LabeledDigraph labeledDigraph) {
        intToString = labeledDigraph.intToString;
        stringToInt = labeledDigraph.stringToInt;

        digraph = new Digraph(labeledDigraph.digraph);
    }

    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int V() {
        return digraph.V();
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int E() {
        return digraph.E();
    }


    /**
     * Adds the directed edge v→w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(String v, String w) {
        try {
            digraph.addEdge(stringToInt.get(v), stringToInt.get(w));
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Vertex doesn't exist in graph");
        }
    }


    /**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<String> adj(String v) {
        Iterable<Integer> adj = digraph.adj(stringToInt.get(v));
        List<String> list = new java.util.ArrayList<>();
        adj.forEach(i -> list.add(intToString.get(i)));

        return list;
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(String v) {
        return ((List) adj(v)).size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(String v) {
        return digraph.indegree(stringToInt.get(v));
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V() + " vertices, " + E() + " edges " + NEWLINE);
        for (int v = 0; v < V(); v++) {
            s.append(String.format("%s: ", intToString.get(v)));
            for (int w : digraph.adj(v)) {
                s.append(String.format("%s ", intToString.get(w)));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public int shortestPath(String src, String dest) {
        BFS bfs = new BFS(this.digraph);

        return bfs.shortestPath(stringToInt.get(src), stringToInt.get(dest));
    }

    public Set<String> getNodes() {
        return this.stringToInt.keySet();
    }
}
