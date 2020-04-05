package fr.sorbonne.gutenberg.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;

public class IndexWriter extends IndexWorker {
    private static final Logger logger = LoggerFactory.getLogger(IndexWriter.class);

    ScheduledExecutorService ses;

    private ObjectMapper jsonMapper = new JsonMapper();
    private String path;


    public IndexWriter(ExecutorService executor, List<Index> indexes, String path) {
        super(executor, indexes);
        this.path = path;
        ses = Executors.newScheduledThreadPool(1);


        Runnable task = () -> {
            for (int i = 0; i < indexes.size(); i++) {

                try {
                    if (indexes.get(i).isDirty()) {

                        jsonMapper.writeValue(Paths.get(path + i).toFile(), indexes.get(i).getIndex());
                        indexes.get(i).setDirty(false);
                    }
                } catch (IOException e) {
                    logger.error("Error saving file {}", e.getMessage());
                }
            }
        };
        ses.scheduleAtFixedRate(task, 2, 2, TimeUnit.MINUTES);

    }


    public void add(TokenizedBook book, int bookId) {
        int i = bookId % indexes.size();
        CompletableFuture.runAsync(() -> indexes.get(i).add(book, bookId), executor);
    }


}
