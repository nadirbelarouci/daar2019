package fr.sorbonne.gutenberg.core;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document implements Comparable<Document>, Serializable {
    @EqualsAndHashCode.Include
    private int id;
    private int c;

    @Override
    public int compareTo(Document o) {
        return Integer.compare(id, o.id);
    }


    @Override
    public String toString() {
        return "[" + id + ", " + c + "]";
    }
}
