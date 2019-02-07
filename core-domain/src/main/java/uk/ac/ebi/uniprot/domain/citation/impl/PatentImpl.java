package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.List;

import static java.util.Collections.emptyList;

public class PatentImpl extends AbstractCitationImpl implements Patent {
    private final String patentNumber;

    private PatentImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null);
    }

    public PatentImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                      String title, PublicationDate publicationDate, String patentNumber) {
        super(CitationType.PATENT, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.patentNumber = Utils.nullToEmpty(patentNumber);
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
