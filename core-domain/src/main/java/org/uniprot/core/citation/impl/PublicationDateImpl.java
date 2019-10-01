package org.uniprot.core.citation.impl;

import java.util.Objects;

import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.util.Utils;

public class PublicationDateImpl implements PublicationDate {
    private static final long serialVersionUID = 3232437573866835411L;
    private String value;

    private PublicationDateImpl() {}

    public PublicationDateImpl(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return Utils.notNullOrEmpty(this.value);
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
        return "PublicationDateImpl{" + "value='" + value + '\'' + '}';
    }
}
