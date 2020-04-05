package fr.sorbonne.gutenberg.core;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public abstract class IndexWorker {
    protected ExecutorService executor;
    protected List<Index> indexes;
}
