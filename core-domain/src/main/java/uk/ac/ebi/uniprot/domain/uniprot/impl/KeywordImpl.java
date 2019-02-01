package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KeywordImpl extends EvidencedValueImpl implements Keyword {

    public static final String DEFAULT_ACCESSION = "KW-00000";
    private String id;

    private KeywordImpl() {
        this("", "", Collections.emptyList());
    }

    public KeywordImpl(String id, String value, List<Evidence> evidences) {
        super(value, evidences);
        if (id == null || id.isEmpty()) {
            this.id = DEFAULT_ACCESSION;
        } else
            this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KeywordImpl keyword = (KeywordImpl) o;
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
