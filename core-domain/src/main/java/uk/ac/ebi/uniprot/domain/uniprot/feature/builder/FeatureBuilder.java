package uk.ac.ebi.uniprot.domain.uniprot.feature.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class FeatureBuilder implements Builder2<FeatureBuilder, Feature> {
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
        nonNullAddAll(evidences, this.evidences);
        return this;
    }

    public FeatureBuilder evidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }
}
