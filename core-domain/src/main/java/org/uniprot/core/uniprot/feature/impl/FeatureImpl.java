package org.uniprot.core.uniprot.feature.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.feature.*;
import org.uniprot.core.util.Utils;

public class FeatureImpl implements Feature {
    private static final long serialVersionUID = -5308576363211194641L;
    private FeatureType type;
    private Range location;
    private FeatureDescription description;
    private FeatureId featureId;
    private AlternativeSequence alternativeSequence;
    private DBCrossReference<FeatureXDbType> dbXref;
    private List<Evidence> evidences;

    public FeatureImpl(FeatureType type, Range location, String description, List<Evidence> evidences) {
        this(type, location, description, null, evidences);

    }

    public FeatureImpl(FeatureType type, Range location, String description, FeatureId featureId,
                       List<Evidence> evidences) {
        this(type, location, description, featureId, null, null, evidences);

    }

    public FeatureImpl(FeatureType type, Range location, String description, FeatureId featureId,
                       AlternativeSequence alternativeSequence, DBCrossReference<FeatureXDbType> dbXref, List<Evidence> evidences) {
        this(type, location, new FeatureDescriptionImpl(description),
             featureId, alternativeSequence, dbXref, evidences
        );
    }

    private FeatureImpl() {
        evidences = Collections.emptyList();
    }

    public FeatureImpl(FeatureType type,
                       Range location,
                       FeatureDescription description,
                       FeatureId featureId,
                       AlternativeSequence alternativeSequence,
                       DBCrossReference<FeatureXDbType> dbXref,
                       List<Evidence> evidences) {

        this.type = type;
        this.location = location;
        this.description = description;
        this.featureId = featureId;
        this.alternativeSequence = alternativeSequence;
        this.dbXref = dbXref;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
    }

    @Override
    public FeatureType getType() {
        return type;
    }

    @Override
    public Range getLocation() {
        return location;
    }

    @Override
    public FeatureDescription getDescription() {
        return description;
    }

    @Override
    public FeatureId getFeatureId() {
        return featureId;
    }

    @Override
    public boolean hasFeatureId() {
        return this.featureId != null && FeatureIdImpl.hasFeatureId(type);
    }


    @Override
    public AlternativeSequence getAlternativeSequence() {
        return alternativeSequence;
    }

    @Override
    public boolean hasAlternativeSequence() {
        return AlternativeSequenceHelper.hasAlternativeSequence(type);
    }

    @Override
    public boolean hasDbXref() {
        return this.dbXref != null;
    }

    @Override
    public DBCrossReference<FeatureXDbType> getDbXref() {
        return dbXref;
    }

    @Override
    public boolean hastLocation() {
        return this.location != null;
    }

    @Override
    public boolean hasDescription() {
        return this.description != null && this.description.hasValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternativeSequence == null) ? 0 : alternativeSequence.hashCode());
        result = prime * result + ((dbXref == null) ? 0 : dbXref.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeatureImpl other = (FeatureImpl) obj;
        if (alternativeSequence == null) {
            if (other.alternativeSequence != null)
                return false;
        } else if (!alternativeSequence.equals(other.alternativeSequence))
            return false;
        if (dbXref == null) {
            if (other.dbXref != null)
                return false;
        } else if (!dbXref.equals(other.dbXref))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (featureId == null) {
            if (other.featureId != null)
                return false;
        } else if (!featureId.equals(other.featureId))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        return type == other.type;
    }
}