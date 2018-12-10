package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.util.Utils;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AlternativeSequenceImpl implements AlternativeSequence {
	private final String originalSequence;
	private final List<String> alternativeSequences;
	private final SequenceReport report;
	private static final Set<FeatureType> ALTERNATIVE_SEQUENCE_SET = EnumSet.of(FeatureType.CONFLICT,
			FeatureType.MUTAGEN, FeatureType.VARIANT, FeatureType.VAR_SEQ);

	@JsonCreator
	public AlternativeSequenceImpl(@JsonProperty("originalSequence") String originalSequence,
			@JsonProperty("alternativeSequences") List<String> alternativeSequences,
			@JsonProperty("report") SequenceReport report) {
		this.originalSequence = Utils.resetNull(originalSequence);
		this.alternativeSequences = Utils.unmodifierList(alternativeSequences);
	
		this.report = report;
	}

	@Override
	public String getOriginalSequence() {
		return this.originalSequence;
	}

	@Override
	public List<String> getAlternativeSequences() {
		return this.alternativeSequences;
	}

	@Override
	public SequenceReport getReport() {
		return report;
	}

	public static boolean hasAlternativeSequence(FeatureType type) {
		return ALTERNATIVE_SEQUENCE_SET.contains(type);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeSequences == null) ? 0 : alternativeSequences.hashCode());
		result = prime * result + ((originalSequence == null) ? 0 : originalSequence.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
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
		AlternativeSequenceImpl other = (AlternativeSequenceImpl) obj;
		if (alternativeSequences == null) {
			if (other.alternativeSequences != null)
				return false;
		} else if (!alternativeSequences.equals(other.alternativeSequences))
			return false;
		if (originalSequence == null) {
			if (other.originalSequence != null)
				return false;
		} else if (!originalSequence.equals(other.originalSequence))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		return true;
	}



	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class SequenceReportImpl implements SequenceReport {
		private final List<String> value;

		@JsonCreator
		public SequenceReportImpl(@JsonProperty("value") List<String> value) {
			if (value == null || value.isEmpty()) {
				this.value = Collections.emptyList();
			} else {
				this.value = Collections.unmodifiableList(value);
			}
		}

		@Override
		public List<String> getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
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
			SequenceReportImpl other = (SequenceReportImpl) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}

}
