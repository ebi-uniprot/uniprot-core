package org.uniprot.core.unirule.impl;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.RuleExceptionAnnotationType;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RuleExceptionImpl<T extends RuleExceptionAnnotationType> implements RuleException<T> {

    private static final long serialVersionUID = 1653869814974844099L;
    private String note;
    private String category;
    private T annotation;
    private List<UniProtKBAccession> accessions;

    RuleExceptionImpl() {
        this.accessions = Collections.emptyList();
    }

    RuleExceptionImpl(
            String note, String category, T annotation, List<UniProtKBAccession> accessions) {
        if (Utils.nullOrEmpty(category)) {
            throw new IllegalArgumentException(
                    "category is a mandatory parameter for a RuleException entry");
        }
        this.note = note;
        this.category = category;
        this.annotation = annotation;
        this.accessions = Utils.unmodifiableList(accessions);
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public T getAnnotation() {
        return annotation;
    }

    @Override
    public List<UniProtKBAccession> getAccessions() {
        return accessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleExceptionImpl<?> that = (RuleExceptionImpl<?>) o;
        return Objects.equals(note, that.note)
                && Objects.equals(category, that.category)
                && Objects.equals(annotation, that.annotation)
                && Objects.equals(accessions, that.accessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, category, annotation, accessions);
    }
}
