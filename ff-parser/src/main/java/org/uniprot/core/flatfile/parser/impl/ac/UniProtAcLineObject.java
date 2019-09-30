package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;

import org.uniprot.core.uniprot.UniProtAccession;

public class UniProtAcLineObject {

    private final UniProtAccession primaryAccession;
    private final List<UniProtAccession> secondAccessions;

    public UniProtAcLineObject(
            UniProtAccession primaryAccession, List<UniProtAccession> secondAccessions) {
        this.primaryAccession = primaryAccession;
        this.secondAccessions = secondAccessions;
    }

    public UniProtAccession getPrimaryAccession() {
        return primaryAccession;
    }

    public List<UniProtAccession> getSecondAccessions() {
        return secondAccessions;
    }
}
