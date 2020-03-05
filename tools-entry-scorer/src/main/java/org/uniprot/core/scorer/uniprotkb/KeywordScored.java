package org.uniprot.core.scorer.uniprotkb;

import java.util.List;

import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 17:43:31 To change this template
 * use File | Settings | File Templates.
 */
public class KeywordScored implements HasScore {
    @SuppressWarnings("unused")
    private final Keyword keyword;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    public KeywordScored(Keyword keyword, List<EvidenceDatabase> evidenceDatabases) {
        this.keyword = keyword;
        this.evidenceDatabases = evidenceDatabases;
    }

    public KeywordScored(Keyword copy) {
        this(copy, null);
    }

    public double score() {
        return 0;
    }

    public Consensus consensus() {
        return Consensus.NUMBER;
    }
}
