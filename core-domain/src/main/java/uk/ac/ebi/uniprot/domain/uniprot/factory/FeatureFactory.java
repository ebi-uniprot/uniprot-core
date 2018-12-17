package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl.SequenceReportImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



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
	
	public SequenceReport createReport(List<String> value) {
		return new SequenceReportImpl(value);
	}
	
	public SequenceReport createReport(String value) {
		if(value == null || value.isEmpty())
			return new SequenceReportImpl(Collections.emptyList());
		else
			return new SequenceReportImpl(Arrays.asList(value));
	}
	public AlternativeSequence createAlternativeSequence(String originalSequence,
			List<String> alternativeSequences,SequenceReport report) {
		return new AlternativeSequenceImpl(originalSequence, alternativeSequences, report);
	}
	public FeatureImpl.Builder newFeatureBuilder(){
		return FeatureImpl.createBuilder();
	}
}
