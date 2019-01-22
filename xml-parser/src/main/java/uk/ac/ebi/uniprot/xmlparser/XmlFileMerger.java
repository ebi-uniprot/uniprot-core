package uk.ac.ebi.uniprot.xmlparser;

import java.io.IOException;
import java.util.List;

public interface XmlFileMerger {
    void mergeFiles(String xmlFile, List<String> inputFiles) throws IOException;
}
