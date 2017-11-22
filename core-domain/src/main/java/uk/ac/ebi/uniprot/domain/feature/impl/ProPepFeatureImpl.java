package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.ProPepFeature;

import java.util.regex.Pattern;

public class ProPepFeatureImpl extends FeatureWithFeatureIdImpl implements ProPepFeature {
    private static final Pattern FEATURE_ID_PATTERN = Pattern.compile("PRO_(\\d+)");

    public ProPepFeatureImpl(FeatureLocation location, String description,
        String featureId) {
        super(FeatureType.PROPEP, location, description, featureId, FEATURE_ID_PATTERN);

    }

}
