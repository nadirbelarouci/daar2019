package com.sorbonne.classification;

import com.sorbonne.classification.Jaccard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JacquardSimpleTest {

    @Test
    public void index() throws URISyntaxException, IOException {
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("cat", 3);
        expectedResult.put("dog", 2);
        expectedResult.put("lion", 1);

        Jaccard jaccard = new Jaccard(Path.of(ClassLoader.getSystemResource("test1.txt").toURI()));
        var result = jaccard.index();

        jaccard.saveIndex();

        assertEquals(expectedResult, result);
    }

    @Test
    public void distance() throws URISyntaxException, IOException {
        Jaccard jaccard = new Jaccard(Path.of(ClassLoader.getSystemResource("test1.txt").toURI()));

        Jaccard jaccard1 = new Jaccard(Path.of(ClassLoader.getSystemResource("test2.txt").toURI()));

        double result = jaccard.distance(jaccard1.index());

        assertEquals(0.9, result, 0.1);
    }

    @Test
    public void indexDocument60kLines() throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();

        Jaccard jaccard = new Jaccard(Path.of(ClassLoader.getSystemResource("12381.txt").toURI()));

        Map<String, Integer> index = jaccard.index();
        System.out.println("index = " + index.size());

        long end = System.currentTimeMillis();

        System.out.println((end - start));

        jaccard.saveIndex();
    }

    @Test
    public void distanceBetweenSameDocument() throws URISyntaxException, IOException {
        Jaccard jaccard = new Jaccard(Path.of(ClassLoader.getSystemResource("test1.txt").toURI()));

        double result = jaccard.distance(jaccard.index());

        assertEquals(0, result, 0);
    }

    @Test
    public void distanceBetweenSameDocument1() throws URISyntaxException, IOException {
        Jaccard jaccard = new Jaccard(Path.of(ClassLoader.getSystemResource("test2.txt").toURI()));

        System.out.println("jaccard.index() = " + jaccard.index());
        double result = jaccard.distance(jaccard.index());

        assertEquals(0, result, 0);
    }

    @Test
    public void distanceBetween10039And10084Files() {
        Jaccard jaccard10039 = new Jaccard(Path.of("book-master/10039-0.txt"));
        Jaccard jaccard10084 = new Jaccard(Path.of("book-master/10084.txt.utf-8"));

        System.out.println(jaccard10039.distance(jaccard10084.index()));

    }
}
