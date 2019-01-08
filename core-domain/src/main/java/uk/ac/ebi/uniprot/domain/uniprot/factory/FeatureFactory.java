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


public enum FeatureFactory {
    INSTANCE;

	public Feature createFeature(FeatureType type, Range location, String description, List<Evidence> evidences) {
		return new FeatureImpl(type, location, description, evidences);
	}
	
	public Feature createFeature(FeatureType type, Range location, String description, FeatureId featureId,
			List<Evidence> evidences) {
		return new FeatureImpl(type, location, description, featureId, evidences);
	}
	
	public Feature createFeature(FeatureType type, Range location, String description, FeatureId featureId,
			AlternativeSequence alternativeSequence, List<Evidence> evidences) {
		return new FeatureImpl(type, location, description, featureId, alternativeSequence, null, evidences);
	}
	
	public Feature createFeature(FeatureType type, Range location, String description, FeatureId featureId,
			AlternativeSequence alternativeSequence, DBCrossReference<FeatureXDbType> dbXref, List<Evidence> evidences) {
		return new FeatureImpl(type, location, description, featureId, alternativeSequence, dbXref, evidences);
	}
	
	public FeatureId createFeatureId(String value) {
		return new FeatureIdImpl(value);
	}
	public FeatureDescription createFeatureDescription(String value) {
		return new FeatureDescriptionImpl(value);
	}

	public AlternativeSequence createAlternativeSequence(String originalSequence,
			List<String> alternativeSequences) {
		return new AlternativeSequenceImpl(originalSequence, alternativeSequences);
	}
	public FeatureImpl.Builder newFeatureBuilder(){
		return FeatureImpl.createBuilder();
	}

}
