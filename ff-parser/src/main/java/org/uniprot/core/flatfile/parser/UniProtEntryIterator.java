package org.uniprot.core.flatfile.parser;

import java.util.Iterator;

import org.uniprot.core.uniprotkb.UniProtkbEntry;

public interface UniProtEntryIterator extends Iterator<UniProtkbEntry> {
    void setInput(
            String filename,
            String keywordFile,
            String diseaseFile,
            String accessionGoPubmedFile,
            String subcellularLocationFile);
}
