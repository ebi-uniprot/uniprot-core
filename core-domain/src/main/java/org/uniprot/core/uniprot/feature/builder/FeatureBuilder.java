package org.uniprot.core.uniprot.feature.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.feature.*;
import org.uniprot.core.uniprot.feature.impl.FeatureDescriptionImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureIdImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureImpl;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class FeatureBuilder implements Builder<FeatureBuilder, Feature> {
    private FeatureType type;
    private Range location;
    private FeatureDescription description;
    private FeatureId featureId;
    private AlternativeSequence alternativeSequence;
    private DBCrossReference<FeatureXDbType> dbXref;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public Feature build() {
        return new FeatureImpl(type,
                               location,
                               description,
                               featureId,
                               alternativeSequence,
                               dbXref,
                               evidences);
    }

    @Override
    public FeatureBuilder from(Feature instance) {
        return new FeatureBuilder()
                .evidences(instance.getEvidences())
                .description(instance.getDescription())
                .type(instance.getType())
                .location(instance.getLocation())
                .featureId(instance.getFeatureId())
                .alternativeSequence(instance.getAlternativeSequence())
                .dbXref(instance.getDbXref());
    }

    public FeatureBuilder type(FeatureType type) {
        this.type = type;
        return this;
    }

    public FeatureBuilder location(Range location) {
        this.location = location;
        return this;
    }

    public FeatureBuilder description(String description) {
        this.description = new FeatureDescriptionImpl(description);
        return this;
    }

    public FeatureBuilder description(FeatureDescription description) {
        this.description = description;
        return this;
    }

    public FeatureBuilder featureId(FeatureId featureId) {
        this.featureId = featureId;
        return this;
    }

    public FeatureBuilder featureId(String featureId) {
        this.featureId = new FeatureIdImpl(featureId);
        return this;
    }

    public FeatureBuilder alternativeSequence(AlternativeSequence alternativeSequence) {
        this.alternativeSequence = alternativeSequence;
        return this;
    }

    public FeatureBuilder dbXref(DBCrossReference<FeatureXDbType> dbXref) {
        this.dbXref = dbXref;
        return this;
    }

    public FeatureBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public FeatureBuilder evidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }
}
