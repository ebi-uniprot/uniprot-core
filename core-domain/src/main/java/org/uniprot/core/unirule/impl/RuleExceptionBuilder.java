package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.RuleExceptionAnnotation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class RuleExceptionBuilder implements Builder<RuleException> {

    protected String note;
    protected String category;
    protected RuleExceptionAnnotation annotation;
    protected List<UniProtKBAccession> accessions = new ArrayList<>();

    public RuleExceptionBuilder(String category) {
        this.category = category;
    }

    public @Nonnull RuleExceptionBuilder note(String note) {
        this.note = note;
        return this;
    }

    public @Nonnull RuleExceptionBuilder category(String category) {
        this.category = category;
        return this;
    }

    public @Nonnull RuleExceptionBuilder annotation(RuleExceptionAnnotation annotation) {
        this.annotation = annotation;
        return this;
    }

    public @Nonnull RuleExceptionBuilder accessionsAdd(UniProtKBAccession accession) {
        addOrIgnoreNull(accession, this.accessions);
        return this;
    }

    public @Nonnull RuleExceptionBuilder accessionsSet(List<UniProtKBAccession> accessions) {
        this.accessions = modifiableList(accessions);
        return this;
    }

    @Nonnull
    @Override
    public RuleException build() {
        return new RuleExceptionImpl(note, category, annotation, accessions);
    }

    public static @Nonnull RuleExceptionBuilder from(@Nonnull RuleException instance) {
        nullThrowIllegalArgument(instance);
        RuleExceptionBuilder builder = new RuleExceptionBuilder(instance.getCategory());
        builder.note(instance.getNote())
                .annotation(instance.getAnnotation())
                .accessionsSet(instance.getAccessions());
        return builder;
    }
}
