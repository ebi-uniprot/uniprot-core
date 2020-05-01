package org.uniprot.core.unirule.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.RuleExceptionAnnotationType;
import org.uniprot.core.unirule.RuleException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.uniprot.core.util.Utils.*;

public class RuleExceptionBuilder<T extends RuleExceptionAnnotationType> implements Builder<RuleException<T>> {

    protected String note;
    protected String category;
    protected T annotation;
    protected List<UniProtKBAccession> accessions = new ArrayList<>();

    public RuleExceptionBuilder(String category) {
        this.category = category;
    }

    public @Nonnull RuleExceptionBuilder<T> note(String note) {
        this.note = note;
        return this;
    }

    public @Nonnull RuleExceptionBuilder<T> category(String category) {
        this.category = category;
        return this;
    }

    public @Nonnull RuleExceptionBuilder<T> annotation(T annotation) {
        this.annotation = annotation;
        return this;
    }

    public @Nonnull RuleExceptionBuilder<T> accessionsAdd(UniProtKBAccession accession) {
        addOrIgnoreNull(accession, this.accessions);
        return this;
    }

    public @Nonnull RuleExceptionBuilder<T> accessionsSet(List<UniProtKBAccession> accessions) {
        this.accessions = modifiableList(accessions);
        return this;
    }

    @Nonnull
    @Override
    public RuleException<T> build() {
        return new RuleExceptionImpl(note, category, annotation, accessions);
    }

    public static @Nonnull <T extends RuleExceptionAnnotationType> RuleExceptionBuilder<T> from(@Nonnull RuleException<T> instance) {
        nullThrowIllegalArgument(instance);
        RuleExceptionBuilder<T> builder = new RuleExceptionBuilder<>(instance.getCategory());
        builder.note(instance.getNote());
        builder.annotation(instance.getAnnotation());
        builder.accessionsSet(instance.getAccessions());
        return builder;
    }
}
