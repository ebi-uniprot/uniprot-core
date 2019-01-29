package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.domain.util.Utils;

public class PatentImpl extends AbstractCitationImpl implements Patent {
    private final String patentNumber;

    private PatentImpl() {
        this(new PatentBuilder());
    }

    public PatentImpl(PatentBuilder builder) {
        super(CitationType.PATENT, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
        this.patentNumber = Utils.nullToEmpty(builder.getPatentNumber());
    }

    @Override
    public String getPatentNumber() {
        return patentNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((patentNumber == null) ? 0 : patentNumber.hashCode());
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
        PatentImpl other = (PatentImpl) obj;
        if (patentNumber == null) {
            if (other.patentNumber != null)
                return false;
        } else if (!patentNumber.equals(other.patentNumber))
            return false;
        return true;
    }
}
