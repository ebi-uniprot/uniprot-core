package org.uniprot.core.flatfile.parser;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtkbEntry;

public interface UniProtParser extends Serializable {
    UniProtkbEntry parse(String entryff);
}
