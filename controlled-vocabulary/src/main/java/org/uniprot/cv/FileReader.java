package org.uniprot.cv;

import java.util.List;

public interface FileReader<T> {
    List<T> parse(String filename);
}
