package org.uniprot.core.flatfile.writer;

import java.util.List;

public interface FFLine {
    List<String> lines();

    void add(FFLine line);

    void add(String line);

    boolean isEmpty();
}
