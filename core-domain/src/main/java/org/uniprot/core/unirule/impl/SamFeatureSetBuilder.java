package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;

public class SamFeatureSetBuilder implements Builder<SamFeatureSet> {

    private List<Condition> conditions = new ArrayList<>();

    private List<Annotation> annotations = new ArrayList<>();

    private SamTrigger samTrigger;

    public SamFeatureSetBuilder(SamTrigger samTrigger) {
        this.samTrigger = samTrigger;
    }

    public @Nonnull SamFeatureSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public @Nonnull SamFeatureSetBuilder conditionsSet(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
        return this;
    }

    public @Nonnull SamFeatureSetBuilder annotationsAdd(Annotation annotation) {
        addOrIgnoreNull(annotation, this.annotations);
        return this;
    }

    public @Nonnull SamFeatureSetBuilder annotationsSet(List<Annotation> annotations) {
        this.annotations = modifiableList(annotations);
        return this;
    }

    public @Nonnull SamFeatureSetBuilder samTrigger(SamTrigger samTrigger) {
        this.samTrigger = samTrigger;
        return this;
    }

    @Nonnull
    @Override
    public SamFeatureSet build() {
        return new SamFeatureSetImpl(conditions, annotations, samTrigger);
    }

    public static @Nonnull SamFeatureSetBuilder from(@Nonnull SamFeatureSet instance) {
        nullThrowIllegalArgument(instance);
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(instance.getSamTrigger());
        builder.conditionsSet(instance.getConditions()).annotationsSet(instance.getAnnotations());
        return builder;
    }
}
