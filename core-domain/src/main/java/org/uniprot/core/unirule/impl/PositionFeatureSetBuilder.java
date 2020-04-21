package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.*;

public class PositionFeatureSetBuilder<T> implements Builder<PositionFeatureSet> {

    private List<Condition> conditions = new ArrayList<>();

    private List<Annotation> annotations = new ArrayList<>();

    private List<PositionalFeature> positionalFeatures;

    private List<RuleException<T>> ruleExceptions = new ArrayList<>();

    private UniProtKBAccession uniProtKBAccession;

    private String alignmentSignature;

    private String tag;

    public PositionFeatureSetBuilder(PositionalFeature positionalFeature) {
        this.positionalFeatures = new ArrayList<>();
        addOrIgnoreNull(positionalFeature, this.positionalFeatures);
    }

    public PositionFeatureSetBuilder(List<PositionalFeature> positionalFeatures) {
        this.positionalFeatures = modifiableList(positionalFeatures);
    }

    public @Nonnull PositionFeatureSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder conditionsSet(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder annotationsAdd(Annotation annotation) {
        addOrIgnoreNull(annotation, this.annotations);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder annotationsSet(List<Annotation> annotations) {
        this.annotations = modifiableList(annotations);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder positionalFeaturesAdd(
            PositionalFeature positionalFeature) {
        addOrIgnoreNull(positionalFeature, this.positionalFeatures);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder positionalFeaturesSet(
            List<PositionalFeature> positionalFeatures) {
        this.positionalFeatures = modifiableList(positionalFeatures);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder ruleExceptionsAdd(RuleException ruleException) {
        addOrIgnoreNull(ruleException, this.ruleExceptions);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder ruleExceptionsSet(
            List<RuleException<T>> ruleExceptions) {
        this.ruleExceptions = modifiableList(ruleExceptions);
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder uniProtKBAccession(
            UniProtKBAccession uniProtKBAccession) {
        this.uniProtKBAccession = uniProtKBAccession;
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder alignmentSignature(String alignmentSignature) {
        this.alignmentSignature = alignmentSignature;
        return this;
    }

    public @Nonnull PositionFeatureSetBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }

    @Nonnull
    @Override
    public PositionFeatureSet build() {
        return new PositionFeatureSetImpl(
                conditions,
                annotations,
                positionalFeatures,
                ruleExceptions,
                uniProtKBAccession,
                alignmentSignature,
                tag);
    }

    public static @Nonnull PositionFeatureSetBuilder from(@Nonnull PositionFeatureSet instance) {
        nullThrowIllegalArgument(instance);
        PositionFeatureSetBuilder builder =
                new PositionFeatureSetBuilder(instance.getPositionalFeatures());
        builder.conditionsSet(instance.getConditions())
                .annotationsSet(instance.getAnnotations())
                .ruleExceptionsSet(instance.getRuleExceptions())
                .uniProtKBAccession(instance.getUniProtKBAccession())
                .alignmentSignature(instance.getAlignmentSignature())
                .tag(instance.getTag());
        return builder;
    }
}
