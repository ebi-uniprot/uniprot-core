package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class ThesisImpl extends AbstractCitationImpl implements Thesis {
    private static final long serialVersionUID = -8487467468767628311L;
    private final String institute;
    private final String address;

    // no arg constructor for JSON deserialization
    ThesisImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null);
    }

    ThesisImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            String institute,
            String address) {
        super(
                CitationType.THESIS,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate);
        this.institute = Utils.emptyOrString(institute);
        this.address = Utils.emptyOrString(address);
    }

    @Override
    public String getInstitute() {
        return institute;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public boolean hasInstitute() {
        return Utils.notNullNotEmpty(this.institute);
    }

    @Override
    public boolean hasAddress() {
        return Utils.notNullNotEmpty(this.address);
    }

    @Override
    public String getHashInput() {
        String hashInput = super.getHashInput();
        if(hasInstitute()) {
            hashInput += institute;
        }
        if(hasAddress()) {
            hashInput += address;
        }
        return hashInput;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((institute == null) ? 0 : institute.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ThesisImpl other = (ThesisImpl) obj;
        return Objects.equals(this.address, other.address)
                && Objects.equals(this.institute, other.institute);
    }

    @Override
    public String toString() {
        return "ThesisImpl{" +
                "institute='" + institute + '\'' +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }
}
