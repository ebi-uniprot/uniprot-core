package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.List;

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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeywordImpl other = (KeywordImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
