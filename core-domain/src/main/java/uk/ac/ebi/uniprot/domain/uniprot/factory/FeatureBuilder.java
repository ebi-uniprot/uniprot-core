package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl.SequenceReportImpl;

public final class FeatureBuilder {
	public static FeatureBuilder newInstance() {
		return new FeatureBuilder();
	}
	
	public static FeatureId createFeatureId(String value) {
		return new FeatureIdImpl(value);
	}
	public static FeatureDescription createFeatureDescription(String value) {
		return new FeatureDescriptionImpl(value);
	}
	
	public static SequenceReport createReport(List<String> value) {
		return new SequenceReportImpl(value);
	}
	
	public static SequenceReport createReport(String value) {
		if(Strings.isNullOrEmpty(value))
			return new SequenceReportImpl(Collections.emptyList());
		else
			return new SequenceReportImpl(Arrays.asList(value));
	}
	public static AlternativeSequence createAlternativeSequence(String originalSequence,
			List<String> alternativeSequences,SequenceReport report) {
		return new AlternativeSequenceImpl(originalSequence, alternativeSequences, report);
	}
	
	public static boolean hasAlternativeSequence(FeatureType featureType) {
		return AlternativeSequenceImpl.hasAlternativeSequence(featureType);
	}
	public FeatureImpl.Builder newFeatureBuilder(){
		return FeatureImpl.createBuilder();
	}
	
	
	
	private FeatureType type;
	private Range location;
	private FeatureDescription description;
	private FeatureId featureId;
	private AlternativeSequence alternativeSequence;
	private DBCrossReference<FeatureXDbType> dbXref;
	private List<Evidence> evidences;
	
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
