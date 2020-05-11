package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

public class UniProtKBAcLineObject {

    private final UniProtKBAccession primaryAccession;
    private final List<UniProtKBAccession> secondAccessions;

    public UniProtKBAcLineObject(
            UniProtKBAccession primaryAccession, List<UniProtKBAccession> secondAccessions) {
        this.primaryAccession = primaryAccession;
        this.secondAccessions = secondAccessions;
    }

    public UniProtKBAccession getPrimaryAccession() {
        return primaryAccession;
    }

    public List<UniProtKBAccession> getSecondAccessions() {
        return secondAccessions;
    }
}
