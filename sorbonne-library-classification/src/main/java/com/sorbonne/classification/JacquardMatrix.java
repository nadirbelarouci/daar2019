package com.sorbonne.classification;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * This class create a Jacquard Matrix starting from simple text documents
 */
public class JacquardMatrix {
    public static final double THRESHOLD = 0.8;

    private Map<String, Map<String, Double>> matrix;
    private List<Path> files;

    public JacquardMatrix() {
        this.files = new ArrayList<>();
        matrix = new HashMap<>();
    }

    public Map<String, Map<String, Double>> getMatrix() {
        return matrix;
    }


    public LabeledDigraph getJacquardGraph() {
        LabeledDigraph labeledDigraph = new LabeledDigraph(matrix.keySet());

        matrix.keySet().forEach(i -> {
            Map<String, Double> iMap = matrix.get(i);
            iMap.entrySet().stream()
                    .filter(stringDoubleEntry -> stringDoubleEntry.getValue() <= THRESHOLD && stringDoubleEntry.getValue() != 0)
                    .map(Map.Entry::getKey)
                    .forEach(value -> labeledDigraph.addEdge(i, value));
        });

        return labeledDigraph;
    }

    /**
     * addFile method
     * @param path
     * @return
     */
    public Map<String, Map<String, Double>> addFile(Path path) {
        this.files.add(path);

        Jaccard jaccard = new Jaccard(path);

        Map<String, Double> collect = this.files.stream()
                .parallel()
                .map(Jaccard::new)
                .collect(Collectors.toMap(e -> e.getFilePath().getFileName().toString(),
                        e -> e.distance(jaccard.index())));

        matrix.forEach((key, value) -> value
                .put(path.getFileName().toString(), collect.get(key)));

        matrix.put(path.getFileName().toString(), collect);

        return matrix;
    }

    public Map<String, Map<String, Double>> addFiles(List<Path> paths) {
        paths.forEach(this::addFile);
        return matrix;
    }
}
