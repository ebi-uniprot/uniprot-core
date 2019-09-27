package org.uniprot.core.flatfile.parser;

import org.uniprot.core.uniprot.UniProtEntry;

import java.util.Iterator;

public interface UniProtEntryIterator extends Iterator<UniProtEntry> {
    void setInput(
            String filename,
            String keywordFile,
            String diseaseFile,
            String accessionGoPubmedFile,
            String subcellularLocationFile);
}
