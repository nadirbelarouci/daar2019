package com.sorbonne.classification;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Jaccard {
    public static final String INDEXES_DIR = "indexes";
    private Path filePath;
    private Map<String, Integer> index;

    public Jaccard(Path file) {
        this.filePath = file;
        index = new HashMap<>();
        readIndexIfExist();
    }

    /**
     * Get the index of a document
     *
     * @return index(D) = {(m, k) : le mot m apparait k fois dans la liste l(D)}∪{(m, 0) : le mot m n’apparait pas dans la liste l(D)}.
     */
    public Map<String, Integer> index() {
        if (!this.index.isEmpty())
            return this.index;

        try {
            this.index = Files.lines(this.filePath)
                    .flatMap(line -> stream(line.split("[^A-Za-z]")))
                    .filter(val -> !val.equals(""))
                    .collect(Collectors.groupingBy(String::toLowerCase, Collectors.summingInt(e -> 1)));
            return this.index;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get com.sorbonne.classification.Jaccard distance between two indexes
     *
     * @param other
     * @return d(D1, D2) =SUM[(m,k1)∈index(D1)∧(m,k2)∈index(D2) max(k1, k2) − min(k1, k2)] / SUM[(m,k1)∈index(D1)∧(m,k2)∈index(D2) max(k1, k2)]
     */
    public double distance(Map<String, Integer> other) {
        Fraction fraction = new Fraction();

        Stream.of(this.index().keySet(), other.keySet())
                .flatMap(Collection::stream)
                .parallel()
                .collect(Collectors.toSet())
                .forEach(key -> {
                    int r = Math.abs(this.index.getOrDefault(key, 0) - other.getOrDefault(key, 0));
                    fraction.addToNumerator(r);
                    int d = Math.max(this.index.getOrDefault(key, 0), other.getOrDefault(key, 0));
                    fraction.addToDenominator(d);
                });

        return fraction.divide();
    }

    public void saveIndex() throws IOException {
        File file = new File("indexes", this.filePath.getFileName().toString());
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        this.index().forEach((key, value) -> {
            try {
                bufferedWriter.write(key + " " + value + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bufferedWriter.close();
    }

    private void readIndexIfExist() {
        File file = new File(INDEXES_DIR + "/" + this.filePath.getFileName().toString());
        if (file.exists()) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                index = bufferedReader.lines()
                        .map(val -> val.split(" "))
                        .collect(Collectors.toMap(val -> val[0], val -> Integer.valueOf(val[1])));
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Path getFilePath() {
        return filePath;
    }
}
