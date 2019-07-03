package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.Objects;

public class PublicationDateImpl implements PublicationDate {
    private static final long serialVersionUID = 3232437573866835411L;
    private String value;

    private PublicationDateImpl() {

    }

    public PublicationDateImpl(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return Utils.notEmpty(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicationDateImpl that = (PublicationDateImpl) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "PublicationDateImpl{" +
                "value='" + value + '\'' +
                '}';
    }
}
