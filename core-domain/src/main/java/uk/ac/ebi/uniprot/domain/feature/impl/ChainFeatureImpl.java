package uk.ac.ebi.uniprot.domain.feature.impl;

import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.feature.ChainFeature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;

public class ChainFeatureImpl extends FeatureWithFeatureIdImpl implements ChainFeature {
    public static final Pattern FEATURE_ID_PATTERN = Pattern.compile("PRO_(\\d+)");   
    public ChainFeatureImpl(FeatureLocation location, String description,
             String featureId) {
        super(FeatureType.CHAIN, location, description, featureId, FEATURE_ID_PATTERN);
        
    }
}
