package com.sorbonne.classification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JacquardMatrixFilesTest {
    static JacquardMatrix jacquardMatrix;

    @BeforeAll
    public static void before() {
        jacquardMatrix = new JacquardMatrix();
    }

    private static Stream<Path> getFiles() throws IOException {
        return Files.list(Paths.get("books-master"))
                .sorted()
                .limit(5);
    }

    @ParameterizedTest(name = "#{index} - Distance : {0}")
    @MethodSource("getFiles")
    @Execution(ExecutionMode.SAME_THREAD)
    public void computeJacquardMatrix(Path file) {
        Map<String, Map<String, Double>> stringMapMap = jacquardMatrix.addFile(file);

        System.out.println(stringMapMap);
    }


}
