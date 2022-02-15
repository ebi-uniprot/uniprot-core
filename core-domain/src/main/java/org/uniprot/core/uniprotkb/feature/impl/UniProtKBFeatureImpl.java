package org.uniprot.core.uniprotkb.feature.impl;

import java.util.*;

import org.uniprot.core.CrossReference;
import org.uniprot.core.feature.*;
import org.uniprot.core.feature.impl.AbstractFeature;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.*;
import org.uniprot.core.util.Utils;

public class UniProtKBFeatureImpl
        extends AbstractFeature<UniprotKBFeatureDatabase, UniprotKBFeatureType>
        implements UniProtKBFeature {

    private static final long serialVersionUID = -5308576363211194641L;
    private final FeatureId featureId;
    private final AlternativeSequence alternativeSequence;
    private final Ligand ligand;
    private final LigandPart ligandPart;

    // no arg constructor for JSON deserialization
    UniProtKBFeatureImpl() {
        this(null, null, null, null, null, null, null, null, null);
    }

    UniProtKBFeatureImpl(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FeatureDescription description,
            FeatureId featureId,
            AlternativeSequence alternativeSequence,
            CrossReference<UniprotKBFeatureDatabase> featureCrossReference,
            List<Evidence> evidences,
            Ligand ligand,
            LigandPart ligandPart) {
        super(type, location, description, featureCrossReference, evidences);
        this.featureId = featureId;
        this.alternativeSequence = alternativeSequence;
        this.ligand = ligand;
        this.ligandPart = ligandPart;
    }

    @Override
    public FeatureId getFeatureId() {
        return featureId;
    }

    @Override
    public AlternativeSequence getAlternativeSequence() {
        return alternativeSequence;
    }

    @Override
    public boolean hasAlternativeSequence() {
        return Utils.notNull(this.alternativeSequence)
                && UniprotKBAlternativeSequenceHelper.hasAlternativeSequence(getType());
    }

    @Override
    public boolean hasFeatureId() {
        return Utils.notNull(this.featureId) && FeatureIdImpl.hasFeatureId(getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniProtKBFeatureImpl that = (UniProtKBFeatureImpl) o;
        return Objects.equals(getFeatureId(), that.getFeatureId())
                && Objects.equals(getAlternativeSequence(), that.getAlternativeSequence())
                && Objects.equals(ligand, that.ligand)
                && Objects.equals(ligandPart, that.ligandPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), getFeatureId(), alternativeSequence, ligand, ligandPart);
    }

    @Override
    public Ligand getLigand() {
        return this.ligand;
    }

    @Override
    public LigandPart getLigandPart() {
        return this.ligandPart;
    }

    @Override
    public boolean hasLigand() {
        return Utils.notNull(this.ligand) && (getType() == UniprotKBFeatureType.BINDING);
    }

    @Override
    public boolean hasLigandPart() {
        return Utils.notNull(this.ligandPart) && (getType() == UniprotKBFeatureType.BINDING);
    }
}
