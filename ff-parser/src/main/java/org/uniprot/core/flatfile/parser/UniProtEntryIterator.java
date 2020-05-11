package org.uniprot.core.flatfile.parser;

import java.util.Iterator;

import org.uniprot.core.uniprotkb.UniProtKBEntry;

public interface UniProtEntryIterator extends Iterator<UniProtKBEntry> {
    void setInput(
            String filename,
            String keywordFile,
            String diseaseFile,
            String accessionGoPubmedFile,
            String subcellularLocationFile);
}
