package fr.sorbonne.gutenberg.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sorbonne.gutenberg.core.Document;
import fr.sorbonne.gutenberg.core.Index;
import fr.sorbonne.gutenberg.core.IndexReader;
import fr.sorbonne.gutenberg.core.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class MapDbIndexesConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MapDbIndexesConfiguration.class);

    private MapDbProperties mapDbProperties;
    //    private DB db;
    private List<Index> indexes;
    private ObjectMapper jsonMapper = new ObjectMapper();

    public MapDbIndexesConfiguration(MapDbProperties mapDbProperties) {
        this.mapDbProperties = mapDbProperties;
//        this.db = db;
        indexes = IntStream.range(0, mapDbProperties.getTablesCount())
                .mapToObj(i -> makeIndex(mapDbProperties.getDbPath() + i))
                .map(Index::new)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private ConcurrentHashMap<String, List<Document>> makeIndex(String indexName) {
        try {
            File file = new File(indexName);
            if (!file.createNewFile())
                return jsonMapper.readValue(new File(indexName), new TypeReference<ConcurrentHashMap<String, List<Document>>>() {});


            return new ConcurrentHashMap<>();
        } catch (Exception e) {
            logger.error("Error reading file {}", e.getMessage());

            return new ConcurrentHashMap<>();
        }

    }

    @Bean
    public IndexWriter indexWriter() {
        return new IndexWriter(Executors.newFixedThreadPool(16), indexes, mapDbProperties.getDbPath());
    }

    @Bean
    public IndexReader indexReader() {
        return new IndexReader(Executors.newFixedThreadPool(8), indexes);
    }

}
