package com.sorbonne.classification;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class JacquardFilesTest {

    private static Stream<Path> getFiles() throws IOException {
        return Files.list(Paths.get("book-master"));
    }

    @ParameterizedTest(name = "#{index} - Indexing : {0}")
    @MethodSource("getFiles")
    @Execution(ExecutionMode.CONCURRENT)
    public void computeIndexAndSave(Path path) throws IOException {
        Jaccard jaccard = new Jaccard(path);
        jaccard.index();
        jaccard.saveIndex();
    }

    @ParameterizedTest(name = "#{index} - Distance : {0}")
    @MethodSource("getFiles")
    @Execution(ExecutionMode.CONCURRENT)
    public void computeDistance(Path path) throws IOException {
        Stream<Path> pathStream = getFiles();
        Map<String, Double> distances = new HashMap<>();

        Jaccard jaccard = new Jaccard(path);

        pathStream.parallel()
                .forEach(p -> {
                    Jaccard j = new Jaccard(p);
                    double distance = jaccard.distance(j.index());
                    distances.put(j.getFilePath().getFileName().toString(), distance);
                });

        File file = new File("distances/" + jaccard.getFilePath().getFileName().toString());
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(distances);
        s.close();
    }

}
