package uk.ac.ebi.uniprot.domain.uniprot.factory;


import java.util.List;


import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;


public final class FeatureBuilder {

    private FeatureType type;
    private Range location;
    private FeatureDescription description;
    private FeatureId featureId;
    private AlternativeSequence alternativeSequence;
    private DBCrossReference<FeatureXDbType> dbXref;
    private List<Evidence> evidences;

    public static FeatureBuilder newInstance() {
        return new FeatureBuilder();
    }

    public static FeatureId createFeatureId(String value) {
        return new FeatureIdImpl(value);
    }

    public static FeatureDescription createFeatureDescription(String value) {
        return new FeatureDescriptionImpl(value);
    }


    public static AlternativeSequence createAlternativeSequence(String originalSequence,
                                                                List<String> alternativeSequences) {
        return new AlternativeSequenceImpl(originalSequence, alternativeSequences);
    }

    public static boolean hasAlternativeSequence(FeatureType featureType) {
        return AlternativeSequenceImpl.hasAlternativeSequence(featureType);
    }

    public FeatureImpl.Builder newFeatureBuilder() {
        return FeatureImpl.createBuilder();
    }

    public FeatureBuilder featureType(FeatureType type) {
        this.type = type;
        return this;
    }

    public Feature build() {
        return new FeatureImpl(type, location, description, featureId, alternativeSequence, dbXref, evidences);
    }

    public FeatureBuilder location(Range location) {
        this.location = location;
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

    public FeatureBuilder alternativeSequence(AlternativeSequence alternativeSequence) {
        this.alternativeSequence = alternativeSequence;
        return this;
    }

    public FeatureBuilder dbXref(DBCrossReference<FeatureXDbType> dbXref) {
        this.dbXref = dbXref;
        return this;
    }

    public FeatureBuilder evidences(List<Evidence> evidences) {
        this.evidences = evidences;
        return this;
    }
}
