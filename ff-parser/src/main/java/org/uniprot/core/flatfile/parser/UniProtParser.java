package org.uniprot.core.flatfile.parser;

import org.uniprot.core.uniprotkb.UniProtKBEntry;

import java.io.Serializable;

public interface UniProtParser extends Serializable {
    UniProtKBEntry parse(String entryff);
}
