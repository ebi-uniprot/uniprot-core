package org.uniprot.core.flatfile.parser;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBEntry;

public interface UniProtParser extends Serializable {
    UniProtKBEntry parse(String entryff);
}
