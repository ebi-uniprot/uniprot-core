package org.uniprot.core.unirule.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.*;
import org.uniprot.core.util.Utils;

public class PositionFeatureSetImpl<T> implements PositionFeatureSet {

    private static final long serialVersionUID = -30144214793146371L;
    private List<Condition> conditions;

    private List<Annotation> annotations;

    private List<PositionalFeature> positionalFeatures;

    private List<RuleException<T>> ruleExceptions;

    private UniProtKBAccession uniProtKBAccession;

    private String alignmentSignature;

    private String tag;

    PositionFeatureSetImpl() {
        this.conditions = Collections.emptyList();
        this.annotations = Collections.emptyList();
        this.positionalFeatures = Collections.emptyList();
        this.ruleExceptions = Collections.emptyList();
    }

    PositionFeatureSetImpl(
            List<Condition> conditions,
            List<Annotation> annotations,
            List<PositionalFeature> positionalFeatures,
            List<RuleException<T>> ruleExceptions,
            UniProtKBAccession uniProtKBAccession,
            String alignmentSignature,
            String tag) {

        if (Utils.nullOrEmpty(positionalFeatures)) {
            throw new IllegalArgumentException(
                    "positionalFeatures is a mandatory parameter for a PositionFeatureSet entry.");
        }
        this.conditions = Utils.unmodifiableList(conditions);
        this.annotations = Utils.unmodifiableList(annotations);
        this.positionalFeatures = Utils.unmodifiableList(positionalFeatures);
        this.ruleExceptions = Utils.unmodifiableList(ruleExceptions);
        this.uniProtKBAccession = uniProtKBAccession;
        this.alignmentSignature = alignmentSignature;
        this.tag = tag;
    }

    @Override
    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public List<PositionalFeature> getPositionalFeatures() {
        return positionalFeatures;
    }

    @Override
    public List<RuleException<T>> getRuleExceptions() {
        return ruleExceptions;
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniProtKBAccession;
    }

    @Override
    public String getAlignmentSignature() {
        return alignmentSignature;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionFeatureSetImpl that = (PositionFeatureSetImpl) o;
        return Objects.equals(conditions, that.conditions)
                && Objects.equals(annotations, that.annotations)
                && Objects.equals(positionalFeatures, that.positionalFeatures)
                && Objects.equals(ruleExceptions, that.ruleExceptions)
                && Objects.equals(uniProtKBAccession, that.uniProtKBAccession)
                && Objects.equals(alignmentSignature, that.alignmentSignature)
                && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                conditions,
                annotations,
                positionalFeatures,
                ruleExceptions,
                uniProtKBAccession,
                alignmentSignature,
                tag);
    }
}
