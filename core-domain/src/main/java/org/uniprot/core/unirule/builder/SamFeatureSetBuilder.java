package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.impl.SamFeatureSetImpl;

public class SamFeatureSetBuilder implements Builder<SamFeatureSet> {

    private List<Condition> conditions;

    private List<Annotation> annotations;

    private SamTrigger samTrigger;

    public SamFeatureSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public SamFeatureSetBuilder conditionsSet(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
        return this;
    }

    public SamFeatureSetBuilder annotationsAdd(Annotation annotation) {
        addOrIgnoreNull(annotation, this.annotations);
        return this;
    }

    public SamFeatureSetBuilder annotationsSet(List<Annotation> annotations) {
        this.annotations = modifiableList(annotations);
        return this;
    }

    public SamFeatureSetBuilder samTrigger(SamTrigger samTrigger) {
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
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder();
        builder.conditionsSet(instance.getConditions())
                .annotationsSet(instance.getAnnotations())
                .samTrigger(instance.getSamTrigger());
        return builder;
    }
}
