package uk.ac.ebi.uniprot.domain.feature.impl;

import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.PeptideFeature;

public class PeptideFeatureImpl extends FeatureWithFeatureIdImpl implements PeptideFeature {
    private static final Pattern FEATURE_ID_PATTERN = Pattern.compile("PRO_(\\d+)");

    public PeptideFeatureImpl(FeatureLocation location, String description,
        String featureId) {
        super(FeatureType.PEPTIDE, location, description, featureId, FEATURE_ID_PATTERN);

    }
}
