package org.uniprot.core.cv;

import java.util.List;

public interface FileReader<T> {
    List<T> parse(String filename);
}
