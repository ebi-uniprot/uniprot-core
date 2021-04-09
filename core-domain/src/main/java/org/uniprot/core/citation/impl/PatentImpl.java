package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class PatentImpl extends AbstractCitationImpl implements Patent {
    private static final long serialVersionUID = -7296535735360564782L;
    private final String patentNumber;

    // no arg constructor for JSON deserialization
    PatentImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null);
    }

    PatentImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            String patentNumber) {
        super(
                CitationType.PATENT,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate);
        this.patentNumber = Utils.emptyOrString(patentNumber);
        super.id = generateId();
    }

    @Override
    public String getPatentNumber() {
        return patentNumber;
    }

    @Override
    public boolean hasPatentNumber() {
        return Utils.notNullNotEmpty(this.patentNumber);
    }

    @Override
    protected String getHashInput() {
        String hashInput = super.getHashInput();
        if (hasPatentNumber()) {
            hashInput += PATENT_NUMBER_PREFIX + patentNumber;
        }
        return hashInput;
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
        return Objects.equals(this.patentNumber, other.patentNumber);
    }
}
