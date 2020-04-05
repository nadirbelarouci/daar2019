package com.sorbonne.classification;

import com.sorbonne.classification.ClosenessCentrality;
import com.sorbonne.classification.LabeledDigraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example inspired from
 * https://www.ebi.ac.uk/training/online/course/network-analysis-protein-interaction-data-introduction/building-and-analysing-ppins-1
 */
public class ClosenessCentralityTest {

    private LabeledDigraph initGraph() {
        LabeledDigraph labeledDigraph = new LabeledDigraph(List.of("A", "B", "C", "D", "E"));

        labeledDigraph.addEdge("A", "B");
        labeledDigraph.addEdge("A", "C");

        labeledDigraph.addEdge("B", "A");
        labeledDigraph.addEdge("B", "C");
        labeledDigraph.addEdge("B", "D");
        labeledDigraph.addEdge("B", "E");

        labeledDigraph.addEdge("C", "A");
        labeledDigraph.addEdge("C", "B");

        labeledDigraph.addEdge("D", "B");

        labeledDigraph.addEdge("E", "B");

        return labeledDigraph;
    }

    public Map<String, Double> initExpectedResult() {
        Map<String, Double> map = new HashMap<>();
        map.put("A", 0.666);
        map.put("B", 1.0);
        map.put("C", 0.666);
        map.put("D", 0.571);
        map.put("E", 0.571);

        return map;
    }

    @Test
    public void computeClosenessCentrality() {
        Map<String, Double> expectedResult = initExpectedResult();

        ClosenessCentrality closenessCentrality = new ClosenessCentrality(initGraph());
        Map<String, Double> map = closenessCentrality.computeClosenessCentrality();

        Assertions.assertEquals(expectedResult.size(), map.size());
        for (Map.Entry<String, Double> expectedValue : expectedResult.entrySet()) {
            Double actualValue = map.get(expectedValue.getKey());

            Assertions.assertEquals(expectedValue.getValue(), actualValue, 0.1);
        }
    }

}
