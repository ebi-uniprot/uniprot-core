package org.uniprot.core.flatfile.parser;

import org.uniprot.core.uniprotkb.UniProtKBEntry;

import java.util.Iterator;

public interface UniProtEntryIterator extends Iterator<UniProtKBEntry> {
    void setInput(
            String filename,
            String keywordFile,
            String diseaseFile,
            String accessionGoPubmedFile,
            String subcellularLocationFile);
}
