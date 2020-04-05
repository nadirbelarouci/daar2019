package com.sorbonne.classification;

import java.util.HashMap;
import java.util.Map;

public class ClosenessCentrality {

    private final LabeledDigraph jacquardGraph;

    public ClosenessCentrality(LabeledDigraph labeledDigraph) {
        this.jacquardGraph = labeledDigraph;
    }


    public Map<String, Double> computeClosenessCentrality() {
        Map<String, Double> map = new HashMap<>();

        for (String v : jacquardGraph.getNodes()) {
            double sum = jacquardGraph.getNodes()
                    .stream()
                    .mapToDouble(dest -> jacquardGraph.shortestPath(v, dest))
                    .sum();

            map.put(v, (double) (jacquardGraph.getNodes().size() - 1) / sum);
        }

        return map;
    }
}
