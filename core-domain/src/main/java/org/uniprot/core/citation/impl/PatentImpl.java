package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class PatentImpl extends AbstractCitationImpl implements Patent {
    private static final long serialVersionUID = 7708555945786333862L;
    private final String patentNumber;

    private PatentImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null);
    }

    public PatentImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<DBCrossReference<CitationXrefType>> citationXrefs,
            String title,
            PublicationDate publicationDate,
            String patentNumber) {
        super(CitationType.PATENT, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.patentNumber = Utils.nullToEmpty(patentNumber);
    }

    @Override
    public String getPatentNumber() {
        return patentNumber;
    }

    @Override
    public boolean hasPatentNumber() {
        return Utils.notEmpty(this.patentNumber);
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
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        PatentImpl other = (PatentImpl) obj;
        if (patentNumber == null) {
            return other.patentNumber == null;
        } else return patentNumber.equals(other.patentNumber);
    }
}
