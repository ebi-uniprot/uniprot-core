package org.uniprot.core.flatfile.parser;

import java.io.Serializable;

import org.uniprot.core.uniprot.UniProtEntry;

public interface UniProtParser extends Serializable {
    UniProtEntry parse(String entryff);
}
