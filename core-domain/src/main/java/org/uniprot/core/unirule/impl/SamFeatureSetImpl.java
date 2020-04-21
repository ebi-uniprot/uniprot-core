package org.uniprot.core.unirule.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.util.Utils;

public class SamFeatureSetImpl implements SamFeatureSet {

    private static final long serialVersionUID = 1476324127705635288L;
    private List<Condition> conditions;

    private List<Annotation> annotations;

    private SamTrigger samTrigger;

    SamFeatureSetImpl() {
        this.conditions = Collections.emptyList();
        this.annotations = Collections.emptyList();
    }

    SamFeatureSetImpl(
            List<Condition> conditions, List<Annotation> annotations, SamTrigger samTrigger) {

        if (Objects.isNull(samTrigger)) {
            throw new IllegalArgumentException(
                    "samTrigger is a mandatory param for SamFeatureSet entry.");
        }

        this.conditions = Utils.unmodifiableList(conditions);
        this.annotations = Utils.unmodifiableList(annotations);
        this.samTrigger = samTrigger;
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
    public SamTrigger getSamTrigger() {
        return samTrigger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SamFeatureSetImpl that = (SamFeatureSetImpl) o;
        return Objects.equals(conditions, that.conditions)
                && Objects.equals(annotations, that.annotations)
                && Objects.equals(samTrigger, that.samTrigger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditions, annotations, samTrigger);
    }
}
