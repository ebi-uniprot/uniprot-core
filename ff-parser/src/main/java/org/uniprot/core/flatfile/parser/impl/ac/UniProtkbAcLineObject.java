package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtkbAccession;

public class UniProtkbAcLineObject {

    private final UniProtkbAccession primaryAccession;
    private final List<UniProtkbAccession> secondAccessions;

    public UniProtkbAcLineObject(
            UniProtkbAccession primaryAccession, List<UniProtkbAccession> secondAccessions) {
        this.primaryAccession = primaryAccession;
        this.secondAccessions = secondAccessions;
    }

    public UniProtkbAccession getPrimaryAccession() {
        return primaryAccession;
    }

    public List<UniProtkbAccession> getSecondAccessions() {
        return secondAccessions;
    }
}
