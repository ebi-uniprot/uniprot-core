package org.uniprot.core.literature.impl;

import java.util.Objects;

import org.uniprot.core.citation.*;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureEntryImpl implements LiteratureEntry {

    private static final long serialVersionUID = -1925700851366460681L;
    private Citation citation;
    private LiteratureStatistics statistics;

    // no arg constructor for JSON deserialization
    LiteratureEntryImpl() {
        this(null, null);
    }

    public LiteratureEntryImpl(Citation citation, LiteratureStatistics statistics) {
        this.citation = citation;
        this.statistics = statistics;
    }

    @Override
    public Citation getCitation() {
        return citation;
    }

    @Override
    public LiteratureStatistics getStatistics() {
        return statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureEntryImpl that = (LiteratureEntryImpl) o;
        return Objects.equals(getCitation(), that.getCitation())
                && Objects.equals(getStatistics(), that.getStatistics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCitation(), getStatistics());
    }

    @Override
    public String toString() {
        return "LiteratureEntryImpl{" + "citation=" + citation + ", statistics=" + statistics + '}';
    }
}
