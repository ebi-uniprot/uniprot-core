package org.uniprot.core.xml;

import java.io.IOException;
import java.util.List;

public interface XmlFileMerger {
    void mergeFiles(String xmlFile, List<String> inputFiles) throws IOException;
}
