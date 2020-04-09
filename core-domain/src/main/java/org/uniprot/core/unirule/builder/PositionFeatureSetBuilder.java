package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.PositionFeatureSetImpl;

public class PositionFeatureSetBuilder implements Builder<PositionFeatureSet> {

    private List<Condition> conditions;

    private List<Annotation> annotations;

    private List<PositionalFeature> positionalFeatures;

    private List<RuleException> ruleExceptions;

    private UniProtKBAccession uniProtKBAccession;

    private String alignmentSignature;

    private String tag;

    public PositionFeatureSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public PositionFeatureSetBuilder conditionsSet(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
        return this;
    }

    public PositionFeatureSetBuilder annotationsAdd(Annotation annotation) {
        addOrIgnoreNull(annotation, this.annotations);
        return this;
    }

    public PositionFeatureSetBuilder annotationsSet(List<Annotation> annotations) {
        this.annotations = modifiableList(annotations);
        return this;
    }

    public PositionFeatureSetBuilder positionalFeaturesAdd(PositionalFeature positionalFeature) {
        addOrIgnoreNull(positionalFeature, this.positionalFeatures);
        return this;
    }

    public PositionFeatureSetBuilder positionalFeaturesSet(
            List<PositionalFeature> positionalFeatures) {
        this.positionalFeatures = modifiableList(positionalFeatures);
        return this;
    }

    public PositionFeatureSetBuilder ruleExceptionsAdd(RuleException ruleException) {
        addOrIgnoreNull(ruleException, this.ruleExceptions);
        return this;
    }

    public PositionFeatureSetBuilder ruleExceptionsSet(List<RuleException> ruleExceptions) {
        this.ruleExceptions = modifiableList(ruleExceptions);
        return this;
    }

    public PositionFeatureSetBuilder uniProtKBAccession(UniProtKBAccession uniProtKBAccession) {
        this.uniProtKBAccession = uniProtKBAccession;
        return this;
    }

    public PositionFeatureSetBuilder alignmentSignature(String alignmentSignature) {
        this.alignmentSignature = alignmentSignature;
        return this;
    }

    public PositionFeatureSetBuilder tag(String tag) {
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
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder();
        builder.conditionsSet(instance.getConditions())
                .annotationsSet(instance.getAnnotations())
                .positionalFeaturesSet(instance.getPositionalFeatures())
                .ruleExceptionsSet(instance.getRuleExceptions())
                .uniProtKBAccession(instance.getUniProtKBAccession())
                .alignmentSignature(instance.getAlignmentSignature())
                .tag(instance.getTag());
        return builder;
    }
}
